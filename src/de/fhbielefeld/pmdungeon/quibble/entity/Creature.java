package de.fhbielefeld.pmdungeon.quibble.entity;

public abstract class Creature extends Entity
{
	/**
	 * The walking speed of this entity, measures in tiles per frame.
	 */
	protected float walkingSpeed;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Creature(float x, float y)
	{
		super(x, y);
		this.walkingSpeed = this.getInitWalkingSpeed();
	}
	
	/**
	 * Creates an entity with a default position
	 */
	public Creature()
	{
		this(0.0F, 0.0F);
	}
	
	/**
	 * Returns the walking speed this entity should be initialized with.
	 * @return init walking speed
	 */
	public abstract float getInitWalkingSpeed();
	
	/**
	 * Returns the current walking speed of this entity measured in tiles per frame.
	 * This is not the current speed.
	 * @return walking speed of this entity
	 */
	public final float getWalkingSpeed()
	{
		return this.walkingSpeed;
	}
}
