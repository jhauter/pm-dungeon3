package de.fhbielefeld.pmdungeon.quibble.entity.projectile;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public abstract class ProjectilePhysical extends Projectile
{
	/**
	 * Creates a projectile entity that will keep moving at the same speed when the velocity is set (by default).
	 * Projectiles will damage creatures on impact and disappear if they hit a wall.
	 * <code>ProjectilePhysical</code> uses physical damage by default.
	 * @param name text for log message. Has no other use.
	 * @param x the x position of the spawn point
	 * @param y the y position of the spawn point
	 * @param stats <code>CreatureStats</code> containing the damage attributes of the projectile
	 * @param owner owner of the projectile. May be <code>null</code>. Owner is invincible to projectile
	 * and gets exp when the projectile kills an entity
	 */
	public ProjectilePhysical(String name, float x, float y, CreatureStats stats, Creature owner)
	{
		super(name, x, y, stats, owner);
	}
	
	@Override
	public DamageType getDamageType()
	{
		return DamageType.PHYSICAL;
	}
	
	@Override
	public CreatureStatsAttribs getDamageStat()
	{
		return CreatureStatsAttribs.DAMAGE_PHYS;
	}
}
