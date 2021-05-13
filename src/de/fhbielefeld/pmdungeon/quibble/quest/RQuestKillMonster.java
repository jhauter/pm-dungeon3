package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestKillMonster extends Quest {

	private int killed;
	private int toKill;

	public RQuestKillMonster(String questName, Player p, int onReward, int toKill) {
		super(questName, p, onReward);
		this.toKill = toKill;
	}

	public RQuestKillMonster(String questName, String texture) {
		super(questName, texture);
	}

	@Override
	public String getTask() {
		return "Reach next Stage";
	}

	@Override
	public String onWork() {
		return killed + "/" + toKill;
	}

	@Override
	public String onComplete() {
		return QUEST_REACH + onReward();
	}

	@Override
	public void onReward(Player player) {
		if (player.triggeredNextLevel()) {
			Item i = (Item) onReward();
			player.getEquippedItems().addItem(i);
		}
	}

}
