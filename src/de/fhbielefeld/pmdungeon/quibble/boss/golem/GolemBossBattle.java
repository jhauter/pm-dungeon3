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

    public GolemBossBattle(DungeonLevel level) {
        super(level);

        phases = new HashMap<>();
        phases.put("start", new GolemStartPhase());
        phases.put("second", new GolemDemoSecondPhase());

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
                System.out.println("huhhuuu");
                var nextPhase= phases.get("second");
                currentPhase.cleanStage();
                currentPhase.active = false;

                currentPhase = nextPhase;
                getCurrentPhase().init(this);
                //currentPhase.init(this);

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
        var animInfo = new AnimationHandlerImpl.AnimationInfo("idle", 4,
                0, 0.5f, 1,
                4, "assets/textures/entity/golem/golem_idle_test.png");
        animList.add(animInfo);
        var bossBuilder = new BossBuilder();
        CreatureStats bossStats = new CreatureStats();
        bossStats.setStat(CreatureStatsAttribs.HEALTH, 3000);

        bossBuilder = bossBuilder
                .setRenderScale(new Vector2(2f,1.2f))
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
