package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;

public class DungeonInputHandler implements InputHandler {

	private ArrayList<InputListener> listener = new ArrayList<>();

	@Override
	public void addInputListener(InputListener listener) {
		if (this.listener.contains(listener)) {
			throw new IllegalArgumentException("this listener was already added");
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
