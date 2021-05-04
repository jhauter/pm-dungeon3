package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.chest.Chest;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class HealthTrap extends Trap{
	
	private final double damageAmount;

	/**
	 * Creates a trap that used to damage Creatures
	 * @param x x-Value of placement Point
	 * @param y y-Value of placement Point
	 * @param damageAmount the damage a Creature will get
	 * @param texture texture of the damage trap
	 */
	public HealthTrap(float x, float y, double damageAmount) {
		super(x, y);
		this.damageAmount = damageAmount;
		this.animationHandler.addAsDefaultAnimation("", 1, 1, Trap.TRAPS_TEXTURE_PATH + "trapBlue.png", 4);
	}

	@Override
	public void onActivated(Creature c) {
		c.damage(damageAmount, DamageType.PHYSICAL, c, true);
	}

	@Override
	public boolean isActivated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int activationLimit() {
		// TODO Auto-generated method stub
		return 0;
	}	
}
