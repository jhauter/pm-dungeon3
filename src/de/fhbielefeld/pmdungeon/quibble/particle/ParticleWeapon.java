package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParticleWeapon extends Particle
{
	public static enum Type
	{
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

	@Override
	public boolean shouldDisappear()
	{
		return this.timeExisted > this.visibleTime;
	}

	@Override
	public TextureRegion getTexture()
	{
		return this.type.tex;
	}

	@Override
	public float getWidth()
	{
		return this.width;
	}

	@Override
	public float getHeight()
	{
		return this.height;
	}
}
