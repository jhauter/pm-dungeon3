package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestDungeonLevel extends Quest {

	private int currentDungeonStage;

	public RQuestDungeonLevel(String questName, Player p, Item onReward) {
		super(questName, p, onReward);
		// TODO Auto-generated constructor stub
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
	public String onComplete() {
		return QUEST_REACH + onReward();
	}

	@Override
	public void handleEvent(EntityEvent event) {
		
	}

	@Override
	public void onReward(Player p) {
		// TODO Auto-generated method stub
		
	}
}
