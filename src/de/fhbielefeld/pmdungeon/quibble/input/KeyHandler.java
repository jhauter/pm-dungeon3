package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyHandler implements InputHandler {

	private ArrayList<InputListener> listener = new ArrayList<>();

	private ArrayList<Boolean> getSimpleB() {
		ArrayList<Boolean> a = new ArrayList<>();
		boolean up = (Gdx.input.isKeyPressed(Input.Keys.W));
		boolean right = (Gdx.input.isKeyPressed(Input.Keys.D));
		boolean down = (Gdx.input.isKeyPressed(Input.Keys.S));
		boolean left = (Gdx.input.isKeyPressed(Input.Keys.A));

		a.add(up);
		a.add(right);
		a.add(down);
		a.add(left);
		return a;
	}

	private ArrayList<Boolean> getDiagonalB() {
		ArrayList<Boolean> a = new ArrayList<>();
		boolean upRight = (Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.D)));
		boolean downRight = (Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.D)));
		boolean downLeft = (Gdx.input.isKeyPressed(Input.Keys.S) && (Gdx.input.isKeyPressed(Input.Keys.A)));
		boolean upLeft = (Gdx.input.isKeyPressed(Input.Keys.W) && (Gdx.input.isKeyPressed(Input.Keys.A)));

		a.add(upRight);
		a.add(downRight);
		a.add(downLeft);
		a.add(upLeft);
		return a;
	}

	private ArrayList<KEY> getSimpleKeys() {
		ArrayList<KEY> keyList = new ArrayList<>();
		keyList.addAll(Arrays.asList(KEY.UP, KEY.RIGHT, KEY.DOWN, KEY.LEFT));
		return keyList;
	}

	private ArrayList<KEY> getDiagonalKeys() {
		ArrayList<KEY> keyList = new ArrayList<>();
		keyList.addAll(Arrays.asList(KEY.UP_RIGHT, KEY.DOWN_RIGHT, KEY.DOWN_LEFT, KEY.UP_LEFT));
		return keyList;
	}

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
	}

	@Override
	public KEY updateHandler() {
		for (int i = 0; i < getSimpleB().size(); i++) {
			for (int j = 0; j < getDiagonalKeys().size(); j++) {
				if(getDiagonalB().get(j))
				return getDiagonalKeys().get(j);
			}
			if(getSimpleB().get(i))
			return getSimpleKeys().get(i);
		}
		return KEY.NO_KEY;
	}

	@Override
	public void registerMouseEvent(KEY key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerKeyEvent(KEY key) {
		// TODO Auto-generated method stub
	}

}
