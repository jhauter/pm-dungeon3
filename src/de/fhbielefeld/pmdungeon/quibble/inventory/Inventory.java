package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public interface Inventory<T extends Item>
{
	/**
	 * @return the maximum amount of items in this inventory
	 */
	public int getCapacity();
	
	/**
	 * Returns the item in the specified inventory slot as <code>InventoryItem</code>.
	 * @param index the index of the inventory slot
	 * @return the item in the specified slot
	 */
	public InventoryItem<T> getItem(int index);
	
	/**
	 * Sets the item in the specified inventory slot.
	 * @param index the index of the inventory slot 
	 * @param itemType the <code>InventoryItem</code> that should be in the slot
	 */
	public void setItem(int index, InventoryItem<T> itemType);
	
	/**
	 * Sets the item in the specified inventory slot. The <code>InventoryItem</code> is generated by
	 * the {@link Item#createInventoryItem()} method.
	 * @param index the index of the inventory slot 
	 * @param itemType the <code>InventoryItem</code> that should be in the slot
	 */
	public void setItem(int index, T itemType);
	
	/**
	 * Adds the specified item to the inventory in the first free slot.
	 * @param itemType the <code>InventoryItem</code> that should be added
	 * @return whether the operation was successful, i.e. there was free space in the inventory
	 */
	public boolean addItem(InventoryItem<T> itemType);
	
	/**
	 * Adds the specified item to the inventory in the first free slot. The <code>InventoryItem</code> is generated by
	 * the {@link Item#createInventoryItem()} method. 
	 * @param itemType the <code>InventoryItem</code> that should be added
	 * @return whether the operation was successful, i.e. there was free space in the inventory
	 */
	public boolean addItem(T itemType);
	
	/**
	 * Removes the item in the specified slot from this inventory
	 * @param index the index of the slot of the item to remove
	 * @return the removed item
	 */
	public InventoryItem<T> removeItem(int index);
	
	/**
	 * @return the first empty slot in this inventory or <code>-1</code> if there are no empty slots
	 */
	public int getEmptySlot();
	
	/**
	 * Adds an inventory listener to this inventory which will be notified when this inventory is changed.
	 * @param listener the listener to add
	 * @throws IllegalArgumentException if the listener is already added
	 */
	public void addInventoryListener(InventoryListener<T> listener);
	
	/**
	 * Removes the specified inventory listener from this inventory.
	 * If the specified listener has not been added to this inventory, then this will do nothing.
	 * @param listener the listener to remove
	 */
	public void removeInventoryListener(InventoryListener<T> listener);
	
	/**
	 * Swaps two items of different inventories.
	 * @param <T> the item type both of the inventories contain
	 * @param inv1 the first inventory
	 * @param index1 slot of the item in the first inventory
	 * @param inv2 the second inventory
	 * @param index2 slot of the item in the first inventory
	 */
	public static <T extends Item> void swap(Inventory<T> inv1, int index1, Inventory<T> inv2, int index2)
	{
		InventoryItem<T> item1 = inv1.getItem(index1);
		InventoryItem<T> item2 = inv2.getItem(index2);
		inv1.setItem(index1, item2);
		inv2.setItem(index2, item1);
	}
	
	/**
	 * Transfers an item from one inventory to another.
	 * If there are no empty slots in the target inventory then this method will return <code>false</code>.
	 * @param <T> the item type both of the inventories contain
	 * @param inv1 the first inventory
	 * @param index1 slot of the item in the first inventory
	 * @param inv2 the second inventory
	 * @return whether the operation was successful, i.e there was an empty slot in the target inventory
	 */
	public static <T extends Item> boolean transfer(Inventory<T> inv1, int index1, Inventory<T> inv2)
	{
		int emptySlotInv2 = inv2.getEmptySlot();
		if(emptySlotInv2 == -1)
		{
			return false;
		}
		inv2.setItem(emptySlotInv2, inv1.getItem(index1));
		inv1.setItem(index1, (InventoryItem<T>)null);
		return true;
	}
	
	/**
	 * Creates a textual representation of the specified inventory
	 * @param <T> the item type the inventory contains
	 * @param inv the inventory
	 * @return the textual representation of the specified inventory
	 */
	public static <T extends Item> String inventoryString(Inventory<T> inv)
	{
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		InventoryItem<T> currentItem;
		final int size = inv.getCapacity();
		for(int i = 0; i < size; ++i)
		{
			currentItem = inv.getItem(i);
			builder.append(currentItem != null ? currentItem.getDisplayText() : "NONE");
			if(i != size - 1)
			{
				builder.append(", ");
			}
		}
		builder.append(']');
		return builder.toString();
	}
}
