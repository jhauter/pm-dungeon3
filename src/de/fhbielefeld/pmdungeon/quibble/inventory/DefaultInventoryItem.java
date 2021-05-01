package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class DefaultInventoryItem<T extends Item> implements InventoryItem<T>
{
	private final T type;
	
	/**
	 * Creates a default inventory item that can be used with inventories.
	 * @param type the item type that this <code>DefaultInventoryItem</code> should represent
	 */
	public DefaultInventoryItem(T type)
	{
		if(type == null)
		{
			throw new IllegalArgumentException("type of inventory items cannot be null");
		}
		this.type = type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getItemType()
	{
		return this.type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayText()
	{
		return this.type.getDisplayName();
	}
}
