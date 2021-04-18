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
	
	private final List<Supplier<Boolean>> straightDirectionIsPressed = (Arrays.asList(UP, RIGHT, DOWN, LEFT));
	private final List<KEY> straightDirectionKEY = new ArrayList<>(Arrays.asList(KEY.UP, KEY.RIGHT, KEY.DOWN, KEY.LEFT));
	
	private final Supplier<Boolean> UP_RIGHT = () -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D);
	private final Supplier<Boolean> DOWN_RIGHT = () -> Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.D));
	private final Supplier<Boolean> DOWN_LEFT = () -> Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.A));
	private final Supplier<Boolean> UP_LEFT = () -> Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.A));

	private final List<Supplier<Boolean>> diagonalDirectionIsPressed = new ArrayList<>(Arrays.asList(UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT));
	private final List<KEY> diagonalDirectionKEY = new ArrayList<>(Arrays.asList(KEY.UP_RIGHT, KEY.DOWN_RIGHT, KEY.DOWN_LEFT, KEY.UP_LEFT));


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
	public void notifyListeners(KEY key) {
		listener.forEach(l -> l.onInputRecieved(key));
	}

	@Override
	public KEY updateHandler() {
		
		for (int i = 0; i < straightDirectionIsPressed.size(); i++) {
			for (int j = 0; j < diagonalDirectionKEY.size(); j++) {
				if(diagonalDirectionIsPressed.get(j).get())
					return diagonalDirectionKEY.get(j);
			}
			if((boolean) straightDirectionIsPressed.get(i).get())
				return straightDirectionKEY.get(i);
		}
		return KEY.NO_KEY;
	}


}
