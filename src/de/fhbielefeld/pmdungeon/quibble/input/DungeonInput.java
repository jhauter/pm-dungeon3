package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public enum DungeonInput {
	
	UP(() -> Gdx.input.isKeyPressed(Input.Keys.W),true,0.0F,1.0F),
	RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.D),true,1.0F,0.0F),
	LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.A),true,-1.0F,0.0F),
	DOWN(() -> Gdx.input.isKeyPressed(Input.Keys.S),true,0.0F,-1.0F),
	UP_RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D),true,1.0F,1.0F),
	UP_LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A),true,-1.0F,1.0F),
	DOWN_LEFT(() -> Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A),true,-1.0F,-1.0F),
	DOWN_RIGHT(() -> Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D),true,1.0F,-1.0F),
	HIT(() -> Gdx.input.isButtonJustPressed(Input.Buttons.LEFT),false,0.0F,0.0F),
	INV_LOG(() -> Gdx.input.isKeyJustPressed(Input.Keys.I),false,0.0F,0.0F),
	EQUIP_LOG(() -> Gdx.input.isKeyJustPressed(Input.Keys.O),false,0.0F,0.0F),
	CLOSE(() -> Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE),false,0.0F,0.0F);
	
	private final Supplier<Boolean> inputCheck;
	private final float axisScaleX;
	private final float axisScaleY;
	private final boolean isMovementInput;
	
	private DungeonInput(Supplier<Boolean> inputCheck, boolean isMovementInput, float axisScaleX, float axisScaleY) {
		this.axisScaleY = axisScaleY;
		this.axisScaleX = axisScaleX;
		this.inputCheck = inputCheck;
		this.isMovementInput = isMovementInput;
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
	
	public boolean isMovementInput()
	{
		return this.isMovementInput;
	}
}
