package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemHealingPotion extends Item
{
	private final double healAmount;
	
	private final String texture;
	
	public ItemHealingPotion(String displayName, double healAmount, String texture)
	{
		super(displayName);
		this.healAmount = healAmount;
		this.texture = texture;
	}
	
	@Override
	public void onUse(Creature user)
	{
		user.heal(this.healAmount);
	}
	
	@Override
	public boolean canBeConsumed()
	{
		return true;
	}

	@Override
	public String getTexture()
	{
		return this.texture;
	}
	
	@Override
	public CreatureStats getItemStats()
	{
		CreatureStats s = new CreatureStats();
		s.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 20.0D);
		s.setStat(CreatureStatsAttribs.HEALTH, 20.0D);
		return s;
	}
}
