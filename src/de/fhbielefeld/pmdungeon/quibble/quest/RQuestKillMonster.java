package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class RQuestKillMonster extends Quest {
	
	private int toKill;
	private int counter;
	
	/**
	 * Creates a quest object that can be completed by the player by killing a certain amount of monsters.
	 * @param questName the display name of the quest
	 * @param p the player that should have the quest
	 * @param itemOnReward the item that is rewarded when the quest is completed
	 * @param expOnReward the exp that are rewarded when the quest is completed
	 */
	public RQuestKillMonster(String questName, Player p, Item itemOnReward, int expOnReward, int toKill) {
		super(questName, p, itemOnReward, expOnReward);
		this.toKill = toKill;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTask() {
		return "Kill " + toKill + " Monsters";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String onWork() {
		return counter + "/" + toKill;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleEvent(EntityEvent event) {
		if(event.getEventID() == Creature.EVENT_ID_HIT_TARGET_POST) {
			final CreatureHitTargetPostEvent hitEvent = (CreatureHitTargetPostEvent)event;
			if(hitEvent.getTarget().isDead() && hitEvent.getTarget().getDeadTicks() == 0) {
				this.counter ++;
				if(counter == toKill) {
					this.setCompleted(true);
					LoggingHandler.logger.log(Level.INFO, "The quest " + this.getQuestName() + "was completed");
				}
			}
		}
	}

	public int getToKill() {
		return toKill;
	}

	public void setToKill(int toKill) {
		this.toKill = toKill;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
}
