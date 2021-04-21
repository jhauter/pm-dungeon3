package de.fhbielefeld.pmdungeon.quibble.entity.battle;

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
		final double tmpOldVal = this.stats[stat.ordinal()];
		this.stats[stat.ordinal()] = value;
		this.fireStatChanged(stat, tmpOldVal, value);
	}
	
	public void addStat(CreatureStatsAttribs stat, double add)
	{
		final double tmpOldVal = this.stats[stat.ordinal()];
		this.stats[stat.ordinal()] += add;
		this.fireStatChanged(stat, tmpOldVal, this.stats[stat.ordinal()]);
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
	
	private void fireStatChanged(CreatureStatsAttribs stat, double oldVal, double newVal)
	{
		if(this.listener != null)
		{
			this.listener.onStatValueChanged(stat, oldVal, newVal);
		}
	}
}
