package de.fhbielefeld.pmdungeon.quibble.entity.event;

@FunctionalInterface
public interface EntityEventHandler
{
	public void handleEvent(EntityEvent event);
}
