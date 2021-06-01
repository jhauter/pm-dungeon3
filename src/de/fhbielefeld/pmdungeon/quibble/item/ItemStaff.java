package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;

public abstract class ItemStaff extends ItemWeaponRanged
{
	private float swingSpeed;
	
	/**
	 * Creates a Magic weapon. Magic weapon mostly have status effects in the
	 * certain Weapon
	 * 
	 * @param name        user friendly display name
	 * @param swingSpeed  the speed at which this weapon will swing
	 * @param visibleTime the number of ticks that the item is displayed at the creature
	 * @param texture the texture that is used to render the item. This is the
	 * complete relative path to the texture including the file extension.
	 */
	protected ItemStaff(String name, float swingSpeed, int visibleTime,
		String texture)
	{
		super(name, visibleTime, texture);
		this.swingSpeed = swingSpeed;
		this.renderPivotX = 0.5F;
		this.renderPivotY = 0.0F;
		this.renderOffsetX = 0.0F;
		this.renderOffsetY = 0.5F;
		this.renderAllowNegativeEntityScale = true;
	}
	
	public ParticleMovement getOnUseMovement(Creature user, float targetX, float targetY)
	{
		return new Swing((int)user.getLookingDirection().getAxisX(), this.swingSpeed, false, 25.0F);
	}
}
