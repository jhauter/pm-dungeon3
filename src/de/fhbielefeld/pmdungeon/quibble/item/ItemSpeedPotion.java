package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;

public class ItemSpeedPotion extends ItemPotion
{
	private final double speedAmount;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param healAmount the amount this potion should heal
	 * @param texture texture that is used to render the item
	 */
	public ItemSpeedPotion(String displayName, double speedAmount, String texture)
	{
		super(displayName, texture);
		this.speedAmount = speedAmount;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUse(Creature user)
	{
		user.getMaxStats().setStat(CreatureStatsAttribs.WALKING_SPEED, speedAmount);
	}

	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}
}
