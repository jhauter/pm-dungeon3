package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;


import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public abstract class MagicalSpell extends RangedCombat {

	public MagicalSpell(float x, float y, Player player, double damageAmount) {
		super(x, y, player, damageAmount);
		this.setPosition(x, y);
	}
	
	@Override
	public DamageType getDamageType() {
		return DamageType.MAGICAL;
	}
	
}
