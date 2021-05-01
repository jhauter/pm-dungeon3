package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class BagInventoryItem<T extends Item, C extends Item> extends DefaultInventoryItem<T>
{
	private Inventory<C> bagInventory;
	
	public BagInventoryItem(T type, int bagCapacity)
	{
		super(type);
		this.bagInventory = new DefaultInventory<>(bagCapacity);
	}
	
	public Inventory<C> getBagItems()
	{
		return this.bagInventory;
	}
	
	@Override
	public String getDispalayText()
	{
		return super.getDispalayText() + Inventory.inventoryString(this.bagInventory);
	}
}
