package de.fhbielefeld.pmdungeon.quibble.particle;

/**
 * Class that controls the movement of particles
 * @author Andreas
 */
public interface ParticleMovement
{
	/**
	 * @param time the time elapsed since the particle has spawned
	 * @return rotation in relation to time
	 */
	public float getRotation(float time);
	
	/**
	 * @param time the time elapsed since the particle has spawned
	 * @return offset from the spawning position on the x axis in relation to time
	 */
	public float getOffsetX(float time);
	
	/**
	 * @param time the time elapsed since the particle has spawned
	 * @return offset from the spawning position on the y axis in relation to time
	 */
	public float getOffsetY(float time);
	
	/**
	 * @param time the time elapsed since the particle has spawned
	 * @return x scale in relation to time
	 */
	public float getScaleX(float time);
	
	/**
	 * @param time the time elapsed since the particle has spawned
	 * @return y scale in relation to time
	 */
	public float getScaleY(float time);
}
