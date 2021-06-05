package de.fhbielefeld.pmdungeon.quibble.item;

public abstract class ItemWeapon extends Item
{
	/**
	 * Creates a weapon item.
	 * @param name user friendly display name
	 * @param visibleTime amount of ticks that this weapon will be visible when used
	 * @param texture the texture that is used to render the item. This is the
	 * complete relative path to the texture including the file extension.
	 */
	protected ItemWeapon(String name, int visibleTime, String texture)
	{
		super(name, visibleTime, texture);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canBeConsumed()
	{
		return false; //don't delete the weapon after use
	}
}
