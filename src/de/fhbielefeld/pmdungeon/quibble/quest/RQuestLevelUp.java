package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureExpEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestLevelUp extends Quest
{
	
	private Player player;
	private int levelToReach;
	private int counter;
	
	/**
	 * Creates a quest object that can be completed by the player by reaching the next exp level.
	 * @param questName the display name of the quest
	 * @param p the player that should have the quest
	 * @param itemOnReward the item that is rewarded when the quest is completed
	 * @param expOnReward the exp that are rewarded when the quest is completed
	 */
	public RQuestLevelUp(String questName, Player p, Item itemOnReward, int expOnReward, int levelToReach)
	{
		super(questName, p, itemOnReward, expOnReward);
		this.player = p;
		this.levelToReach = levelToReach;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTask()
	{
		return "Level up to Level " + this.levelToReach;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String onWork()
	{
		int level = this.player.totalExpFunction(this.player.getCurrentExpLevel()) / 10;
		return level + "/" + levelToReach;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleEvent(EntityEvent event)
	{
		if(event.getEventID() == Creature.EVENT_ID_EXP_CHANGE)
		{
			final CreatureExpEvent expEvent = (CreatureExpEvent)event;
			
			int oldLevel = expEvent.getEntity().expLevelFunction(expEvent.getPreviousTotalExp());
			int newLevel = expEvent.getEntity().expLevelFunction(expEvent.getNewTotalExp());
			if(newLevel > oldLevel)
			{
				counter++;
				if(counter >= levelToReach)
					setCompleted(true);
				LoggingHandler.logger.log(Level.INFO, "The quest " + this.getQuestName() + " was completed");
			}
		}
	}
}
