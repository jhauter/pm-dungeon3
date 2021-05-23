package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class StatusEffectKnockback extends StatusEffect
{
	public StatusEffectKnockback(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public CreatureStats getStatsModification(CreatureStats currentCreatureStats)
	{
		currentCreatureStats.addStat(CreatureStatsAttribs.KNOCKBACK, 1.0D);
		return currentCreatureStats;
	}
}
