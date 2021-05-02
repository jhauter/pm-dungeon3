package de.fhbielefeld.pmdungeon.quibble.particle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.ItemWeapon;

public class ParticleWeapon extends Particle
{
	private static final String TEXTURE_FILE_EXT = ".png";
	
	private final ItemWeapon type;
	
	private final float visibleTime;
	
	private final float width;
	private final float height;
	
	/**
	 * @param type the weapon to display
	 * @param width the width of the weapon
	 * @param height the height of the weapon
	 * @param visibeTime the time the weapon should be visible
	 * @param spawnX the x coordinate to appear on
	 * @param spawnY the y coordinate to appear on
	 * @param particleSource optional particle source that defines an object
	 * with which this particle keep a constant offset with
	 */
	public ParticleWeapon(ItemWeapon type, float spawnX, float spawnY, ParticleSource particleSource)
	{
		super(spawnX, spawnY, particleSource);
		this.type = type;
		this.originX = 0.5F;
		this.originY = 0.0F;
		this.visibleTime = type.getVisibleTime();
		this.width = type.getItemWidth();
		this.height = type.getItemHeight();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDisappear()
	{
		return this.timeExisted > this.visibleTime;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DungeonResource<TextureRegion> getTexture()
	{
		return ResourceHandler.requestResourceInstantly(Item.ITEMS_TEXTURE_PATH + this.type.getTexture() + TEXTURE_FILE_EXT, ResourceType.TEXTURE_REGION);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getWidth()
	{
		return this.width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getHeight()
	{
		return this.height;
	}
}
