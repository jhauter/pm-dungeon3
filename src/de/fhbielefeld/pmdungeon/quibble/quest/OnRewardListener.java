package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public interface OnRewardListener {

	/**
	 * 
	 * @param player player that will be effected by fulfill a quest
	 */
	public void onReward(Player player);
	
}
