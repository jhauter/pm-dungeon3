package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class DefaultInventoryItem implements InventoryItem
{
	private final Item type;
	
	public DefaultInventoryItem(Item type)
	{
		this.type = type;
	}
	
	@Override
	public Item getItemType()
	{
		return this.type;
	}
}
