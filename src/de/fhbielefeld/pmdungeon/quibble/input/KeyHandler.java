package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyHandler implements InputHandler {

	private ArrayList<InputListener> listener = new ArrayList<>();
	
	private final Supplier<Boolean> UP = () -> Gdx.input.isKeyPressed(Input.Keys.W);
	private final Supplier<Boolean> DOWN = () -> Gdx.input.isKeyPressed(Input.Keys.S);
	private final Supplier<Boolean> RIGHT = () -> Gdx.input.isKeyPressed(Input.Keys.D);
	private final Supplier<Boolean> LEFT = () -> Gdx.input.isKeyPressed(Input.Keys.A);
	
	private final List<Supplier<Boolean>> straightDirectionIsPressed = Arrays.asList(UP, RIGHT, DOWN, LEFT);
	private final List<Key> straightDirectionKEY = Arrays.asList(Key.UP, Key.RIGHT, Key.DOWN, Key.LEFT);
	
	private final Supplier<Boolean> UP_RIGHT = () -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D);
	private final Supplier<Boolean> DOWN_RIGHT = () -> Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.D));
	private final Supplier<Boolean> DOWN_LEFT = () -> Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.A));
	private final Supplier<Boolean> UP_LEFT = () -> Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.A));

	private final List<Supplier<Boolean>> diagonalDirectionIsPressed = Arrays.asList(UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT);
	private final List<Key> diagonalDirectionKEY = Arrays.asList(Key.UP_RIGHT, Key.DOWN_RIGHT, Key.DOWN_LEFT, Key.UP_LEFT);


	@Override
	public void addInputListener(InputListener listener) {
		if(this.listener.contains(listener))
		{
			throw new IllegalArgumentException("this listener was already added");
		}
		this.listener.add(listener);
	}

	@Override
	public void removeInputListener(InputListener listener) {
		this.listener.remove(listener);
	}

	@Override
	public void notifyListeners(Key key) {
		listener.forEach(l -> l.onInputRecieved(key));
	}

	@Override
	public void updateHandler() {
		
		for (int i = 0; i < straightDirectionIsPressed.size(); i++) {
			for (int j = 0; j < diagonalDirectionKEY.size(); j++) {
				if(diagonalDirectionIsPressed.get(j).get()) {
					this.notifyListeners(diagonalDirectionKEY.get(j));
					return;
				}
			}
			if(straightDirectionIsPressed.get(i).get()) {
				this.notifyListeners(straightDirectionKEY.get(i));
				return;
			}
		}
	}
}
