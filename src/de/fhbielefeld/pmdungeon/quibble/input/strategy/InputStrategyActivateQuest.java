package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;

public class InputStrategyActivateQuest implements InputStrategy
{
	@Override
	public void handle(Player player)
	{
		List<QuestDummy> l = player.getLevel().getEntitiesInRadius(player.getX(), player.getY(), 1.0F, QuestDummy.class);
		l.forEach(qd -> qd.showQuestDescription());
	}
}
