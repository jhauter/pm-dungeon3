package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.chest.Chest;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerOpenChestEvent;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;

public class InputStrategyOpenChest implements InputStrategy
{
	@Override
	public void handle(Player player)
	{
		List<Chest> chests = player.getLevel().getEntitiesInRadius(player.getX(), player.getY(), 1.0F, Chest.class);
		if(!chests.isEmpty())
		{
			PlayerOpenChestEvent chestEvent = (PlayerOpenChestEvent)player
				.fireEvent(new PlayerOpenChestEvent(PlayerOpenChestEvent.EVENT_ID, player, chests.get(0)));
			
			if(!chestEvent.isCancelled())
			{
				chests.get(0).setOpen();
				
				LoggingHandler.logger.log(Level.INFO, Inventory.inventoryString(chests.get(0).getInv()));
			}
		}
	}
}
