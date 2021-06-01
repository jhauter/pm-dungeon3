package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;

public class ItemWeaponMelee extends ItemWeapon
{
	private final float swingSpeed;
	
	/**
	 * Creates a melee weapon item.
	 * @param name user friendly display name
	 * @param swingSpeed the speed at which this weapon will swing
	 * @param visibleTime the number of ticks that the item is displayed at the creature
	 * @param texture the texture that is used to render the item. This is the
	 * complete relative path to the texture including the file extension.
	 */
	protected ItemWeaponMelee(String name, float swingSpeed, int visibleTime, String texture)
	{
		super(name, visibleTime, texture);
		this.swingSpeed = swingSpeed;
		this.renderPivotX = 0.5F;
		this.renderPivotY = 0.0F;
		this.renderOffsetY = 0.5F;
		this.renderOffsetX = 0.0F;
	}
	
	/**
	 * {@inheritDoc}<br>
	 * 
	 * This method will make a creature perform an AOE attack.
	 * Spawning of the weapon particle is handled in {@link ItemWeapon}
	 */
	@Override
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		if(user.getHitCooldown() > 0.0D)
		{
			return false;
		}
		user.attackAoE();
		return true;
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
	 * @return the speed at which this weapon will swing
	 */
	public float getSwingSpeed()
	{
		return swingSpeed;
	}
	
	public ParticleMovement getOnUseMovement(Creature user, float targetX, float targetY)
	{
		return new Swing((int)user.getLookingDirection().getAxisX(), this.swingSpeed, false, 25.0F);
	}
}
