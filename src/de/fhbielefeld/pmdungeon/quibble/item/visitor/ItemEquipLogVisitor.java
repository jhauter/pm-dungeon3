package de.fhbielefeld.pmdungeon.quibble.item.visitor;

import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.ItemBag;
import de.fhbielefeld.pmdungeon.quibble.item.ItemHealingPotion;
import de.fhbielefeld.pmdungeon.quibble.item.ItemWeapon;

public class ItemEquipLogVisitor implements ItemVisitor
{
	@Override
	public void visit(ItemBag<? extends Item> item)
	{
		LoggingHandler.logger.log(Level.INFO, "Inventory: [Bag] " + item.getDisplayName());
	}
	
	@Override
	public void visit(ItemHealingPotion item)
	{
		LoggingHandler.logger.log(Level.INFO, "Inventory: [Potion] " + item.getDisplayName());
	}
	
	@Override
	public void visit(ItemWeapon item)
	{
		LoggingHandler.logger.log(Level.INFO, "Inventory: [Weapon] " + item.getDisplayName());
	}
}
