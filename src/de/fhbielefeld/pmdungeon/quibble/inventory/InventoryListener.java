package de.fhbielefeld.pmdungeon.quibble.inventory;

@FunctionalInterface
public interface InventoryListener
{
	public void onInventoryChange(int slot, InventoryItem oldValue, InventoryItem newValue);
}
