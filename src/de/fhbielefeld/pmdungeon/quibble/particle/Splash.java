package de.fhbielefeld.pmdungeon.quibble.particle;

public class Splash implements ParticleMovement
{
	private final float upBoost;
	private final float dragDown;
	private final float driftOff;
	
	public Splash()
	{
		this.upBoost = ParticleSystem.RNG.nextFloat() * 2.0F + 4.0F;
		this.driftOff = (float)ParticleSystem.RNG.nextGaussian() * 0.6F;
		this.dragDown = 8.0F;
	}
	
	public Splash(float upBoost, float dragDown, float driftOff)
	{
		this.upBoost = upBoost;
		this.dragDown = dragDown;
		this.driftOff = driftOff;
	}
	
	@Override
	public void originValues(float x, float y)
	{
		
	}
	
	@Override
	public float getRotation(float time)
	{
		return 0.0F;
	}
	
	@Override
	public float getOffsetX(float time)
	{
		return time * this.driftOff;
	}
	
	@Override
	public float getOffsetY(float time)
	{
		return -this.dragDown * time * time + this.upBoost * time;
	}
	
	@Override
	public float getScaleX(float time)
	{
		return 1.0F;
	}
	
	@Override
	public float getScaleY(float time)
	{
		return 1.0F;
	}
}
