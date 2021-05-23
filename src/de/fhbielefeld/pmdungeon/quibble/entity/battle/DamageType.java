package de.fhbielefeld.pmdungeon.quibble.entity.battle;

/**
 * Class that represents the type of damage dealt by entities.
 * @author Andreas
 */
public abstract class DamageType
{
	/**
	 * Physical damage is the default combat damage type and is reduced by physical resistance.
	 */
	public static final DamageType PHYSICAL = new DamagePhysical();
	
	
	/**
	 * Magical damage, an extra combat Damage, reduced by magical resistance
	 */
	public static final DamageType MAGICAL = new DamageMagical();
	
	/**
	 * Calculates the actual damage dealt assuming the target has the stats specified by the <code>stats</code> parameter.
	 * @param sourceDamage the raw/source damage that is output by an entity.
	 * @param stats stats of the target
	 * @return the calculated actual damage
	 */
	public abstract double getDamageAgainst(double sourceDamage, CreatureStats stats);
	
	/**
	 * Returns the stat that is used to calculate the damage type.
	 * For example, return <code>CreatureStatsAttribs.DAMAGE_PHYS</code> if this <code>DamageType</code>
	 * represents physical damage.
	 * @return the stat used to calculate the damage value
	 */
	public abstract CreatureStatsAttribs getSourceDamageStat();
}
