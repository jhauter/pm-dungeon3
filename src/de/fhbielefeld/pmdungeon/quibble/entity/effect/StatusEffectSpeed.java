package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class StatusEffectSpeed extends StatusEffect {

	private Creature creature;
	private double currentNewSpeed;

	/**
	 * 
	 * @param creature        the Creature which will be effected
	 * @param currentNewSpeed the new WalkingSpeed
	 */
	public StatusEffectSpeed(Creature creature, double currentNewSpeed) {
		super(creature);
		this.creature = creature;
		this.currentNewSpeed = currentNewSpeed;
	}

	@Override
	public CreatureStats getStatsModification(CreatureStats currentCreatureStats) {
		currentCreatureStats.setStat(CreatureStatsAttribs.WALKING_SPEED, currentNewSpeed);
		return super.getStatsModification(currentCreatureStats);

	}
}
