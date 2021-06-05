package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.ItemDrop;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class InputStrategyPickUpDrops implements InputStrategy
{
	@Override
	public void handle(Player player)
	{
		List<ItemDrop> drops = player.getLevel().getEntitiesInRadius(player.getX(), player.getY(), 1.0F, ItemDrop.class);
		if(!drops.isEmpty())
		{
			drops.get(0).setPickedUp();
			player.getInventory().addItem(drops.get(0).getItem());
			LoggingHandler.logger.log(Level.INFO, "Picked up: " + drops.get(0).getItem().getDisplayText());
		}
	}
}
