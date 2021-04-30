package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

public abstract class Item
{
	public static final Item POTION_RED_BIG = new ItemHealingPotion("Healing Potion", 5.0D);
	public static final Item POTION_RED_SMALL = new ItemHealingPotion("Small Healing Potion", 2.0D);
	
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
