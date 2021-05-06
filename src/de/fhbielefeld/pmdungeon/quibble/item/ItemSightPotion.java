package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;
import de.fhbielefeld.pmdungeon.quibble.trap.Trap;

public class ItemSightPotion extends ItemPotion
{
	private final double timeOfSight;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param healAmount the amount this potion should heal
	 * @param texture texture that is used to render the item
	 */
	public ItemSightPotion(String displayName, double timeOfSight, String texture)
	{
		super(displayName, texture);
		this.timeOfSight = timeOfSight;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUse(Creature user)
	{
		Trap.PlayerEffect = true;
	}

	@Override
	public void accept(ItemVisitor visitor)
	{
//		visitor.visit(this);
	}
}
