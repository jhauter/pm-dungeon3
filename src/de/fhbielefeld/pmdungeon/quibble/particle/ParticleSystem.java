package de.fhbielefeld.pmdungeon.quibble.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParticleSystem
{
	public static TextureRegion textureCrit;
	public static TextureRegion textureMiss;
	public static TextureRegion[] textureNumbers = new TextureRegion[10];
	public static TextureRegion textureSwordBlue;
	
	public static final Random RNG = new Random();
	
	public static void loadTextures()
	{
		textureCrit = new TextureRegion(new Texture("assets/textures/particle/crit.png"));
		textureMiss = new TextureRegion(new Texture("assets/textures/particle/miss.png"));
		for(int i = 0; i < 10; ++i)
		{
			textureNumbers[i] = new TextureRegion(new Texture("assets/textures/particle/" + i + ".png"));
		}
		textureSwordBlue = new TextureRegion(new Texture("assets/textures/weapon/sword.png"));
	}
	
	private List<Particle> particles;
	
	public ParticleSystem()
	{
		this.particles = new ArrayList<Particle>();
	}
	
	public void addParticle(Particle particle, ParticleMovement movement)
	{
		movement.originValues(particle.getSpawnX(), particle.getSpawnY());
		particle.particleMovement = movement;
		this.particles.add(particle);
	}
	
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
	
	public void draw(float camX, float camY)
	{
		float x, y, width, height, rot, srcOffsetX, srcOffsetY;
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		for(Particle p : this.particles)
		{
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
			batch.draw(p.getTexture(), x - width * 0.5F, y - height * 0.5F, p.originX * width, p.originY * height, width, height, p.particleMovement.getScaleX(p.timeExisted), p.particleMovement.getScaleY(p.timeExisted), rot);
			
		}
		batch.end();
		batch.flush();
	}
	
	public void clearParticles()
	{
		this.particles.clear();
	}
}
