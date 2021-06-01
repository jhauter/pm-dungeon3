package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemKatana extends ItemWeaponMelee
{
	protected ItemKatana()
	{
		super("Katana", 3.5F, 15, "assets/textures/items/katana.png");
		this.renderWidth = 1.25F;
		this.renderHeight = 1.25F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getAttackStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 10.0D);
		return stats;
	}
}
