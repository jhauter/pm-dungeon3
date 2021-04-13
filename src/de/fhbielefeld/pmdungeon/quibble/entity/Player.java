package de.fhbielefeld.pmdungeon.quibble.entity;

import de.fhbielefeld.pmdungeon.quibble.input.InputListener;

public abstract class Player extends Creature implements InputListener
{
	public Player(float x, float y)
	{
		super(x, y);
	}
	
	public Player()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public void onInputRecieved(String eventName)
	{
		
	}
}
