package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyHandler implements InputHandler {

	private ArrayList<InputListener> listener = new ArrayList<>();
	
	private final boolean UP = (Gdx.input.isKeyPressed(Input.Keys.W));
	private final boolean RIGHT = (Gdx.input.isKeyPressed(Input.Keys.D));
	private final boolean DOWN = (Gdx.input.isKeyPressed(Input.Keys.S));
	private final boolean LEFT = (Gdx.input.isKeyPressed(Input.Keys.A));
	
	private final ArrayList<Boolean> straightDirectionIsPressed = new ArrayList<>(Arrays.asList(UP, RIGHT, DOWN, LEFT));
	private final ArrayList<KEY> straightDirectionKEY = new ArrayList<>(Arrays.asList(KEY.UP, KEY.RIGHT, KEY.DOWN, KEY.LEFT));
	
	private final boolean UP_RIGHT = (Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.D)));
	private final boolean DOWN_RIGHT = (Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.D)));
	private final boolean DOWN_LEFT = (Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.A)));
	private final boolean UP_LEFT = (Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.A)));

	private final ArrayList<Boolean> diagonalDirectionIsPressed = new ArrayList<>(Arrays.asList(UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT));
	private final ArrayList<KEY> diagonalDirectionKEY = new ArrayList<>(Arrays.asList(KEY.UP_RIGHT, KEY.DOWN_RIGHT, KEY.DOWN_LEFT, KEY.UP_LEFT));


	@Override
	public void addInputListener(InputListener listener) {
		this.listener.add(listener);
	}

	@Override
	public void removeInputListener(InputListener listener) {
		this.listener.remove(listener);
	}

	@Override
	public void notityListeners(KEY key) {
		listener.forEach(l -> l.onInputRecieved(key));
		for (InputListener inputListener : listener) {
			inputListener.onInputRecieved(key);
		}
	}

	@Override
	public KEY updateHandler() {
		for (int i = 0; i < straightDirectionKEY.size(); i++) {
			for (int j = 0; j < diagonalDirectionKEY.size(); j++) {
				if(diagonalDirectionIsPressed.get(j))
					return diagonalDirectionKEY.get(j);
			}
			if(straightDirectionIsPressed.get(i))
				return straightDirectionKEY.get(i);
		}
		return KEY.NO_KEY;
	}


}
