package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemGreenMagicStaff extends ItemWeaponMelee
{
	protected ItemGreenMagicStaff(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime, String texture)
	{
		super(name, itemWidth, itemHeight, swingSpeed, visibleTime, texture);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getItemStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 3.0D);
		return stats;
	}
}
