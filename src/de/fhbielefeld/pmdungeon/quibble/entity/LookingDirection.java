package de.fhbielefeld.pmdungeon.quibble.entity;

public enum LookingDirection
{
	/**
	 * Indicates looking to the left.
	 */
	LEFT(-1.0F),
	
	/**
	 * Indicates looking to the right.
	 */
	RIGHT(1.0F);
	
	private final float axisX;
	
	private LookingDirection(float axisX)
	{
		this.axisX = axisX;
	}
	
	public float getAxisX()
	{
		return this.axisX;
	}
}
