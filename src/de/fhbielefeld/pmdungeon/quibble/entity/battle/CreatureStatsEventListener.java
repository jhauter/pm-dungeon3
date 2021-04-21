package de.fhbielefeld.pmdungeon.quibble.entity.battle;

@FunctionalInterface
public interface CreatureStatsEventListener
{
	/**
	 * This is executed on a listener every time a stat value is changed.
	 * @param stat the stat that was changed
	 * @param oldVal the old stat value
	 * @param newVal the new stat value
	 */
	public void onStatValueChanged(CreatureStatsAttribs stat, double oldVal, double newVal);
}
