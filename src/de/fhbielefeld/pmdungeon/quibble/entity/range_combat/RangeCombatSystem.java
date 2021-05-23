package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public interface RangeCombatSystem {

	/**
	 * Will determine if a entity should shoot a projectile. It will only attempt to
	 * use a Ranged Weapon, if the Player is on the same y-Axis and is looking
	 * towards the player
	 * 
	 * @param creature     Monster who is able to shoot
	 * @param listOfEntity Entity List in reach of the Monster
	 * @return true if a Monster should fire a projectile
	 */
	public static boolean shouldShoot(Creature creature, List<Entity> listOfEntity) {
		for (Entity entity : listOfEntity) {
			if (entity instanceof Player) {
				int p1 = (int) creature.getPosition().y;
				int p2 = (int) entity.getPosition().y;
				if (p1 == p2) {
					if (creature.getPosition().x - entity.getPosition().x > 0
							&& creature.getLookingDirection() == LookingDirection.LEFT)
						return true;
					else if (creature.getPosition().x - entity.getPosition().x < 0
							&& creature.getLookingDirection() == LookingDirection.RIGHT)
						return true;
				}
			}
		}
		return false;
	}
}
