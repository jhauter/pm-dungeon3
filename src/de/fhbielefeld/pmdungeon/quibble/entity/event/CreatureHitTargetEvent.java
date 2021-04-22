package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class CreatureHitTargetEvent extends EntityEvent
{
	private final Creature target;
	
	private DamageType damageType;
	
	private double damage;
	
	public CreatureHitTargetEvent(int eventID, Creature entity, Creature target, DamageType dmgType, double damage)
	{
		super(eventID, entity);
		this.target = target;
		this.damageType = dmgType;
		this.damage = damage;
	}
	
	public Creature getTarget()
	{
		return this.target;
	}
	
	public DamageType getDamageType()
	{
		return this.damageType;
	}
	
	public double getDamage()
	{
		return this.damage;
	}
	
	public void setDamageType(DamageType damageType)
	{
		this.damageType = damageType;
	}
	
	public void setDamage(double damage)
	{
		this.damage = damage;
	}
}
