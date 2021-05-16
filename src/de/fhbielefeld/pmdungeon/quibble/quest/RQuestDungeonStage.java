package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestDungeonStage extends Quest {

	public RQuestDungeonStage(String questName, Player p, Item itemOnReward, int expOnReward) {
		super(questName, p, itemOnReward, expOnReward);
	}


	@Override
	public String getTask() {
		return "Reach next Stage";
	}

	@Override
	public String onWork() {
		return "";
	}

	@Override
	public void handleEvent(EntityEvent event) {
		if(event.getEventID() == Player.EVENT_ID_DUNGEON_LEVEL_CHANGED)
		{
			setCompleted(true);
		}
	}
}
