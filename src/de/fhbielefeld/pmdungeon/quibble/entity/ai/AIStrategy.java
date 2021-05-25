package de.fhbielefeld.pmdungeon.quibble.entity.ai;

import de.fhbielefeld.pmdungeon.quibble.entity.NPC;

@FunctionalInterface
public interface AIStrategy
{
	/**
	 * This is executed every frame, if this <code>AIStrategy</code> is assigned to an <code>NPC</code>.
	 * This method should be used to calculate an NPC's behavior for a specific behavior strategy.
	 * @param entity the NPC that this <code>AIStrategy</code> is assigned to
	 */
	public void executeStrategy(NPC entity);
}
