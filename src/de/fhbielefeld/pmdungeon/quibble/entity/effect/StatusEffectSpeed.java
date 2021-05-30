package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class StatusEffectSpeed extends StatusEffect
{
	private double speedModifier;
	
	public StatusEffectSpeed(Creature creature, double speedModifier)
	{
		super(creature);
		this.speedModifier = speedModifier;
	}
	
	@Override
	public CreatureStats getStatsModification(CreatureStats currentCreatureStats)
	{
		currentCreatureStats.setStat(CreatureStatsAttribs.WALKING_SPEED,
			currentCreatureStats.getStat(CreatureStatsAttribs.WALKING_SPEED) * speedModifier);
		return super.getStatsModification(currentCreatureStats);
		
	}
}
