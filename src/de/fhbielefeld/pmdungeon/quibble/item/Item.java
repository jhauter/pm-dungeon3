package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;

public abstract class Item
{
	public static final String ITEMS_TEXTURE_PATH = "assets/textures/items/";
	
	public static final Item POTION_RED_BIG = new ItemHealingPotion("Healing Potion", 5.0D, "pot_red_big");
	public static final Item POTION_RED_SMALL = new ItemHealingPotion("Small Healing Potion", 2.0D, "pot_red_small");
	public static final ItemWeaponMelee SWORD_BLUE = new ItemSwordBlue("Blue Sword", 0.5F, 1.25F, 3.5F, 0.25F, "sword_blue");
	public static final ItemBag<Item> BAG_DEFAULT = new ItemBag<Item>("Bag", 4, "bag_default");
	
	private final String displayName;
	
	/**
	 * Creates an item with the specified display name
	 * @param name the display name of the item
	 */
	protected Item(String name)
	{
		this.displayName = name;
	}
	
	/**
	 * This is called when a creature uses this item.
	 * @param user the creature that used the item
	 */
	public abstract void onUse(Creature user);
	
	/**
	 * Items that can be consumed will disappear from the inventory after use.
	 * @return whether this item is consumable
	 */
	public abstract boolean canBeConsumed();
	
	/**
	 * The texture that is used to render the item. Note the <code>ITEMS_TEXTURE_PATH</code> constant,
	 * that will be put at the beginning of the texture path.
	 * @return the texture that is used to render the item
	 */
	public abstract String getTexture();
	
	/**
	 * These stats will be added to a creature's base stats if the creature has this item equipped.
	 * @return stats of this item
	 */
	public CreatureStats getItemStats()
	{
		//Everything is initialized to 0.0
		return new CreatureStats();
	}
	
	/**
	 * @return user friendly display name
	 */
	public final String getDisplayName()
	{
		return this.displayName;
	}
	
	/**
	 * This generates an <code>InventoryItem</code> for use with inventories.
	 * @return a new <code>InventoryItem</code> instance that represents this item
	 */
	public InventoryItem<? extends Item> createInventoryItem()
	{
		return new DefaultInventoryItem<Item>(this);
	}
}
