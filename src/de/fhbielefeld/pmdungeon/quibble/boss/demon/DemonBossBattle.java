package de.fhbielefeld.pmdungeon.quibble.boss.demon;

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

public class DemonBossBattle extends BossBattle {
    private HashMap<String, BossPhase> phases;
    private int counter;
    private boolean finished = false;
    /**
     * @param level Current level
     */
    public DemonBossBattle(DungeonLevel level) {
        super(level);
        phases = new HashMap<>();
        phases.put("transform", new DemonTransformPhase(this));
        phases.put("second", new DemonSecondPhase(this));
        currentPhase = phases.get("transform");
        boss.physBuff = 10;
        boss.magBuff = 3;
    }

    @Override
    protected void updateLogic() {
        super.updateLogic();
    }

    @Override
    public void start() {
        super.start();
        boss.setRenderOffset(0, 2);
        boss.growBoundingBox(4,9);
    }


    @Override
    protected void updateEnd(){
        super.updateEnd();
        }

    @Override
    protected void onBossBattleEnd() {
        if(!finished) {
            finished = true;
            //DungeonStart.getDungeonMain().getUICredits().play();
        }
    }

    @Override
    protected boolean isBattleOver() {
        return boss.getCurrentHealth() <= 0;
    }

    @Override
    protected void switchPhase() {
        counter++;
        if(currentPhase == phases.get("transform") && counter >= 200) {
            counter = 0;
            var nextPhase = phases.get("second");
            currentPhase.cleanStage();
            currentPhase.active = false;

            currentPhase = nextPhase;
            getCurrentPhase().init();

        }
    }

    @Override
    public Vector2 getInitialBossPosition() {
        return initialPosition;
    }

    @Override
    protected BossBuilder getBossInformation() {
        var animList = new ArrayList<AnimationHandlerImpl.AnimationInfo>();
        var idle = new AnimationHandlerImpl.AnimationInfo("idle",
                12, 160,0.1f, 12, 32, "assets/textures/entity/boss_slime/sheet.png");
        var transform = new AnimationHandlerImpl.AnimationInfo("transform", 32, 96, 0.2f, 12, 32,"assets/textures/entity/boss_slime/sheet.png");
        var slam = new AnimationHandlerImpl.AnimationInfo("slam", 18, 224, 0.1f, 12, 32, "assets/textures/entity/boss_slime/sheet.png");
        var shoot = new AnimationHandlerImpl.AnimationInfo("shoot", 6, 288, 0.1f, 12, 32, "assets/textures/entity/boss_slime/sheet.png");
        animList.add(idle);
        animList.add(transform);
        animList.add(slam);
        animList.add(shoot);

        var bossBuilder = new BossBuilder();
        CreatureStats bossStats = new CreatureStats();
        bossStats.setStat(CreatureStatsAttribs.HEALTH, 80000);

        bossBuilder = bossBuilder
                .setRenderScale(new Vector2(8,6))
                .setAnimation(animList)
                .setMaxHP(bossStats);

        return bossBuilder;
    }

    @Override
    protected BossPhase getCurrentPhase() {
        return currentPhase;
    }
}
