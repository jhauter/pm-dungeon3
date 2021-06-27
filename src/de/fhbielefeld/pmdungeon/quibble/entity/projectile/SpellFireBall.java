package de.fhbielefeld.pmdungeon.quibble.entity.projectile;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectBurning;

public class SpellFireBall extends ProjectileMagic
{
	
	/**
	 * Creates a fire ball projectile.
	 * It will set a Creature on fire for a certain probability
	 * @param name text for log message. Has no other use.
	 * @param x the x position of the spawn point
	 * @param y the y position of the spawn point
	 * @param stats <code>CreatureStats</code> containing the damage attributes of the projectile
	 * @param owner owner of the projectile. May be <code>null</code>. Owner is invincible to projectile
	 * and gets exp when the projectile kills an entity
	 */
	public SpellFireBall(String name, float x, float y, CreatureStats stats, Creature owner)
	{
		super(name, x, y, stats, owner);
		this.animationHandler.addAsDefaultAnimation("", 8, 0.1F, 1, 8, PROJECTILE_PATH + "fireBall.png");
	}
	
	@Override
	public void onProjectileImpactCreature(Creature hitCreature)
	{
		super.onProjectileImpactCreature(hitCreature);
		if(this.getLevel().getRNG().nextInt(4) == 1)
			hitCreature.addStatusEffect(new StatusEffectBurning(hitCreature, this.getOwner()), 200);
	}
	
	@Override
	public int getTicksLasting()
	{
		return 45;
	}
	
	@Override
	public float getDamageDecreaseOverTime()
	{
		return this.getTicks() * 0.06F;
	}
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		this.level.getFogOfWarController().light(this.getX(), this.getY(), 0.5F);
	}
}
