package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public enum DungeonInput {
	
	UP(() -> Gdx.input.isKeyPressed(Input.Keys.W),0.0F,1.0F),
	RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.D),1.0F,0.0F),
	LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.A),-1.0F,0.0F),
	DOWN(() -> Gdx.input.isKeyPressed(Input.Keys.S),0.0F,-1.0F),
	UP_RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D),1.0F,1.0F),
	UP_LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A),-1.0F,1.0F),
	DOWN_LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A),-1.0F,-1.0F),
	DOWN_RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D),1.0F,-1.0F),
	HIT(() -> Gdx.input.isButtonJustPressed(Input.Buttons.LEFT),0.0F,0.0F);
	
	private final Supplier<Boolean> inputCheck;
	private final float axisScaleX;
	private final float axisScaleY;
	
	private DungeonInput(Supplier<Boolean> inputCheck, float axisScaleX, float axisScaleY) {
		this.axisScaleY = axisScaleY;
		this.axisScaleX = axisScaleX;
		this.inputCheck = inputCheck;
	}
	
	public float getAxisScaleX() {
		return axisScaleX;
	}

	public float getAxisScaleY() {
		return axisScaleY;
	}

	public boolean isPressed() {
		return inputCheck.get();
	}
}
