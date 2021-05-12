package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public abstract class Quest extends Entity {
	
	public final static String QUEST_TEXTURE_PATH = "assets/textures/quest/";
	
	protected boolean isAccept;
	
	public Quest(float x, float y) {
		super(x, y);
	}

	@Override
	public boolean deleteable() {
		return isAccept;
	}
	
}
