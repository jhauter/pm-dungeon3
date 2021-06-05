package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class InputStrategyCloseChest implements InputStrategy
{
	@Override
	public void handle(Player player)
	{
		DungeonStart.getDungeonMain().getUIChestView().setInventory(null);
	}
}
