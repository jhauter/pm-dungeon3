package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

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
		this.setAIStrategy(new AIMoveAround());
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
		this.setAIStrategy(new AIMoveAround());
	}
	
	/**
	 * Sets the currently used <code>AIStrategy</code> for this NPC.
	 * The <code>AIStrategy</code> dictates how the entity moves and behaves.
	 * This method is protected because it is only intended to be used in <code>calculateCurrentBehavior()</code>.
	 * Otherwise this method could interfere with the logic in said method and could cause undefined behavior.
	 * @param newBehavior the new <code>AIStrategy</code> that this NPC should follow
	 * @see #getCurrentAIStrategy()
	 * @see #calculateCurrentBehavior()
	 * @see AIStrategy
	 */
	protected final void setAIStrategy(AIStrategy newBehavior)
	{
		this.currentBehavior = newBehavior;
	}
	
	/**
	 * Returns the current <code>AIStrategy</code> that this NPC is currently following.
	 * The <code>AIStrategy</code> dictates how the entity moves and behaves.
	 * @return the <code>AIStrategy</code> that is currently set for this NPC
	 */
	public final AIStrategy getCurrentAIStrategy()
	{
		return this.currentBehavior;
	}
	
	/**
	 * This is called every update to determine which AI strategy should be used currently.
	 * This method should be overridden to implement custom behavior decision.
	 * The method {@link #setAIStrategy(AIStrategy)} can be used to set an <code>AIStrategy</code>.
	 * By default all NPCs use a behavior which makes them move aimlessly until they see the player.
	 * Then they will try to get as close as possible to the player to hit them.
	 */
	public void calculateCurrentBehavior()
	{
		//Default implementation for monsters
		List<Player> players = this.level.getPlayers();
		if(players.isEmpty())
		{
			//Can happen if the player dies?
			return;
		}
		if(this.hasLineOfSightTo(new Vector2(players.get(0).getPosition().x, players.get(0).getPosition().y))
			&& !(this.currentBehavior instanceof AIApproachTarget))
		{
			this.setAIStrategy(new AIApproachTarget(players.get(0)));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		
		if(!this.isDead())
		{
			this.calculateCurrentBehavior();
			
			if(this.currentBehavior != null)
			{
				this.currentBehavior.executeStrategy(this);
			}
		}
	}
}
