package de.fhbielefeld.pmdungeon.quibble.trap;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

public class TrapDamage extends Trap implements DamageSource
{
	private final double damage;
	
	public TrapDamage(float x, float y, double damage)
	{
		super(x, y);
		this.damage = damage;
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1, Trap.TRAP_TEXTURE_PATH + "trapPink.png");
	}
	
	@Override
	public void onPlayerStepOnTrap(Player p)
	{
		p.damage(this, DamageType.PHYSICAL, null, false);
	}
	
	@Override
	public CreatureStats getCurrentStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, damage);
		return stats;
	}
	
	@Override
	public int getCoolDownTicks()
	{
		return 44;
	}
	
}
