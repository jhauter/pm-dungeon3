package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class RangedCombat extends Entity {

	public static final String PROJECTILE_PATH = "assets/textures/projectiles/";

	// Boolean to indicate if an Element should be deleted
	private boolean isDepleted;

	// Player which use the Combat System
	private Player player;

	// The Point where the Projectile starts
	private Point point;

	// The amount of damage this weapon will make
	private double damageAmount;

	// Used to reduce damage after time
	private int ticks;

	/**
	 * A ranged Combat System for projectiles like Arrow or Spells
	 * 
	 * @param x            x start of the Projectile on x - Axis
	 * @param y            y start of the Projectile on y - Axis
	 * @param p            Player who shoots an Projectile
	 * @param damageAmount the amount of damage this will make, will be reduced by
	 *                     time.
	 */
	public RangedCombat(float x, float y, Player player, double damageAmount) {
		point = new Point(x, y);
		this.player = player;

		// Could be a variable to make it dynamic
		this.ticks = 10;
		// To increase at first and do decrease after time the damage
		this.damageAmount = damageAmount + ticks / 5;
	}

	/**
	 *
	 * @return the DamageType this util will use
	 */
	public abstract DamageType getDamageType();

	@Override
	protected void updateLogic() {
		if (ticks == 0)
			setDepleted();
		else {
			ticks--;
			damageAmount--;
		}
	}

	@Override
	protected void onEntityCollision(Entity otherEntity) {
		// it have to be check if it is an Creature cause the Chest etc are also Entity
		// And the own weapon should not damage the player
		if (otherEntity instanceof Creature && !(otherEntity instanceof Player)) {
			((Creature) otherEntity).damage(damageAmount, getDamageType(), this.player, false);
			setDepleted();
		}
	}

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

	public Player getPlayer() {
		return player;
	}

}
