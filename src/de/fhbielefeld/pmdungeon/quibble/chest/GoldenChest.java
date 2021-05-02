package de.fhbielefeld.pmdungeon.quibble.chest;

public class GoldenChest extends Chest {
	
	/**
	 * Creates a new Golden Chest at the certain Point
	 * @param x value for position
	 * @param y value for position
	 */
	public GoldenChest(float x, float y){
		super(x, y);
		
		this.animationHandler.addAsDefaultAnimation("", 1, 1, Chest.TEXTURE_PATH_CHEST + "/chest_gold_empty_open_anim/chest_empty_open_anim_f.png", 4);
		this.animationHandler.addAnimation("Open_Gold", 3, 5, Chest.TEXTURE_PATH_CHEST + "/chest_gold_empty_open_anim/chest_empty_open_anim_f.png", 4);
		this.animationHandler.addAnimation(ANIM_OPEN_STATE, 1, 2, Chest.TEXTURE_PATH_CHEST + "/chest_gold_empty_open_anim/chest_empty_open_anim_f2.png", -1);
		fillInventory();
	}
	
	private void fillInventory() {
	
	}
}
