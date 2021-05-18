package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;

public class SpellIceBlast extends RangeCombatMagic {

	public SpellIceBlast(float x, float y, Creature creature) {
		super(x, y, creature);

		this.animationHandler.addAsDefaultAnimation(RangedCombatUtils.SPELL_ICE_BLAST.name, 8, 5,
				RangedCombatUtils.SPELL_ICE_BLAST.path, 4);
	}
	
	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (otherEntity instanceof Creature) {
			((Creature) otherEntity).addStatusEffect(new StatusEffectSpeed((Creature) otherEntity, 0.005), 100);
		}
	}

	@Override
	public double getDamageFromStat() {
		return this.getCreature().getCurrentStats().getStat(CreatureStatsAttribs.DAMAGE_MAGIC);
	}
	
}
