package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class StatusEffectFogSight extends StatusEffect
{
	private final double fogSightChange;
	
	/**
	 * Creates a status effect that changes how far a creature can see in the fog of war.
	 * Currently only affects players.
	 * @param creature the creature to influence
	 * @param fogSightChange value that is added to the <code>CreatureStatsAttribs.FOW_SIGHT</code> stat for the duration of the effect.
	 */
	public StatusEffectFogSight(Creature creature, double fogSightChange)
	{
		super(creature);
		this.fogSightChange = fogSightChange;
	}
	
	@Override
	public CreatureStats getStatsModification(CreatureStats currentCreatureStats)
	{
		currentCreatureStats.setStat(CreatureStatsAttribs.FOW_SIGHT, currentCreatureStats.getStat(CreatureStatsAttribs.FOW_SIGHT) + fogSightChange);
		return currentCreatureStats;
	}
}
