package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public abstract class Trap extends Entity
{
	public final static String TRAP_TEXTURE_PATH = "assets/textures/traps/";
	
	private boolean shouldDespawn;
	
	private boolean visible;
	
	private int ticksVisibleRemaining;
	
	private int activationLimit = 0;//0 => unlimited activations
	
	private int activationCount;
	
	private int coolDown;
	
	public Trap(float x, float y)
	{
		super(x, y);
	}
	
	@Override
	protected final void onEntityCollision(Entity otherEntity)
	{
		super.onEntityCollision(otherEntity);
		if(otherEntity instanceof Player && coolDown <= 0)
		{
			onPlayerStepOnTrap((Player)otherEntity);
			this.coolDown = this.getCoolDownTicks();
			if(this.revealWhenSteppedOn())
			{
				this.visible = true;
			}
			++this.activationCount;
		}
	}
	
	/**
	 * Called when a player steps on the trap.
	 * @param p the player that activated the trap
	 */
	public abstract void onPlayerStepOnTrap(Player p);
	
	/**
	 * @return time in ticks until the trap can be activated again
	 */
	public abstract int getCoolDownTicks();
	
	/**
	 * @return <code>true</code> if the trap should become visible when a player activates it
	 */
	public boolean revealWhenSteppedOn()
	{
		return true;
	}
	
	/**
	 * Sets how many times the trap can be activated until it despawns.
	 * <code>0</code> means the trap can be activated infinitely.
	 * @param i the number of remaining activations or <code>0</code> for unlimited activations
	 */
	public void setActivationLimit(int i)
	{
		this.activationLimit = i;
	}
	
	@Override
	public boolean isInvisible()
	{
		return !visible;
	}
	
	@Override
	public boolean shouldDespawn()
	{
		return shouldDespawn;
	}
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		if(coolDown > 0)
		{
			coolDown--;
		}
		
		if(this.activationLimit > 0 && this.activationCount >= this.activationLimit)
		{
			shouldDespawn = true;
		}
		
		if(ticksVisibleRemaining > 0)
		{
			ticksVisibleRemaining--;
			if(ticksVisibleRemaining == 0)
			{
				visible = false;
			}
		}
	}
	
	/**
	 * @param b whether the trap should be visible
	 */
	public void setVisible(boolean b)
	{
		this.visible = b;
	}
	
	/**
	 * Makes the trap visible for a set number of ticks.
	 * @param visibleTicks number of ticks until the trap vanishes again
	 */
	public void setVisible(int visibleTicks)
	{
		this.visible = true;
		this.ticksVisibleRemaining = visibleTicks;
	}
	
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.4F, -0.4F, 0.8F, 0.8F);
	}
}
