package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public interface InventoryItem<T extends Item>
{
	public T getItemType();
}
