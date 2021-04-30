package de.fhbielefeld.pmdungeon.quibble.inventory;

import java.util.ArrayList;
import java.util.List;

public class DefaultInventory implements Inventory
{
	private final InventoryItem[] items;
	
	private List<InventoryListener> inventoryListeners;
	
	public DefaultInventory(int capacity)
	{
		this.items = new InventoryItem[capacity];
		this.inventoryListeners = new ArrayList<>();
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
		final InventoryItem old = this.items[index];
		this.items[index] = itemType;
		this.inventoryListeners.forEach(c -> c.onInventoryChange(index, old, this.items[index]));
	}
	
	@Override
	public boolean addItem(InventoryItem itemType)
	{
		int emptySlot = this.getEmptySlot();
		if(emptySlot == -1)
		{
			return false;
		}
		this.setItem(emptySlot, itemType);
		return true;
	}

	@Override
	public InventoryItem removeItem(int index)
	{
		InventoryItem ret = this.items[index];
		this.setItem(index, null);
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

	@Override
	public void addInventoryListener(InventoryListener listener)
	{
		if(this.inventoryListeners.contains(listener))
		{
			throw new IllegalArgumentException("this InventoryListener was already added");
		}
		this.inventoryListeners.add(listener);
	}

	@Override
	public void removeInventoryListener(InventoryListener list)
	{
		this.inventoryListeners.remove(list);
	}
}
