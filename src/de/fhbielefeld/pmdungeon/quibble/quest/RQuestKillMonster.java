package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;

public class RQuestKillMonster extends Quest {

	private int killed;
	private int toKill;
	private int counter;

	public RQuestKillMonster(String questName, Player p, int toKill, int onReward) {
		super(questName, p, onReward);
		this.killed = p.getKilledEntitys();
		this.toKill = toKill + killed;
	}O

	@Override
	public String getTask() {
		return "Kill " + toKill + " Monsters";
	}

	@Override
	public String onWork() {
		return counter + "/" + toKill;
	}

	@Override
	public String onComplete() {
		return QUEST_REACH + onReward();
	}

	@Override
	public void handleEvent(EntityEvent event) {
		if(event.getEventID() == Creature.EVENT_ID_HIT_TARGET_POST) {
			final CreatureHitTargetPostEvent hitEvent = (CreatureHitTargetPostEvent)event;
			if(hitEvent.getTarget().isDead() && hitEvent.getTarget().getDeadTicks() == 0) {
				this.counter ++;
				if(counter == toKill) {
					this.setCompleted(true);
				}
			}
		}
	}

	@Override
	public void onReward(Player p) {
		System.out.println(onReward());
		p.rewardExp((int) onReward());
	}
}
