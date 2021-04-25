package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class CreatureHitTargetEvent extends EntityEvent
{
	private final Creature target;
	
	private DamageType damageType;
	
	private double damage;
	
	/**
	 * @param eventID the event ID
	 * @param entity the entity that is hitting another entity
	 * @param target the entity that is being hit
	 * @param dmgType the type of damage with which the target is hit
	 * @param damage the damage before stat calculation that he damage dealer wants to deal
	 */
	public CreatureHitTargetEvent(int eventID, Creature entity, Creature target, DamageType dmgType, double damage)
	{
		super(eventID, entity);
		this.target = target;
		this.damageType = dmgType;
		this.damage = damage;
	}
	
	/**
	 * @return the entity that is being hit
	 */
	public Creature getTarget()
	{
		return this.target;
	}
	
	/**
	 * @return the type of damage with which the target is hit
	 */
	public DamageType getDamageType()
	{
		return this.damageType;
	}
	
	/**
	 * @return the damage before stat calculation that he damage dealer wants to deal
	 */
	public double getDamage()
	{
		return this.damage;
	}
	
	/**
	 * Allows to change the damage type as this is still before stat calculations.
	 * @param damageType the new damage type
	 */
	public void setDamageType(DamageType damageType)
	{
		this.damageType = damageType;
	}
	
	/**
	 * Changes the output damage of the damage dealer.
	 * Note that this is still before stat calculations.
	 * @param damage the new raw damage
	 */
	public void setDamage(double damage)
	{
		this.damage = damage;
	}
	
	/**
	 * @return the entity that is hitting the other entity
	 */
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
}
