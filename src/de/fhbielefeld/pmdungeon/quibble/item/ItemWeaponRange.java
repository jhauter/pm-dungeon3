package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class ItemWeaponRange extends ItemWeapon {

	private Creature creature;

	protected ItemWeaponRange(String name, float itemWidth, float itemHeight, float visibleTime, String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
	}

	Point setProjectileStartPoint(float shift, float adjustYAxis) {
		if (isLookingLeft())
			return new Point(creature.getPosition().x - shift, creature.getPosition().y + adjustYAxis);
		return new Point(creature.getPosition().x + shift, creature.getPosition().y + adjustYAxis);
	}

	float setVelocity(float speed) {
		if (isLookingLeft())
			return -speed;
		return speed;
	}

	void setUser(Creature creature) {
		this.creature = creature;
	}

	private boolean isLookingLeft() {
		return creature.getLookingDirection() == LookingDirection.LEFT;
	}

}
