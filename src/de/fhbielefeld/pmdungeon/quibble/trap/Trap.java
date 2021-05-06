package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;

public abstract class Trap extends Entity implements DamageSource {
	
	public final static String TRAP_TEXTURE_PATH= "assets/textures/traps/";

	protected boolean noActivationLimit;

	protected boolean depleted;
	
	protected boolean activated;

	protected int activationLimit;

	protected int coolDown;
	
	/**
	 * Creates a trap on a certain position
	 * 
	 * @param x               the x value of the current level
	 * @param y               the y value of the current level
	 * @param texture         the displayed texture for the trap
	 * @param activationLimit true if a trap doesn't has a ActivationLimit
	 */
	public Trap(float x, float y, boolean noActivationLimit) {
		super(x, y);
		this.noActivationLimit = noActivationLimit;
	}

	/**
	 * Creates a trap on a certain position
	 * 
	 * @param x               the x value of the current level
	 * @param y               the y value of the current level
	 * @param texture         the displayed texture for the trap
	 * @param activationLimit will set a Number how often this Trap will get
	 *                        activated till its depleted
	 */
	public Trap(float x, float y, int activationLimit) {
		super(x, y);
		this.activationLimit = activationLimit;
	}

	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (coolDown > 0)
			return;
		else if (coolDown <= 0) {
			isActiv(otherEntity);
		}
	}

	/**
	 * If the Trap is active
	 * 
	 * @param e The Entity which will be effect
	 */
	public abstract void isActiv(Entity e);

	/**
	 * Is called if activation limit is reached
	 * 
	 * @return
	 */
	public boolean depleted() {
		return depleted;
	}

	/**
	 * Set the Number of activations
	 * 
	 * @param i limit of Activation
	 */
	public void setActivationLimit(int i) {
		this.activationLimit = i;
	}

	@Override
	public boolean isInvisible() {
		return activated;
	}

	@Override
	public boolean deleteableWorkaround() {
		return depleted;
	}

	@Override
	public abstract CreatureStats getCurrentStats();

	@Override
	protected void updateLogic() {
		super.updateLogic();
		if (coolDown > 0)
			coolDown--;
		
		if(activationLimit < 0 && (!(noActivationLimit))) 
			depleted = true;
		
		System.out.println(isInvisible());
	}
	
	/**
	 * 
	 * @param b true for visible traps
	 */
	public void setActivated(boolean b) {
		this.activated = b;
	}

}
