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
	
	private float rotMult;
	
	private float rotOffset;
	
	private SwingOrientation orientation;
	
	/**
	 * @param orientation the swing direction
	 * @param speed the swing speed
	 * @param reverseSwing whether to swing in the opposite direction
	 * @param rotOffset rotation starting point in degrees
	 */
	public Swing(SwingOrientation orientation, float speed, boolean reverseSwing, float rotOffset)
	{
		this.orientation = orientation;
		this.swingSpeed = speed;
		this.rotMult = reverseSwing ? -1 : 1;
		this.rotOffset = rotOffset;
	}
	
	/**
	 * @param orientation values < 0 mean swing left; values >= 0 mean swing right
	 * @param speed the swing speed
	 * @param reverseSwing whether to swing in the opposite direction
	 * @param rotOffset rotation starting point in degrees
	 */
	public Swing(int orientation, float speed, boolean reverseSwing, float rotOffset)
	{
		this(orientation < 0 ? SwingOrientation.LEFT : SwingOrientation.RIGHT, speed, reverseSwing, rotOffset);
	}
	
	/**
	 * @param orientation the swing direction
	 * @param speed the swing speed
	 * @param reverseSwing whether to swing in the opposite direction
	 */
	public Swing(SwingOrientation orientation, float speed, boolean reverseSwing)
	{
		this.orientation = orientation;
		this.swingSpeed = speed;
		this.rotMult = reverseSwing ? -1 : 1;
	}
	
	/**
	 * @param orientation values < 0 mean swing left; values >= 0 mean swing right
	 * @param speed the swing speed
	 * @param reverseSwing whether to swing in the opposite direction
	 */
	public Swing(int orientation, float speed, boolean reverseSwing)
	{
		this(orientation < 0 ? SwingOrientation.LEFT : SwingOrientation.RIGHT, speed, reverseSwing);
	}
	
	/**
	 * @param orientation values < 0 mean swing left; values >= 0 mean swing right
	 * @param speed the swing speed
	 */
	public Swing(int orientation, float speed)
	{
		this(orientation, speed, false);
	}
	
	/**
	 * Creates a swing movement that swings to the right.
	 */
	public Swing()
	{
		this(SwingOrientation.RIGHT, 1.0F, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getRotation(float time)
	{
		return this.rotOffset * this.orientation.scaleX + time * 100.0F * this.swingSpeed * -this.orientation.scaleX * this.rotMult;
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
