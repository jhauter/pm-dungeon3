package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

@FunctionalInterface
public interface InventoryListener<T extends Item>
{
	/**
	 * This is called every time the inventory is changed to which this listener has been added.
	 * @param slot the index of the inventory slot that has been changed
	 * @param oldValue the previous item that was in the slot
	 * @param newValue the new item that is now in the slot
	 */
	public void onInventoryChange(int slot, InventoryItem<T> oldValue, InventoryItem<T> newValue);
}
