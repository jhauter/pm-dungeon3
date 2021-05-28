package de.fhbielefeld.pmdungeon.quibble.input.strategy;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;

public class InputStrategyDecline extends InputStrategy{

	/**
	 * Allows the player to decline an Event
	 * @param player the certain user
	 */
	public InputStrategyDecline(Player player) {
		super(player);
	}

	@Override
	public void handle() {
		List<Entity> l = getPlayer().getLevel().getEntitiesInRadius(getPlayer().getX(), getPlayer().getY(), 1);
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).canBeAccepted()) {
				if(l.get(i) instanceof QuestDummy) {
					QuestDummy qd = (QuestDummy) l.get(i);
					qd.setDecided(false);
				}
			}
		}
	}

}