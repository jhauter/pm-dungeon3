package de.fhbielefeld.pmdungeon.quibble.inventory;

public class DefaultInventory implements Inventory
{
	private final InventoryItem[] items;
	
	public DefaultInventory(int capacity)
	{
		this.items = new InventoryItem[capacity];
	}
	
	@Override
	public int getCapacity()
	{
		return this.items.length;
	}
	
	@Override
	public InventoryItem getItem(int index)
	{
		return this.items[index];
	}
	
	@Override
	public void setItem(int index, InventoryItem itemType)
	{
		this.items[index] = itemType;
	}
	
	@Override
	public boolean addItem(InventoryItem itemType)
	{
		int emptySlot = this.getEmptySlot();
		if(emptySlot == -1)
		{
			return false;
		}
		this.items[emptySlot] = itemType;
		return true;
	}

	@Override
	public InventoryItem removeItem(int index)
	{
		InventoryItem ret = this.items[index];
		this.items[index] = null;
		return ret;
	}

	@Override
	public int getEmptySlot()
	{
		for(int i = 0; i < this.items.length; ++i)
		{
			if(this.items[i] == null)
			{
				return i;
			}
		}
		return -1;
	}
}
