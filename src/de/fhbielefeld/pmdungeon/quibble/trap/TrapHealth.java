package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class TrapHealth extends Trap {

	private final double damageAmount;

	/**
	 * Creates a trap that used to damage Creatures
	 * 
	 * @param x            x-Value of placement Point
	 * @param y            y-Value of placement Point
	 * @param damageAmount the damage a Creature will get
	 * @param texture      texture of the damage trap
	 */
	public TrapHealth(float x, float y, double damageAmount) {
		super(x, y);
		this.damageAmount = damageAmount;
		this.animationHandler.addAsDefaultAnimation("", 1, 1, Trap.TRAPS_TEXTURE_PATH + "trapBlue.png", 4);
	}

	@Override
	protected void onEntityCollision(Entity otherEntity) {
		// TODO Auto-generated method stub
		super.onEntityCollision(otherEntity);
		if(isActivated()) {
			return;
		}
		else if (otherEntity instanceof Player) {
			((Player) otherEntity).damage(damageAmount, DamageType.PHYSICAL, this, false);
			setActivated(true);
		}
	}

	@Override
	public void onActivated(Entity e) {
		this.deleteableWorkaround();
		System.out.println(isActivated());
	}

	@Override
	public boolean isActivated() {
		// TODO Auto-generated method stub
		return this.activ;
	}

	@Override
	public int activationLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setActivated(boolean activ) {
		this.activ = true;
	}

	@Override
	public CreatureStats getCurrentStats() {
		
		return this.getTrapStats();
	}

}
