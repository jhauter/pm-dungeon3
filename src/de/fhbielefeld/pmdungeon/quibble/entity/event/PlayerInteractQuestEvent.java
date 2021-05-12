package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;

public class PlayerInteractQuestEvent extends EntityEvent{
	
	public static final int EVENT_ID = EntityEvent.genEventID();
	
	private final Quest quest;

	public PlayerInteractQuestEvent(int eventID, Player player, Quest quest) {
		super(eventID, player);
		this.quest = quest;
	}

	public Quest getQuest() {
		return this.quest;
	}
	
	@Override
	public Player getEntity() {
		return (Player)super.getEntity();
	}
	
}
