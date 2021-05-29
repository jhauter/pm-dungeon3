package de.fhbielefeld.pmdungeon.quibble.chest;

import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public abstract class Chest extends Entity {

	public static final String ANIM_OPEN_STATE = "open_state";
	public static final String ANIM_OPENING= "opening";
	public static final String TEXTURE_PATH_CHEST = "assets/textures/chest/";
	
	private Inventory<Item> inv;
	
	private boolean isOpen = false;

	public Chest(float x, float y) {
		super(x, y);
		this.inv = new DefaultInventory<>(5);
		fillChest();
	}
	
	private void fillChest() {
		Random r = new Random();
		int i = r.nextInt(4) + 1;
		
		for (int j2 = 0; j2 < i; j2++) {
			int j = r.nextInt(Item.getNumItems());
			inv.addItem(Item.getItem(j));
		}
	}
	
	public Inventory<Item> getInv() {
		return inv;
	}
	
	/**
	 * if activated the chest was opened once and this chest will show a open chest Texture.
	 */
	public void setOpen() {
		if(!this.isOpen)
		{
			this.isOpen = true;
			this.animationHandler.playAnimation(ANIM_OPENING, 100, false);
		}
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
