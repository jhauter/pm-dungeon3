package de.fhbielefeld.pmdungeon.quibble.chest;

public class GoldenChest extends Chest
{
	/**
	 * Creates a golden chest at the given position
	 * @param x the x-position
	 * @param y the y-position
	 */
	public GoldenChest(float x, float y)
	{
		super(x, y);
		
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 4, Chest.TEXTURE_PATH_CHEST + "chest.png");
		this.animationHandler.addAnimation(ANIM_OPENING, 3, 0.25F, 1, 4, Chest.TEXTURE_PATH_CHEST + "chest_full.png");
		this.animationHandler.addAnimation(ANIM_OPEN_STATE, 1, 2, 999, 1, 4, Chest.TEXTURE_PATH_CHEST + "chest_full.png");
	}
}
