package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public abstract class InputStrategy
{
	
	private Player player;
	
	/**
	 * 
	 * @param player The user who performs an action
	 */
	public InputStrategy(Player player)
	{
		this.player = player;
	}
	
	/**
	 * The actual method that does the main work
	 */
	public abstract void handle();
	
	/**
	 * 
	 * @return the user of the Strategy
	 */
	public Player getPlayer()
	{
		return player;
	}
	
}
