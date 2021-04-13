package de.fhbielefeld.pmdungeon.quibble.entity;

public class Knight extends Player
{
	public Knight(float x, float y)
	{
		super(x, y);
	}
	
	public Knight()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public float getInitWalkingSpeed()
	{
		return 1.0F;
	}
}
