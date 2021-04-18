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
	
	public Particle(float spawnX, float spawnY)
	{
		this.spawnX = spawnX;
		this.spawnY = spawnY;
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
	
	public abstract TextureRegion getTexture();
	
	public abstract boolean shouldDisappear();
	
	public abstract float getWidth();
	
	public abstract float getHeight();
}
