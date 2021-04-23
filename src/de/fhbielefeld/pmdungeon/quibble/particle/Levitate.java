package de.fhbielefeld.pmdungeon.quibble.particle;

public class Levitate implements ParticleMovement
{
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
		return 0.0F;
	}
	
	@Override
	public float getOffsetY(float time)
	{
		return time * 1.6F;
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
