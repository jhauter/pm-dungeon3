package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestLevelUp;

public class PlayerInteractDummyQuestEvent extends EntityEvent {

	public static final int EVENT_ID = EntityEvent.genEventID();

	private final Quest quest;
	private final Player player;

	public PlayerInteractDummyQuestEvent(int eventID, Player player, Quest quest) {
		super(eventID, player);
		this.quest = quest;
		this.player = player;
	}

	public Quest getQuest() {
		return this.quest;
	}

	@Override
	public Player getEntity() {
		return (Player) super.getEntity();
	}

	public void setQuest() {
		if (quest.equals(Quest.QUEST_YELLOW_FLAG)) {
			int level = this.player.totalExpFunction(this.player.getCurrentExpLevel()) / 10;
			QuestLevelUp newQuest = new QuestLevelUp("LevelQuest", player, level + 1, 20);
			this.player.addQuest(newQuest);
		}
	}
}
