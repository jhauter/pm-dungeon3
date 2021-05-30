package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class QuestDungeonStage extends QuestItemReward
{
	/**
	 * Creates a quest object that can be completed by the player by reaching the next dungeon stage.
	 * @param questName the display name of the quest
	 * @param rewardItems the items that are given to the player when the quest is completed
	 * @param expOnReward the exp that are rewarded when the quest is completed
	 */
	public QuestDungeonStage(String questName, int expOnReward, Item ... rewardItems)
	{
		super(questName, expOnReward, rewardItems);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTask()
	{
		return "Reach next Stage";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProgressText()
	{
		return "";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleEvent(EntityEvent event)
	{
		if(event.getEventID() == Player.EVENT_ID_DUNGEON_LEVEL_CHANGED)
		{
			setCompleted(true);
		}
	}
	
	@Override
	public String getIconPath()
	{
		return "blue_flag";
	}
}
