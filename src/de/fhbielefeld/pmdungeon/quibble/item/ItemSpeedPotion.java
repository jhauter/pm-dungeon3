package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;

public class ItemSpeedPotion extends ItemPotion
{
	private double speedAmount;
	private final int ticks;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param speedAmount the amount this potion should increase speed
	 * @param ticks the frames the speed effect should be stay on
	 * @param texture file name of the texture without file extension.
	 * File must be in {@value Item#ITEMS_TEXTURE_PATH}.
	 */
	public ItemSpeedPotion(String displayName, double speedAmount, int ticks, String texture)
	{
		super(displayName, texture);
		this.speedAmount = speedAmount;
		this.ticks = ticks;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		user.addStatusEffect(new StatusEffectSpeed(user, speedAmount), ticks);
		return true;
	}
}
