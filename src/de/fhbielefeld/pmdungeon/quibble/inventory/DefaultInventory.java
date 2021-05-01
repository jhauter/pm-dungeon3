package de.fhbielefeld.pmdungeon.quibble.inventory;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class DefaultInventory<T extends Item> implements Inventory<T>
{
	private final ArrayList<InventoryItem<T>> items;
	
	private final int capacity;
	
	private List<InventoryListener<T>> inventoryListeners;
	
	/**
	 * Creates a default inventory that has the given capacity.
	 * @param capacity the capacity that this inventory should have
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCapacity()
	{
		return this.items.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public InventoryItem<T> getItem(int index)
	{
		return this.items.get(index);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setItem(int index, InventoryItem<T> itemType)
	{
		final InventoryItem<T> old = this.items.get(index);
		this.items.set(index, itemType);
		this.inventoryListeners.forEach(c -> c.onInventoryChange(index, old, this.items.get(index)));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setItem(int index, T itemType)
	{
		/*
		 * This cannot be cast if the item creates an inventory item of the wrong type
		 * Example:
		 * 			The inventory can store <ItemWeapon>.
		 * 			We use -> addItem(Item.SWORD)
		 * 			The class for Item.SWORD has method createInventoryItem()
		 * 			createInventoryItem() method does not return InventoryItem<ItemWeapon>
		 */
		this.setItem(index, (InventoryItem<T>)itemType.createInventoryItem());
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean addItem(T itemType)
	{
		/*
		 * This cannot be cast if the item creates an inventory item of the wrong type
		 * Example:
		 * 			The inventory can store <ItemWeapon>.
		 * 			We use -> addItem(Item.SWORD)
		 * 			The class for Item.SWORD has method createInventoryItem()
		 * 			createInventoryItem() method does not return InventoryItem<ItemWeapon>
		 */
		return this.addItem((InventoryItem<T>)itemType.createInventoryItem());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public InventoryItem<T> removeItem(int index)
	{
		InventoryItem<T> ret = this.items.get(index);
		this.setItem(index, (InventoryItem<T>)null);
		return ret;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInventoryListener(InventoryListener<T> listener)
	{
		if(this.inventoryListeners.contains(listener))
		{
			throw new IllegalArgumentException("this InventoryListener was already added");
		}
		this.inventoryListeners.add(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeInventoryListener(InventoryListener<T> list)
	{
		this.inventoryListeners.remove(list);
	}
}
