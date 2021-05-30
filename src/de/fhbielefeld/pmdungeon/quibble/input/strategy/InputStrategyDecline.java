package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;

public class InputStrategyDecline extends InputStrategy
{
	/**
	 * Allows the player to decline an Event
	 * @param player the certain user
	 */
	public InputStrategyDecline(Player player)
	{
		super(player);
	}
	
	@Override
	public void handle()
	{
		List<QuestDummy> l = getPlayer().getLevel().getEntitiesInRadius(getPlayer().getX(), getPlayer().getY(), 1.0F, QuestDummy.class);
		l.forEach(qd -> qd.onPlayerDecision(false, getPlayer()));
	}
}
