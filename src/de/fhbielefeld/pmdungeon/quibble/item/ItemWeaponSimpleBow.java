package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.Projectile;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.ShootArrow;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.SpellIceBlast;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;

public class ItemWeaponSimpleBow extends ItemWeaponRange{

	private Projectile arrow;
	
	protected ItemWeaponSimpleBow(String name, float itemWidth, float itemHeight, float visibleTime, String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUse(Creature user) {
		super.onUse(user);
		setUser(user);
		user.getLevel().spawnEntity(spawnProjectile(user));
		user.getCurrentStats().setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15);
	}


	@Override
	public void accept(ItemVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CreatureStats getItemStats() {
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2);
		return stats;
	}

	@Override
	public Projectile spawnProjectile(Creature user) {
		arrow = new ShootArrow(this.setProjectileStartPoint(0.5f), user);
		arrow.setVelocityX(setVelocity(1));
		return arrow;
	}

}
