package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public abstract class Quest implements OnRewardListener {

	public final static String QUEST_TEXTURE_PATH = "assets/textures/quest/";
	public final static String ACCEPT_DECLINE = "To Accept press J to Decline press N";
	public final static String QUEST_REACH = "You will gain: ";

	public static final Quest QUEST_YELLOW_FLAG = new RQuestLevelUp("Level Quest", "yellow_flag");
	public static final Quest QUEST_BLUE_FLAG = new RQuestDungeonLevel("Dungeon Level", "blue_flag");
	public static final Quest QUEST_RED_FLAG = new RQuestKillMonster("KillQuest", "red_flag");
	

	protected boolean isAccept;
	protected boolean isActive;

	private String texture;
	private String questName;

	private Player player;

	private Object onReward;

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

	public Quest(String questName, Player p, Object onReward2) {
		this.questName = questName;
		this.onReward = onReward2;
		this.player = p;
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
	 * @param c Creature to receive the specific reward
	 */
	public abstract void onReward(Player player);

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

	public final Object onReward() {
		return this.onReward;
	}

	public Player getPlayer() {
		return player;
	}
}
