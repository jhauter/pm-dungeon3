package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;

public class QuestYellowFlag extends Quest implements EntityEventHandler {


	public QuestYellowFlag(String questName, String texture) {
		super(questName, texture);
	}

	@Override
	public void handleEvent(EntityEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getTask() {
		return "Level dich auf Level 5";
	}

	@Override
	public void onComplete(Creature c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWork(Creature c) {
		// TODO Auto-generated method stub

	}

}
