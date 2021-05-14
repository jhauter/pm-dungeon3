package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public class PlayerNextDungeonLevelEvent extends EntityEvent
{
	private final int currentLevel;
	private int toReachLevel;
	
	public PlayerNextDungeonLevelEvent(int eventID, Creature entity, int currentLevel, int nextLevel)
	{
		super(eventID, entity);
		this.currentLevel = currentLevel;
		this.toReachLevel = nextLevel;
	}
	
	@Override
	public Creature getEntity()
	{
		return (Creature)super.getEntity();
	}
	
	public int getcurrentLevel()
	{
		return this.currentLevel;
	}
	
	public void nextLevel()
	{
		toReachLevel ++;
	}
}
