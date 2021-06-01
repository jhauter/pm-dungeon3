package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;

public abstract class ItemPotion extends Item
{
	/**
	 * Creates a potion item.
	 * @param displayName user friendly display name
	 * @param texture file name of the texture without file extension.
	 * File must be in {@value Item#ITEMS_TEXTURE_PATH}.
	 */
	public ItemPotion(String displayName, String texture)
	{
		super(displayName, 10, Item.ITEMS_TEXTURE_PATH + texture + ".png");
		this.holdOffsetX = 0.25F;
		this.renderOffsetY = 0.25F;
		this.renderPivotX = 0.5F;
		this.renderPivotY = 0.5F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canBeConsumed()
	{
		return false;
	}
	
	@Override
	public ParticleMovement getOnUseMovement(Creature user, float targetX, float targetY)
	{
		return new Swing((int)user.getLookingDirection().getAxisX(), 4F, true);
	}
}
