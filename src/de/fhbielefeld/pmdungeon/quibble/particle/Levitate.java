package de.fhbielefeld.pmdungeon.quibble.particle;

public class Levitate implements ParticleMovement
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void originValues(float x, float y)
	{
		
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
		return 0.0F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getOffsetY(float time)
	{
		return time * 1.6F;
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
