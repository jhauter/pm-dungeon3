package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemRedMagicStaff extends ItemWeaponMagic {
	protected ItemRedMagicStaff(String name, float itemWidth, float itemHeight, float swingTime, float visibleTime,
			String texture, Creature creature) {
		super(name, itemWidth, itemHeight, swingTime, visibleTime, texture, creature);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getItemStats() {
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 2);
		return stats;
	}

}
