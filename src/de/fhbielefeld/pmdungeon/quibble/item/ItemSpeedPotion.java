package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;

public class ItemSpeedPotion extends ItemPotion
{
	private double speedAmount;
	private final int ticks;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param speedAmount the amount this potion should heal
	 * @param ticks the frames the speed effect should be stay on
	 * @param texture texture that is used to render the item
	 */
	public ItemSpeedPotion(String displayName, double speedAmount,int ticks, String texture)
	{
		super(displayName, texture);
		this.speedAmount = speedAmount;
		this.ticks = ticks;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUse(Creature user)
	
	{
		user.addStatusEffect(new StatusEffectSpeed(user, speedAmount), ticks);
	}

	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}
}
