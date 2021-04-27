package de.fhbielefeld.pmdungeon.quibble.particle;

import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;

public class DrawingUtil
{
	public static final int ORIGINAL_SCREEN_WIDTH = 640;
	public static final int ORIGINAL_SCREEN_HEIGHT = 480;

	public static final Supplier<Integer> CURRENT_SCREEN_WIDTH = () -> Gdx.graphics.getWidth();
	public static final Supplier<Integer> CURRENT_SCREEN_HEIGHT = () -> Gdx.graphics.getHeight();
	
	public static final float TILE_SCALE = 48.0F;
	
	//Bug: weapon particle not displayed correctly after screen resize
	
	/**
	 * Converts dungeon coordinates to screen coordinates.
	 * @param x the x coordinate
	 * @return the x coordinate in screen space
	 */
	public static float dungeonToScreenX(float x)
	{
		return x * TILE_SCALE * Gdx.graphics.getWidth() / ORIGINAL_SCREEN_WIDTH;
	}
	
	/**
	 * Converts dungeon coordinates to screen coordinates.
	 * @param y the y coordinate
	 * @return the y coordinate in screen space
	 */
	public static float dungeonToScreenY(float y)
	{
		return y * TILE_SCALE * Gdx.graphics.getHeight() / ORIGINAL_SCREEN_HEIGHT;
	}
	
	/**
	 * Converts dungeon coordinates to screen coordinates, taking into account the camera position.
	 * @param x the x coordinate in dungeon space
	 * @param camX the x coordinate of the camera in dungeon space
	 * @return the x coordinate in screen space
	 */
	public static float dungeonToScreenXCam(float x, float camX)
	{
		return dungeonToScreenX(x - camX) + CURRENT_SCREEN_WIDTH.get() / 2.0F;
	}
	/**
	 * Converts dungeon coordinates to screen coordinates, taking into account the camera position.
	 * @param y the y coordinate in dungeon space
	 * @param camY the y coordinate of the camera in dungeon space
	 * @return the y coordinate in screen space
	 */
	public static float dungeonToScreenYCam(float y, float camY)
	{
		return dungeonToScreenX(y - camY) + CURRENT_SCREEN_HEIGHT.get() / 2.0F;
	}
}
