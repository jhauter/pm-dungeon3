package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.chest.Chest;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerOpenChestEvent;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;

public class InputStrategyOpenChest extends InputStrategy {

	/**
	 * Strategy that allows the player to open a Chest
	 * @param player user of the Method
	 */
	public InputStrategyOpenChest(Player player) {
		super(player);
		handle();
	}

	@Override
	public void handle() {
		Chest chest = getClosestChest();
		if (chest != null) {
			PlayerOpenChestEvent chestEvent = (PlayerOpenChestEvent) getPlayer()
					.fireEvent(new PlayerOpenChestEvent(PlayerOpenChestEvent.EVENT_ID, getPlayer(), chest));

			if (!chestEvent.isCancelled()) {
				chest.setOpen();

				LoggingHandler.logger.log(Level.INFO, Inventory.inventoryString(chest.getInv()));
			}
		}
	}

	/**
	 * @return the closest chest
	 */
	private Chest getClosestChest() {
		if (getPlayer().getLevel() != null) {
			List<Entity> l = getPlayer().getLevel().getEntitiesInRadius(getPlayer().getX(), getPlayer().getY(), 1);
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i) instanceof Chest) {
					return (Chest) l.get(i);
				}
			}
		}
		return null;
	}
}
