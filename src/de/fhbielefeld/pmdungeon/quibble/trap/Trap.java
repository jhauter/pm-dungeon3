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
	protected boolean isActivationLimit = true;
	protected boolean depleted;
	protected int activationLimit;

	/**
	 * Creates a trap on a certain position
	 * 
	 * @param x               the x value of the current level
	 * @param y               the y value of the current level
	 * @param texture         the displayed texture for the trap
	 * @param activationLimit if there is a limit or not
	 */
	public Trap(float x, float y, boolean isActivationLimit) {
		super(x, y);
	}

	/**
	 * Creates a trap on a certain position
	 * 
	 * @param x               the x value of the current level
	 * @param y               the y value of the current level
	 * @param texture         the displayed texture for the trap
	 * @param activationLimit will set a Number how often this Trap will get
	 *                        activated
	 */
	public Trap(float x, float y, int activationLimit) {
		super(x, y);
	}

	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		isInvisible();
		if (depleted())
			return;
		else if (otherEntity instanceof Player) {
			isActiv(otherEntity);
			setActivationLimit(activationLimit - 1);
			activ = true;
		}
	}

	/**
	 * If the Trap is active
	 * 
	 * @param e The Entity which will be effect
	 */
	public abstract void isActiv(Entity e);

	/**
	 * Will inform the Trap that it is on the ActivationLimit if it is set.
	 * 
	 * @return
	 */
	public boolean depleted() {
		return depleted;
	}

	/**
	 * If an ActivationLimit is set, this Method will determine the Amount.
	 * 
	 * @param i limit of Activation
	 */
	public void setActivationLimit(int i) {
		this.activationLimit = i;
		if (activationLimit <= 0 && isActivationLimit) {
			depleted = true;
		}
	}

	public CreatureStats getTrapStats() {

		// Everything is initialized to 0.0
		return new CreatureStats();
	}

	@Override
	public boolean isInvisible() {
		if (!(activ))
			return true;
		return false;
	}

	@Override
	public boolean deleteableWorkaround() {
		if(depleted)
			return true;
		return false;
	}
}
