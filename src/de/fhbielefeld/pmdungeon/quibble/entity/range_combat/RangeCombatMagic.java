package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;


import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public abstract class RangeCombatMagic extends Projectile {

	public RangeCombatMagic(float x, float y, Creature creature) {
		super(x, y, creature);
		this.setPosition(x, y);
	}
	
	@Override
	public DamageType getDamageType() {
		return DamageType.MAGICAL;
	}
	
}
