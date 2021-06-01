package de.fhbielefeld.pmdungeon.quibble.item;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.PointAt;

public abstract class ItemWeaponRanged extends ItemWeapon
{
	/**
	 * The superclass of the range weapons (magic spells, arrows etc. . ).
	 * 
	 * @param name        user friendly display name
	 * @param swingSpeed  the speed at which this weapon will swing
	 * @param visibleTime the number of ticks that the item is displayed at the creature
	 * @param texture the texture that is used to render the item. This is the
	 * complete relative path to the texture including the file extension.
	 */
	protected ItemWeaponRanged(String name, int visibleTime, String texture)
	{
		super(name, visibleTime, texture);
		this.renderPivotX = 0.0F;
		this.renderPivotY = 0.5F;
		this.renderOffsetX = 0.5F;
		this.renderOffsetY = 0.0F;
		this.renderAllowNegativeEntityScale = false;
	}
	
	/**
	 * The projectile that should be spawn if the weapon is used.
	 * There is no need to change the velocity or direction of the projectile
	 * as this is done by the superclass after returning the new projectile.
	 * It is sufficient to only return a plain instance of the projectile without configuring it.
	 * 
	 * @param user user of the weapon
	 * @return the new projectile that should be spawn
	 */
	public abstract Projectile spawnProjectile(Creature user);
	
	public abstract float getProjectileSpeed();
	
	@Override
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		if(user.getHitCooldown() > 0.0D)
		{
			return false;
		}
		
		Projectile proj = spawnProjectile(user);
		
		Vector2 dir = new Vector2(targetX - user.getX(), targetY - user.getY());
		dir.setLength(this.getProjectileSpeed());
		proj.setVelocity(dir.x, dir.y);
		user.getLevel().spawnEntity(proj);
		
		user.getCurrentStats().setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15);
		return true;
	}
	
	@Override
	public ParticleMovement getOnUseMovement(Creature user, float targetX, float targetY)
	{
		return new PointAt(user.getX(), user.getY(), targetX, targetY);
	}
}
