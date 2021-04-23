package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParticleFightText extends Particle
{
	public static enum Type
	{
		CRIT(0.9F, 0.9F),
		MISS(0.9F, 0.9F),
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
	
	public ParticleFightText(Type type, int numberIndex, float spawnX, float spawnY, ParticleSource particleSource)
	{
		super(spawnX, spawnY, particleSource);
		this.type = type;
		this.numberIndex = numberIndex;
	}
	
	public ParticleFightText(Type type, float spawnX, float spawnY, ParticleSource particleSource)
	{
		this(type, -1, spawnX, spawnY, particleSource);
	}

	@Override
	public boolean shouldDisappear()
	{
		return this.timeExisted > 0.5F;
	}

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

	@Override
	public float getWidth()
	{
		return this.type.width;
	}

	@Override
	public float getHeight()
	{
		return this.type.height;
	}
}
