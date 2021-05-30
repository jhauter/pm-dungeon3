package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestDungeonStage extends Quest
{
	
	/**
	 * Creates a quest object that can be completed by the player by reaching the next dungeon stage.
	 * @param questName the display name of the quest
	 * @param p the player that should have the quest
	 * @param itemOnReward the item that is rewarded when the quest is completed
	 * @param expOnReward the exp that are rewarded when the quest is completed
	 */
	public RQuestDungeonStage(String questName, Player p, Item itemOnReward, int expOnReward)
	{
		super(questName, p, itemOnReward, expOnReward);
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
	public String onWork()
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
}
