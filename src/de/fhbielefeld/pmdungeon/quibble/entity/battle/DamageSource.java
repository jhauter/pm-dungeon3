package de.fhbielefeld.pmdungeon.quibble.entity.battle;

/**
 * This interface represents anything that can damage creatures.
 * While <code>Creature</code> implements this interface, it
 * can be used to make creatures take damage from something that is
 * not a creature
 * @author Andreas
 */
public interface DamageSource
{
	/**
	 * The x position of the damage source which is used for knockback direction
	 * @return x position
	 */
	public float getX();
	
	/**
	 * The x position of the damage source which is used for knockback direction
	 * @return y position
	 */
	public float getY();
	
	/**
	 * The stat values of this damage source. This can be stat values of a creature
	 * or in case this is not a creature, it can be stat value that affect the damage calculations
	 * @return stat values used for damage calculations
	 */
	public CreatureStats getCurrentStats();
}
