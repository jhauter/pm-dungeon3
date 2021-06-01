package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemSwordBlue extends ItemWeaponMelee
{
	protected ItemSwordBlue()
	{
		super("Blue Sword", 3.5F, 15, "assets/textures/items/sword_blue.png");
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
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 4.0D);
		return stats;
	}
}
