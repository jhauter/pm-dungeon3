package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class TrapHealth extends Trap implements DamageSource
{
	
	private final double damageAmount;
	
	/**
	 * Creates a trap that used to damage Creatures
	 * 
	 * @param x               x-Value of placement Point
	 * @param y               y-Value of placement Point
	 * @param damageAmount    the damage a Creature will get
	 * @param activationLimit if false, the trap will stay activ
	 */
	public TrapHealth(float x, float y, double damageAmount, boolean noActivationLimit)
	{
		super(x, y, noActivationLimit);
		this.damageAmount = damageAmount;
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1, Trap.TRAP_TEXTURE_PATH + "trapPink.png");
		this.noActivationLimit = noActivationLimit;
	}
	
	/**
	 * Creates a trap that used to damage Creatures
	 * 
	 * @param x               x-Value of placement Point
	 * @param y               y-Value of placement Point
	 * @param damageAmount    the damage a Creature will get
	 * @param activationLimit will set a Number how often this Trap will get
	 *                        activated
	 */
	public TrapHealth(float x, float y, double damageAmount, int activationLimit)
	{
		super(x, y, activationLimit);
		this.damageAmount = damageAmount;
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1, Trap.TRAP_TEXTURE_PATH + "trapPink.png");
		this.activationLimit = activationLimit;
	}
	
	@Override
	public void isActiv(Entity e)
	{
		if(e instanceof Player)
		{
			((Player)e).damage(this, DamageType.PHYSICAL, null, false);
			this.coolDown = 44;
			setActivationLimit(activationLimit - 1);
			this.visible = true;
		}
	}
	
	@Override
	public CreatureStats getCurrentStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, damageAmount);
		return stats;
	}
	
}
