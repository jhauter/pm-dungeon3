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
	
	public DamageSource getDamageSource()
	{
		return this.damageSource;
	}
	
	public DamageType getDamageType()
	{
		return damageType;
	}
	
	public double getDamageRaw()
	{
		return damageRaw;
	}
	
	public double getDamageActual()
	{
		return damageActual;
	}
	
	public void setDamageActual(double damageActual)
	{
		this.damageActual = damageActual;
	}
	
	public float getKnockbackX()
	{
		return knockbackX;
	}
	
	public void setKnockbackX(float knockbackX)
	{
		this.knockbackX = knockbackX;
	}
	
	public float getKnockbackY()
	{
		return knockbackY;
	}
	
	public void setKnockbackY(float knockbackY)
	{
		this.knockbackY = knockbackY;
	}
	
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
}
