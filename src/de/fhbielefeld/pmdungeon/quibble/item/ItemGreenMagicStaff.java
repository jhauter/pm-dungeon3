package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.Projectile;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.SpellIceBlast;

public class ItemGreenMagicStaff extends ItemWeaponMagic {

	private Projectile iceBlast;

	protected ItemGreenMagicStaff(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime,
			String texture) {
		super(name, itemWidth, itemHeight, swingSpeed, visibleTime, texture);
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

	@Override
	public void onUse(Creature user) {
		super.onUse(user);
		this.setUser(user);
		iceBlast = new SpellIceBlast(this.setProjectileStartPoint(1), user);
		iceBlast.setVelocityX(setVelocity(1));
		user.getLevel().spawnEntity(iceBlast);
	}
}
