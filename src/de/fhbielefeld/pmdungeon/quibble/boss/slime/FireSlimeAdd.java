package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.PuddleFireAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.GroundEffectBossAction;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class FireSlimeAdd extends NPC {
    private int puddleCD = 60;
    private int puddleCDCounter = 0;
    private int explosionCD = 300;
    private int explosionCDCounter = 0;
    private boolean shouldDespawn = false;

    private DungeonLevel level;
    public FireSlimeAdd(float x, float y) {
        super(x, y);
        this.animationHandler.addAsDefaultAnimation("idle", 8, 0.1f, 5, 8,
                "assets/textures/entity/boss_slime/slime_adds.png");
        this.renderWidth= 0.6f;
        this.renderHeight = 1f;
    }
    public FireSlimeAdd() {
        super(0,0);
        this.animationHandler.addAsDefaultAnimation("idle", 8, 0.1f, 5, 8,
                "assets/textures/entity/boss_slime/slime_adds.png");
        this.renderWidth= 0.6f;
        this.renderHeight = 1f;
    }

    @Override
    protected CreatureStats getBaseStatsForLevel(int level) {
        CreatureStats stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.HEALTH, 20 + 2 * level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
        stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
        stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.15D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.3D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2.0D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D);
        stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.03D);
        stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.2D);
        stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 10.0D);
        return stats;
    }

    @Override
    protected void onEntityCollision(Entity otherEntity) {
        super.onEntityCollision(otherEntity);
        if (otherEntity instanceof Player) //Attack player when touched
        {
            this.attack((Player) otherEntity);
        }
    }

    @Override
    public float getRadius() {
        return 0.5F;
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        super.onSpawn(level);
        this.level = level;
    }

    @Override
    protected void updateLogic() {
        super.updateLogic();
        puddleCDCounter++;
        explosionCDCounter++;

        if(puddleCDCounter >= puddleCD) {
            var puddle = new PuddleFireAOE();
            puddle.setPosition(this.getPosition());
            puddle.shouldDespawn = false;
            level.spawnEntity(puddle);
            puddleCDCounter = 0;
        }

        if(explosionCDCounter >= explosionCD) {
            var knockback = new KnockbackGroundAOE();
            knockback.setStrength(0.3f);
            knockback.setPosition(this.getPosition());
            knockback.setRadius(1);
            knockback.shouldDespawn = false;
            level.spawnEntity(knockback);
            shouldDespawn = true;
        }
    }

    @Override
    public boolean shouldDespawn() {
        return shouldDespawn;
    }
}