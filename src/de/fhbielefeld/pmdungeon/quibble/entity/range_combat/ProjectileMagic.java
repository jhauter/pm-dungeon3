package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class ProjectileMagic extends Projectile {

	/**
	 * Creates a projectile of a spell Used as parent class, it only should get used
	 * if the Creature can use Magic, like with a Staff
	 * 
	 * @param point spawning point of the projectile
	 * @param creature that used this magic 
	 */
	public ProjectileMagic(String name, Point point, Creature creature) {
		super(name, point, creature);
	}

	@Override
	public DamageType getDamageType() {
		return DamageType.MAGICAL;
	}
	
	@Override
	public CreatureStatsAttribs getDamageFromStat() {
		 return CreatureStatsAttribs.DAMAGE_MAGIC;
	}

}
