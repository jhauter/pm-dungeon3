package de.fhbielefeld.pmdungeon.quibble.fow;

public interface LightSpreadFunction
{
	public float getLightIntensityFallOff(int dist, float fogTileSize);
	
	public float getLightIntensityFallOffBlocked(int dist, float fogTileSize);
	
	public int getMaxDist(float fogTileSize);
}
