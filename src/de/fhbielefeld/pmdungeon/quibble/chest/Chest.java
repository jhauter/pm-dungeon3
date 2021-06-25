package de.fhbielefeld.pmdungeon.quibble.chest;

import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.RandomItemGenerator;

public abstract class Chest extends Entity
{
	public static final String ANIM_OPEN_STATE = "open_state";
	public static final String ANIM_OPENING = "opening";
	public static final String TEXTURE_PATH_CHEST = "assets/textures/chest/";
	
	private final Inventory<Item> inv;
	
	private boolean isOpen = false;
	
	public Chest(float x, float y)
	{
		super(x, y);
		this.inv = new DefaultInventory<>(5);
		fillChest();
	}
	
	private void fillChest()
	{
		Random r = new Random();
		int itemAmount = r.nextInt(5) + 1;

		for(int j = 0; j < itemAmount; j++){
			this.inv.addItem(RandomItemGenerator.getInstance().generateRandomItem(DungeonStart.getDungeonMain().getLevelCount()));
		}
	}
	
	/**
	 * @return the inventory of the chest
	 */
	public Inventory<Item> getInv()
	{
		return inv;
	}
	
	/**
	 * Puts the chest into the "open" state visually and plays an animation that
	 * shows the chest opening.
	 */
	public void setOpen()
	{
		if(!this.isOpen)
		{
			this.isOpen = true;
			this.animationHandler.playAnimation(ANIM_OPENING, 100, false);
		}
	}
	
	/**
	 * @return whether the chest has been opened
	 */
	public boolean isOpen()
	{
		return this.isOpen;
	}
	
	@Override
	protected void updateAnimationState()
	{
		super.updateAnimationState();
		if(isOpen)
		{
			this.animationHandler.playAnimation(ANIM_OPEN_STATE, 2, true);
		}
	}
}
