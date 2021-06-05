package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import com.badlogic.gdx.Gdx;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;

public class InputStrategyUseItem implements InputStrategy
{
	@Override
	public void handle(Player player)
	{
		if(player.getSelectedEquipSlot() > -1
			&& player.getSelectedEquipSlot() < player.getEquippedItems().getCapacity())
		{
			int mouseX = Gdx.input.getX();
			int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
			
			float dungeonX = DrawingUtil.screenToDungeonXCam(mouseX, DungeonStart.getDungeonMain().getCamPosX());
			float dungeonY = DrawingUtil.screenToDungeonYCam(mouseY, DungeonStart.getDungeonMain().getCamPosY());
			
			player.useEquippedItem(player.getSelectedEquipSlot(), dungeonX, dungeonY);
		}
	}
}
