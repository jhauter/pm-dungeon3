package de.fhbielefeld.pmdungeon.quibble.entity;

/**
 * This class is not yet used but will be used for complex collision detection.
 * @author Andreas
 */
public class BoundingBox
{
	/**
	 * X-Coordinate of this <code>BoundingBox</code>.
	 * Measured in DungeonWorld units.
	 */
	public final float x;
	
	/**
	 * Y-Coordinate of this <code>BoundingBox</code>.
	 * Measured in DungeonWorld units.
	 */
	public final float y;
	
	/**
	 * Width of this <code>BoundingBox</code>.
	 * Measured in DungeonWorld units.
	 */
	public final float width;
	
	/**
	 * Height of this <code>BoundingBox</code>.
	 * Measured in DungeonWorld units.
	 */
	public final float height;
	
	/**
	 * Creates a <code>BoundingBox</code> with the specified position and dimension.
	 * All values are measured in DungeonWorld units.
	 * (X | Y) is the bottom left corner of the <code>BoundingBox</code>.
	 * @param x x position
	 * @param y y position
	 * @param width bounding box width
	 * @param height bounding box height
	 */
	public BoundingBox(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
