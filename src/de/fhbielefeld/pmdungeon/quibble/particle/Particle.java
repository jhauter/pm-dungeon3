package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Particle
{
	protected float originX = 0.5F;
	protected float originY = 0.5F;
	
	private final float spawnX;
	private final float spawnY;
	
	protected float timeExisted;
	
	protected TextureRegion textureRegion;
	
	protected ParticleMovement particleMovement;
	
	private ParticleSource particleSource;

	private final float diffSourceX;
	private final float diffSourceY;
	
	public Particle(float spawnX, float spawnY, ParticleSource particleSource)
	{
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.particleSource = particleSource;
		if(this.particleSource != null)
		{
			this.diffSourceX = this.spawnX - this.particleSource.getX();
			this.diffSourceY = this.spawnY - this.particleSource.getY();
		}
		else
		{
			this.diffSourceX = 0.0F;
			this.diffSourceY = 0.0F;
		}
	}
	
	protected void update(float delta)
	{
		this.timeExisted += delta;
	}
	
	public final float getSpawnX()
	{
		return this.spawnX;
	}
	
	public final float getSpawnY()
	{
		return this.spawnY;
	}
	
	public ParticleSource getParticleSource()
	{
		return this.particleSource;
	}
	
	public float getSourceDiffX()
	{
		return this.diffSourceX;
	}
	
	public float getSourceDiffY()
	{
		return this.diffSourceY;
	}
	
	public abstract TextureRegion getTexture();
	
	public abstract boolean shouldDisappear();
	
	public abstract float getWidth();
	
	public abstract float getHeight();
}
