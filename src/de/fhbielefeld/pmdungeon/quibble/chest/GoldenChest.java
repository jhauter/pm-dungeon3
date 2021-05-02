package de.fhbielefeld.pmdungeon.quibble.chest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.ItemHealingPotion;

public class GoldenChest extends Chest {

	private final Random r = new Random();

	Inventory<Item> inv;
	public Inventory<Item> getInv() {
		return inv;
	}

	Item ItemHealingBig = new ItemHealingPotion("Healing Potion", 10, "pot_red_big");
	Item ItemHealingSmall = new ItemHealingPotion("Healing Potion", 5, "pot_red_Small");
	
	
	List<Item> ItemList = Arrays.asList(ItemHealingBig, ItemHealingSmall);
	HashMap<String, Item> ItemMap = new HashMap<>();
	
	/**
	 * Creates a new Golden Chest at the certain Point
	 * @param x value for position
	 * @param y value for position
	 */
	public GoldenChest(float x, float y){
		super(x, y);
		
		this.animationHandler.addAsDefaultAnimation("", 1, 1, Chest.TEXTURE_PATH_CHEST + "/chest_gold_empty_open_anim/chest_empty_open_anim_f.png", 4);
		this.animationHandler.addAnimation("Open_Gold", 3, 5, Chest.TEXTURE_PATH_CHEST + "/chest_gold_empty_open_anim/chest_empty_open_anim_f.png", 4);
		this.animationHandler.addAnimation("Open", 1, 2, Chest.TEXTURE_PATH_CHEST + "/chest_gold_empty_open_anim/chest_empty_open_anim_f2.png", -1);
		fillInventory();
	}
	
	private void fillInventory() {
	
	}
	
	/**
	 * if activated the chest was opened once and this chest will show a open chest Texture.
	 */
	public void setOpen() {
		this.isOpen = true;
	}
	
	@Override
	protected void updateAnimationState() {
		super.updateAnimationState();
		if (isOpen)
			this.animationHandler.playAnimation("Open", 2, true);
	}
}
