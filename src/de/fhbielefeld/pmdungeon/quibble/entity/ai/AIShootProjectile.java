package de.fhbielefeld.pmdungeon.quibble.entity.ai;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

/**
 * AIStrategy template for automatically shooting projectiles in an interval.
 * 
 * @author Andreas
 */
public abstract class AIShootProjectile extends AIDistantAction
{
	public AIShootProjectile(Creature target)
	{
		super(target);
	}
	
	@Override
	public final void onAction(NPC entity)
	{
		Projectile newProj = this.createProjectile(entity);
		Vector2 dir = new Vector2(this.getTarget().getX() - entity.getX(), this.getTarget().getY() - entity.getY());
		dir.setLength(this.getProjectileSpeed());
		newProj.setVelocity(dir.x, dir.y);
		entity.getLevel().spawnEntity(newProj);
	}
	
	/**
	 * This is called every time the entity shoots a projectile, so
	 * this method should return a new instance of the projectile that
	 * should be shot.
	 * The projectile should only be created but not spawned into the level, as
	 * this is done internally.
	 * @param owner the npc that is shooting the projectile
	 * @return a new projectile instance
	 */
	public abstract Projectile createProjectile(NPC owner);
	
	/**
	 * @return the speed at which projectiles should be shot
	 */
	public abstract float getProjectileSpeed();
}
