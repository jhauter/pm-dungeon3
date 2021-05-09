package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public class CreatureExpEvent extends EntityEvent
{
	private final int previousTotalExp;
	private int newTotalExp;
	
	public CreatureExpEvent(int eventID, Creature entity, int previousTotalExp, int newTotalExp)
	{
		super(eventID, entity);
		this.previousTotalExp = previousTotalExp;
		this.newTotalExp = newTotalExp;
	}
	
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
	
	public int getPreviousTotalExp()
	{
		return this.previousTotalExp;
	}
	
	public int getNewTotalExp()
	{
		return newTotalExp;
	}
	
	public void setNewTotalExp(int newTotalExp)
	{
		this.newTotalExp = newTotalExp;
	}
}
