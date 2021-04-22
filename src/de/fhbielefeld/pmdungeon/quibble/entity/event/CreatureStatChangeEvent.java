package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class CreatureStatChangeEvent extends EntityEvent
{
	private final CreatureStatsAttribs stat;
	
	private final double oldValue;
	
	private double newValue;
	
	public CreatureStatChangeEvent(int eventID, Creature creature, CreatureStatsAttribs stat, double oldVal, double newVal)
	{
		super(eventID, creature);
		this.stat = stat;
		this.oldValue = oldVal;
		this.newValue = newVal;
	}
	
	public void setNewValue(double newValue)
	{
		this.newValue = newValue;
	}
	
	public double getNewValue()
	{
		return this.newValue;
	}
	
	public double getOldValue()
	{
		return this.oldValue;
	}
	
	public CreatureStatsAttribs getStat()
	{
		return this.stat;
	}
	
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
}
