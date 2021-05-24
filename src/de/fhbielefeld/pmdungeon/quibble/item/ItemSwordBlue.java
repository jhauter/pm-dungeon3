package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemSwordBlue extends ItemWeaponMelee
{
	protected ItemSwordBlue(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime, String texture)
	{
		super(name, itemWidth, itemHeight, swingSpeed, visibleTime, texture);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getAttackStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 4.0D);
		return stats;
	}
}
