package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.Projectile;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.ProjectileTypes;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.SpellIceBlast;

public class ItemGreenMagicStaff extends ItemWeaponMagic {

	/**
	 * A Green Magic staff which fires Ice Blast. Ice Blast's will slow down the hit
	 * creature
	 * 
	 * @param name        user friendly display name
	 * @param itemWidth   render width of this weapon
	 * @param itemHeight  render height of this weapon
	 * @param swingSpeed  the speed at which this weapon will swing
	 * @param visibleTime time in seconds that this weapon will be visible when used
	 * @param texture     texture used to render this item
	 */
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
	public Projectile spawnProjectile(Creature user) {
		Projectile iceBlast = new SpellIceBlast(ProjectileTypes.SPELL_ICE_BLAST.name(), user.getPosition(), user);
		iceBlast.setVelocityX(1);
		return iceBlast;
	}
}
