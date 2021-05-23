package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.Projectile;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.ProjectileTypes;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.SpellFireBall;

public class ItemRedMagicStaff extends ItemWeaponMagic {
	
	/**
	 * 
	 * @param name        user friendly display name
	 * @param itemWidth   render width of this weapon
	 * @param itemHeight  render height of this weapon
	 * @param swingSpeed  the speed at which this weapon will swing
	 * @param visibleTime time in seconds that this weapon will be visible when used
	 * @param texture     texture used to render this item
	 */
	protected ItemRedMagicStaff(String name, float itemWidth, float itemHeight, float swingTime, float visibleTime,
			String texture) {
		super(name, itemWidth, itemHeight, swingTime, visibleTime, texture);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getItemStats() {
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 5);
		return stats;
	}

	@Override
	public Projectile spawnProjectile(Creature user) {
		Projectile fireBall = new SpellFireBall(ProjectileTypes.SPELL_FIRE_BALL.name(), user.getPosition(), user);
		fireBall.setVelocityX(1);
		return fireBall;
	}

}
