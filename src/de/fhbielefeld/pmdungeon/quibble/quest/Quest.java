package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public abstract class Quest {

	public final static String QUEST_TEXTURE_PATH = "assets/textures/quest/";
	public final static String ACCEPT_DECLINE = "To Accept press J to Decline press N";

	public static final QuestLevelUp QUEST_YELLOW_FLAG = new QuestLevelUp("Level Quest", "yellow_flag");
	

	protected boolean isAccept;
	protected boolean isActive;

	private String texture;
	private String questName;
	
	private int onReward;

	/**
	 * Abstract real logic Quest
	 * 
	 * @param questName name of a certain Quest
	 * @param texture   the texture which should be used
	 */
	public Quest(String questName, String texture) {
		this.texture = texture;
		this.questName = questName;
	}
	
	public Quest(String questName, int onReward) {
		this.questName = questName;
		this.onReward = onReward;
	}

	/**
	 * whether a quest has been accepted and has now become active
	 */
	protected void setActive() {
		this.isActive = true;
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
	 * 
	 * @return A string that represents what reward the quest will give
	 */
	public abstract String onComplete();

	/**
	 * 
	 * @param c Creature to receive the specific reward
	 */
	public abstract void onReward(Creature c);

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
	
	public final int onReward() {
		return this.onReward;
	}
}
