package de.fhbielefeld.pmdungeon.quibble.entity;

import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class ItemDrop extends Entity
{
	private final InventoryItem<Item> item;
	
	private boolean isPickedUp;
	
	public ItemDrop(InventoryItem<Item> item, float x, float y)
	{
		super(x, y);
		this.item = item;
		this.animationHandler.addAsDefaultAnimation("default", 1, 999, Item.ITEMS_TEXTURE_PATH + item.getItemType().getTexture() + ".png", -1);
	}
	
	public ItemDrop(InventoryItem<Item> item)
	{
		this(item, 0.0F, 0.0F);
	}
	
	public InventoryItem<Item> getItem()
	{
		return this.item;
	}
	
	@Override
	public boolean deleteableWorkaround()
	{
		return this.isPickedUp;
	}
	
	public void setPickedUp()
	{
		this.isPickedUp = true;
	}
}
