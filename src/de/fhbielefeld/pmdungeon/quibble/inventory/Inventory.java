package de.fhbielefeld.pmdungeon.quibble.inventory;

import de.fhbielefeld.pmdungeon.quibble.item.Item;

public interface Inventory<T extends Item>
{
	public int getCapacity();
	
	public InventoryItem<T> getItem(int index);
	
	public void setItem(int index, InventoryItem<T> itemType);
	
	public void setItem(int index, T itemType);
	
	public boolean addItem(InventoryItem<T> itemType);
	
	public boolean addItem(T itemType);
	
	public InventoryItem<T> removeItem(int index);
	
	public int getEmptySlot();
	
	public void addInventoryListener(InventoryListener<T> listener);
	
	public void removeInventoryListener(InventoryListener<T> list);
	
	public static <T extends Item> void swap(Inventory<T> inv1, int index1, Inventory<T> inv2, int index2)
	{
		InventoryItem<T> item1 = inv1.getItem(index1);
		InventoryItem<T> item2 = inv2.getItem(index2);
		inv1.setItem(index1, item2);
		inv2.setItem(index2, item1);
	}
	
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
	
	public static <T extends Item> String inventoryString(Inventory<T> inv)
	{
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		InventoryItem<T> currentItem;
		final int size = inv.getCapacity();
		for(int i = 0; i < size; ++i)
		{
			currentItem = inv.getItem(i);
			builder.append(currentItem != null ? currentItem.getDispalayText() : "NONE");
			if(i != size - 1)
			{
				builder.append(", ");
			}
		}
		builder.append(']');
		return builder.toString();
	}
}
