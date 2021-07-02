package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.vorgaben.tools.DungeonCamera;

public class CutsceneCamera extends NPC
{
	private boolean triggered = false;
	private Entity target;
	private Entity hero;
	private Entity boss;
	private boolean deleteFlag;
	private float zoom;
	private float defaultZoom;
	private float zoomTo = 3.0f;
	private boolean zoomIn = false;
	private boolean camReturn = false;
	private float speed = 0.25f;
	private DungeonCamera cam;
	private boolean wait = false;
	private float delay = 0;
	private int timer = 0;
	private float spawnAnimationDelay = 1;
	
	/**
	 * set position
	 */
	public CutsceneCamera(float x, float y)
	{
		super(x, y);
		this.deleteFlag = false;
		this.cam = DungeonStart.getDungeonMain().getCamera();
		this.defaultZoom = cam.zoom;
		this.zoom = this.defaultZoom;
	}
	
	public void setSpawnAnimationDelay(float spawnAnimationDelay)
	{
		this.spawnAnimationDelay = spawnAnimationDelay;
	}
	
	/**
	 * @param hero Set hero to get hero position
	 */
	public void setHero(Entity hero)
	{
		this.hero = hero;
	}
	
	/**
	 * @param target Set hero to get target position
	 */
	public void setTarget(Entity target)
	{
		this.target = target;
	}
	
	/**
	 * @param boss Set hero to get target position
	 */
	public void setBoss(Entity boss)
	{
		this.boss = boss;
	}
	
	public boolean getCamReturn()
	{
		return this.camReturn;
	}
	
	private void zoomCamera()
	{
		if(zoomIn)
		{
			if(this.zoom > this.zoomTo)
			{
				this.zoom = this.zoom - (speed * 0.03f);
			}
		}
		else
		{
			if(this.zoom < this.zoomTo)
			{
				this.zoom = this.zoom + (speed * 0.01f);
			}
		}
		cam.zoom = this.zoom;
	}
	
	public boolean stopMoving()
	{
		if(this.delay > 0)
		{
			this.timer++;
			if(this.timer >= this.delay * 60)
			{
				this.delay = 0;
				this.timer = 0;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Override updateLogic to get into update loop
	 * move camera to the boss and back to the player
	 */
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		
		if(this.stopMoving())
		{
			return;
		}
		
		this.zoomCamera();
		float dirX = this.target.getX() - this.getX();
		float dirY = this.target.getY() - this.getY();
		float angle = (float)Math.toDegrees(Math.atan2(dirY, dirX));
		
		final float rad = (float)Math.toRadians(angle);
		this.setVelocityX((float)Math.cos(rad) * speed * speed);
		this.setVelocityY((float)Math.sin(rad) * speed * speed);
		
	}
	
	@Override
	public boolean canWalk()
	{
		return true;
	}
	
	@Override
	protected CreatureStats getBaseStatsForLevel(int level)
	{
		CreatureStats stats = new CreatureStats();
		return stats;
	}
	
	/**
	 * @return delete if target player reached
	 */
	@Override
	public boolean shouldDespawn()
	{
		return deleteFlag;
	}
	
	/**
	 * @param level set level ...
	 * set Camera target to CutsceneCamera
	 */
	@Override
	public void onSpawn(DungeonLevel level)
	{
		super.onSpawn(level);
		DungeonStart.getDungeonMain().setCameraTarget(this);
	}
	
	/**
	 * @return BoundingBox of one pixel
	 */
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(0, 0, 0.1f, 0.1f);
	}
	
	/**
	 * @return Invisible
	 */
	@Override
	public boolean isInvisible()
	{
		return true;
	}
	
	@Override
	public boolean useAnimationHandler()
	{
		return false;
	}
	
	/**
	 * @param otherEntity the entity this entity is colliding with boss or player changes direction
	 */
	@Override
	protected void onEntityCollision(Entity otherEntity)
	{
		super.onEntityCollision(otherEntity);
		if(otherEntity instanceof Player && camReturn)
		{
			DungeonStart.getDungeonMain().setCameraTarget(this.hero);
			this.cam.zoom = this.defaultZoom;
			this.deleteFlag = true;
		}
		if(otherEntity instanceof Boss)
		{
			this.setTarget(hero);
			if(!camReturn)
			{
				this.delay = this.spawnAnimationDelay;
			}
			this.camReturn = true;
			this.speed = 0.35f;
			this.zoomTo = this.defaultZoom;
			this.zoomIn = true;
		}
	}
}
