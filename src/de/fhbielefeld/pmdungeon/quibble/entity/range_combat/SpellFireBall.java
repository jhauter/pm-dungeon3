package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class SpellFireBall extends ProjectileMagic{
	private Creature creature;

	public SpellFireBall(String name, Point point, Creature creature) {
		super(name, point, creature);
		this.creature = creature;
		this.animationHandler.addAsDefaultAnimation(ProjectileTypes.SPELL_FIRE_BALL.name, 8, 5,
				ProjectileTypes.SPELL_FIRE_BALL.path, 4);
	}

}
