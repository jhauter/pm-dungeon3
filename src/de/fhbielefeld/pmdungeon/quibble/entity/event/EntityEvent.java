package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public class EntityEvent
{
	private static int nextEventID;
	
	/**
	 * Generates an ID that has not been returned before by this method.
	 * This ID should be used for assigning IDs to events.
	 * @return new event ID
	 */
	public static int genEventID()
	{
		return ++EntityEvent.nextEventID;
	}
	
	private final int eventID;
	
	private final Entity entity;
	
	private boolean cancelled;
	
	/**
	 * Constructs a basic entity event which only has the essential attributes necessary for an entity event.
	 * @param eventID an ID that is unique per event type
	 * @param entity the entity that caused this event
	 */
	public EntityEvent(int eventID, Entity entity)
	{
		this.eventID = eventID;
		this.entity = entity;
		this.cancelled = false;
	}
	
	/**
	 * Returns an ID that is unique per event type and can be used to identify this event type.
	 * @return the evnt ID
	 */
	public int getEventID()
	{
		return this.eventID;
	}
	
	/**
	 * @return the entity that caused the event
	 */
	public Entity getEntity()
	{
		return this.entity;
	}
	
	/**
	 * Whether the event that this object represents should actually happen or be cancelled.
	 * Canceling an event <b>can</b> prevent the changes that this event represents from happening
	 * but the causer of this event is not obliged to honor the cancel state and thus can be without effect
	 * depending on the implementation.
	 * Note that event listeners to whose this event is yet to be passed to, can also change the cancel state.
	 * @param cancelled whether to cancel this event
	 */
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
	
	/**
	 * Whether this event should be cancelled, e.g. whether the changes indicated by this event should actually happen.
	 * See {@link #setCancelled(boolean)} for more information.
	 * @return whether this event is set to be cancelled
	 */
	public boolean isCancelled()
	{
		return this.cancelled;
	}
}
