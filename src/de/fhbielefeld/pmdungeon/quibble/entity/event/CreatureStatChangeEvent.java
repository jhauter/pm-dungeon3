package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class CreatureStatChangeEvent extends EntityEvent
{
	private final CreatureStatsAttribs stat;
	
	private final double oldValue;
	
	private double newValue;
	
	/**
	 * @param eventID the event ID
	 * @param creature the creature whose stats are changing
	 * @param stat the particular stat
	 * @param oldVal the previous stat value
	 * @param newVal the new stat value
	 */
	public CreatureStatChangeEvent(int eventID, Creature creature, CreatureStatsAttribs stat, double oldVal, double newVal)
	{
		super(eventID, creature);
		this.stat = stat;
		this.oldValue = oldVal;
		this.newValue = newVal;
	}
	
	/**
	 * Sets the new stat value that the creature should have
	 * @param newValue
	 */
	public void setNewValue(double newValue)
	{
		this.newValue = newValue;
	}
	
	/**
	 * @return the new stat value that the creature should have
	 */
	public double getNewValue()
	{
		return this.newValue;
	}
	
	/**
	 * @return the previous stat value
	 */
	public double getOldValue()
	{
		return this.oldValue;
	}
	
	/**
	 * @return the particular stat that is being changed in this event
	 */
	public CreatureStatsAttribs getStat()
	{
		return this.stat;
	}
	
	/**
	 * @return the entity whose stats are changing
	 */
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
}
