package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;


import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class RangeCombatMagic extends Projectile {

	public RangeCombatMagic(Point point, Creature creature) {
		super(point, creature);
	}
	
	@Override
	public DamageType getDamageType() {
		return DamageType.MAGICAL;
	}
	
}
