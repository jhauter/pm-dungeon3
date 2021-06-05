package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureExpEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class QuestLevelUp extends QuestItemReward
{
	private int levelToReach;
	private int currentLv;
	
	/**
	 * Creates a quest object that can be completed by the player by reaching the next exp level.
	 * @param questName the display name of the quest
	 * @param rewardItems the items that are given to the player when the quest is completed
	 * @param expOnReward the exp that are rewarded when the quest is completed
	 */
	public QuestLevelUp(String questName, int levelToReach, int expOnReward, Item ... rewardItems)
	{
		super(questName, expOnReward, rewardItems);
		this.levelToReach = levelToReach;
	}
	
	@Override
	public void onAccept(Player player)
	{
		super.onAccept(player);
		this.currentLv = player.getCurrentExpLevel();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTask()
	{
		return "Level up to lv. " + this.levelToReach;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProgressText()
	{
		return this.currentLv + "/" + levelToReach;
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
				this.currentLv = newLevel;
			}
			if(this.currentLv >= this.levelToReach)
			{
				setCompleted(true);
			}
		}
	}
	
	@Override
	public String getIconPath()
	{
		return "yellow_flag";
	}
}
