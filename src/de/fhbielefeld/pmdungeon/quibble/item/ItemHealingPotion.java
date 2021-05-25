package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public class ItemHealingPotion extends ItemPotion
{
	private final double healAmount;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param healAmount the amount this potion should heal
	 * @param texture texture that is used to render the item
	 */
	public ItemHealingPotion(String displayName, double healAmount, String texture)
	{
		super(displayName, texture);
		this.healAmount = healAmount;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUse(Creature user)
	{
		user.heal(this.healAmount);
	}
}
