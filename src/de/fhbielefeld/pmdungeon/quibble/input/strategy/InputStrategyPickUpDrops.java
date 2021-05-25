package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.ItemDrop;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class InputStrategyPickUpDrops extends InputStrategy{

	/**
	 * Allows the Player to pick up a drop from the Ground
	 * @param player user of the Method
	 */
	public InputStrategyPickUpDrops(Player player) {
		super(player);
	}

	@Override
	public void handle() {
		ItemDrop drop = getClosestItemDrop();
		if (drop != null) {
			drop.setPickedUp();
			getPlayer().getInventory().addItem(drop.getItem());
			LoggingHandler.logger.log(Level.INFO, "Picked up: " + drop.getItem().getDisplayText());
		}
	}

	/**
	 * @return the closest dropped item entity
	 */
	private ItemDrop getClosestItemDrop() {
		if (getPlayer().getLevel() != null) {
			List<Entity> l = getPlayer().getLevel().getEntitiesInRadius(getPlayer().getX(), getPlayer().getY(), 1);
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i) instanceof ItemDrop) {
					return (ItemDrop) l.get(i);
				}
			}
		}
		return null;
	}
	
}
