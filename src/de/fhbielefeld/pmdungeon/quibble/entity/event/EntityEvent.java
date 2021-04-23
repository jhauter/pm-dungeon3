package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public class EntityEvent
{
	private static int nextEventID;
	
	public static int genEventID()
	{
		return ++EntityEvent.nextEventID;
	}
	
	private final int eventID;
	
	private final Entity entity;
	
	private boolean cancelled;
	
	public EntityEvent(int eventID, Entity entity)
	{
		this.eventID = eventID;
		this.entity = entity;
		this.cancelled = false;
	}
	
	public int getEventID()
	{
		return this.eventID;
	}
	
	public Entity getEntity()
	{
		return this.entity;
	}
	
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
	
	public boolean isCancelled()
	{
		return this.cancelled;
	} 
}
