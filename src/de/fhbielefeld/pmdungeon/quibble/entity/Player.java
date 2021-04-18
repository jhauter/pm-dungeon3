package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.quibble.input.InputListener;
import de.fhbielefeld.pmdungeon.quibble.input.KEY;
import de.fhbielefeld.pmdungeon.quibble.particle.Drop;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleFightText;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleSystem;

public abstract class Player extends Creature implements InputListener
{
	private boolean triggeredNextLevel;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Player(float x, float y)
	{
		super(x, y);
	}
	
	/**
	 * Creates a player entity with a default position
	 */
	public Player()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public void onInputRecieved(KEY key)
	{
		if(key == KEY.NO_KEY)
		{
			return;
		}
		this.walk(key.getAngle());
		if(key.getAngle() > 90 && key.getAngle() < 270)
		{
			this.setLookingDirection(LookingDirection.LEFT);
		}
		else if(key.getAngle() < 90 || key.getAngle() > 270)
		{
			this.setLookingDirection(LookingDirection.RIGHT);
		}
		//if it is exactly 90 or 270 do nothing
		//So that when you press up or down, the looking direction does not change
	}
	
	/**
	 * This must be called to reset the <code>triggeredNextLevel</code> flag
	 * directly after the next level has been loaded.
	 */
	public void onNextLevelEntered()
	{
		//IMPORTANT never forget to set this or else all levels will be loaded so
		//quickly that you're at the end level immediately.
		this.triggeredNextLevel = false;
	}
	
	/**
	 * Whether the <code>triggeredNextLevel</code> flag is set.
	 * This is set when the player steps on the ladder that leads to the next level
	 * and will cause the next level to load.
	 * @return the <code>triggeredNextLevel</code> flag
	 */
	public boolean triggeredNextLevel()
	{
		return this.triggeredNextLevel;
	}
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		if(this.level.getDungeon().getNextLevelTrigger() == this.level.getDungeon().getTileAt((int)this.getPosition().x, (int)this.getPosition().y))
		{
			this.triggeredNextLevel = true;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			Random r = new Random();
			for(int i = 0; i < 1; ++i)
			{
				this.level.getParticleSystem().addParticle(new ParticleFightText(ParticleFightText.Type.NUMBER, ParticleSystem.RNG.nextInt(10), this.getPosition().x + (r.nextFloat()  - 0.5F) * 0.1F, this.getPosition().y + r.nextFloat() * 0.1F + 0.5F), new Drop());
			}
		}
		
		/************ DEBUG ***********
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			Tile t = this.level.getDungeon().getTileAt((int)this.getPosition().x, (int)this.getPosition().y);
			this.level.spawnEntity(new DotRed(t.getX(), t.getY()));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.T))
		{
			Tile t = this.level.getDungeon().getNextLevelTrigger();
			Point pos = this.getPosition();
			System.out.println("Trigger Tile: (" + t.getX() + " | " + t.getY() + ")");
			System.out.println("Player Pos: (" + pos.x + " | " + pos.y + ")");
		}
		
		***********************************/
	}
}