package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.Texture;

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
	
	/**
	 * @param spawnX the x coordinate to appear on
	 * @param spawnY the y coordinate to appear on
	 */
	public Particle(float spawnX, float spawnY)
	{
		this.spawnX = spawnX;
		this.spawnY = spawnY;
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
	 * @return the texture that is used to render the particle
	 */
	public abstract DungeonResource<Texture> getTexture();
	
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
