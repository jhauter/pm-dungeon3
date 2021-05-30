package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDGroup;

public class InputStrategyCloseChest extends InputStrategy
{
	
	/**
	 * Allows to close the Inventory HUD of a open Chest
	 * @param player
	 */
	public InputStrategyCloseChest(Player player)
	{
		super(player);
	}
	
	@Override
	public void handle()
	{
		HUDGroup g = DungeonStart.getDungeonMain().getChestHud();
		if(g != null)
		{
			DungeonStart.getDungeonMain().getHudManager().removeGroup(g);
			DungeonStart.getDungeonMain().getHudManager().setElementOnMouse(null);
		}
		Label label = DungeonStart.getDungeonMain().getChestLabel();
		if(label != null)
		{
			label.setText("");
		}
	}
}
