package de.fhbielefeld.pmdungeon.quibble.particle;

/**
 * This interface can be used to make particles keep a constant offset with an object
 * like an entity.
 * @author Andreas
 */
public interface ParticleSource
{
	/**
	 * @return current x position
	 */
	public float getX();
	
	/**
	 * @return current y position
	 */
	public float getY();
}
