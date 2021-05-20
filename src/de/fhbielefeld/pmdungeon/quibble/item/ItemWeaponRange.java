package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;

public abstract class ItemWeaponRange extends ItemWeapon {

	private Creature creature;

	protected ItemWeaponRange(String name, float itemWidth, float itemHeight, float visibleTime, String texture,
			Creature creature) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
		// TODO Auto-generated constructor stub
	}

	boolean isLookingLeft() {
		return creature.getLookingDirection() == LookingDirection.LEFT;
	}

}
