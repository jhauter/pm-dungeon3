package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemSwordBlue extends ItemWeaponMelee
{
	protected ItemSwordBlue()
	{
		this("Blue Sword");
		
	}
	
	protected ItemSwordBlue(String displayName)
	{
		super(displayName, 3.5F, 15, "assets/textures/items/sword_blue.png");
		this.renderWidth = 1.25F;
		this.renderHeight = 1.25F;
		
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 4.0D);
		this.setAttackStats(stats);
	}
}
