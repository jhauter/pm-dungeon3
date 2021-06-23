package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

/**
 * Contains information and functionality required to controll
 * the entire boss fight, including spawning, despawning specific projectileSpawners for
 * projectile patterns and seperate entities "created" by the boss during the encounter
 * Thus Boss should not be used directly
 */

public abstract class BossBattle extends Entity {

    /*
        Represents the current state of the fight independent from the stage queue.
     */
    public DungeonLevel level;

    public BossBattle(DungeonLevel level) {
        this.level = level;
        level.spawnEntity(this);
    }

    public static Boss boss;

    protected BossPhase currentPhase;

    public Boss createBoss(BossBuilder boss) {
        return new Boss(boss);
    }

    public void start() {
        prepareArea();
        var builder = getBossInformation();
        boss = createBoss(builder);
        UIBossBar bossUi = new UIBossBar();
        bossUi.setBoss(boss);

        DungeonStart.getDungeonMain().getUIManager().addUI(bossUi);
        boss.setPosition(getInitialBossPosition());

        this.level.spawnEntity(boss);
        currentPhase = getCurrentPhase();
        currentPhase.init(this);
    }

    public void prepareArea() {
        var enemies = this.level.getAllEntitiesOf(Creature.class,
                DungeonStart.getDungeonMain().getPlayer());
        enemies.forEach(Creature::setDead);
    }

    public void onBossBattleEnd() {

    }

    @Override
    protected void updateLogic() {
        switchPhase();
        currentPhase.run();
    }

    @Override
    protected void updateEnd() {
        super.updateEnd();
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        super.onSpawn(level);
    }

    abstract protected void switchPhase();

    abstract public Vector2 getInitialBossPosition();
    abstract protected BossBuilder getBossInformation();
    abstract protected BossPhase getCurrentPhase();

    protected BoundingBox getInitBoundingBox() {
        return new BoundingBox(0, 0, 0.5F, 5);
    }

    public boolean useAnimationHandler() {
        return false;
    }

    public boolean isInvisible() {
        return true;
    }
}
