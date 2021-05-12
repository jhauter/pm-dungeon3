package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;

public class YellowFlag extends Quest implements EntityEventHandler{

	public YellowFlag(float x, float y) {
		super(x, y);
		this.animationHandler.addAsDefaultAnimation("", 1, 1, Quest.QUEST_TEXTURE_PATH + "yellow_flag.png", 4);
	}

	@Override
	public void handleEvent(EntityEvent event) {
		// TODO Auto-generated method stub
	}

}
