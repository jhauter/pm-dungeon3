package de.fhbielefeld.pmdungeon.quibble.item;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.SpellIceBlast;

public class ItemBlueMagicStaff extends ItemStaff
{
	/**
	 * A Blue Magic staff which fires Ice Blast. Ice Blast's will slow down the hit
	 * creature.
	 */
	protected ItemBlueMagicStaff()
	{
		this("Blue Staff");
	}
	
	/**
	 * A Blue Magic staff which fires Ice Blast. Ice Blast's will slow down the hit
	 * creature.
	 *
	 * This constructor can be used to give the staff a custom name.
	 */
	protected ItemBlueMagicStaff(String displayName)
	{
		super(displayName, 2.5F, 15, "assets/textures/items/blue_magic_staff.png");
		this.renderWidth = 1.0F;
		this.renderHeight = 1.0F;
		
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 2.0D);
		this.setAttackStats(stats);
	}
	
	@Override
	public Projectile spawnProjectile(Creature user)
	{
		Projectile iceBlast = new SpellIceBlast(this.getDisplayName() + " Projectile", user.getX(), user.getY(),
			user.getCurrentStats().addCopy(getAttackStats()), user);
		return iceBlast;
	}
	
	@Override
	public float getProjectileSpeed()
	{
		return 0.25F;
	}
}
