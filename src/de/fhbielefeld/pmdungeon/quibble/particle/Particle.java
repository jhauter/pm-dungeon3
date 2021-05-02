package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;

public abstract class Particle
{
	/**
	 * Rotation pivot on the x axis.
	 */
	protected float originX = 0.5F;
	
	/**
	 * Rotation pivot on the y axis.
	 */
	protected float originY = 0.5F;
	
	private final float spawnX;
	private final float spawnY;
	
	protected float timeExisted;
	
	protected ParticleMovement particleMovement;
	
	private ParticleSource particleSource;

	private final float diffSourceX;
	private final float diffSourceY;
	
	/**
	 * @param spawnX the x coordinate to appear on
	 * @param spawnY the y coordinate to appear on
	 * @param particleSource optional particle source that defines an object
	 * with which this particle keep a constant offset with
	 */
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
	
	/**
	 * Called every frame to calculate logic of the particle.
	 * @param delta time in seconds that the last frame took
	 */
	protected void update(float delta)
	{
		this.timeExisted += delta;
	}
	
	/**
	 * @return the x coordinate where this particle spawned
	 */
	public final float getSpawnX()
	{
		return this.spawnX;
	}
	
	/**
	 * @return the y coordinate where this particle spawned
	 */
	public final float getSpawnY()
	{
		return this.spawnY;
	}
	
	/**
	 * @return particle source that defines an object
	 * with which this particle keep a constant offset with (can be null)
	 */
	public ParticleSource getParticleSource()
	{
		return this.particleSource;
	}
	
	/**
	 * @return the particle's x offset to the particle source when the particle spawned
	 */
	public float getSourceDiffX()
	{
		return this.diffSourceX;
	}
	
	/**
	 * @return the particle's y offset to the particle source when the particle spawned
	 */
	public float getSourceDiffY()
	{
		return this.diffSourceY;
	}
	
	/**
	 * @return the texture region that is used to render the particle
	 */
	public abstract DungeonResource<TextureRegion> getTexture();
	
	/**
	 * @return whether the particle should despawn on the next update
	 */
	public abstract boolean shouldDisappear();
	
	/**
	 * @return the width of the particle
	 */
	public abstract float getWidth();
	
	/**
	 * @return the height of the particle
	 */
	public abstract float getHeight();
}
