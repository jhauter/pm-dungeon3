package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBuilder;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

import java.util.ArrayList;
import java.util.HashMap;

public class GolemBossBattle extends BossBattle {

    private HashMap<String, BossPhase> phases;
    private BossPhase currentPhase;
    private long timePassed;

    public GolemBossBattle(DungeonLevel level) {
        super(level);

        phases = new HashMap<>();
        phases.put("start", new GolemStartPhase());
        phases.put("second", new GolemDemoSecondPhase());
        phases.put("third", new GolemDemoThirdPhase());
        currentPhase = phases.get("start");
    }
    @Override
    protected boolean isBattleOver() {
            return boss.getCurrentHealth() <= 0;
    }

    @Override
    protected void switchPhase() {
        var hpPercent = boss.getCurrentHealth() / boss.getMaxStats().getStat(CreatureStatsAttribs.HEALTH);
        if(hpPercent <= 0.75) {
            if(currentPhase.active && currentPhase != phases.get("second")) {
                timePassed = 0;
                var nextPhase= phases.get("second");
                currentPhase.cleanStage();
                currentPhase.active = false;

                currentPhase = nextPhase;
                getCurrentPhase().init(this);
                //currentPhase.init(this);
            }
        }
        if(currentPhase.active && currentPhase == phases.get("second")) {
            if(timePassed >= 1500) {
                timePassed = 0;
                var nextPhase = phases.get("third");
                currentPhase.cleanStage();
                currentPhase.active = false;

                currentPhase = nextPhase;
                getCurrentPhase().init(this);
            }
        }
    }

    @Override
    protected void updateLogic() {
        super.updateLogic();
    }

    @Override
    protected BossBuilder getBossInformation() {
        var animList = new ArrayList<AnimationHandlerImpl.AnimationInfo>();
        var animInfoIdle = new AnimationHandlerImpl.AnimationInfo("idle", 4,
                0, 0.5f, 1,
                4, "assets/textures/entity/golem/golem_idle_test.png");
        var animInfoGroundSlam = new AnimationHandlerImpl.AnimationInfo("slam", 7, 0, 0.1f, 1, 7,
                "assets/textures/entity/golem/golem_slam.png");
        var animInfoShield = new AnimationHandlerImpl.AnimationInfo("shield", 7, 0, 0.8f, 1, 7,
                "assets/textures/entity/golem/golem_shield.png");
        var animInfoShieldIdle = new AnimationHandlerImpl.AnimationInfo("shield_idle", 1, 0, 0.3f, 1, 1,
                "assets/textures/entity/golem/golem_shield_idle.png");

        var animInfoArmTransform = new AnimationHandlerImpl.AnimationInfo("arm_transform", 9, 0, 0.3f, 1, 9,
                "assets/textures/entity/golem/golem_arm_transform.png");

        animList.add(animInfoIdle);
        animList.add(animInfoGroundSlam);
        animList.add(animInfoShield);
        animList.add(animInfoShieldIdle);

        var bossBuilder = new BossBuilder();
        CreatureStats bossStats = new CreatureStats();
        bossStats.setStat(CreatureStatsAttribs.HEALTH, 3000);

        bossBuilder = bossBuilder
                .setRenderScale(new Vector2(1f,1f))
                .setAnimation(animList)
                .setMaxHP(bossStats);
        return bossBuilder;
    }

    @Override
    protected BossPhase getCurrentPhase() {
        return currentPhase;
    }

    @Override
    public Vector2 getInitialBossPosition() {
        return new Vector2(9, 28);
    }


}
