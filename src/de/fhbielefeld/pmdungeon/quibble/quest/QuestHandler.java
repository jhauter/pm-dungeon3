package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public class QuestHandler implements IQuestHandler {

	private List<OnRewardListener> listener = new ArrayList<>();
	
	@Override
	public void addInputListener(OnRewardListener listener) {
		if (this.listener.contains(listener)) {
			IllegalArgumentException e = new IllegalArgumentException("this listener was already added");
			LoggingHandler.logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		this.listener.add(listener);
	}

	@Override
	public void removeInputListener(OnRewardListener listener) {
		this.listener.remove(listener);
	}

	@Override
	public void notifyListeners(Creature c) {
		listener.forEach(l -> l.onReward(c));
	}

	@Override
	public void updateHandler() {
		
	}

}
