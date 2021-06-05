package de.fhbielefeld.pmdungeon.quibble.particle;

public class Splash implements ParticleMovement
{
	private final float upBoost;
	private final float dragDown;
	private final float driftOff;
	
	/**
	 * Creates a Splash movement instance with default values (with RNG).
	 */
	public Splash()
	{
		this.upBoost = ParticleSystem.RNG.nextFloat() * 2.0F + 4.0F;
		this.driftOff = (float)ParticleSystem.RNG.nextGaussian() * 0.6F;
		this.dragDown = 8.0F;
	}
	
	/**
	 * @param upBoost control of the upward force at the beginning
	 * @param dragDown control of the drag down force
	 * @param driftOff controls how much the particle drifts off to the side
	 */
	public Splash(float upBoost, float dragDown, float driftOff)
	{
		this.upBoost = upBoost;
		this.dragDown = dragDown;
		this.driftOff = driftOff;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getRotation(float time)
	{
		return 0.0F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getOffsetX(float time)
	{
		return time * this.driftOff;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getOffsetY(float time)
	{
		return -this.dragDown * time * time + this.upBoost * time;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getScaleX(float time)
	{
		return 1.0F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getScaleY(float time)
	{
		return 1.0F;
	}
}
