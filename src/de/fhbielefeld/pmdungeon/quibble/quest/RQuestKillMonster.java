package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestKillMonster extends Quest {

	private int killed;
	private int toKill;
	private int counter;

	public RQuestKillMonster(String questName, Player p, int toKill, int onReward) {
		super(questName, p, onReward);
		this.killed = p.getKilledEntitys();
		this.toKill = toKill + killed;
	}

	public RQuestKillMonster(String questName, String texture) {
		super(questName, texture);
	}

	@Override
	public String getTask() {
		return "Kill " + toKill + " Monsters";
	}

	@Override
	public String onWork() {
		return counter + "/" + toKill;
	}

	@Override
	public String onComplete() {
		return QUEST_REACH + onReward();
	}

	@Override
	public void onReward(Player player) {
		if(killed < player.getKilledEntitys()) {
			counter ++;
			killed = player.getKilledEntitys();
		}
		if(counter == toKill) {
			player.rewardExp((int) onReward());
			counter ++;
		}
	}

}
