package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public class ItemHealingPotion extends ItemPotion
{
	private final double healAmount;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param healAmount the amount this potion should heal
	 * @param texture file name of the texture without file extension.
	 * File must be in {@value Item#ITEMS_TEXTURE_PATH}.
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
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		user.heal(this.healAmount);
		return true;
	}
}
