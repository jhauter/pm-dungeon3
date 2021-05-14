package de.fhbielefeld.pmdungeon.quibble.quest;

public enum QuestTypes {

	QUEST_YELLOW_FLAG("Level Quest", "yellow_flag"),
	QUEST_BLUE_FLAG("Dungeon Level Quest", "blue_flag"),
	QUEST_RED_FLAG("Kill Quest", "red_flag");

	String questName;
	String texture;
	
	QuestTypes(String questName, String texture) {
		this.questName = questName;
		this.texture = texture;
	}
}
