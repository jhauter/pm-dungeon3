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
	}

	@Override
	public String getTask() {
		return "Level up to Level 3";
	}

	@Override
	public String onWork() {
		return null;
	}

	@Override
	public String onComplete() {
		return null;
	}

	@Override
	public void onReward(Creature c) {
		// TODO Auto-generated method stub
		
	}

}
