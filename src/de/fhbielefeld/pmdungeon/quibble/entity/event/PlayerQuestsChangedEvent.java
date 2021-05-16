package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;

public class PlayerQuestsChangedEvent extends EntityEvent
{
	public static final int EVENT_ID = EntityEvent.genEventID();
	
	private final Quest quest;
	
	public PlayerQuestsChangedEvent(int eventID, Player player, Quest quest)
	{
		super(eventID, player);
		this.quest = quest;
	}
	
	/**
	 * @return the quest that was added or removed
	 */
	public Quest getQuest()
	{
		return this.quest;
	}
	
	@Override
	public Player getEntity()
	{
		return (Player)super.getEntity();
	}
}
