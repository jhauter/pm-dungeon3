package de.fhbielefeld.pmdungeon.quibble.entity.projectile;

import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.SimpleDamageSource;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;

public abstract class Projectile extends Entity
{
	public static final String PROJECTILE_PATH = "assets/textures/projectiles/";
	
	//To indicate if the projectile should be deleted
	private boolean isDepleted;
	
	// Owner of the projectile. To check that the projectile cannot hit the owner.
	private Creature owner;
	
	//This contains stats like damage, knockback, crit chance, miss chance...
	private CreatureStats stats;
	
	// To identify the projectile (only for logging, otherwise useless)
	private String name;
	
	private Animation<TextureRegion> projectileAnimation;
	
	private float stateTime;
	
	private float width;
	private float height;
	
	/**
	 * Creates a projectile entity that will keep moving at the same speed when the velocity is set (by default).
	 * Projectiles will damage creatures on impact and disappear if they hit a wall.
	 * @param name text for log message. Has no other use.
	 * @param x the x position of the spawn point
	 * @param y the y position of the spawn point
	 * @param stats <code>CreatureStats</code> containing the damage attributes of the projectile
	 * @param owner owner of the projectile. May be <code>null</code>. Owner is invincible to projectile
	 * and gets exp when the projectile kills an entity
	 */
	public Projectile(String name, float x, float y, CreatureStats stats, Creature owner)
	{
		this.name = name;
		this.setPosition(x, y);
		this.owner = owner;
		this.setLinearDamping(1.0F);
		
		this.projectileAnimation = this.getProjectileAnimation();
		
		this.setSize(1.0F, 1.0F);
		
		this.stats = new CreatureStats(stats);
	}
	
	/**
	 * Sets the visual size of the projectile.
	 * @param width width measured in tile units
	 * @param height height measured in tile units
	 */
	public void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return the visual width of the projectile in tile units
	 */
	public float getWidth()
	{
		return width;
	}
	
	/**
	 * @return the visual height of the projectile in tile units
	 */
	public float getHeight()
	{
		return height;
	}
	
	/**
	 * @return the damage type of this projectile
	 */
	public abstract DamageType getDamageType();
	
	@Override
	protected void updateLogic()
	{
		if(getTicks() >= this.getTicksLasting())
			setDepleted();
		
		this.stateTime += Gdx.graphics.getDeltaTime();
	}
	
	@Override
	protected final void onEntityCollision(Entity otherEntity)
	{
		if(otherEntity == owner )
		{
			return;
		}
		// With this, NPC’s no longer harm each other
		if(owner instanceof NPC)
		{
			if(otherEntity instanceof NPC)
			{
				return;
			}
		}
		// it have to be check if it is an Creature cause the Chest etc are also Entity
		if(otherEntity instanceof Creature)
		{
			this.onProjectileImpactCreature((Creature)otherEntity);
			LoggingHandler.logger.log(Level.INFO, name + " has hit an Entity");
		}
	}
	
	@Override
	protected void onTileCollision()
	{
		this.isDepleted = true;
	}
	
	/**
	 * @return which stat represents the damage stat (e.g. DAMAGE_MAGIC for the magic damage stat)
	 */
	public abstract CreatureStatsAttribs getDamageFromStat();
	
	@Override
	public void doCustomRendering(Batch batch, float x, float y)
	{
		if(this.projectileAnimation == null)
		{
			return;
		}
		
		x -= DrawingUtil.dungeonToScreenX(this.width * 0.5F);
		y -= DrawingUtil.dungeonToScreenY(this.height * 0.5F);
		float w = DrawingUtil.dungeonToScreenX(this.width);
		float h = DrawingUtil.dungeonToScreenY(this.height);
		
		float rot = (float)Math.toDegrees(Math.atan2(this.getVelocity().y, this.getVelocity().x));
		
		batch.draw(this.projectileAnimation.getKeyFrame(this.stateTime, true), x, y, w * 0.5F, h * 0.5F, w, h, 1, 1, rot);
	}
	
	@Override
	public boolean useDefaultDrawing()
	{
		return false;
	}
	
	@Override
	public boolean useAnimationHandler()
	{
		return false;
	}
	
	/**
	 * 
	 * @return if its depleted it will be deleted
	 */
	public boolean isDepleted()
	{
		return isDepleted;
	}
	
	/**
	 * Set's an Flag for this Combat util is depleted
	 */
	void setDepleted()
	{
		this.isDepleted = true;
	}
	
	@Override
	public boolean shouldDespawn()
	{
		return isDepleted;
	}
	
	/**
	 * @return the creature that spawned this projectile (may be null)
	 */
	public Creature getOwner()
	{
		return owner;
	}
	
	/**
	 * Called when this projectile hits a creature that is not the owner of this projectile
	 * @param hitCreature the creature that has been hit
	 */
	public void onProjectileImpactCreature(Creature hitCreature)
	{
		//Can modify the stats here without copy because the projectile gets deleted on impact anyway
		double damage = stats.getStat(this.getDamageFromStat());
		stats.setStat(this.getDamageFromStat(), Math.ceil(damage - this.getDamageDecreaseOverTime()));
		
		SimpleDamageSource dmgSrc = new SimpleDamageSource(this.getX(), this.getY(), this.stats);
		
		((Creature)hitCreature).damage(dmgSrc, this.getDamageType(), this.owner, false);
		
		setDepleted();
	}
	
	public abstract int getTicksLasting();
	
	public abstract float getDamageDecreaseOverTime();
	
	public abstract Animation<TextureRegion> getProjectileAnimation();
	
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.35F, -0.35F, 0.7F, 0.7F);
	}
}
