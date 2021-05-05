package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class TrapHealth extends Trap {

	private final double damageAmount;

	/**
	 * Creates a trap that used to damage Creatures
	 * 
	 * @param x               x-Value of placement Point
	 * @param y               y-Value of placement Point
	 * @param damageAmount    the damage a Creature will get
	 * @param activationLimit will set a Number how often this Trap will get
	 *                        activated, if no activation Limit is required, set -1
	 * @param invisible       whether this trap is visible or not
	 */
	public TrapHealth(float x, float y, double damageAmount, int activationLimit, boolean invisible) {
		super(x, y, activationLimit, invisible);
		this.damageAmount = damageAmount;
		this.animationHandler.addAsDefaultAnimation("", 1, 1, Trap.TRAPS_TEXTURE_PATH + "trapBlue.png", 4);
		this.activationLimit = activationLimit;
	}

	@Override
	public CreatureStats getCurrentStats() {
		return getTrapStats();
	}

	@Override
	public boolean depleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void isActiv(Entity e) {
		((Creature) e).damage(damageAmount, DamageType.PHYSICAL, this, true);
	}

}
