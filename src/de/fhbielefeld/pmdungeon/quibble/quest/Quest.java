package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public abstract class Quest {

	public final static String QUEST_TEXTURE_PATH = "assets/textures/quest/";

	public static final QuestYellowFlag QUEST_YELLOW_FLAG = new QuestYellowFlag("Level Quest", "yellow_flag");
	
	
	
	protected boolean isAccept;
	protected boolean isActive;

	private final String texture;
	private final String questName;
	
	public Quest(String questName, String texture) {
		this.texture = texture;
		this.questName = questName;
	}

	public void setAccept(boolean isAccept){
		this.isAccept = isAccept;
	}
	
	public boolean isAccept() {
		return isAccept;
	}
	
	public void setActive(boolean isActiv) {
		this.isActive = isActiv;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public abstract String getTask();

	public abstract void onComplete(Creature c);

	public abstract void onWork(Creature c);
	
	public final String getTexture() {
		return this.texture;
	}
	
	public final String getQuestName() {
		return this.questName;
	}
}
