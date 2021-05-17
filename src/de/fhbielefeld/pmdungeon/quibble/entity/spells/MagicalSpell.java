package de.fhbielefeld.pmdungeon.quibble.entity.spells;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public abstract class MagicalSpell extends Entity{

	
	
	@Override
	public boolean isInvisible() {
		//for not using the AnimationHandler
		return true;
	}
}
