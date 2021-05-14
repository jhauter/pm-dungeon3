package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;

public abstract class Quest implements EntityEventHandler {

	public final static String QUEST_TEXTURE_PATH = "assets/textures/quest/";
	public final static String ACCEPT_DECLINE = "To Accept press J to Decline press N";
	public final static String QUEST_REACH = "You will gain: ";

	protected boolean isAccept;
	protected boolean isActive;
	private boolean isCompleted;

	private String texture;
	private String questName;

	private Player player;

	private Object onReward;

	public Quest(String questName, Player p, Object onReward) {
		this.questName = questName;
		this.onReward = onReward;
		this.player = p;
		LoggingHandler.logger.log(Level.INFO, "The Quest: " + questName + " was accepted");
	}

	/**
	 * whether a quest has been accepted and has now become active
	 */
	protected void setActive(boolean b) {
		this.isActive = b;
	}

	/**
	 * 
	 * @return whether a quest is active or not
	 */
	public boolean isActive() {
		return isActive;
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
	 * @return A string that represents what reward the quest will give
	 */
	public abstract String onComplete();

	/**
	 * 
	 * @return the given Texture
	 */
	public final String getTexture() {
		return this.texture;
	}

	/**
	 * 
	 * @return name of a Quest
	 */
	public final String getQuestName() {
		return this.questName;
	}
	
	public abstract void onReward(Player p);

	public final Object onReward() {
		return this.onReward;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
}
