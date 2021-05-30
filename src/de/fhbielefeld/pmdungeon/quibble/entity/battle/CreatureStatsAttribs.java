package de.fhbielefeld.pmdungeon.quibble.entity.battle;

public enum CreatureStatsAttribs
{
	/**
	 * Health of the creature.
	 */
	HEALTH(false),
	
	/**
	 * Resistance which protects from physical damage.
	 * 1 point in resistance blocks 1 point of incoming damage.
	 */
	RESISTANCE_PHYS(true),
	
	/**
	 * Resistance which protects from magical damage.
	 * 1 point in resistance blocks 1 point of incoming damage.
	 */
	RESISTANCE_MAGIC(true),
	
	/**
	 * Chance of missing a hit as percent value (<code>&gt;= 0.0 && &lt;= 1.0</code>).
	 */
	MISS_CHANCE(true),
	
	/**
	 * Chance of dealing double the damage on a hit as percent value (<code>&gt;= 0.0 && &lt;= 1.0</code>).
	 */
	CRIT_CHANCE(true),
	
	/**
	 * Strength of knockback on hit enemies (unit is tbd).
	 */
	KNOCKBACK(true),
	
	/**
	 * Reduction of incoming knockback force as percent value (<code>&gt;= 0.0 && &lt;= 1.0</code>).
	 */
	KNOCKBACK_RES(true),
	
	/**
	 * How much physical damage is dealt with one hit.
	 */
	DAMAGE_PHYS(true),
	
	/**
	 * How much physical damage is dealt with one hit.
	 */
	DAMAGE_MAGIC(true),
	
	/**
	 * How fast an entity can walk measured in DungeonWorld tile units per frame.
	 */
	WALKING_SPEED(true),
	
	/**
	 * How far an entity can reach when hitting.
	 */
	HIT_REACH(true),
	
	/**
	 * Ticks remaining until a creature can hit again. Zero means no cool down and ready to hit.
	 */
	HIT_COOLDOWN(false);
	
	private final boolean fillIfNewMax;
	
	private CreatureStatsAttribs(boolean fillIfNewMax)
	{
		this.fillIfNewMax = fillIfNewMax;
	}
	
	/**
	 * Whether to fill up this stat if new maximum stats are set and this would result in the current
	 * stat to be lower than the maximum stat.
	 * @return whether to fill this stat up if new max. stats are set
	 */
	public boolean fillIfNewMax()
	{
		return this.fillIfNewMax;
	}
}
