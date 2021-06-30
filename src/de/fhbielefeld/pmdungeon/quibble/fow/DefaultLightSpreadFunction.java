package de.fhbielefeld.pmdungeon.quibble.fow;

/**
 * A light spread function that makes light travel about 4 dungeon tiles
 * and should be suitable for most cases.
 * @author Andreas
 */
public class DefaultLightSpreadFunction implements LightSpreadFunction
{
	@Override
	public float getLightIntensityFallOff(int dist, float fogTileSize)
	{
		return (float)(fogTileSize * 0.015F * Math.pow(dist * fogTileSize, 2));
	}

	@Override
	public int getMaxDist(float fogTileSize)
	{
		return (int)(8 / fogTileSize);
	}
	
	@Override
	public float getLightIntensityFallOffBlocked(int dist, float fogTileSize)
	{
		return fogTileSize * 1.5F;
	}

	@Override
	public float getIntensityOverTime(float startIntensity, float delta)
	{
		return startIntensity - delta;
	}
}
