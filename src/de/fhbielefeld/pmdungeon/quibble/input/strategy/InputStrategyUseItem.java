package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import com.badlogic.gdx.Gdx;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;

public class InputStrategyUseItem extends InputStrategy
{
	
	/**
	 * Allows the Player to use an Item
	 * To use A Range Weapon it has to be a Mouse Button
	 * @param player user of the method
	 */
	public InputStrategyUseItem(Player player)
	{
		super(player);
	}
	
	@Override
	public void handle()
	{
		if(getPlayer().getSelectedEquipSlot() > -1
			&& getPlayer().getSelectedEquipSlot() < getPlayer().getEquippedItems().getCapacity())
		{
			int mouseX = Gdx.input.getX();
			int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
			
			float dungeonX = DrawingUtil.screenToDungeonXCam(mouseX, DungeonStart.getDungeonMain().getCamPosX());
			float dungeonY = DrawingUtil.screenToDungeonYCam(mouseY, DungeonStart.getDungeonMain().getCamPosY());
			
			getPlayer().useEquippedItem(getPlayer().getSelectedEquipSlot(), dungeonX, dungeonY);
		}
	}
}
