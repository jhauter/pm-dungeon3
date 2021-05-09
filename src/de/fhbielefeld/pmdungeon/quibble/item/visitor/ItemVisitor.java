package de.fhbielefeld.pmdungeon.quibble.item.visitor;

import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.ItemBag;
import de.fhbielefeld.pmdungeon.quibble.item.ItemHealingPotion;
import de.fhbielefeld.pmdungeon.quibble.item.ItemSightPotion;
import de.fhbielefeld.pmdungeon.quibble.item.ItemSpeedPotion;
import de.fhbielefeld.pmdungeon.quibble.item.ItemWeapon;

public interface ItemVisitor
{
	default void visit(ItemHealingPotion item)
	{
		
	}
	
	default void visit(ItemSightPotion item)
	{
		
	}
	
	default void visit(ItemSpeedPotion item)
	{
		
	}
	
	default void visit(ItemBag<? extends Item> item)
	{
		
	}
	
	default void visit(ItemWeapon item)
	{
		
	}
}
