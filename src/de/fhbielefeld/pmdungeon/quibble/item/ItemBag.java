package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;

public class ItemBag<C extends Item> extends Item
{
	private final String texture;
	
	private final int bagCapacity;
	
	public ItemBag(String displayName, int capacity, String texture)
	{
		super(displayName);
		this.texture = texture;
		this.bagCapacity = capacity;
	}
	
	@Override
	public void onUse(Creature user)
	{
		System.out.println("what to do when bag is used??");
	}
	
	@Override
	public boolean canBeConsumed()
	{
		return false;
	}

	@Override
	public String getTexture()
	{
		return this.texture;
	}
	
	public int getBagCapacity()
	{
		return this.bagCapacity;
	}
	
	@Override
	public BagInventoryItem<ItemBag<C>, C> createInventoryItem()
	{
		return new BagInventoryItem<ItemBag<C>, C>(this, this.bagCapacity);
	}
}
