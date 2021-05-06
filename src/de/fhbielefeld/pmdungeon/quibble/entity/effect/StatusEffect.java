package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

public abstract class StatusEffect
{
	private final Creature creature;
	
	private int remainingTicks;
	
	private boolean removable;
	
	public StatusEffect(Creature creature)
	{
		this.creature = creature;
	}
	
	/**
	 * This is called every frame if the status effect is added to a creature.
	 */
	public void update()
	{
		if(this.remainingTicks > 0)
		{
			--this.remainingTicks;
		}
		else
		{
			this.setRemovable();
		}
	}
	
	/**
	 * This is called when the status effect is removed.
	 */
	public void onRemove()
	{
		
	}
	
	/**
	 * Returns the stats that the entity should have while under influence of this status effect.
	 * The parameter allows the stat change to be dependent on the current creature stats.
	 * Note that the parameter may be influenced by other status effects that have been calculated before this.
	 * @param currentCreatureStats the current stats of the creature
	 * @return the new stats of the creature
	 */
	public CreatureStats getStatsModification(CreatureStats currentCreatureStats)
	{
		return currentCreatureStats;
	}
	
	/**
	 * @return the creature that is under influence of this status effect
	 */
	public final Creature getCreature()
	{
		return this.creature;
	}
	
	/**
	 * Marks this status effect as removable. Once this has been done, the status effect
	 * will be removed from the creature on the next frame.
	 */
	public void setRemovable()
	{
		this.removable = true;
	}
	
	/**
	 * @return whether the status effect will be removed on the next frame
	 */
	public boolean isRemovable()
	{
		return this.removable;
	}
	
	/**
	 * @return in how many ticks this status effect will be removed
	 */
	public final int getRemainingTicks()
	{
		return this.remainingTicks;
	}
	
	/**
	 * Sets the remaining time in ticks that this status effect will last
	 * @param ticks the new status effect duration
	 */
	public final void setRemainingTicks(int ticks)
	{
		this.remainingTicks = ticks;
	}
}
