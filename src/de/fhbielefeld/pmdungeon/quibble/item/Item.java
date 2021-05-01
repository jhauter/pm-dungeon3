package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

public abstract class Item
{
	public static final String ITEMS_TEXTURE_PATH = "assets/textures/items/";
	
	public static final Item POTION_RED_BIG = new ItemHealingPotion("Healing Potion", 5.0D, "pot_red_big");
	public static final Item POTION_RED_SMALL = new ItemHealingPotion("Small Healing Potion", 2.0D, "pot_red_small");
	public static final Item SWORD_BLUE = new ItemSwordBlue("Blue Sword", 0.5F, 1.25F, 3.5F, 0.25F, "sword_blue");
	
	private final String displayName;
	
	protected Item(String name)
	{
		this.displayName = name;
	}
	
	public abstract void onUse(Creature user);
	
	public abstract boolean canBeConsumed();
	
	public abstract String getTexture();
	
	public CreatureStats getItemStats()
	{
		//Everything is initialized to 0.0
		return new CreatureStats();
	}
	
	public final String getDisplayName()
	{
		return this.displayName;
	}
}
