package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class QuestDungeonLevel extends Quest {
	
	private int currentDungeonStage;

	public QuestDungeonLevel(String questName, Player p, Item onReward) {
		super(questName, p, onReward);
		// TODO Auto-generated constructor stub
	}

		public QuestDungeonLevel(String questName, String texture) {
		super(questName, texture);
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
	public void onReward(Player player) {
		player.getInventory().addItem((InventoryItem<Item>) onReward());
	}

}
