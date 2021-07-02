package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectFogSight;

public class ItemFogSightPotion extends ItemPotion
{
	private final double fogSightChange;
	private final int duration;
	
	public ItemFogSightPotion(String displayName, double fogSightChange, int duration, String texture)
	{
		super(displayName, texture);
		this.fogSightChange = fogSightChange;
		this.duration = duration;
	}
	
	@Override
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		user.addStatusEffect(new StatusEffectFogSight(user, this.fogSightChange), this.duration);
		return true;
	}
}
