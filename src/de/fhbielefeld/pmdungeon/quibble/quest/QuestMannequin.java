package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public class QuestMannequin extends Entity {
	
	private boolean isAccept;
	
	private final Quest quest;
	
	public QuestMannequin(Quest quest, float x, float y) {
		super(x, y);
		this.quest = quest;
		this.animationHandler.addAsDefaultAnimation("default", 1, 1, Quest.QUEST_TEXTURE_PATH + quest.getTexture() + ".png", -1);
	}
	
	@Override
	public boolean deleteable() {
		return isAccept;
	}
	
	public Quest getQuest() {
		return this.quest;
	}
	
	public void setAccept() {
		this.isAccept = true;
	}
	
}
