package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemGreenMagicStaff extends ItemWeaponMagic {
	protected ItemGreenMagicStaff(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime,
			String texture, Creature creature) {
		super(name, itemWidth, itemHeight, swingSpeed, visibleTime, texture, creature);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getItemStats() {
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 3.0D);
		return stats;
	}
}
