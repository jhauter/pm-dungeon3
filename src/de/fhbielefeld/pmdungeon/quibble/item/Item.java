package de.fhbielefeld.pmdungeon.quibble.item;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;

public abstract class Item
{
	private static List<Item> registeredItems = new ArrayList<Item>();
	
	/**
	 * @return the amount of items in the game
	 */
	public static int getNumItems()
	{
		return registeredItems.size();
	}
	
	/**
	 * @param index the index of the item
	 * @return returns the item at the specified index
	 */
	public static Item getItem(int index)
	{
		return registeredItems.get(index);
	}
	
	public static final String ITEMS_TEXTURE_PATH = "assets/textures/items/";
	
	public static final ItemHealingPotion POTION_RED_BIG = new ItemHealingPotion("Healing Potion", 5.0D, "pot_red_big");
	public static final ItemHealingPotion POTION_RED_SMALL = new ItemHealingPotion("Small Healing Potion", 2.0D, "pot_red_small");
	public static final ItemSpeedPotion POTION_YELLOW_BIG = new ItemSpeedPotion("Big Speed Potion",  0.3, 300, "pot_yellow_big");
	public static final ItemSpeedPotion POTION_YELLOW_Small = new ItemSpeedPotion("Small Speed Potion",  0.2, 200, "pot_yellow_small");
	public static final ItemSightPotion POTION_SIGHT_BIG = new ItemSightPotion("Big Sight Potion", 0.2D, "pot_green_big");
	public static final ItemWeaponMelee SWORD_BLUE = new ItemSwordBlue("Blue Sword", 1.25F, 1.25F, 3.5F, 0.25F, "sword_blue");
	public static final ItemWeaponMelee SWORD_KATANA = new ItemKatana("Katana", 0.25F, 1.25F, 3.5F, 0.25F, "katana");
	public static final ItemWeaponMagic RED_MAGIC_STAFF = new ItemRedMagicStaff("Red Staff", 0.25F, 1.25F, 3.5F, 0.25F, "red_magic_staff");
	public static final ItemWeaponMagic GREEN_MAGIC_STAFF = new ItemGreenMagicStaff("Green Staff", 0.15F, 1.25F, 3.5F, 0.25F, "green_magic_staff");
	public static final ItemBag<Item> BAG_DEFAULT = new ItemBag<Item>("Bag", 3, "bag");
	
	private final String displayName;
	
	/**
	 * Creates an item with the specified display name
	 * @param name the display name of the item
	 */
	protected Item(String name)
	{
		this.displayName = name;
		Item.registeredItems.add(this);
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
	
	public abstract void accept(ItemVisitor visitor);
}
