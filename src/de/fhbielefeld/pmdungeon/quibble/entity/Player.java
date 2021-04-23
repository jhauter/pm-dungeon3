package de.fhbielefeld.pmdungeon.quibble.entity;

import de.fhbielefeld.pmdungeon.quibble.input.InputListener;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInput;

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
	public void onInputRecieved(DungeonInput input)
	{
		float ctrlX = input.getAxisScaleX();
		float ctrlY = input.getAxisScaleY();
		
		float angle = (float)Math.toDegrees(Math.atan2(ctrlY, ctrlX));
		
		this.walk(angle);
		if(angle > 90 && angle < 270)
		{
			this.setLookingDirection(LookingDirection.LEFT);
		}
		else if(angle < 90 || angle > 270)
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