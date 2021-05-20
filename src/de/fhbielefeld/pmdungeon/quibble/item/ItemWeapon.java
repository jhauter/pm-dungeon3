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
	
	/**
	 * Creates a weapon item.
	 * @param name user friendly display name
	 * @param itemWidth render width of this weapon
	 * @param itemHeight render height of this weapon
	 * @param visibleTime time in seconds that this weapon will be visible when used
	 * @param texture texture used to render this item
	 */
	protected ItemWeapon(String name, float itemWidth, float itemHeight, float visibleTime, String texture)
	{
		super(name);
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
		this.visibleTime = visibleTime;
		this.texture = texture;
	}
	
	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canBeConsumed()
	{
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTexture()
	{
		return this.texture;
	}

	/**
	 * @return render width of this weapon
	 */
	public float getItemWidth()
	{
		return itemWidth;
	}

	/**
	 * @return render height of this weapon
	 */
	public float getItemHeight()
	{
		return itemHeight;
	}

	/**
	 * @return time in seconds that this weapon is visible when used
	 */
	public float getVisibleTime()
	{
		return visibleTime;
	}
	
	/**
	 * @param user the creature that used the weapon
	 * @return the particle movement that the spawned weapon particle should use
	 */
	public ParticleMovement getWeaponMovement(Creature user) {
		return null;
	}
}
