package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;

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
			getPlayer().useEquippedItem(getPlayer().getSelectedEquipSlot());
		}
	}
}
