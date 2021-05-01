package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public class ItemHealingPotion extends Item
{
	private final double healAmount;
	
	private final String texture;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param healAmount the amount this potion should heal
	 * @param texture texture that is used to render the item
	 */
	public ItemHealingPotion(String displayName, double healAmount, String texture)
	{
		super(displayName);
		this.healAmount = healAmount;
		this.texture = texture;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUse(Creature user)
	{
		user.heal(this.healAmount);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canBeConsumed()
	{
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTexture()
	{
		return this.texture;
	}
}
