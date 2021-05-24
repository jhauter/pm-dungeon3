package de.fhbielefeld.pmdungeon.quibble.entity.ai;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.SpellFireBall;

public class AIShootFireball extends AIShootProjectile
{
	public AIShootFireball(Creature target)
	{
		super(target);
	}

	@Override
	public Projectile createProjectile(NPC owner)
	{
		return new SpellFireBall("Fireball", owner.getX(), owner.getY() + 0.5F, owner.getCurrentStats(), owner);
	}

	@Override
	public float getProjectileSpeed()
	{
		return 0.1F;
	}

	@Override
	public int getTicksBetweenActions()
	{
		return 30;
	}

	@Override
	public float getAimDistanceSq()
	{
		return 16F;
	}
}
