package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.ItemDrop;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class InputStrategyPickUpDrops extends InputStrategy
{
	/**
	 * Allows the Player to pick up a drop from the Ground
	 * @param player user of the Method
	 */
	public InputStrategyPickUpDrops(Player player)
	{
		super(player);
	}
	
	@Override
	public void handle()
	{
		List<ItemDrop> drops = getPlayer().getLevel().getEntitiesInRadius(getPlayer().getX(), getPlayer().getY(), 1.0F, ItemDrop.class);
		if(!drops.isEmpty())
		{
			drops.get(0).setPickedUp();
			getPlayer().getInventory().addItem(drops.get(0).getItem());
			LoggingHandler.logger.log(Level.INFO, "Picked up: " + drops.get(0).getItem().getDisplayText());
		}
	}
}
