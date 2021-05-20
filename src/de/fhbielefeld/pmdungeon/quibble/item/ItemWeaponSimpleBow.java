package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.Projectile;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;

public class ItemWeaponSimpleBow extends ItemWeaponRange{

	protected ItemWeaponSimpleBow(String name, float itemWidth, float itemHeight, float visibleTime, String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void accept(ItemVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CreatureStats getItemStats() {
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2);
		return super.getItemStats();
	}

	@Override
	public Projectile spawnProjectile(Creature user) {
		// TODO Auto-generated method stub
		return null;
	}

}
