package de.fhbielefeld.pmdungeon.quibble.entity.battle;

public abstract class DamageType
{
	public static final DamageType PHYSICAL = new DamagePhysical();
	
	public abstract double getDamageAgainst(double sourceDamage, CreatureStats stats);
	
	public abstract CreatureStatsAttribs getSourceDamageStat();
}
