package de.fhbielefeld.pmdungeon.quibble.fow;

public class FogOfWarLightSource
{
	private final int x;
	private final int y;
	private final float intensity;
	
	private float deltaTime;
	
	private final LightSpreadFunction function;
	
	/**
	 * Same as {@link #FogOfWarLightSource(int, int, float, LightSpreadFunction) FogOfWarLightSource}
	 * {@code (x, y, intensity, new DefaultLightSpreadFunction())}
	 */
	public FogOfWarLightSource(int x, int y, float intensity)
	{
		this(x, y, intensity, new DefaultLightSpreadFunction());
	}
	
	/**
	 * Creates a light source for a <code>FogOfWarController</code>
	 * @param x x-position in fog units
	 * @param y y-position in fog units
	 * @param intensity intensity of the light source
	 * @param lightSpreadFunction <code>LightSpreadFunction</code> to use for this light source
	 */
	public FogOfWarLightSource(int x, int y, float intensity, LightSpreadFunction lightSpreadFunction)
	{
		this.x = x;
		this.y = y;
		this.intensity = intensity;
		this.function = lightSpreadFunction;
	}
	
	/**
	 * @return x-position of this light source in fog units
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return y-position of this light source in fog units
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * @return the intensity of this light source
	 */
	public float getIntensity()
	{
		return intensity;
	}
	
	/**
	 * @return the <code>LightSpreadFunction</code> that this light source uses
	 */
	public LightSpreadFunction getLightSpreadFunction()
	{
		return this.function;
	}
	
	/**
	 * Adds time to the time counter that counts how long this light source has existed
	 * @param deltaTime time to add in seconds
	 */
	public void addDeltaTime(float deltaTime)
	{
		this.deltaTime += deltaTime;
	}
	
	/**
	 * @return time in seconds since this light source has been created
	 */
	public float getDeltaTime()
	{
		return this.deltaTime;
	}
}
