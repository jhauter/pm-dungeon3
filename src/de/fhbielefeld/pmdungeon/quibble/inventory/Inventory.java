package de.fhbielefeld.pmdungeon.quibble.inventory;

public interface Inventory
{
	public int getCapacity();
	
	public InventoryItem getItem(int index);
	
	public void setItem(int index, InventoryItem itemType);
	
	public boolean addItem(InventoryItem itemType);
	
	public InventoryItem removeItem(int index);
	
	public int getEmptySlot();
	
	public static void swap(Inventory inv1, int index1, Inventory inv2, int index2)
	{
		InventoryItem item1 = inv1.getItem(index1);
		InventoryItem item2 = inv2.getItem(index2);
		inv1.setItem(index1, item2);
		inv2.setItem(index2, item1);
	}
	
	public static boolean transfer(Inventory inv1, int index1, Inventory inv2)
	{
		int emptySlotInv2 = inv2.getEmptySlot();
		if(emptySlotInv2 == -1)
		{
			return false;
		}
		inv2.setItem(emptySlotInv2, inv1.getItem(index1));
		inv1.setItem(index1, null);
		return true;
	}
	
	public static String inventoryString(Inventory inv)
	{
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		InventoryItem currentItem;
		final int size = inv.getCapacity();
		for(int i = 0; i < size; ++i)
		{
			currentItem = inv.getItem(i);
			builder.append(currentItem != null ? currentItem : "LEER");
			if(i != size - 1)
			{
				builder.append(", ");
			}
		}
		builder.append(']');
		return builder.toString();
	}
}
