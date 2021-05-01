package de.fhbielefeld.pmdungeon.quibble.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;

public class ParticleSystem
{
	public static final Random RNG = new Random();
	
	private List<Particle> particles;
	
	/**
	 * Creates a particle system.
	 */
	public ParticleSystem()
	{
		this.particles = new ArrayList<Particle>();
	}
	
	/**
	 * Adds a particle to the system. The specified <code>ParticleMovement</code> will determine the movement.
	 * @param particle the particle to add
	 * @param movement the movement for the particle
	 */
	public void addParticle(Particle particle, ParticleMovement movement)
	{
		movement.originValues(particle.getSpawnX(), particle.getSpawnY());
		particle.particleMovement = movement;
		this.particles.add(particle);
	}
	
	/**
	 * Updates all particles.
	 * @param delta time in seconds that the last frame took
	 */
	public void update(float delta)
	{
		Particle currentParticle;
		for(int i = 0; i < this.particles.size(); ++i)
		{
			if(this.particles.get(i).shouldDisappear())
			{
				this.particles.remove(i);
				--i;
			}
			else
			{
				currentParticle = this.particles.get(i);
				currentParticle.update(delta);
			}
		}
	}
	
	/**
	 * Draws all particles.
	 * @param camX x position of the dungeon camera
	 * @param camY y position of the dungeon camera
	 */
	public void draw(float camX, float camY)
	{
		float x, y, width, height, rot, srcOffsetX, srcOffsetY;
		DungeonResource<TextureRegion> particleTexture;
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		for(Particle p : this.particles)
		{
			particleTexture = p.getTexture();
			if(!particleTexture.isLoaded())
			{
				continue;
			}
			if(p.getParticleSource() != null)
			{
				srcOffsetX = (p.getParticleSource().getX() + p.getSourceDiffX()) - p.getSpawnX();
				srcOffsetY = (p.getParticleSource().getY() + p.getSourceDiffY()) - p.getSpawnY();
			}
			else
			{
				srcOffsetX = 0.0F;
				srcOffsetY = 0.0F;
			}
			x = DrawingUtil.dungeonToScreenXCam(p.particleMovement.getOffsetX(p.timeExisted) + p.getSpawnX() + srcOffsetX, camX);
			y = DrawingUtil.dungeonToScreenYCam(p.particleMovement.getOffsetY(p.timeExisted) + p.getSpawnY() + srcOffsetY, camY);
			width = DrawingUtil.dungeonToScreenX(p.getWidth());
			height = DrawingUtil.dungeonToScreenY(p.getHeight());
			rot = p.particleMovement.getRotation(p.timeExisted);
			
			//SpriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
			batch.draw(particleTexture.getResource(), x - width * 0.5F, y - height * 0.5F, p.originX * width, p.originY * height, width, height, p.particleMovement.getScaleX(p.timeExisted), p.particleMovement.getScaleY(p.timeExisted), rot);
			
		}
		batch.end();
		batch.flush();
	}
	
	/**
	 * Removes all particles from the system.
	 */
	public void clearParticles()
	{
		this.particles.clear();
	}
}
