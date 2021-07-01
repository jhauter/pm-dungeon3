package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBuilder;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.boss.demon.DemonBossBattle;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

import java.util.ArrayList;
import java.util.HashMap;

public class SlimeBossBattle extends BossBattle {
    private HashMap<String, BossPhase> phases;
    private boolean shouldEnd = false;
    /**
     * @param level Current level
     */
    public SlimeBossBattle(DungeonLevel level) {
        super(level);
        phases = new HashMap<>();
        phases.put("start", new SlimeFirstPhase(this));
        phases.put("second", new SlimeSecondPhase(this));
        currentPhase = phases.get("start");
    }
    @Override
    protected void updateLogic() {
        super.updateLogic();
    }

    @Override
    public void start() {
        super.start();
        boss.setRenderOffset(0, 5.6f);
    }

    @Override
    protected void onBossBattleEnd() {
    }

    @Override
    protected boolean isBattleOver() {
        return shouldEnd;
    }

    @Override
    protected void switchPhase() {
        if(currentPhase.active && boss.getCurrentHealth() <= 75 && currentPhase == phases.get("start")) {
            var nextPhase = phases.get("second");
            currentPhase.cleanStage();
            currentPhase.active = false;
            currentPhase = nextPhase;
            getCurrentPhase().init();
        }

        if(!shouldEnd && currentPhase == phases.get("second") && boss.getCurrentHealth() <= 25) {
            currentPhase.cleanStage();
            currentPhase.active = false;
            var demon = new DemonBossBattle(this.level);
            demon.start(false, boss.getPosition());

            this.shouldEnd = true;
        }
    }

    @Override
    public Vector2 getInitialBossPosition() {
        return new Vector2(9, 28);
    }

    @Override
    protected BossBuilder getBossInformation() {
        var animList = new ArrayList<AnimationHandlerImpl.AnimationInfo>();
        var idle = new AnimationHandlerImpl.AnimationInfo("idle", 6, 0,
                0.1f, 12, 32, "assets/textures/entity/boss_slime/sheet.png");
        var slam = new AnimationHandlerImpl.AnimationInfo("slam", 8, 32, 0.3f, 12, 32,
                "assets/textures/entity/boss_slime/sheet.png");


        animList.add(idle);
        animList.add(slam);

        var bossBuilder = new BossBuilder();
        CreatureStats bossStats = new CreatureStats();
        bossStats.setStat(CreatureStatsAttribs.HEALTH, 8000);

        bossBuilder = bossBuilder
                .setRenderScale(new Vector2(14,12))
                .setAnimation(animList)
                .setMaxHP(bossStats);
        return bossBuilder;
    }

    @Override
    protected BossPhase getCurrentPhase() {
        return currentPhase;
    }

}
