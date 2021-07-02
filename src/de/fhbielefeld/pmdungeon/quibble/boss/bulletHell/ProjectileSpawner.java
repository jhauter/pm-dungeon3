package de.fhbielefeld.pmdungeon.quibble.boss.bulletHell;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

/**
 * Invisible entity that shoots projectiles in the direction that the spawner is currently facing
 */
public class ProjectileSpawner extends Entity
{
	private DungeonLevel level;
	
	private int shootingIntervall;
	private int shootingIntervallCounter;
	
	private int stateSwitchIntervall;
	private int getStateSwitchIntervallCounter;
	
	private CreatureStats parentStats;
	
	private List<ProjectileMovementPattern> patterns;
	
	private Vector2 facing = new Vector2(1, 1);
	private float angle;
	private float projectileSpeed = 0.5F;
	public Entity parent;
	
	public Vector2 offset;
	public boolean despawnFlag = false;
	public BulletCreationFunction bulletCreationFunction;
	public float currentBulletSpeed = 1;
	
	/**
	 * @param shootingIntervall Amount of time between each projectile spawn
	 * @param parentStats Stats of the projectiles that are shot by this instance
	 * @param position Initial Position
	 * @param func Method describing the proectile that is emitted by this instance
	 */
	public ProjectileSpawner(int shootingIntervall, CreatureStats parentStats, Vector2 position, BulletCreationFunction func)
	{
		this.shootingIntervall = shootingIntervall;
		this.parentStats = parentStats;
		
		this.facing = this.facing.setAngleDeg(370);
		this.setPosition(position);
		this.patterns = new ArrayList<>();
		this.offset = position;
		this.bulletCreationFunction = func;
	}
	
	public ProjectileSpawner(int shootingIntervall, CreatureStats parentStats, Vector2 position, BulletCreationFunction func, Entity parent)
	{
		this.shootingIntervall = shootingIntervall;
		this.parentStats = parentStats;
		
		this.facing = this.facing.setAngleDeg(370);
		this.offset = position;
		this.setPosition(parent.getX() + position.x, parent.getY() + position.y);
		this.patterns = new ArrayList<>();
		
		this.bulletCreationFunction = func;
		this.parent = parent;
	}
	
	/**
	 *
	 * @param pattern Movementpattern that is supposed to be added to this instance. Multiple patterns may be combined
	 */
	public void addPattern(ProjectileMovementPattern pattern)
	{
		patterns.add(pattern);
	}
	
	@Override
	protected void updateBegin()
	{
		
	}
	
	@Override
	protected void updateLogic()
	{
		
		super.updateLogic();
		//patterns.peek().execute();
		//Switch State
		//Dequeue
		this.shootingIntervallCounter++;
		
		for(var a : patterns)
		{
			a.execute();
		}
		if(parent != null)
		{
			this.setPosition(offset.x + parent.getX(), offset.y + parent.getY());
		}
		if(shootingIntervallCounter >= shootingIntervall)
		{
			shoot(currentBulletSpeed);
			shootingIntervallCounter = 0;
		}
	}
	
	/**
	 * Manually shoot a projectile with the given speed
	 * @param speed Speed of the projectile
	 */
	public void shoot(float speed)
	{
		//Projectile proj = new ArrowProjectile("Arrow", this.getX(), this.getY(), parentStats, BossBattle.boss);
		Projectile proj = bulletCreationFunction.createProjectile();
		proj.setPosition(this.getX(), this.getY());
		Vector2 dir = facing;
		dir.setLength(projectileSpeed);
		proj.setVelocity(dir.x * speed, dir.y * speed);
		level.spawnEntity(proj);
	}
	
	@Override
	protected void updateEnd()
	{
		super.updateEnd();
	}
	
	@Override
	public void onSpawn(DungeonLevel level)
	{
		super.onSpawn(level);
		this.level = level;
	}
	
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(0, 0, 0.5f, 0.5f);
	}
	
	@Override
	public boolean useAnimationHandler()
	{
		return false;
	}
	
	@Override
	public boolean isInvisible()
	{
		return true;
	}
	
	/**
	 * Sets the direction that the spawner is facing
	 * @param angle Angle in degrees
	 */
	public void setFacing(float angle)
	{
		this.angle = angle;
		
		this.facing = this.facing.setAngleDeg(angle);
	}
	
	public float getAngle()
	{
		return this.angle;
	}
	
	@Override
	public boolean shouldDespawn()
	{
		return despawnFlag;
	}
	
}
