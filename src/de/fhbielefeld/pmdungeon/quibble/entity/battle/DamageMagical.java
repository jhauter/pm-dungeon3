package de.fhbielefeld.pmdungeon.quibble.entity.battle;

public class DamageMagical extends DamageType
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getDamageAgainst(double sourceDamage, CreatureStats stats)
	{
		final double dmg = sourceDamage - stats.getStat(CreatureStatsAttribs.RESISTANCE_MAGIC);
		return dmg < 0.0D ? 0.0D : dmg;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStatsAttribs getSourceDamageStat()
	{
		return CreatureStatsAttribs.DAMAGE_MAGIC;
	}
}
