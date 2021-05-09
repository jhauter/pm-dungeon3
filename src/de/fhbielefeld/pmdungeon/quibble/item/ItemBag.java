package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;

/**
 * This class is used to register a bag item in the game. This is not an actual bag.
 * Actual bags are instances of the class <code>BagInventoryItem</code>.
 * @author Andreas
 *
 * @param <C> the item type that the bag can store
 */
public class ItemBag<C extends Item> extends Item
{
	private final String texture;
	
	private final int bagCapacity;
	
	/**
	 * Creates a bag item.
	 * @param displayName a user friendly display name
	 * @param capacity capacity of the bag
	 * @param texture texture to render the item
	 */
	public ItemBag(String displayName, int capacity, String texture)
	{
		super(displayName);
		this.texture = texture;
		this.bagCapacity = capacity;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUse(Creature user)
	{
		System.out.println("what to do when bag is used??");
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
	 * {@inheritDoc}
	 */
	@Override
	public String getTexture()
	{
		return this.texture;
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
	public BagInventoryItem<ItemBag<C>, C> createInventoryItem()
	{
		return new BagInventoryItem<ItemBag<C>, C>(this, this.bagCapacity);
	}

	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}
}
