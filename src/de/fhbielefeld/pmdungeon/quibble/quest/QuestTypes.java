	package de.fhbielefeld.pmdungeon.quibble.quest;

public enum QuestTypes {

	QUEST_LEVEL_UP("Level Quest", "yellow_flag"),
	QUEST_NEXT_STAGE("Dungeon Level Quest", "blue_flag"),
	QUEST_KILL("Kill Quest", "red_flag");

	String questName;
	String texture;
	
	QuestTypes(String questName, String texture) {
		this.questName = questName;
		this.texture = texture;
	}
}
