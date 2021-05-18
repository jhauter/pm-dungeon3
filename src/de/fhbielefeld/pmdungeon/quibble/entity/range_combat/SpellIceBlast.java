package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;

public class SpellIceBlast extends MagicalSpell {

	public SpellIceBlast(float x, float y, Player player, double damageAmount) {
		super(x, y, player, damageAmount);

		this.animationHandler.addAsDefaultAnimation(RangedCombatUtils.SPELL_ICE_BLAST.name, 8, 5,
				RangedCombatUtils.SPELL_ICE_BLAST.path, 4);
	}
	
	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (otherEntity instanceof Creature && !(otherEntity instanceof Player)) {
			((Creature) otherEntity).addStatusEffect(new StatusEffectSpeed((Creature) otherEntity, 0.005), 100);
		}
	}
	
	
}
