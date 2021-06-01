package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class TrapTeleport extends Trap
{
	public TrapTeleport(float x, float y)
	{
		super(x, y);
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1, Trap.TRAP_TEXTURE_PATH + "trapBlue.png");
	}
	
	@Override
	public void onPlayerStepOnTrap(Player p)
	{
		Point pos = this.level.getDungeon().getRandomPointInDungeon();
		((Creature)p).setPosition(pos.x, pos.y);
	}

	@Override
	public int getCoolDownTicks()
	{
		return 44;
	}
}
