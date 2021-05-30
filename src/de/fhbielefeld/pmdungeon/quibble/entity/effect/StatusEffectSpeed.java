package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class StatusEffectSpeed extends StatusEffect
{
	
	private double newSpeed;
	
	/**
	 * 
	 * @param creature        the Creature which will be effected
	 * @param newSpeed the new WalkingSpeed
	 */
	public StatusEffectSpeed(Creature creature, double newSpeed)
	{
		super(creature);
		this.newSpeed = newSpeed;
	}
	
	@Override
	public CreatureStats getStatsModification(CreatureStats currentCreatureStats)
	{
		currentCreatureStats.setStat(CreatureStatsAttribs.WALKING_SPEED, newSpeed);
		return super.getStatsModification(currentCreatureStats);
		
	}
}
