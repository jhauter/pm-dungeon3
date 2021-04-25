package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class CreatureHitTargetPostEvent extends EntityEvent
{
	private final Creature target;
	
	private final DamageType damageType;
	
	private final double damage;
	
	/**
	 * @param eventID the event ID
	 * @param entity the entity that is hitting another entity
	 * @param target the entity that is being hit
	 * @param dmgType the type of damage with which the target is hit
	 * @param damage the damage before stat calculation that he damage dealer wants to deal
	 */
	public CreatureHitTargetPostEvent(int eventID, Creature entity, Creature target, DamageType dmgType, double damage)
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
	 * @return the entity that is hitting the other entity
	 */
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
}
