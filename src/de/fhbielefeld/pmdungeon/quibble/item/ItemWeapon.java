package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleWeapon;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class ItemWeapon extends Item
{
	private final float itemWidth;
	
	private final float itemHeight;
	
	private final float visibleTime;
	
	private final String texture;
	
	protected ItemWeapon(String name, float itemWidth, float itemHeight, float visibleTime, String texture)
	{
		super(name);
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
		this.visibleTime = visibleTime;
		this.texture = texture;
	}

	@Override
	public void onUse(Creature user)
	{
		if(user.getHitCooldown() > 0.0D)
		{
			return;
		}
		final Point weaponOffset = user.getWeaponHoldOffset();
		user.getLevel().getParticleSystem().addParticle(
			new ParticleWeapon(this, user.getX() + weaponOffset.x, user.getY() + weaponOffset.y, user),
			this.getWeaponMovement(user));
	}

	@Override
	public boolean canBeConsumed()
	{
		return false;
	}

	@Override
	public String getTexture()
	{
		return this.texture;
	}

	public float getItemWidth()
	{
		return itemWidth;
	}

	public float getItemHeight()
	{
		return itemHeight;
	}

	public float getVisibleTime()
	{
		return visibleTime;
	}
	
	public abstract ParticleMovement getWeaponMovement(Creature user);
}
