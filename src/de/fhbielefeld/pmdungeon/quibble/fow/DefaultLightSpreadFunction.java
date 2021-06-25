package de.fhbielefeld.pmdungeon.quibble.fow;

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
}
