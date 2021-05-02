package de.fhbielefeld.pmdungeon.quibble.item;

public abstract class ItemPotion extends Item
{
	private final String texture;
	
	/**
	 * Creates a potion item.
	 * @param displayName user friendly display name
	 * @param texture texture that is used to render the item
	 */
	public ItemPotion(String displayName, String texture)
	{
		super(displayName);
		this.texture = texture;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canBeConsumed()
	{
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTexture()
	{
		return this.texture;
	}
}
