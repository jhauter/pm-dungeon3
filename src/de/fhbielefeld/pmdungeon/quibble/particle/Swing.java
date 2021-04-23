package de.fhbielefeld.pmdungeon.quibble.particle;

public class Swing implements ParticleMovement
{
	public static enum SwingOrientation
	{
		RIGHT(1.0F),
		LEFT(-1.0F);
		
		private final float scaleX;
		
		private SwingOrientation(float scaleX)
		{
			this.scaleX = scaleX;
		}
	}
	
	private float swingSpeed;
	
	private SwingOrientation orientation;
	
	public Swing(SwingOrientation orientation, float speed)
	{
		this.orientation = orientation;
		this.swingSpeed = speed;
	}
	
	public Swing()
	{
		this(SwingOrientation.RIGHT, 1.0F);
	}
	
	@Override
	public void originValues(float x, float y)
	{
		
	}
	
	@Override
	public float getRotation(float time)
	{
		return time * 100.0F * this.swingSpeed * -this.orientation.scaleX;
	}
	
	@Override
	public float getOffsetX(float time)
	{
		return 0.0F;
	}
	
	@Override
	public float getOffsetY(float time)
	{
		return 0.0F;
	}
	
	@Override
	public float getScaleX(float time)
	{
		return this.orientation.scaleX;
	}
	
	@Override
	public float getScaleY(float time)
	{
		return 1.0F;
	}
}
