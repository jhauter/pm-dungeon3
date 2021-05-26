package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public abstract class Quest implements EntityEventHandler {

	public final static String QUEST_TEXTURE_PATH = "assets/textures/quest/";
	public final static String QUEST_REACHED = "You will gain: ";

	private boolean isCompleted;

	private final String questName;

	private final Player player;

	private final Item itemOnReward;
	private final int expOnReward;

	/**
	 * Creates a quest object that can track the player's progress on an objective.
	 * @param questName the display name of the quest
	 * @param player the player that should have the quest
	 * @param itemOnReward the item that is rewarded when the quest is completed
	 * @param expOnReward the exp that are rewarded when the quest is completed
	 */
	public Quest(String questName, Player player, Item itemOnReward, int expOnReward) {
		this.questName = questName;
		this.itemOnReward = itemOnReward;
		this.expOnReward = expOnReward;
		this.player = player;
	}

	/**
	 * 
	 * @return The specific task of the quest as a string
	 */
	public abstract String getTask();

	/**
	 * 
	 * @return A string that represents how far a quest has already been completed
	 */
	public abstract String onWork();

	/**
	 * Use <code>QUEST_REACH</code> + <code>onReward</code>
	 * 
	 * @return A string that represents what reward the quest will give
	 */
	public String onComplete() {
		if (this.getExpOnReward() > 0 && itemOnReward == null)
			return QUEST_REACHED + this.getExpOnReward();
		if (this.getExpOnReward() == 0 && itemOnReward != null)
			return QUEST_REACHED + getItemOnReward();
		else
			return QUEST_REACHED + this.getExpOnReward() + " and " + getItemOnReward().getDisplayName();
	}

	/**
	 * 
	 * @return name of a Quest
	 */
	public final String getQuestName() {
		return this.questName;
	}

	public void onReward(Player p) {
		p.rewardExp(expOnReward);
		if (itemOnReward != null)
			p.getEquippedItems().addItem(itemOnReward);
	}

	/**
	 * 
	 * @return the player that has this quest
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return whether the quest has been completed and is marked for removal
	 */
	public boolean isCompleted() {
		return isCompleted;
	}

	/**
	 * Marks this quest for removal
	 * @param isCompleted whether this quest should be removed
	 */
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	/**
	 * @return the items that the player gets when the quest is completed
	 */
	public Item getItemOnReward() {
		return itemOnReward;
	}

	/**
	 * @return the amount of exp that the player gets when the quest is completed
	 */
	public int getExpOnReward() {
		return expOnReward;
	}
}
