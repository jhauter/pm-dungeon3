package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;

public class ItemWeaponBow extends ItemWeapon{

	protected ItemWeaponBow(String name, float itemWidth, float itemHeight, float visibleTime, String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ParticleMovement getWeaponMovement(Creature user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(ItemVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
