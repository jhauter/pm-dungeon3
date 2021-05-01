package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public interface InventoryItem<T extends Item>
{
	/**
	 * @return the item that this <code>InventoryItem</code> represents
	 */
	public T getItemType();
	
	/**
	 * @return user friendly textual representation of this item
	 */
	public String getDisplayText();
}
