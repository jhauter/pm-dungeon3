package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;

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
		if(otherEntity instanceof Player) {
			((Player) otherEntity).attack((Player)otherEntity);
		}
	}

	@Override
	public void onActivated(Creature c) {
		super.onEntityCollision(c);
		if (c instanceof Player) {
			System.out.println("H");
			CreatureHitTargetEvent event = (CreatureHitTargetEvent) this
					.fireEvent(new CreatureHitTargetPostEvent(EntityEvent.genEventID(), c, c, null, damageAmount));
			
		}
	}

	@Override
	public boolean isActivated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int activationLimit() {
		// TODO Auto-generated method stub
		return 0;
	}
}
