package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.chest.Chest;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class PlayerOpenChestEvent extends EntityEvent
{
	public static final int EVENT_ID = EntityEvent.genEventID();
	
	private final Chest chest;
	
	public PlayerOpenChestEvent(int eventID, Player player, Chest chest)
	{
		super(eventID, player);
		this.chest = chest;
	}
	
	public Chest getChest()
	{
		return this.chest;
	}
	
	@Override
	public Player getEntity()
	{
		return (Player)super.getEntity();
	}
}
