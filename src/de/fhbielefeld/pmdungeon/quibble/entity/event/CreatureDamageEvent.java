package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class CreatureDamageEvent extends EntityEvent
{
	private final DamageType damageType;
	
	private final DamageSource damageSource;
	
	private final double damageRaw;
	
	private double damageActual;
	
	private float knockbackX;
	private float knockbackY;
	
	/**
	 * Constructs a <code>CreatureDamageEvent</code> with the specified attributes.
	 * @param eventID the event ID
	 * @param entity the entity that is taking damage
	 * @param dmgSource the damage source (which can be an entity)
	 * @param type the damage type
	 * @param dmgRaw the raw damage before calculations with stats
	 * @param dmgReal the real damage after calculations with stats
	 * @param kbX the knockback force along the x axis
	 * @param kbY the knockback force along the y axis
	 */
	public CreatureDamageEvent(int eventID, Creature entity, DamageSource dmgSource, DamageType type, double dmgRaw, double dmgReal, float kbX, float kbY)
	{
		super(eventID, entity);
		this.damageSource = dmgSource;
		this.damageType = type;
		this.damageRaw = dmgRaw;
		this.damageActual = dmgReal;
		this.knockbackX = kbX;
		this.knockbackY = kbY;
	}
	
	/**
	 * @return the damage source (which can be an entity)
	 */
	public DamageSource getDamageSource()
	{
		return this.damageSource;
	}
	
	/**
	 * @return the type of damage for this event
	 */
	public DamageType getDamageType()
	{
		return damageType;
	}
	
	/**
	 * @return the raw damage before calculations with stats
	 */
	public double getDamageRaw()
	{
		return damageRaw;
	}
	
	/**
	 * @return the real damage after calculations with stats
	 */
	public double getDamageActual()
	{
		return damageActual;
	}
	
	public void setDamageActual(double damageActual)
	{
		this.damageActual = damageActual;
	}
	
	/**
	 * The knockback force with which the hit entity should be knocked back with.
	 * @return the knockback force along the x axis
	 */
	public float getKnockbackX()
	{
		return knockbackX;
	}
	
	public void setKnockbackX(float knockbackX)
	{
		this.knockbackX = knockbackX;
	}
	
	/**
	 * The knockback force with which the hit entity should be knocked back with.
	 * @return the knockback force along the x axis
	 */
	public float getKnockbackY()
	{
		return knockbackY;
	}
	
	public void setKnockbackY(float knockbackY)
	{
		this.knockbackY = knockbackY;
	}
	
	/**
	 * @return the entity that is taking damage
	 */
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
}
