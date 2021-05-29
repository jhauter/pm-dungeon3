package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.HashMap;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class QuestDummy extends Entity {

	private final StringBuilder sb = new StringBuilder();
	private final StringBuffer stars = new StringBuffer();
	private final int logLenght = 97;
	
	private final HashMap<Integer, String> questMap;

	private Quest quest;
	private Player player;

	private boolean decide;

	private final int index;

	/**
	 * Will create a visible Quest Entity
	 * 
	 * @param quest the quest which should be visible
	 * @param x     x float of the Position
	 * @param y     y float of the Position
	 */
	public QuestDummy(int index, float x, float y) {
		super(x, y);
		this.index = index;
		questMap = createQuestMap();
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1,
				Quest.QUEST_TEXTURE_PATH + questMap.get(index).toString() + ".png");
	}

	/**
	 * Creates from all available Quest the Quest that symbolizes the dummy
	 * @return the specific quest
	 */
	private Quest createQuest() {
		if(index == 0) 
			return quest = new RQuestLevelUp("Level Up Quest", player, Item.SWORD_KATANA, 20, player.getCurrentExpLevel() +1);
		else if(index == 1)
			return quest = new RQuestDungeonStage("Reach next Stage Quest", player, Item.SWORD_KATANA, 20);
		else if(index == 2)
			return quest = new RQuestKillMonster("Kill Monster Quest", player, null, 20, 5);
		else
			throw new IndexOutOfBoundsException("Index out of Bounds for Index :" + this.index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDespawn()
	{
		return decide;
	}
	
	/**
	 * HashMap which includes a special string for the texture of the quest
	 * @return Map with Index and Strings for the texture
	 */
	private HashMap<Integer, String> createQuestMap() {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(0, "yellow_flag");
		map.put(1, "blue_flag");
		map.put(2, "red_flag");
		return map;
	}

	/**
	 * 
	 * @param player
	 */
	public void setActive(Player player) {
		this.player = player;
		quest = createQuest();
		LoggingHandler.logger.info(msg());
	}

	/**
	 * if set the quest becomes active and the doll disappears
	 */
	public void setDecided(boolean b) {
		if(b)
			this.player.addQuest(quest);
		this.decide = true;
	}

	/**
	 * 
	 * @return a log message for a easy reading
	 */
	private String msg() {
		String msg_1 = "!You have activated a Quest Flag!";
		String msg_2 = " This Quest is a: " + quest.getQuestName() + " ";
		String msg_3 = " You have to : " + quest.getTask() + " ";
		// String msg_4 is a method below
		String msg_5 = " If you would like to Accept press J, else N ";

		// 1. line
		msg_stars(getSize(msg_1));
		sb.append("\n" + createMsg(msg_1));
		deleteStarsInSB();
		// 2. line
		msg_stars(getSize(msg_2));
		sb.append(createMsg(msg_2));
		deleteStarsInSB();
		// 3. line
		msg_stars(getSize(msg_3));
		sb.append(createMsg(msg_3));
		deleteStarsInSB();
		// 4. line
		msg_stars(getSize(msg_4()));
		sb.append(createMsg(msg_4()));
		deleteStarsInSB();
		// 5. line
		msg_stars(getSize(msg_5));
		sb.append(createMsg(msg_5));
		deleteStarsInSB();
		// 6. line
		msg_stars(this.logLenght);
		sb.append(stars.toString());
		deleteStarsInSB();

		return sb.toString();
	}
	
	/**
	 * 
	 * @return String with get an ItemReward if a quest provides one
	 */
	private String msg_4() {
		if(quest.getItemOnReward() == null) {
			return " Gain: " + quest.getExpOnReward() + " exp ";
		}
		return " Gain: " + quest.getItemOnReward().getDisplayName() + " and " + quest.getExpOnReward() + " exp ";
	}

	private String createMsg(String msg) {
		return stars.toString() + msg + stars.toString() + "\n";
	}

	private int getSize(String msg) {
		return (logLenght - msg.length()) / 2;
	}

	private void msg_stars(int length) {
		for (int i = 0; i < length; i++) {
			stars.append("*");
		}
	}

	private void deleteStarsInSB() {
		stars.delete(0, stars.length());
	}

}
