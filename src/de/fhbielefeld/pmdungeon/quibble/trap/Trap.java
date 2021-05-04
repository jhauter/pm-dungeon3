package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

public abstract class Trap extends Entity{
	
	private String texture;
	
	/**
	 * Creates a trap on a certain position
	 * @param x the x value of the current level
	 * @param y the y value of the current level
	 * @param texture the displayed texture for the trap
	 */
	public Trap(float x, float y, String texture) {
		super(x,y);
		this.texture = texture;
	}
	
	/**
	 * Will be called if a Creature collide with the trap
	 * @param c certain Creature who will be effected by the trap
	 */
	public abstract void onActivated(Creature c);

	/**
	 * Some traps will only be activated once
	 * @return whether this trap was activated or not
	 */
	public abstract boolean isActivated();
	
	/**
	 * Some traps can be activated more then once
	 * @return An Integer which will count down. If the Limit is reached isActivated() will be true
	 */
	public abstract int activationLimit();
	
	/**
	 * The Texture which is used to render the traps
	 * Note the <code>TRAP_TEXTURE_PATH</code> constant,
	 * that will be put at the beginning of the texture path.
	 * @return texture to render the trap
	 */
	public abstract String getTexturePath();
	
	public CreatureStats getTrapStats() {
		
		//Everything is initialized to 0.0
		return new CreatureStats();
	}
}
