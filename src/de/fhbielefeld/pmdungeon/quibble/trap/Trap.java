package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;

public abstract class Trap extends Entity implements DamageSource {
	// Path to the Folder for the Trap Textures
	public static final String TRAPS_TEXTURE_PATH = "assets/textures/traps/";

	protected String texture;
	protected boolean activ;
	protected boolean invisible;
	protected int activationLimit;

	/**
	 * Creates a trap on a certain position
	 * 
	 * @param x               the x value of the current level
	 * @param y               the y value of the current level
	 * @param texture         the displayed texture for the trap
	 * @param activationLimit will set a Number how often this Trap will get
	 *                        activated if no activation Limit is required, set -1
	 */
	public Trap(float x, float y, int activationLimit, boolean invisible) {
		super(x, y);
	}

	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (activ)
			return;
		else if (otherEntity instanceof Player) {
			setActivationLimit(activationLimit - 1);
			isActiv(otherEntity);
		}
	}

	/**
	 * If the Trap is activ
	 * 
	 * @param e The Entity which will be effect
	 * @return if the trap's effect still on
	 */
	public abstract void isActiv(Entity e);

	/**
	 * Will inform the Trap that it is on the ActivationLimit if it is set.
	 * 
	 * @return
	 */
	public abstract boolean depleted();

	/**
	 * If an ActivationLimit is set, this Method will determine the Amount.
	 * 
	 * @param i limit of Activation
	 */
	public void setActivationLimit(int i) {
		this.activationLimit = i;
		if (activationLimit <= 0) {
			activ = true;
		}
	}

	public CreatureStats getTrapStats() {

		// Everything is initialized to 0.0
		return new CreatureStats();
	}
}
