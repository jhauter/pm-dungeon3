package de.fhbielefeld.pmdungeon.quibble.entity.ai;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

public abstract class AIShootProjectile extends AIDistantAction
{
	public AIShootProjectile(Creature target)
	{
		super(target);
	}

	@Override
	public void onAction(NPC entity)
	{
		Projectile newProj = this.createProjectile(entity);
		Vector2 dir = new Vector2(this.getTarget().getX() - entity.getX(), this.getTarget().getY() - entity.getY());
		dir.setLength(this.getProjectileSpeed());
		newProj.setVelocity(dir.x, dir.y);
		entity.getLevel().spawnEntity(newProj);
	}
	
	public abstract Projectile createProjectile(NPC owner);
	
	public abstract float getProjectileSpeed();
}
