package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class CreatureHitTargetPostEvent extends EntityEvent
{
	private final Creature target;
	
	private final DamageType damageType;
	
	private final double damage;
	
	public CreatureHitTargetPostEvent(int eventID, Creature entity, Creature target, DamageType dmgType, double damage)
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
	
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
}
