package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class ProjectilePhysical extends Projectile {

	public ProjectilePhysical(String name, Point point, Creature creature) {
		super(name, point, creature);
	}
	
	@Override
	public DamageType getDamageType() {
		return DamageType.PHYSICAL;
	}
	
	@Override
	public CreatureStatsAttribs getDamageFromStat() {
		 return CreatureStatsAttribs.DAMAGE_PHYS;
	}

}
