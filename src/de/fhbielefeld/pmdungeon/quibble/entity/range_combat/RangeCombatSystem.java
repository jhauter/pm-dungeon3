package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

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

	/**
	 * Is used to Use the projectiles.
	 * Projectiles are for Example:
	 * <code>SpellIceBlast<code>
	 * @param level level to spawn the projectile
	 * @param creature creature that use this projectile
	 */
	public RangeCombatSystem(DungeonLevel level, Creature creature) {
		this.creature = creature;
		this.level = level;
		positionX = creature.getPosition().x;
		// else the Projectile will be little to low
		positionY = creature.getPosition().y + 0.5f;
	}

	/**
	 * Is used to decide which combat weapon is used or at all
	 * 
	 * @param projectile the projectile that will be used
	 */
	public void RangedCombat(ProjectileTypes projectile) {
		if (projectile == ProjectileTypes.SPELL_ICE_BLAST) {
			if (creature.getEquippedItems().getItem(0).getItemType() instanceof ItemWeaponMagic) {
				util = new SpellIceBlast(setPoint(0.5f), creature);
				creature.useEquippedItem(0);
				util.setVelocityX(setVelocity(1));
				level.spawnEntity(util);
			}
		}
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

}
