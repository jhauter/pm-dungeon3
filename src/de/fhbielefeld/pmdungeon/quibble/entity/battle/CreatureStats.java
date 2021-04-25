package de.fhbielefeld.pmdungeon.quibble.entity.battle;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsEventListener.CreatureStatsEvent;

public class CreatureStats
{
	private final double[] stats;
	
	private CreatureStatsEventListener listener;
	
	/**
	 * Creates a stat values array with every value initialized to <code>0.0</code>.
	 */
	public CreatureStats()
	{
		this.stats = new double[CreatureStatsAttribs.values().length];
	}
	
	/**
	 * Sets the value of the specified stat
	 * @param stat the stat to change
	 * @param value the new value
	 */
	public void setStat(CreatureStatsAttribs stat, double value)
	{
		CreatureStatsEvent ev = this.fireStatChange(stat, this.stats[stat.ordinal()], value);
		if(ev != null)
		{
			if(ev.isCancelled())
			{
				return;
			}
			this.stats[stat.ordinal()] = ev.getNewValue();
		}
		else
		{
			this.stats[stat.ordinal()] = value;
		}
	}
	
	/**
	 * Adds to the value of the specified stat
	 * @param stat the stat to change
	 * @param value the value to add
	 */
	public void addStat(CreatureStatsAttribs stat, double add)
	{
		CreatureStatsEvent ev = this.fireStatChange(stat, this.stats[stat.ordinal()], this.stats[stat.ordinal()] + add);
		if(ev != null)
		{
			if(ev.isCancelled())
			{
				return;
			}
			this.stats[stat.ordinal()] = ev.getNewValue();
		}
		else
		{
			this.stats[stat.ordinal()] += add;
		}
	}
	
	/**
	 * Returns current the value of the specified stat
	 * @param stat the stat whose value to return
	 * @return the current stat value
	 */
	public double getStat(CreatureStatsAttribs stat)
	{
		return this.stats[stat.ordinal()];
	}
	
	/**
	 * Creates a copy of this <code>CreatureStats</code> and add all stat values of the argument
	 * to the corresponding stat values of the copy of this <code>CreatureStats</code>.
	 * @param stats <code>CreatureStats</code> whose values should be added to the copied stats
	 * @return the copied <code>CreatureStats</code> to which the values were added
	 */
	public CreatureStats addCopy(CreatureStats stats)
	{
		CreatureStats copy = new CreatureStats();
		for(int i = 0; i < copy.stats.length; ++i)
		{
			copy.stats[i] = this.stats[i] + stats.stats[i];
		}
		return copy;
	}
	
	/**
	 * Sets the event listener which should be notified if stat values change.
	 * Passing <code>null</code> removes the event listener.
	 * @param l the listener
	 */
	public void setEventListener(CreatureStatsEventListener l)
	{
		this.listener = l;
	}
	
	private CreatureStatsEvent fireStatChange(CreatureStatsAttribs stat, double oldVal, double newVal)
	{
		if(this.listener == null)
		{
			return null;
		}
		final CreatureStatsEvent event =  new CreatureStatsEvent(stat, oldVal, newVal);
		this.listener.onStatValueChange(event);
		return event;
	}
}
