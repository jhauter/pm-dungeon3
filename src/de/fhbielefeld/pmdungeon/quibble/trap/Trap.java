package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public abstract class Trap extends Entity
{
	
	public final static String TRAP_TEXTURE_PATH = "assets/textures/traps/";
	
	protected boolean noActivationLimit;
	
	protected boolean depleted;
	
	protected boolean visible;
	
	private double ticksTillInvisible;
	
	protected int activationLimit;
	
	protected int coolDown;
	
	/**
	 * Creates a trap on a certain position
	 * 
	 * @param x               the x value of the current level
	 * @param y               the y value of the current level
	 * @param texture         the displayed texture for the trap
	 * @param activationLimit true if a trap doesn't has a ActivationLimit
	 */
	public Trap(float x, float y, boolean noActivationLimit)
	{
		super(x, y);
		this.noActivationLimit = noActivationLimit;
	}
	
	/**
	 * Creates a trap on a certain position
	 * 
	 * @param x               the x value of the current level
	 * @param y               the y value of the current level
	 * @param texture         the displayed texture for the trap
	 * @param activationLimit will set a Number how often this Trap will get
	 *                        activated till its depleted
	 */
	public Trap(float x, float y, int activationLimit)
	{
		super(x, y);
		this.activationLimit = activationLimit;
	}
	
	@Override
	protected void onEntityCollision(Entity otherEntity)
	{
		super.onEntityCollision(otherEntity);
		if(coolDown > 0)
			return;
		else if(coolDown <= 0)
		{
			isActiv(otherEntity);
		}
	}
	
	/**
	 * If the Trap is active
	 * 
	 * @param e The Entity which will be effect
	 */
	public abstract void isActiv(Entity e);
	
	/**
	 * Is called if activation limit is reached
	 * 
	 * @return
	 */
	public boolean depleted()
	{
		return depleted;
	}
	
	/**
	 * Set the Number of activations
	 * 
	 * @param i limit of Activation
	 */
	public void setActivationLimit(int i)
	{
		this.activationLimit = i;
	}
	
	@Override
	public boolean isInvisible()
	{
		return (!(visible));
	}
	
	@Override
	public boolean shouldDespawn()
	{
		return depleted;
	}
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		if(coolDown > 0)
			coolDown--;
		
		if(activationLimit < 0 && (!(noActivationLimit)))
			depleted = true;
		
		if(ticksTillInvisible > 0)
		{
			ticksTillInvisible--;
			if(ticksTillInvisible == 0)
			{
				visible = false;
			}
		}
	}
	
	/**
	 * 
	 * @param b           true for visible traps
	 */
	public void setVisible(boolean b)
	{
		this.visible = b;
	}
	
	/**
	 * 
	 * @param b           true for visible traps
	 * @param timeOfSight Time until the trap becomes invisible again
	 */
	public void setVisible(boolean b, double timeOfSight)
	{
		this.visible = b;
		this.ticksTillInvisible = timeOfSight;
	}
	
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.4F, -0.4F, 0.8F, 0.8F);
	}
}
