package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class InputStrategySelectItem extends InputStrategy
{
	
	private int index;
	
	public InputStrategySelectItem(Player player, int index)
	{
		super(player);
		this.index = index;
	}
	
	@Override
	public void handle()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			InventoryItem<Item> droppedItem = getPlayer().getInventory().getItem(index - 1);
			if(getPlayer().drop(index - 1) && droppedItem != null)
			{
				LoggingHandler.logger.log(Level.INFO, "Dropped item: " + droppedItem.getDisplayText());
			}
		}
		else
		{
			getPlayer().setSelectedEquipSlot(index - 1);
			DungeonStart.getDungeonMain().setMarkedEquipSlot(index - 1);
			LoggingHandler.logger.log(Level.INFO, "Selected equipment slot " + (index));
		}
	}
	
}
