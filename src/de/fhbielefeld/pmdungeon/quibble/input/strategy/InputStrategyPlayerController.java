package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class InputStrategyPlayerController implements InputStrategy
{
	private final float axisX;
	private final float axisY;
	
	public InputStrategyPlayerController(float axisX, float axisY)
	{
		this.axisX = axisX;
		this.axisY = axisY;
	}
	
	@Override
	public void handle(Player player)
	{
		player.influenceControlAxisMinMax(axisX, axisY);
	}
}
