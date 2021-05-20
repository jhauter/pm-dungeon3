package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.Projectile;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class ItemWeaponRange extends ItemWeapon {

	private Creature creature;

	/**
	 * The superclass of the range weapons (magic spells, arrows etc. . ).
	 * 
	 * @param name        user friendly display name
	 * @param itemWidth   render width of this weapon
	 * @param itemHeight  render height of this weapon
	 * @param swingSpeed  the speed at which this weapon will swing
	 * @param visibleTime time in seconds that this weapon will be visible when used
	 * @param texture     texture used to render this item
	 */
	protected ItemWeaponRange(String name, float itemWidth, float itemHeight, float visibleTime, String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
	}

	/**
	 * Sets the starting point of a projectile.For it to work,
	 * <code>setUser(Creature creature)<code> must be used to determine its position
	 * as well as its current direction of view
	 * 
	 * @param adjustYAxis for adjust y-Axis if the projectile starts to way up or
	 *                    down
	 * @return the spawn Point of the projectile
	 */
	Point setProjectileStartPoint(float adjustYAxis) {
		if (isLookingLeft())
			return new Point(creature.getPosition().x, creature.getPosition().y + adjustYAxis);
		return new Point(creature.getPosition().x, creature.getPosition().y + adjustYAxis);
	}

	/**
	 * Sets the speed of a projectile. For it to work, <code>setUser(Creature
	 * creature)<code> must be used to determine its position as well as its current
	 * direction of view
	 * 
	 * @param speed the amount of speed a projectile has
	 * @return A floating point number passed to the projectile as
	 *         <code>setVelocityX<code>
	 */
	float setVelocity(float speed) {
		if (isLookingLeft())
			return -speed;
		return speed;
	}

	/**
	 * Used to determine the user's current direction of view as well as its current
	 * Position
	 * 
	 * @param creature the user of the weapon
	 */
	void setUser(Creature creature) {
		this.creature = creature;
	}

	/**
	 * Convenience Method to determine the user's LookingDirection
	 * 
	 * @return if the user is looking left
	 */
	private boolean isLookingLeft() {
		return creature.getLookingDirection() == LookingDirection.LEFT;
	}

	@Override
	public ParticleMovement getWeaponMovement(Creature user) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/**
	 * The projectile that should be spawn if the weapon is used
	 * 
	 * @param user user of the weapon
	 * @return the new projectile that should be spawn
	 */
	public abstract Projectile spawnProjectile(Creature user);

}
