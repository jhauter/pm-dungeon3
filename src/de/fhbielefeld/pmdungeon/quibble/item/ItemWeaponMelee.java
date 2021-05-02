package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;

public class ItemWeaponMelee extends ItemWeapon
{
	private final float swingSpeed;

	/**
	 * Creates a melee weapon item.
	 * @param name user friendly display name
	 * @param itemWidth render width of this weapon
	 * @param itemHeight render height of this weapon
	 * @param swingSpeed the speed at which this weapon will swing
	 * @param visibleTime time in seconds that this weapon will be visible when used
	 * @param texture texture used to render this item
	 */
	protected ItemWeaponMelee(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime, String texture)
	{
		super(name, itemWidth, itemHeight, visibleTime, texture);
		this.swingSpeed = swingSpeed;
	}

	/**
	 * {@inheritDoc}<br>
	 * 
	 * This method will make a creature perform an AOE attack.
	 * Spawning of the weapon particle is handled in {@link ItemWeapon}
	 */
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
	public ParticleMovement getWeaponMovement(Creature user)
	{
		SwingOrientation swingDir = user.getLookingDirection() == LookingDirection.RIGHT ? SwingOrientation.RIGHT : SwingOrientation.LEFT;
		return new Swing(swingDir, this.swingSpeed);
	}

	/**
	 * @return the speed at which this weapon will swing
	 */
	public float getSwingSpeed()
	{
		return swingSpeed;
	}

	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}
}
