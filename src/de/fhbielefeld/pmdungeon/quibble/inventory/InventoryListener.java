package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

@FunctionalInterface
public interface InventoryListener<T extends Item>
{
	public void onInventoryChange(int slot, InventoryItem<T> oldValue, InventoryItem<T> newValue);
}
