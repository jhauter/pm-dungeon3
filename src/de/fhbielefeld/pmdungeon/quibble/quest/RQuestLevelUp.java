package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class RQuestLevelUp extends Quest {

	private Player player;
	private int levelToReach;

	public RQuestLevelUp(String questName, String texture) {
		super(questName, texture);
	}

	public RQuestLevelUp(String questName, Player p, int levelToReach, int onReward) {
		super(questName, p, onReward);
		this.player = p;
		this.levelToReach = levelToReach;
	}

	@Override
	public String getTask() {
		return "Level up to Level " + this.levelToReach;
	}

	@Override
	public String onWork() {
		int level = this.player.totalExpFunction(this.player.getCurrentExpLevel()) / 10;
		return level + "/" + levelToReach;
	}

	@Override
	public String onComplete() {
		return QUEST_REACH + this.onReward();
	}

	@Override
	public void onReward(Player p) {
		int level = this.player.totalExpFunction(this.player.getCurrentExpLevel()) / 10;
		if(level == levelToReach) {
			player.rewardExp((int) onReward());
		}
	}

}
