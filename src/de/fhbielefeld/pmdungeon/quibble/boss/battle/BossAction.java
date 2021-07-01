package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import java.util.Objects;

/**
 * Represents an action that can be executed during a boss battle. Is responsible for
 * removing itself from the list of active actions and calling for the next action to
 * be added to the queue
 */
public abstract class BossAction
{
	
	/**
	 * Amount of ticks that the BossAction is supposed to be executed
	 */
	public int duration;
	
	private int durationCounter;
	
	/**
	 * Amount of ticks until next action can be executed
	 */
	public int cooldown;
	
	private int cooldownCounter;
	
	protected BossBattle battle;
	
	/** Called each time when switched to this action*
	 * @param battle BossBattle instance
	 */
	
	public void onActionBegin(BossBattle battle)
	{
		this.battle = battle;
	}
	
	/**
	 * Called each frame while action is executed
	 */
	public void execute()
	{
		durationCounter++;
		cooldownCounter++;
		if(this.cooldownCounter >= cooldown)
		{
			//            System.out.println("Switch");
			battle.nextAction();
			cooldownCounter = 0;
		}
		
		if(this.durationCounter >= duration)
		{
			//            System.out.println("End");
			this.onActionEnd();
			battle.removeAction(this);
			durationCounter = 0;
		}
		
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		BossAction that = (BossAction)o;
		return duration == that.duration && durationCounter == that.durationCounter && cooldown == that.cooldown && cooldownCounter == that.cooldownCounter
			&& Objects.equals(battle, that.battle);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(duration, durationCounter, cooldown, cooldownCounter, battle);
	}
	
	/**
	 * Called when action is finished
	 */
	public abstract void onActionEnd();
}
