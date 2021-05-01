package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class BagInventoryItem<T extends Item, C extends Item> extends DefaultInventoryItem<T>
{
	private Inventory<C> bagInventory;
	
	/**
	 * Creates an <code>InventoryItem</code> that represents a bag that has its own inventory.
	 * @param type the item type that represents the bag itself
	 * @param bagCapacity the capacity of the bag
	 */
	public BagInventoryItem(T type, int bagCapacity)
	{
		super(type);
		this.bagInventory = new DefaultInventory<>(bagCapacity);
	}
	
	/**
	 * @return the inventory of the bag
	 */
	public Inventory<C> getBagItems()
	{
		return this.bagInventory;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayText()
	{
		return super.getDisplayText() + Inventory.inventoryString(this.bagInventory);
	}
}
