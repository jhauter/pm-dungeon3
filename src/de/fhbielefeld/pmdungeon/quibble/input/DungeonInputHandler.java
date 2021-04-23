package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;

public class DungeonInputHandler implements InputHandler {

	private ArrayList<InputListener> listener = new ArrayList<>();

	@Override
	public void addInputListener(InputListener listener) {
		if (this.listener.contains(listener)) {
			IllegalArgumentException e = new IllegalArgumentException("this listener was already added");
			LoggingHandler.logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		this.listener.add(listener);
	}

	@Override
	public void removeInputListener(InputListener listener) {
		this.listener.remove(listener);
	}

	@Override
	public void notifyListeners(DungeonInput key) {
		listener.forEach(l -> l.onInputRecieved(key));
	}

	@Override
	public void updateHandler() {
		DungeonInput[] inputs = DungeonInput.values();
		for (DungeonInput i : inputs) {
			if (i.isPressed()) {
				notifyListeners(i);
			}
		}

	}
}
