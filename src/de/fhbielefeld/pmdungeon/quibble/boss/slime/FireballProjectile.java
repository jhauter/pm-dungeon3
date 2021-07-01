package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.ProjectileMagic;

public class FireballProjectile extends ProjectileMagic
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
	public FireballProjectile(String name, float x, float y, CreatureStats stats, Creature owner)
	{
		super(name, x, y, stats, owner);
		this.animationHandler.addAsDefaultAnimation("idle", 45, 0, 0.05f, 1, 45,
			"assets/textures/entity/boss_slime/fireball.png");
		this.renderHeight = 5;
		this.renderWidth = 5;
	}
	
	@Override
	public int getTicksLasting()
	{
		return 500;
	}
	
	@Override
	public float getDamageDecreaseOverTime()
	{
		return 0;
	}
}
