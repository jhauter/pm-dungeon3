package de.fhbielefeld.pmdungeon.quibble.entity.battle;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsEventListener.CreatureStatsEvent;

public class CreatureStats
{
	private final double[] stats;
	
	private CreatureStatsEventListener listener;
	
	public CreatureStats()
	{
		this.stats = new double[CreatureStatsAttribs.values().length];
	}
	
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
	
	public double getStat(CreatureStatsAttribs stat)
	{
		return this.stats[stat.ordinal()];
	}
	
	public CreatureStats addCopy(CreatureStats stats)
	{
		CreatureStats copy = new CreatureStats();
		for(int i = 0; i < copy.stats.length; ++i)
		{
			copy.stats[i] = this.stats[i] + stats.stats[i];
		}
		return copy;
	}
	
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
