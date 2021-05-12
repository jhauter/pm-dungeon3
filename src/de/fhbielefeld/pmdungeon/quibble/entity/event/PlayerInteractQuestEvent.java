package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestMannequin;

public class PlayerInteractQuestEvent extends EntityEvent{
	
	public static final int EVENT_ID = EntityEvent.genEventID();
	
	private final QuestMannequin quest;

	public PlayerInteractQuestEvent(int eventID, Player player, QuestMannequin quest) {
		super(eventID, player);
		this.quest = quest;
	}

	public QuestMannequin getQuest() {
		return this.quest;
	}
	
	@Override
	public Player getEntity() {
		return (Player)super.getEntity();
	}
	
}
