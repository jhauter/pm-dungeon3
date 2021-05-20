package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class Arrow extends ProjectilePhysical {

	public Arrow(Point point, Creature creature) {
		super(point, creature);
		
		this.animationHandler.addAsDefaultAnimation(ProjectileTypes.SHOT_ARROW.name, 1, 1,
				ProjectileTypes.SHOT_ARROW.path, 4);
	}

	@Override
	protected boolean isPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

}
