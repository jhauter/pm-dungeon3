package de.fhbielefeld.pmdungeon.quibble.entity.battle;

public enum CreatureStatsAttribs
{
	/**
	 * Health of the creature.
	 */
	HEALTH,
	
	/**
	 * Resistance which protects from physical damage.
	 * 1 point in resistance blocks 1 point of incoming damage.
	 */
	RESISTANCE_PHYS,
	
	/**
	 * Resistance which protects from magical damage.
	 * 1 point in resistance blocks 1 point of incoming damage.
	 */
	RESISTANCE_MAGIC,
	
	/**
	 * Chance of missing a hit as percent value (<code>&gt;= 0.0 && &lt;= 1.0</code>).
	 */
	MISS_CHANCE,
	
	/**
	 * Chance of dealing double the damage on a hit as percent value (<code>&gt;= 0.0 && &lt;= 1.0</code>).
	 */
	CRIT_CHANCE,
	
	/**
	 * Strength of knockback on hit enemies (unit is tbd).
	 */
	KNOCKBACK,
	
	/**
	 * Reduction of incoming knockback force as percent value (<code>&gt;= 0.0 && &lt;= 1.0</code>).
	 */
	KNOCKBACK_RES,
	
	/**
	 * How much physical damage is dealt with one hit.
	 */
	DAMAGE_PHYS,
	
	/**
	 * How much physical damage is dealt with one hit.
	 */
	DAMAGE_MAGIC,
	
	/**
	 * How fast an entity can walk measured in DungeonWorld tile units per frame.
	 */
	WALKING_SPEED,
	
	/**
	 * How far an entity can reach when hitting.
	 */
	HIT_REACH,
	
	/**
	 * Ticks remaining until a creature can hit again. Zero means no cool down and ready to hit.
	 */
	HIT_COOLDOWN
}
