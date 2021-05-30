package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.ArrowProjectile;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;

public class ItemWeaponSimpleBow extends ItemWeaponRange
{
	
	protected ItemWeaponSimpleBow(String name, float itemWidth, float itemHeight, float visibleTime, String texture)
	{
		super(name, itemWidth, itemHeight, visibleTime, texture);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CreatureStats getAttackStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2);
		return stats;
	}
	
	@Override
	public Projectile spawnProjectile(Creature user)
	{
		Projectile arrow = new ArrowProjectile(this.getDisplayName() + " Projectile", user.getX(), user.getY() + 0.5F,
			user.getCurrentStats().addCopy(getAttackStats()), user);
		arrow.setVelocityX(1);
		return arrow;
	}
	
	@Override
	public ParticleMovement getWeaponMovement(Creature user)
	{
		SwingOrientation swingDir = user.getLookingDirection() == LookingDirection.RIGHT ? SwingOrientation.RIGHT
			: SwingOrientation.LEFT;
		return new Swing(swingDir, 0.0F);
	}
	
	@Override
	public float getProjectileSpeed()
	{
		return 0.25F;
	}
}
