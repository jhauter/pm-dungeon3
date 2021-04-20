package de.fhbielefeld.pmdungeon.quibble.entity.battle;

public class CreatureStats
{
	public final double[] stats;
	
	public CreatureStats()
	{
		this.stats = new double[CreatureStatsAttribs.values().length];
	}
	
	public void setStat(CreatureStatsAttribs stat, double value)
	{
		this.stats[stat.ordinal()] = value;
	}
	
	public void addStat(CreatureStatsAttribs stat, double add)
	{
		this.stats[stat.ordinal()] += add;
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
}
