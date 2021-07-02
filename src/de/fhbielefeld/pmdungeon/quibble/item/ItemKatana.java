package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class ItemKatana extends ItemWeaponMelee
{
	protected ItemKatana()
	{
		this("Katana");
	}
	
	protected ItemKatana(String displayName)
	{
		super(displayName, 3.5F, 15, "assets/textures/items/katana.png");
		this.renderWidth = 1.25F;
		this.renderHeight = 1.25F;
		
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 10.0D);
		this.setAttackStats(stats);
	}
}
