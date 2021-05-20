package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIApproachTarget;
import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIMoveAround;
import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIStrategy;

public abstract class NPC extends Creature
{
	private AIStrategy currentBehavior;
	
	/**
	 * Creates an NPC entity at the default position of (0 | 0).
	 * When the NPC is spawned into the level, it will appear on that position.
	 */
	public NPC()
	{
		super();
	}
	
	/**
	 * Creates an NPC entity at the specified position.
	 * When the NPC is spawned into the level, it will appear on that position.
	 * @param x the position on the x-axis
	 * @param y the position on the y-axis
	 */
	public NPC(float x, float y)
	{
		super(x, y);
	}
	
	public final void setAIStrategy(AIStrategy newBehavior)
	{
		this.currentBehavior = newBehavior;
	}
	
	public final AIStrategy getCurrentAIStrategy()
	{
		return this.currentBehavior;
	}
	
	public void calculateCurrentBehavior()
	{
		//Default implementation for monsters
		List<Entity> entities = this.level.getEntitiesInRadius(this.getX(), this.getY(), 999999);
		Player aPlayer = null;
		for(int i = 0; i < entities.size(); ++i)
		{
			if(entities.get(i) instanceof Player)
			{
				aPlayer = (Player)entities.get(i);
				break;
			}
		}
		if(aPlayer == null)
		{
			//Can happen if the player dies?
			return;
		}
		if(this.currentBehavior == null)
		{
			this.currentBehavior = new AIMoveAround();
		}
		else if(this.hasLineOfSightTo(getPosition()) && !(this.currentBehavior instanceof AIApproachTarget))
		{
			this.setAIStrategy(new AIApproachTarget(aPlayer));
			System.out.println("efef");
		}
	}
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		
		this.calculateCurrentBehavior();
		
		if(this.currentBehavior != null)
		{
			this.currentBehavior.executeStrategy(this);
		}
	}
}
