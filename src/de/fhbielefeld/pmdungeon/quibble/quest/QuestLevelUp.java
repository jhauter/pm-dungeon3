package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.event.QuestLevelUpEvent;

public class QuestLevelUp extends Quest implements EntityEventHandler {

	private Player player;
	private int levelToReach;

	public QuestLevelUp(String questName, String texture) {
		super(questName, texture);
	}

	public QuestLevelUp(String questName, Player p, int levelToReach, int onReward) {
		super(questName, onReward);
		this.player = p;
		this.levelToReach = levelToReach;
	}
	

	@Override
	public void handleEvent(EntityEvent event) {
		
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
		return "You will gein " + this.onReward();
	}

	@Override
	public void onReward(Creature c) {
		int level = this.player.totalExpFunction(this.player.getCurrentExpLevel()) / 10;
		if(level == levelToReach) {
			QuestLevelUpEvent qe = new QuestLevelUpEvent(999, player, this.onReward());
			qe.onReached();
			
		}
	}

}
