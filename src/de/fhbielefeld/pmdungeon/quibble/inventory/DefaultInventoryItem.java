package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.entity.items.ItemList;

public class DefaultInventoryItem implements InventoryItem
{
	private final ItemList type;
	
	public DefaultInventoryItem(ItemList type)
	{
		this.type = type;
	}
	
	@Override
	public ItemList getItemType()
	{
		return this.type;
	}
}
