package de.fhbielefeld.pmdungeon.quibble.entity.event;

@FunctionalInterface
public interface EntityEventHandler
{
	/**
	 * Executed when an entity fires an event.
	 * The parameter can also be subtypes of <code>EntityEvent</code>.
	 * @param event the event object
	 */
	public void handleEvent(EntityEvent event);
}
