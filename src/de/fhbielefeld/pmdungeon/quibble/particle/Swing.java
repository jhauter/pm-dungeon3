package de.fhbielefeld.pmdungeon.quibble.particle;

public class Swing implements ParticleMovement
{
	public static enum SwingOrientation
	{
		/**
		 * Swings to the right.
		 */
		RIGHT(1.0F),
		
		/**
		 * Swings to the left.
		 */
		LEFT(-1.0F);
		
		private final float scaleX;
		
		private SwingOrientation(float scaleX)
		{
			this.scaleX = scaleX;
		}
	}
	
	private float swingSpeed;
	
	private SwingOrientation orientation;
	
	/**
	 * @param orientation the swing direction
	 * @param speed the swing speed
	 */
	public Swing(SwingOrientation orientation, float speed)
	{
		this.orientation = orientation;
		this.swingSpeed = speed;
	}
	
	/**
	 * Creates a swing movement that swings to the right.
	 */
	public Swing()
	{
		this(SwingOrientation.RIGHT, 1.0F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void originValues(float x, float y)
	{
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getRotation(float time)
	{
		return time * 100.0F * this.swingSpeed * -this.orientation.scaleX;
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
		return this.orientation.scaleX;
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
