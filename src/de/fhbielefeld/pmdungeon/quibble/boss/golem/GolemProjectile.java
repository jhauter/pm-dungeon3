package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.ProjectileMagic;

public class GolemProjectile extends ProjectileMagic
{
	/**
	 * Creates a projectile entity that will keep moving at the same speed when the velocity is set (by default).
	 * Projectiles will damage creatures on impact and disappear if they hit a wall.
	 * <code>ProjectileMagic</code> uses magic damage by default.
	 *
	 * @param name  text for log message. Has no other use.
	 * @param x     the x position of the spawn point
	 * @param y     the y position of the spawn point
	 * @param stats <code>CreatureStats</code> containing the damage attributes of the projectile
	 * @param owner owner of the projectile. May be <code>null</code>. Owner is invincible to projectile
	 */
	public GolemProjectile(String name, float x, float y, CreatureStats stats, Creature owner)
	{
		super(name, x, y, stats, owner);
		//this.animationHandler.addAsDefaultAnimation("", 8, 0.1F, 1, 8, PROJECTILE_PATH + "fireBall.png");
		this.animationHandler.addAsDefaultAnimation("", 1, 0.01F, 1, 1,
			"assets/textures/entity/golem/laser_projectile.png");
	}
	
	@Override
	public int getTicksLasting()
	{
		return 300;
	}
	
	@Override
	public float getDamageDecreaseOverTime()
	{
		return 0;
	}
}
