package de.fhbielefeld.pmdungeon.quibble.entity;

public abstract class Creature extends Entity
{
	protected float walkingSpeed;
	
	public Creature(float x, float y)
	{
		super(x, y);
		this.walkingSpeed = this.getInitWalkingSpeed();
	}
	
	public Creature()
	{
		this(0.0F, 0.0F);
	}
	
	public abstract float getInitWalkingSpeed();
	
	public final float getWalkingSpeed()
	{
		return this.walkingSpeed;
	}
}
