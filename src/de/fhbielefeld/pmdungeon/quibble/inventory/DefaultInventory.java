package de.fhbielefeld.pmdungeon.quibble.inventory;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class DefaultInventory<T extends Item> implements Inventory<T>
{
	private final ArrayList<InventoryItem<T>> items;
	
	private final int capacity;
	
	private List<InventoryListener> inventoryListeners;
	
	public DefaultInventory(int capacity)
	{
		this.capacity = capacity;
		this.items = new ArrayList<InventoryItem<T>>(capacity);
		for(int i = 0; i < capacity; ++i)
		{
			this.items.add(null);
		}
		this.inventoryListeners = new ArrayList<>();
	}
	
	@Override
	public int getCapacity()
	{
		return this.items.size();
	}
	
	@Override
	public InventoryItem<T> getItem(int index)
	{
		return this.items.get(index);
	}
	
	@Override
	public void setItem(int index, InventoryItem<T> itemType)
	{
		final InventoryItem<T> old = this.items.get(index);
		this.items.set(index, itemType);
		this.inventoryListeners.forEach(c -> c.onInventoryChange(index, old, this.items.get(index)));
	}
	
	@Override
	public boolean addItem(InventoryItem<T> itemType)
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
	public InventoryItem<T> removeItem(int index)
	{
		InventoryItem<T> ret = this.items.get(index);
		this.setItem(index, null);
		return ret;
	}

	@Override
	public int getEmptySlot()
	{
		for(int i = 0; i < this.capacity; ++i)
		{
			if(this.items.get(i) == null)
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
