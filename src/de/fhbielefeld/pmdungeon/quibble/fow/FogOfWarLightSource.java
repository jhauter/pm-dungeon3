package de.fhbielefeld.pmdungeon.quibble.fow;

public class FogOfWarLightSource
{
	private final int x;
	private final int y;
	private final float intensity;
	
	private float deltaTime;
	
	private final LightSpreadFunction function;
	
	public FogOfWarLightSource(int x, int y, float intensity)
	{
		this(x, y, intensity, new DefaultLightSpreadFunction());
	}
	
	public FogOfWarLightSource(int x, int y, float intensity, LightSpreadFunction lightSpreadFunction)
	{
		this.x = x;
		this.y = y;
		this.intensity = intensity;
		this.function = lightSpreadFunction;
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
	
	public LightSpreadFunction getLightSpreadFunction()
	{
		return this.function;
	}
	
	public void addDeltaTime(float deltaTime)
	{
		this.deltaTime += deltaTime;
	}
	
	public float getDeltaTime()
	{
		return this.deltaTime;
	}
}
