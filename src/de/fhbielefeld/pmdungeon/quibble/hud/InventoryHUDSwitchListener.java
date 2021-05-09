package de.fhbielefeld.pmdungeon.quibble.hud;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryListener;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class InventoryHUDSwitchListener implements InventoryListener<Item>
{
	private final DungeonStart mainRef;
	
	private final String inventoryID;
	
	private final Inventory<Item> inventory;
	
	private final int displayX, displayY;
	
	public InventoryHUDSwitchListener(DungeonStart mainRef, String invID, Inventory<Item> inv, int x, int y)
	{
		this.mainRef = mainRef;
		this.inventoryID = invID;
		this.inventory = inv;
		this.displayX = x;
		this.displayY = y;
	}
	
	@Override
	public void onInventoryChange(int slot, InventoryItem<Item> oldValue, InventoryItem<Item> newValue)
	{
		this.show();
	}
	
	public void show()
	{
		this.mainRef.showInventory(this.inventoryID, this.inventory, this.displayX, this.displayY);
	}
}
