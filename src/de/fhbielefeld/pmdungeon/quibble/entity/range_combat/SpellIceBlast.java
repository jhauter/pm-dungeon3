package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectSpeed;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class SpellIceBlast extends ProjectileMagic {

	private Creature creature;

	/**
	 * Creates a Spell called Ice Blast
	 * 
	 * @param point    Spawning Point of this projectile
	 * @param creature Creature that used this spell
	 */
	public SpellIceBlast(String name, Point point, Creature creature) {
		super(name, point, creature);
		this.creature = creature;
		this.animationHandler.addAsDefaultAnimation(ProjectileTypes.SPELL_FIRE_BALL.name, 8, 5,
				ProjectileTypes.SPELL_FIRE_BALL.path, 4);
	}

	/**
	 * Will also effect an status effect at the knocked entity
	 */
	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (otherEntity instanceof Creature) {
			if(otherEntity == creature) {
				return;
			}
			((Creature) otherEntity).addStatusEffect(new StatusEffectSpeed((Creature) otherEntity, 0.005), 100);
		}
	}
}
