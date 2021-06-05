package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;

public class InputStrategyAccept implements InputStrategy
{
	@Override
	public void handle(Player player)
	{
		System.out.println("ACCEPT");
		List<QuestDummy> l = player.getLevel().getEntitiesInRadius(player.getX(), player.getY(), 1.0F, QuestDummy.class);
		l.forEach(qd -> qd.onPlayerDecision(true, player));
	}
}
