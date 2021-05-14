package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestDungeonLevel extends Quest {

	public RQuestDungeonLevel(String questName, Player p, Item itemOnReward, int expOnReward) {
		super(questName, p, itemOnReward, expOnReward);
	}

	private int currentDungeonStage;


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
		
	}

	@Override
	public void onReward(Player p) {
		// TODO Auto-generated method stub
		
	}
}
