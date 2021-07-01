package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
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
    private long timePassed;

    public GolemBossBattle(DungeonLevel level) {
        super(level);

        phases = new HashMap<>();
        phases.put("start", new GolemStartPhase(this));
        phases.put("second", new GolemDemoSecondPhase(this));
        phases.put("third", new GolemDemoThirdPhase(this));
        phases.put("last", new GolemLastPhase(this));

        currentPhase = phases.get("start");
        boss.physBuff = 10;
        boss.magBuff = 8;
    }

    @Override
    protected void onBossBattleEnd() {
        boss.playAttackAnimation("death", false, 100);
    }

    @Override
    protected boolean isBattleOver() {
            return boss.getCurrentHealth() <= 0;
    }

    @Override
    protected void switchPhase() {
        var hpPercent = boss.getCurrentHealth() / boss.getMaxStats().getStat(CreatureStatsAttribs.HEALTH);
        if(hpPercent <= 0.75) {
            if(currentPhase.active && currentPhase == phases.get("start")) {
                timePassed = 0;
                var nextPhase= phases.get("second");
                currentPhase.cleanStage();
                currentPhase.active = false;

                currentPhase = nextPhase;
                getCurrentPhase().init();
                //currentPhase.init(this);
            }
        }
        if(currentPhase.active && currentPhase == phases.get("second")) {
            if(timePassed >= 1000) {
                timePassed = 0;
                var nextPhase = phases.get("third");
                currentPhase.cleanStage();
                currentPhase.active = false;

                currentPhase = nextPhase;
                getCurrentPhase().init();
            }
        }

        if(hpPercent <= 0.25 && currentPhase == phases.get("third")) {
            timePassed = 0;
            var nextPhase = phases.get("last");
            currentPhase.cleanStage();
            currentPhase.active = false;

            currentPhase = nextPhase;
            getCurrentPhase().init();
        }
    }

    @Override
    protected void updateLogic() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            DungeonStart.getDungeonMain().getPlayer().heal(10000);
        }
        super.updateLogic();
        timePassed++;
    }

    @Override
    protected BossBuilder getBossInformation() {
        var animList = new ArrayList<AnimationHandlerImpl.AnimationInfo>();
        var animSpawn = new AnimationHandlerImpl.AnimationInfo("spawn", 8, 10, 0.8f, 10, 10,
                "assets/textures/entity/golem/Character_sheet.png");

        var animInfoIdle = new AnimationHandlerImpl.AnimationInfo("idle", 4,
                0, 0.5f, 10,
                10, "assets/textures/entity/golem/Character_sheet.png");
        var animShoot = new AnimationHandlerImpl.AnimationInfo("shoot", 7, 50, 0.5f, 10,
                10, "assets/textures/entity/golem/Character_sheet.png");

        var animInfoGroundSlam = new AnimationHandlerImpl.AnimationInfo("slam", 7, 40, 0.35f, 10, 10,
                "assets/textures/entity/golem/Character_sheet.png");
        var animInfoShield = new AnimationHandlerImpl.AnimationInfo("shield", 7, 30, 0.3f, 10, 10,
                "assets/textures/entity/golem/Character_sheet.png");
        var animInfoShieldIdle = new AnimationHandlerImpl.AnimationInfo("shield_idle", 1, 36, 0.3f, 10, 10,
                "assets/textures/entity/golem/Character_sheet.png");

        var animInfoArmTransform = new AnimationHandlerImpl.AnimationInfo("arm_transform", 9, 20, 0.4f, 10, 10,
                "assets/textures/entity/golem/Character_sheet.png");

        var animPanic = new AnimationHandlerImpl.AnimationInfo("panic", 10, 70, 0.4f, 10,10, "assets/textures/entity/golem/Character_sheet.png");
        var animPanicIdle = new AnimationHandlerImpl.AnimationInfo("panic_idle", 2, 78, 0.4f, 10,10, "assets/textures/entity/golem/Character_sheet.png");
        var animDeath = new AnimationHandlerImpl.AnimationInfo("death", 4, 80, 0.4f, 10,10, "assets/textures/entity/golem/Character_sheet.png");

        animList.add(animInfoIdle);
        animList.add(animSpawn);
        animList.add(animShoot);
        animList.add(animInfoGroundSlam);
        animList.add(animInfoShield);
        animList.add(animInfoShieldIdle);
        animList.add(animInfoArmTransform);
        animList.add(animPanic);
        animList.add(animPanicIdle);
        animList.add(animDeath);

        var bossBuilder = new BossBuilder();
        CreatureStats bossStats = new CreatureStats();
        bossStats.setStat(CreatureStatsAttribs.HEALTH, 9000);

        bossBuilder = bossBuilder
                .setRenderScale(new Vector2(6,5))
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
