package de.fhbielefeld.pmdungeon.quibble.entity.event;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class QuestLevelUpEvent extends EntityEvent {

	Player player;
	private int expGain;

	public QuestLevelUpEvent(int eventID, Player player, int expGain) {
		super(eventID, player);
		this.player = player;
		this.expGain = expGain;
	}

	public void onReached() {
		player.rewardExp(expGain);
	}

}
