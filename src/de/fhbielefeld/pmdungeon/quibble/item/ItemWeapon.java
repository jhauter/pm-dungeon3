package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public class ItemWeapon extends Item
{
	protected ItemWeapon(String name)
	{
		super(name);
	}

	@Override
	public void onUse(Creature user)
	{
		user.attackAoE();
	}

	@Override
	public boolean canBeConsumed()
	{
		return false;
	}

	@Override
	public String getTexture()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
