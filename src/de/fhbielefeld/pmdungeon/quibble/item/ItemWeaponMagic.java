package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;

public abstract class ItemWeaponMagic extends ItemWeapon {

	private float swingSpeed;

	protected ItemWeaponMagic(String name, float itemWidth, float itemHeight, float swingSpeed, float visibleTime,
			String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
		this.swingSpeed = swingSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getItemStats() {
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 3.0D);
		return stats;
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
