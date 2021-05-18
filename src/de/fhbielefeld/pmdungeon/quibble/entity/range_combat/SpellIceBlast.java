package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class SpellIceBlast extends RangeCombatMagic {

	public SpellIceBlast(Point point, Creature creature) {
		super(point, creature);

		this.animationHandler.addAsDefaultAnimation(ProjectileTypes.SPELL_ICE_BLAST.name, 8, 5,
				ProjectileTypes.SPELL_ICE_BLAST.path, 4);
	}
	
	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (otherEntity instanceof Creature) {
			((Creature) otherEntity).addStatusEffect(new StatusEffectSpeed((Creature) otherEntity, 0.005), 100);
		}
	}

	@Override
	public CreatureStatsAttribs getDamageFromStat() {
		return CreatureStatsAttribs.DAMAGE_MAGIC;
	}
	
}
