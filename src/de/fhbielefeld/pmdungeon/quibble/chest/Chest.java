package de.fhbielefeld.pmdungeon.quibble.chest;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public abstract class Chest extends Entity {
	
	public static final String ANIM_OPEN_STATE = "Open";
	public static final String TEXTURE_PATH_CHEST = "assets/textures/chest/";
	
	private Inventory<Item> inv;
	
	private boolean isOpen = false;

	public Chest(float x, float y) {
		super(x, y);
		this.inv = new DefaultInventory<>(5);
	}
	
	public Inventory<Item> getInv() {
		return inv;
	}
	
	/**
	 * if activated the chest was opened once and this chest will show a open chest Texture.
	 */
	public void setOpen() {
		this.isOpen = true;
	}
	
	public boolean isOpen()
	{
		return this.isOpen;
	}
	
	@Override
	protected void updateAnimationState() {
		super.updateAnimationState();
		if (isOpen)
			this.animationHandler.playAnimation(ANIM_OPEN_STATE, 2, true);
	}
}
