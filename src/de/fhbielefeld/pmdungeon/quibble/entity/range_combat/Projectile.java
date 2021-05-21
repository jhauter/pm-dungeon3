package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Projectile extends Entity {

	public static final String PROJECTILE_PATH = "assets/textures/projectiles/";

	// Boolean to indicate if an Element should be deleted
	private boolean isDepleted;

	// Creature which use the Combat System
	private Creature creature;

	// The Point where the Projectile starts
	private Point point;

	// Damage that is used for the certain projectile
	// will be set by a CreatureStat
	private double damage;

	/**
	 * A ranged Combat System for projectiles like Arrow or Spells
	 * 
	 * @param x      x start of the Projectile on x - Axis
	 * @param y      y start of the Projectile on y - Axis
	 * @param player Creature who shoots an Projectile
	 */
	public Projectile(Point point, Creature creature) {
		this.point = point;
		this.setPosition(point);
		this.creature = creature;

		setDamageAmount();
	}

	/**
	 *
	 * @return the DamageType this util will use
	 */
	public abstract DamageType getDamageType();

	@Override
	protected void updateLogic() {
		if (getTicks() == 10)
			setDepleted();
		else {
			damage--;
		}
	}

	@Override
	protected void onEntityCollision(Entity otherEntity) {
		// it have to be check if it is an Creature cause the Chest etc are also Entity
		if (otherEntity instanceof Creature && isPlayer()) {
			if(otherEntity == creature) {
				return;
			}
			((Creature) otherEntity).damage(damage, getDamageType(), this.creature, false);
		} else if (otherEntity instanceof Player) {
			((Player) otherEntity).damage(damage, getDamageType(), this.creature, false);
		}
		setDepleted();
	}

	/**
	 * 
	 * @return if the user of the projectile a human Player
	 */
	private boolean isPlayer() {
		if (creature instanceof Player)
			return true;
		return false;
	}

	/**
	 * 
	 * @param stat Stat's to use to calculate damage Amount
	 * @return damage this projectile should do
	 */
	public void setDamageAmount() {
		damage = creature.getCurrentStats().getStat(getDamageFromStat());
	}

	/**
	 * 
	 * @return the CreatureStat to calculate the damage
	 */
	public abstract CreatureStatsAttribs getDamageFromStat();

	/**
	 * 
	 * @return if its depleted it will be deleted
	 */
	public boolean isDepleted() {
		return isDepleted;
	}

	/**
	 * Set's an Flag for this Combat util is depleted
	 */
	void setDepleted() {
		this.isDepleted = true;
	}

	@Override
	public boolean deleteable() {
		// TODO Auto-generated method stub
		return isDepleted;
	}

	/**
	 * 
	 * @return Creature which want to use a Ranged Combat
	 */
	public Creature getCreature() {
		return creature;
	}

	/**
	 * 
	 * @return point of the projectile
	 */
	public Point getPoint() {
		return point;
	}

}
