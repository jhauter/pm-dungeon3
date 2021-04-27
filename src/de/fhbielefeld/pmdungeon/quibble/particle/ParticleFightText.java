package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParticleFightText extends Particle
{
	public static enum Type
	{
		/**
		 * Displays a "crit" particle.
		 */
		CRIT(0.9F, 0.9F),
		
		/**
		 * Displays a "miss" particle.
		 */
		MISS(0.9F, 0.9F),
		
		/**
		 * Displays a number particle. Requires the numberIndex to be set.
		 */
		NUMBER(0.5F, 0.5F);
		
		private final float width;
		private final float height;
		
		private Type(float width, float height)
		{
			this.width = width;
			this.height = height;
		}
	}
	
	private final Type type;
	
	private int numberIndex;
	
	/**
	 * @param type determines the text that should be displayed
	 * @param numberIndex if <code>type</code> is <code>NUMBER</code> this selects the number to show
	 * @param spawnX the x coordinate to spawn on
	 * @param spawnY the y coordinate to spawn on
	 * @param particleSource optional particle source that defines an object
	 * with which this particle keep a constant offset with 
	 */
	public ParticleFightText(Type type, int numberIndex, float spawnX, float spawnY, ParticleSource particleSource)
	{
		super(spawnX, spawnY, particleSource);
		this.type = type;
		this.numberIndex = numberIndex;
	}
	
	/**
	 * @param type determines the text that should be displayed
	 * @param spawnX the x coordinate to spawn on
	 * @param spawnY the y coordinate to spawn on
	 * @param particleSource optional particle source that defines an object
	 * with which this particle keep a constant offset with 
	 */
	public ParticleFightText(Type type, float spawnX, float spawnY, ParticleSource particleSource)
	{
		this(type, -1, spawnX, spawnY, particleSource);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDisappear()
	{
		return this.timeExisted > 0.5F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextureRegion getTexture()
	{
		if(this.type == Type.CRIT)
		{
			return ParticleSystem.textureCrit;
		}
		else if(this.type == Type.MISS)
		{
			return ParticleSystem.textureMiss;
		}
		else
		{
			return ParticleSystem.textureNumbers[this.numberIndex];
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getWidth()
	{
		return this.type.width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getHeight()
	{
		return this.type.height;
	}
}
