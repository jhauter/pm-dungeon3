package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class GolemArmAdd extends NPC {

    @Override
    protected CreatureStats getBaseStatsForLevel(int level) {
        CreatureStats stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.HEALTH, 100 + 2 * level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
        stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
        stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.15D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.3D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 3.0D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D);
        stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.5D);
        stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.3D);
        stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 10.0D);
        return stats;
    }

    @Override
    protected void onEntityCollision(Entity otherEntity)
    {
        super.onEntityCollision(otherEntity);
        if(otherEntity instanceof Player) //Attack player when touched
        {
            this.attack((Player)otherEntity);
        }
    }
    @Override
    public float getRadius() {
        return 0.5F;
    }
}
