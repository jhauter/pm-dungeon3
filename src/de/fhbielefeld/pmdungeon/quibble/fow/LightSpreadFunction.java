package de.fhbielefeld.pmdungeon.quibble.fow;

public interface LightSpreadFunction
{
	/**
	 * @param dist number of fog tiles that the light has traveled
	 * @param fogTileSize size of a fog tile in dungeon units
	 * @return light intensity decrease per fog tile
	 */
	public float getLightIntensityFallOff(int dist, float fogTileSize);
	
	/**
	 * @param dist number of fog tiles that the light has traveled
	 * @param fogTileSize size of a fog tile in dungeon units
	 * @return light intensity decrease per fog tile when it is inside a wall
	 */
	public float getLightIntensityFallOffBlocked(int dist, float fogTileSize);
	
	/**
	 * 
	 * @param fogTileSize size of a fog tile in dungeon units
	 * @return maximum nulber of tiles that the light can travel
	 */
	public int getMaxDist(float fogTileSize);
	
	/**
	 * @param startIntensity intensity that the light source had when it was created
	 * @param delta time in seconds since the light source has been created
	 * @return intensity of a light source over time
	 */
	public float getIntensityOverTime(float startIntensity, float delta);
}
