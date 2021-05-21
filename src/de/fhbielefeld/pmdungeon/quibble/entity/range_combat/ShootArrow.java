package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class ShootArrow extends ProjectilePhysical {

	public ShootArrow(String name, Point point, Creature creature) {
		super(name, point, creature);
		this.animationHandler.addAsDefaultAnimation(ProjectileTypes.SHOT_ARROW.name, 1, 1,
				"assets/textures/projectiles/arrow_right_anim_f.png", 4);
	}

}
