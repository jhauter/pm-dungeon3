package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class SpellIceBlast extends ProjectileMagic {

	/**
	 * Creates a Spell called Ice Blast
	 * 
	 * @param point    Spawning Point of this projectile
	 * @param creature Creature that used this spell
	 */
	public SpellIceBlast(Point point, Creature creature) {
		super(point, creature);
		this.animationHandler.addAsDefaultAnimation(ProjectileTypes.SPELL_ICE_BLAST.name, 8, 5,
				ProjectileTypes.SPELL_ICE_BLAST.path, 4);
	}

	/**
	 * Will also effect an status effect at the knocked entity
	 */
	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (otherEntity instanceof Creature) {
			((Creature) otherEntity).addStatusEffect(new StatusEffectSpeed((Creature) otherEntity, 0.005), 100);
			LoggingHandler.logger.log(Level.INFO, ProjectileTypes.SPELL_ICE_BLAST.name + " has hit an Entity");
		}
	}

}
