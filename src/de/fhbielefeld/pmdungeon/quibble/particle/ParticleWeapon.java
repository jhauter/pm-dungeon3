package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParticleWeapon extends Particle
{
	public static enum Type
	{
		/**
		 * Displays a sword weapon.
		 */
		SWORD(ParticleSystem.textureSwordBlue);
		
		private final TextureRegion tex;
		
		private Type(TextureRegion tex)
		{
			this.tex = tex;
		}
	}
	
	private final Type type;
	
	private final float visibleTime;
	
	private final float width;
	private final float height;
	
	/**
	 * @param type the weapon to display
	 * @param width the width of the weapon
	 * @param height the height of the weapon
	 * @param visibeTime the time the weapon should be visible
	 * @param spawnX the x coordinate to appear on
	 * @param spawnY the y coordinate to appear on
	 * @param particleSource optional particle source that defines an object
	 * with which this particle keep a constant offset with
	 */
	public ParticleWeapon(Type type, float width, float height, float visibeTime, float spawnX, float spawnY, ParticleSource particleSource)
	{
		super(spawnX, spawnY, particleSource);
		this.type = type;
		this.originX = 0.5F;
		this.originY = 0.0F;
		this.visibleTime = visibeTime;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDisappear()
	{
		return this.timeExisted > this.visibleTime;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextureRegion getTexture()
	{
		return this.type.tex;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getWidth()
	{
		return this.width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getHeight()
	{
		return this.height;
	}
}
