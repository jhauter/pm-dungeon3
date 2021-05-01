package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class DefaultInventoryItem<T extends Item> implements InventoryItem<T>
{
	private final T type;
	
	public DefaultInventoryItem(T type)
	{
		this.type = type;
	}
	
	@Override
	public T getItemType()
	{
		return this.type;
	}
}
