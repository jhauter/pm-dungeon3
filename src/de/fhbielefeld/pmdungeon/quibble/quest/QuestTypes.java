	package de.fhbielefeld.pmdungeon.quibble.quest;

public enum QuestTypes {

	/**
	 * Quests that lets a player reach the next exp level.
	 */
	QUEST_LEVEL_UP("Level Quest", "yellow_flag"),
	/**
	 * Quest that lets a player reach the next dungeon stage.
	 */
	QUEST_NEXT_STAGE("Dungeon Level Quest", "blue_flag"),
	/**
	 * Quest that lets a player kill a certain amount of enemies.
	 */
	QUEST_KILL("Kill Quest", "red_flag");

	String questName;
	String texture;
	
	QuestTypes(String questName, String texture) {
		this.questName = questName;
		this.texture = texture;
	}
}
