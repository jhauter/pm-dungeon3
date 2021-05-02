package de.fhbielefeld.pmdungeon.quibble.chest;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public abstract class Chest extends Entity {
	public static final String TEXTURE_PATH_CHEST = "assets/textures/chest/";
	Boolean isOpen = false;

	public Chest(float x, float y) {
		super(x, y);
	}
}
