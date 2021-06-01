package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;

/**
 * This class is used to register a bag item in the game. This is not an actual bag.
 * Actual bags are instances of the class <code>BagInventoryItem</code>.
 * @author Andreas
 *
 * @param <C> the item type that the bag can store
 */
public class ItemBag<C extends Item> extends Item
{
	private final int bagCapacity;
	
	/**
	 * Creates a bag item.
	 * @param displayName a user friendly display name
	 * @param capacity capacity of the bag
	 * @param texture file name of the texture without file extension.
	 * File must be in {@value Item#ITEMS_TEXTURE_PATH}.
	 */
	public ItemBag(String displayName, int capacity, String texture)
	{
		super(displayName, 15, Item.ITEMS_TEXTURE_PATH + texture + ".png");
		this.bagCapacity = capacity;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		System.out.println("what to do when bag is used??");
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canBeConsumed()
	{
		return false;
	}
	
	/**
	 * @return the capacity of this bag item
	 */
	public int getBagCapacity()
	{
		return this.bagCapacity;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BagInventoryItem<Item, C> createInventoryItem()
	{
		return new BagInventoryItem<Item, C>(this, this.bagCapacity);
	}
}
