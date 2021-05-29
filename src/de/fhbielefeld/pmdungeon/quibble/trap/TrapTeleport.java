package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class TrapTeleport extends Trap {

	/**
	 * Creates a trap that used to damage Creatures
	 * 
	 * @param x               x-Value of placement Point
	 * @param y               y-Value of placement Point
	 * @param damageAmount    the damage a Creature will get
	 * @param activationLimit if false, the trap will stay activ
	 */
	public TrapTeleport(float x, float y, boolean noActivationLimit) {
		super(x, y, noActivationLimit);
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1, Trap.TRAP_TEXTURE_PATH + "trapBlue.png");
		this.noActivationLimit = noActivationLimit;
	}

	/**
	 * Creates a trap that used to damage Creatures
	 * 
	 * @param x               x-Value of placement Point
	 * @param y               y-Value of placement Point
	 * @param damageAmount    the damage a Creature will get
	 * @param activationLimit will set a Number how often this Trap will get
	 *                        activated
	 */
	public TrapTeleport(float x, float y, int activationLimit) {
		super(x, y, activationLimit);
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1, Trap.TRAP_TEXTURE_PATH + "trapBlue.png");
		this.activationLimit = activationLimit;
	}
	
	@Override
	public void isActiv(Entity e) {
		if (e instanceof Player) {
			Point p = this.level.getDungeon().getRandomPointInDungeon();
			((Creature) e).setPosition(p.x, p.y);
			this.coolDown = 44;
			setActivationLimit(activationLimit-1);
			this.visible = true;
		}
	}
}
