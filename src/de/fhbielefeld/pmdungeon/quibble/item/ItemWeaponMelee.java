package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;

public class ItemWeaponMelee extends ItemWeapon
{
	private final float swingSpeed;

	protected ItemWeaponMelee(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime, String texture)
	{
		super(name, itemWidth, itemHeight, visibleTime, texture);
		this.swingSpeed = swingSpeed;
	}

	@Override
	public void onUse(Creature user)
	{
		super.onUse(user);
		if(user.getHitCooldown() > 0.0D)
		{
			return;
		}
		user.attackAoE();
	}

	@Override
	public boolean canBeConsumed()
	{
		return false;
	}
	
	@Override
	public ParticleMovement getWeaponMovement(Creature user)
	{
		SwingOrientation swingDir = user.getLookingDirection() == LookingDirection.RIGHT ? SwingOrientation.RIGHT : SwingOrientation.LEFT;
		return new Swing(swingDir, this.swingSpeed);
	}

	public float getSwingSpeed()
	{
		return swingSpeed;
	}
}
