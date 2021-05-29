package de.fhbielefeld.pmdungeon.quibble.entity;

/**
 * This class is used for collision detection.
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
	
	/**
	 * Tests if this bounding box overlaps with another one.
	 * @param other the other bounding box
	 * @return true if the two boxes overlap
	 */
	public boolean intersects(BoundingBox other)
	{
		return this.x < other.x + other.width && this.x + this.width > other.x &&
			   this.y + this.height > other.y && this.y < other.y + other.height;
	}
	
	/**
	 * Creates a new bounding box by applying an offset to this bounding box.
	 * @param x offset x
	 * @param y offset y
	 * @return a new bounding box relative to this bounding box by the specified offset
	 */
	public BoundingBox offset(float x, float y)
	{
		return new BoundingBox(this.x + x, this.y + y, this.width, this.height);
	}
	
	/**
	 * Creates a new bounding box by growing it while keeping it centered.
	 * The added length is distributed on each side equally.
	 * @param x length added to the width
	 * @param y length added to the height
	 * @return a new bounding box relative to this bounding box by the specified offset
	 */
	public BoundingBox grow(float x, float y)
	{
		return new BoundingBox(this.x - x * 0.5F, this.y - y * 0.5F, this.width + x * 2.0F, this.height + y * 2.0F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "BoundingBox[" + this.x + ", " + this.y + ", " + this.width + ", " + this.height + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + Float.floatToIntBits(width);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		BoundingBox other = (BoundingBox)obj;
		if(Float.floatToIntBits(height) != Float.floatToIntBits(other.height)) return false;
		if(Float.floatToIntBits(width) != Float.floatToIntBits(other.width)) return false;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) return false;
		if(Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) return false;
		return true;
	}
}
