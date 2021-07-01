package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;

/**
 * Class representing a circular area on the ground that triggers after a specific amount of time
 */
public abstract class GroundAoe extends Entity implements DamageSource
{
	
	protected int ticksUntilAction;
	
	/**
	 * Time it takes for the aoe to despawn after having been triggered. Used to play animations etc
	 */
	protected int ticksUntilRemove = 20;
	
	protected boolean finished = false;
	
	protected int actionCounter = 0;
	protected int radius = 1;
	
	public boolean shouldDespawn = false;
	
	protected Entity target = null;
	
	public Entity getTarget()
	{
		return target;
	}
	
	/**
	 * @param target Sets the target of this action.
	 */
	public void setTarget(Entity target)
	{
		this.target = target;
	}
	
	/**
	 * Called if aoe is active but not yet triggered
	 */
	protected abstract void onRoam();
	
	/**
	 * Called on trigger
	 */
	protected abstract void onTrigger();
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		actionCounter++;
		if(finished)
		{
			if(actionCounter >= ticksUntilRemove)
			{
				this.shouldDespawn = true;
			}
			else
			{
				return;
			}
		}
		if(actionCounter >= ticksUntilAction)
		{
			onTrigger();
			actionCounter = 0;
			this.finished = true;
		}
		else
		{
			onRoam();
		}
	}
	
	@Override
	public CreatureStats getCurrentStats()
	{
		//?
		return new CreatureStats();
	}
	
	@Override
	public boolean shouldDespawn()
	{
		return this.shouldDespawn;
	}
	
}
