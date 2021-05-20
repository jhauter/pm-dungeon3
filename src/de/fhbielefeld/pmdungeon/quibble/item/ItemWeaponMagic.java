package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.range_combat.Projectile;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleWeapon;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class ItemWeaponMagic extends ItemWeaponRange {

	private float swingSpeed;

	/**
	 * Creates a Magic weapon. Magic weapon mostly have status effects in the
	 * certain Weapon
	 * 
	 * @param name        user friendly display name
	 * @param itemWidth   render width of this weapon
	 * @param itemHeight  render height of this weapon
	 * @param swingSpeed  the speed at which this weapon will swing
	 * @param visibleTime time in seconds that this weapon will be visible when used
	 * @param texture     texture used to render this item
	 */
	protected ItemWeaponMagic(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime,
			String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
		this.swingSpeed = swingSpeed;
	}

	@Override
	public void onUse(Creature user) {
		if (user.getHitCooldown() > 0.0D) {
			return;
		}
		setUser(user);
		final Point weaponOffset = user.getWeaponHoldOffset();
		user.getLevel().getParticleSystem().addParticle(
				new ParticleWeapon(this, user.getX() + weaponOffset.x, user.getY() + weaponOffset.y, user),
				this.getWeaponMovement(user));
		user.getLevel().spawnEntity(spawnProjectile(user));
		user.getCurrentStats().setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15);
	}

	/**
	 * The projectile that should be spawn if the weapon is used
	 * 
	 * @param user user of the weapon
	 * @return the new projectile that should be spawn
	 */
	public abstract Projectile spawnProjectile(Creature user);

	@Override
	public ParticleMovement getWeaponMovement(Creature user) {

		SwingOrientation swingDir = user.getLookingDirection() == LookingDirection.RIGHT ? SwingOrientation.RIGHT
				: SwingOrientation.LEFT;
		return new Swing(swingDir, this.swingSpeed);
	}

	@Override
	public void accept(ItemVisitor visitor) {
		// TODO Auto-generated method stub
	}

}
