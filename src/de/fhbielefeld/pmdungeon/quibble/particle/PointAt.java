package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.math.Vector2;

public class PointAt implements ParticleMovement
{
	private final Vector2 origin;
	
	private final Vector2 point;
	
	/**
	 * @param origin the point where the object is at
	 * @param point the point that the object should point at
	 */
	public PointAt(Vector2 origin, Vector2 point)
	{
		this.origin = origin;
		this.point = point;
	}
	
	/**
	 * @param x1 x-coordinate of the object position
	 * @param y1 y-coordinate of the object position
	 * @param x2 x-coordinate of the target point
	 * @param y2 x-coordinate of the target point
	 */
	public PointAt(float x1, float y1, float x2, float y2)
	{
		this(new Vector2(x1, y1), new Vector2(x2, y2));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getRotation(float time)
	{
		return new Vector2(this.point.x - this.origin.x, this.point.y - this.origin.y).angleDeg();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getOffsetX(float time)
	{
		return 0.0F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getOffsetY(float time)
	{
		return 0.0F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getScaleX(float time)
	{
		return 1.0F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getScaleY(float time)
	{
		return 1.0F;
	}
}
