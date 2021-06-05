package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class InputStrategySelectItem implements InputStrategy
{
	private int index;
	
	public InputStrategySelectItem(int index)
	{
		this.index = index;
	}
	
	@Override
	public void handle(Player player)
	{
		if(Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			InventoryItem<Item> droppedItem = player.getInventory().getItem(index);
			if(player.drop(index) && droppedItem != null)
			{
				LoggingHandler.logger.log(Level.INFO, "Dropped item: " + droppedItem.getDisplayText());
			}
		}
		else
		{
			player.setSelectedEquipSlot(index);
			DungeonStart.getDungeonMain().getUIEquipmentView().setMarkedSlot(index);
			LoggingHandler.logger.log(Level.INFO, "Selected equipment slot " + (index + 1));
		}
	}
	
}
