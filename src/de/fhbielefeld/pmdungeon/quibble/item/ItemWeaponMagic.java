package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;

public abstract class ItemWeaponMagic extends ItemWeaponRange {

	private float swingSpeed;

	/**
	 * Creates a Magic weapon. Magic weapon mostly have status effects in the certain Weapon
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
		super.onUse(user);
		if(user.getHitCooldown() > 0.0D) {
			return;
		}
		user.attackAoE();
	}

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
