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
	 * Creates a stat values array copied from the parameter.
	 * @param copyFrom the creature stats from which to copy the values
	 */
	public CreatureStats(CreatureStats copyFrom)
	{
		this();
		for(int i = 0; i < this.stats.length; ++i)
		{
			this.stats[i] = copyFrom.stats[i];
		}
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
	 * Sets all stat values to match the <code>CreatureStats</code> argument.
	 * @param stat the stat values to copy values from
	 */
	public void setStats(CreatureStats stats)
	{
		CreatureStatsAttribs[] values = CreatureStatsAttribs.values();
		for(int i = 0; i < this.stats.length; ++i)
		{
			this.setStat(values[i], stats.stats[i]);
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
	 * Adds all values of the specified <code>CreatureStats</code> to the corresponding values
	 * of this <code>CreatureStats</code>.
	 * @param stats the stat containing the values to add to this <code>CreatureStats</code>
	 */
	public void addStats(CreatureStats stats)
	{
		CreatureStatsAttribs[] values = CreatureStatsAttribs.values();
		for(int i = 0; i < this.stats.length; ++i)
		{
			this.addStat(values[i], stats.stats[i]);
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
	 * Creates a copy of this <code>CreatureStats</code> and adds all stat values of the argument
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
	 * Checks for every stat values if its greater than the corresponding stat value of the provided argument
	 * and if this is true, sets the stat value to match the stat value of the argument.
	 * @param stats the stat values to limit this <code>CreatureStats</code>'s stat values to
	 */
	public void limit(CreatureStats stats)
	{
		for(int i = 0; i < this.stats.length; ++i)
		{
			if(this.stats[i] > stats.stats[i])
			{
				this.setStat(CreatureStatsAttribs.values()[i], stats.stats[i]);
			}
		}
	}
	
	/**
	 * Cuts all values off or raises them to the <code>newMax</code> stats, depending on
	 * the declaration of each <code>CreatureStatsAttrib</code> in its enum.
	 * @param newMax the <code>CreatureStats</code> that represent the new maximum values
	 */
	public void newMax(CreatureStats newMax)
	{
		for(int i = 0; i < this.stats.length; ++i)
		{
			if(this.stats[i] > newMax.stats[i])
			{
				this.setStat(CreatureStatsAttribs.values()[i], newMax.stats[i]);
			}
			else if(this.stats[i] < newMax.stats[i] && CreatureStatsAttribs.values()[i].fillIfNewMax())
			{
				this.setStat(CreatureStatsAttribs.values()[i], newMax.stats[i]);
			}
		}
	}

	/**
	 * Multiplies all stats by a certain value. Is used to change the strengh of an item entirely without changing the
	 * proportions of the stats
	 * @param value the value by which every stats should be multiplied
	 */
	public void multiplyAllStats(double value){
		for(int i = 0; i < stats.length; i++){
			stats[i] *= value;
		}
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
		final CreatureStatsEvent event = new CreatureStatsEvent(stat, oldVal, newVal);
		this.listener.onStatValueChange(event);
		return event;
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder("CreatureStats[");
		CreatureStatsAttribs[] values = CreatureStatsAttribs.values();
		for(int i = 0; i < this.stats.length; ++i)
		{
			b.append(values[i].name() + ": " + this.stats[i] + (i != this.stats.length - 1 ? ", " : ""));
		}
		b.append("]");
		return b.toString();
	}
}
