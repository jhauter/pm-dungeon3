package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;

public class InputStrategyActivateQuest extends InputStrategy {

	private QuestDummy questDummy;

	/**
	 * Allows the player to activate a Quest if he stand right beneath it
	 * @param player the certain player
	 */
	public InputStrategyActivateQuest(Player player) {
		super(player);
	}

	@Override
	public void handle() {
		List<Entity> l = getPlayer().getLevel().getEntitiesInRadius(getPlayer().getX(), getPlayer().getY(), 1);
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i) instanceof QuestDummy) {
				questDummy = (QuestDummy) l.get(i);
				questDummy.setActive(getPlayer());
			}
		}
	}
}
