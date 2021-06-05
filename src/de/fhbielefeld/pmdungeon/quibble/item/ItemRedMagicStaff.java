package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.SpellFireBall;

public class ItemRedMagicStaff extends ItemStaff
{
	protected ItemRedMagicStaff()
	{
		super("Red Staff", 2.5F, 15, "assets/textures/items/red_magic_staff.png");
		this.renderWidth = 1.0F;
		this.renderHeight = 1.0F;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatureStats getAttackStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1);
		return stats;
	}
	
	@Override
	public Projectile spawnProjectile(Creature user)
	{
		return new SpellFireBall(this.getDisplayName() + " Projectile", user.getX(), user.getY(), user.getCurrentStats().addCopy(getAttackStats()), user);
	}
	
	@Override
	public float getProjectileSpeed()
	{
		return 0.3F;
	}
}
