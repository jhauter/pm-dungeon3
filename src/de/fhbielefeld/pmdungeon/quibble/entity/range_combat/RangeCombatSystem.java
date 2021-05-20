package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import java.util.HashMap;
import java.util.Map;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.item.ItemWeaponMagic;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class RangeCombatSystem {

	private DungeonLevel level;
	private Creature creature;
	private Projectile util;

	private float positionX;
	private float positionY;

	Map<ProjectileTypes, Strategy> strategyMap;

	/**
	 * Is used to Use the projectiles. Projectiles are for Example:
	 * <code>SpellIceBlast<code>
	 * 
	 * @param level    level to spawn the projectile
	 * @param creature creature that use this projectile
	 */
	public RangeCombatSystem(DungeonLevel level) {
		this.level = level;
		strategyMap = createStrategy();
	}

	/**
	 * Is used to decide which combat weapon is used or at all
	 * 
	 * @param projectile the projectile that will be used
	 */
	public void RangedCombat(ProjectileTypes projectile, Creature creature) {
		this.creature = creature;
		positionX = creature.getPosition().x;
		// else the Projectile will be little to low
		positionY = creature.getPosition().y + 0.5f;
		
		strategyMap.get(projectile).spawn();
	}

	/**
	 * Convenience Method
	 * 
	 * @return true if the entity is looking left
	 */
	private boolean isLookingLeft() {
		return creature.getLookingDirection() == LookingDirection.LEFT;
	}

	/**
	 * Utility method to set the projectile little bit left or right from i'ts user
	 * 
	 * @param shift the amount of shift a projectile have from it's user
	 * @return the new Point, a little bit right or left from the certain entity
	 */
	private Point setPoint(float shift) {
		if (isLookingLeft())
			return new Point(positionX - shift, positionY);
		return new Point(positionX + shift, positionY);
	}

	/**
	 * Utility method to set the movement of the projectile to right or left
	 * 
	 * @param speed the amount of speed and range a projectile will receive
	 * @return a float which will be negative or positive whether the entity is
	 *         looking left or right
	 */
	private float setVelocity(float speed) {
		if (isLookingLeft())
			return -speed;
		return speed;
	}
	
	/**
	 * Combined all Strategys to avoid if else statements
	 * @return
	 */
	private Map<ProjectileTypes, Strategy> createStrategy() {
		HashMap<ProjectileTypes, Strategy> map = new HashMap<>();
		map.put(ProjectileTypes.SPELL_ICE_BLAST, iceBlast);
		return map;
	}
	
	/**
	 * Strategy interface for <code>strategyMap<code>
	 *
	 */
	static interface Strategy {
		void spawn();
	}

	/**
	 * Interface to Spawn IceBlast
	 * If user holds a Magic Weapon on ItemSlot 1 (Index 0)
	 */
	Strategy iceBlast = new Strategy() {

		@Override
		public void spawn() {
			if (creature.getEquippedItems().getItem(0).getItemType() instanceof ItemWeaponMagic) {
				util = new SpellIceBlast(setPoint(0.5f), creature);
				creature.useEquippedItem(0);
				util.setVelocityX(setVelocity(1));
				level.spawnEntity(util);
			}
		}
	};
	
//	Strategy iceBlast = new Strategy() {
//
//		@Override
//		public void spawn() {
//			if (creature.getEquippedItems().getItem(0).getItemType() instanceof ItemWeaponMagic) {
//				util = new SpellIceBlast(setPoint(0.5f), creature);
//				creature.useEquippedItem(0);
//				util.setVelocityX(setVelocity(1));
//				level.spawnEntity(util);
//			}
//		}
//	};

}
