package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public enum DungeonInput {

	UP(() -> Gdx.input.isKeyPressed(Input.Keys.W)),
	RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.D)),
	LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.A)),
	DOWN(() -> Gdx.input.isKeyPressed(Input.Keys.S)),
	UP_RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)),
	UP_LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)),
	DOWN_LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)),
	DOWN_RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)),
	HIT(() -> Gdx.input.isButtonJustPressed(Input.Buttons.LEFT));
	private final Supplier<Boolean> inputCheck;
	
	private DungeonInput(Supplier<Boolean> inputCheck) {
		this.inputCheck = inputCheck;
	}
	
	public boolean isPressed() {
		return inputCheck.get();
	}
}
