package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.chest.Chest;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerOpenChestEvent;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;

public class InputStrategyOpenChest extends InputStrategy
{
	
	/**
	 * Strategy that allows the player to open a Chest
	 * @param player user of the Method
	 */
	public InputStrategyOpenChest(Player player)
	{
		super(player);
	}
	
	@Override
	public void handle()
	{
		List<Chest> chests = getPlayer().getLevel().getEntitiesInRadius(getPlayer().getX(), getPlayer().getY(), 1.0F, Chest.class);
		if(!chests.isEmpty())
		{
			PlayerOpenChestEvent chestEvent = (PlayerOpenChestEvent)getPlayer()
				.fireEvent(new PlayerOpenChestEvent(PlayerOpenChestEvent.EVENT_ID, getPlayer(), chests.get(0)));
			
			if(!chestEvent.isCancelled())
			{
				chests.get(0).setOpen();
				
				LoggingHandler.logger.log(Level.INFO, Inventory.inventoryString(chests.get(0).getInv()));
			}
		}
	}
}
