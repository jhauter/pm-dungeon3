package de.fhbielefeld.pmdungeon.quibble;

public class FogOfWarLightSource
{
	private final int x;
	private final int y;
	private final float intensity;
	
	public FogOfWarLightSource(int x, int y, float intensity)
	{
		this.x = x;
		this.y = y;
		this.intensity = intensity;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public float getIntensity()
	{
		return intensity;
	}
}
