package de.fhbielefeld.pmdungeon.quibble.entity.projectile;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;

public class SpellIceBlast extends ProjectileMagic {

	/**
	 * Creates an ice blast projectile which will slow down entities on impact.
	 * @param name text for log message. Has no other use.
	 * @param x the x position of the spawn point
	 * @param y the y position of the spawn point
	 * @param stats <code>CreatureStats</code> containing the damage attributes of the projectile
	 * @param owner owner of the projectile. May be <code>null</code>. Owner is invincible to projectile
	 * and gets exp when the projectile kills an entity
	 */
	public SpellIceBlast(String name, float x, float y, CreatureStats stats, Creature owner)
	{
		super(name, x, y, stats, owner);
		this.animationHandler.addAsDefaultAnimation("", 8, 0.1F, 1, 8, PROJECTILE_PATH + "IceBlast.png");
	}
	
	@Override
	public void onProjectileImpactCreature(Creature hitCreature)
	{
		super.onProjectileImpactCreature(hitCreature);
		
		//Slows down hit creatures
		hitCreature.addStatusEffect(new StatusEffectSpeed(hitCreature, 0.005), 100);
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
}
