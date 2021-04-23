package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.animation.AnimationStateHelper;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsEventListener;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureDamageEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureStatChangeEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.particle.Levitate;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleFightText;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleFightText.Type;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleWeapon;
import de.fhbielefeld.pmdungeon.quibble.particle.Splash;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Creature extends Entity implements DamageSource, CreatureStatsEventListener
{
	public static final int EVENT_ID_STAT_CHANGE = EntityEvent.genEventID();
	public static final int EVENT_ID_TAKE_DAMAGE = EntityEvent.genEventID();
	public static final int EVENT_ID_HIT_TARGET = EntityEvent.genEventID();
	public static final int EVENT_ID_HIT_TARGET_POST = EntityEvent.genEventID();
	
	private static final int ANIM_SWITCH_IDLE_L = 0;
	private static final int ANIM_SWITCH_IDLE_R = 1;
	private static final int ANIM_SWITCH_RUN_L = 2;
	private static final int ANIM_SWITCH_RUN_R = 3;
	
	private static final int ANIM_PRIO_IDLE = 0;
	private static final int ANIM_PRIO_RUN = 5;
	private static final int ANIM_PRIO_HIT = 10;
	private static final int ANIM_PRIO_ATTACK = 8;
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_IDLE_L = "idle_left";
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_IDLE_R = "idle_right";
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_RUN_L = "run_left";
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_RUN_R = "run_right";
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_HIT_L = "hit_left";
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_HIT_R = "hit_right";
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_ATTACK_L = "attack_left";
	
	/**
	 * Animation name constant used by the <code>AnimationStateHelper</code>.
	 * Names of animations that are handled by the <code>AnimationStateHelper</code> need to match this.
	 */
	protected static final String ANIM_NAME_ATTACK_R = "attack_right";
	
	private boolean isWalking;
	
	private LookingDirection lookingDirection;
	
	private AnimationStateHelper defaultAnimationsHelper;
	
	private CreatureStats baseStats;
	
	private CreatureStats maxStats;
	
	private CreatureStats currentStats;
	
	private int expLevel;
	
	private int invulnerableTicks;
	
	private int deadTicks;
	
	private boolean isDead;
	
	private boolean beingMoved;
	
	private float beingMovedThreshold = 0.005F;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Creature(float x, float y)
	{
		super(x, y);
		
		//Default looking direction should be right
		this.lookingDirection = LookingDirection.RIGHT;
		
		//=====Improve this section=====
		this.baseStats = this.getBaseStatsForLevel(this.expLevel);
		this.maxStats = this.calculateMaxStats();
		this.currentStats = this.maxStats.addCopy(new CreatureStats());
		this.currentStats.setEventListener(this);
		//==============================
		
		if(this.useDefaultAnimation())
		{
			this.defaultAnimationsHelper = new AnimationStateHelper(this.animationHandler);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_IDLE_L, ANIM_NAME_IDLE_L, ANIM_PRIO_IDLE);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_IDLE_R, ANIM_NAME_IDLE_R, ANIM_PRIO_IDLE);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_RUN_L, ANIM_NAME_RUN_L, ANIM_PRIO_RUN);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_RUN_R, ANIM_NAME_RUN_R, ANIM_PRIO_RUN);
		}
	}
	
	/**
	 * Creates an entity with a default position
	 */
	public Creature()
	{
		this(0.0F, 0.0F);
	}
	
	/**
	 * Returns the current walking speed of this entity measured in tiles per frame.
	 * This is not the current speed.
	 * @return walking speed of this entity
	 */
	public final float getWalkingSpeed()
	{
		return (float)this.getCurrentStats().getStat(CreatureStatsAttribs.WALKING_SPEED);
	}
	
	/**
	 * Sets the creature into a walking state which allows for easier animation.
	 * The creature walks into the direction specified in the arguments.
	 * For the new velocity the current walking speed is used.
	 * This method must be called every frame in which the creature should move or
	 * else the walking state will be reset and the creature stops moving.
	 * <br><br>
	 * Examples:
	 * <blockquote>
	 * <li><code>walk(0F, 1.0F)</code> moves the creature right with walking speed</li>
	 * <li><code>walk(90F, 0.5F)</code> moves the creature up with half the walking speed</li>
	 * <li><code>walk(180F, 1.0F)</code> moves the creature left</li>
	 * <li><code>walk(270F, 1.0F)</code> moves the creature down</li>
	 * </blockquote>
	 * @param angle angle in degrees which determines the direction
	 * @param mult value which scales the walking speed
	 */
	public void walk(float angle, float mult)
	{
		if(!this.canWalk())
		{
			return;
		}
		this.isWalking = true;
		final float rad = (float)Math.toRadians(angle);
		this.setVelocityX((float)Math.cos(rad) * this.getWalkingSpeed() * mult);
		this.setVelocityY((float)Math.sin(rad) * this.getWalkingSpeed() * mult);
	}
	
	/**
	 * Same as <code>walk(angle, 1.0F)</code>.
	 * @param angle angle which determines the direction
	 * @see Creature#walk(float, float)
	 */
	public void walk(float angle)
	{
		this.walk(angle, 1.0F);
	}
	
	/**
	 * Returns whether this creature is currently in a walking state.
	 * This is primarily used for animation.
	 * @return whether this creature is currently in a walking state
	 */
	public boolean isWalking()
	{
		return this.isWalking;
	}
	
	/**
	 * Returns whether the creature is able to walk at the moment.
	 * The entity can not walk for example when it is under the influence of knockback or
	 * when it is dead.
	 * @return whether the creature can walk at the moment
	 */
	public boolean canWalk()
	{
		return !this.isDead && !this.beingMoved;
	}
	
	public boolean isBeingMoved()
	{
		return this.beingMoved;
	}
	
	public void setBeingMoved()
	{
		this.beingMoved = true;
	}
	
	/**
	 * Sets the direction in which the creature should look.
	 * This is used to set the correct animation texture.
	 * @param d the new looking direction
	 */
	public void setLookingDirection(LookingDirection d)
	{
		this.lookingDirection = d;
	}
	
	/**
	 * Returns the direction in which the creature looks.
	 * This is used to set the correct animation texture.
	 * @return the current looking direction
	 */
	public LookingDirection getLookingDirection()
	{
		return this.lookingDirection;
	}
	
	/**
	 * Whether this entity should use default animation that has a walk and run animation.
	 * This method must be overridden to change this behavior.
	 * If this is true then an <code>AnimationStateHelper</code> is created for this class.
	 * @return whether this entity should use default animation
	 */
	protected boolean useDefaultAnimation()
	{
		return true;
	}
	
	/**
	 * Whether this entity should use an attack animation when it attacks.
	 * This method must be overridden to change this behavior.
	 * The default for this method is false.
	 * @return whether this entity should use an attack animation
	 */
	protected boolean useAttackAnimation()
	{
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		
		if(this.invulnerableTicks > 0)
		{
			--this.invulnerableTicks;
		}
		
		if(this.isDead)
		{
			++this.deadTicks;
		}
		
		if(this.beingMoved && this.getVelocity().len2() <= this.beingMovedThreshold)
		{
			this.beingMoved = false;
		}
		
		if(this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_COOLDOWN) > 0.0D)
		{
			this.getCurrentStats().addStat(CreatureStatsAttribs.HIT_COOLDOWN, -1.0D);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateAnimationState()
	{
		super.updateAnimationState();
		if(this.useDefaultAnimation())
		{
			final boolean lookLeft = this.getLookingDirection() == LookingDirection.LEFT;
			final boolean lookRight = this.getLookingDirection() == LookingDirection.RIGHT;
			this.defaultAnimationsHelper.setSwitchValue(ANIM_SWITCH_IDLE_L, lookLeft);
			this.defaultAnimationsHelper.setSwitchValue(ANIM_SWITCH_IDLE_R, lookRight);
			this.defaultAnimationsHelper.setSwitchValue(ANIM_SWITCH_RUN_L, this.isWalking() && lookLeft);
			this.defaultAnimationsHelper.setSwitchValue(ANIM_SWITCH_RUN_R, this.isWalking() && lookRight);
			this.defaultAnimationsHelper.update();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateEnd()
	{
		//Reset walking state so that a creature only walks during frames in which walk() was executed.
		this.isWalking = false;
		
		super.updateEnd();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Point getDrawingOffsetOverride()
	{
		//Draw the entity half its width to the left.
		//Draw the entity a bit higher to make its feet position match its real position.
		//Y of zero makes the bottom side the position y.
		return new Point(-0.5F, 0.0F);
	}
	
	/**
	 * Calculates the base stats that a creature has based on its exp level
	 * @param level current exp level
	 * @return the base stats
	 */
	protected abstract CreatureStats getBaseStatsForLevel(int level);
	
	/**
	 * Returns the base stats that a creature has based on its exp level.
	 * These are only base stats and are not the necessarily the final stats.
	 * Things like item effects, weapons, armor, status effects, etc. are yet to be calculated.
	 * @return the base stats
	 */
	public CreatureStats getBaseStats()
	{
		return this.baseStats;
	}
	
	/**
	 * No real implementation by now but can later be used when items and status effects are added
	 * @return the base stats for now
	 */
	public CreatureStats calculateMaxStats()
	{
		return this.baseStats;
	}
	
	/**
	 * Returns the maximum stats values for this creatures. These are the base stats extended by effects from
	 * items, etc.
	 * @return the maximum stats
	 */
	public CreatureStats getMaxStats()
	{
		return this.maxStats;
	}
	
	/**
	 * Returns the current stats which move between 0 and maxStats.
	 * There stats are the actual stat values that the creature has.
	 * For example the health stat has its maximum value stored in maxStats.
	 * The current health is stored in currentStats.
	 * This system allows for modification of every stat value dynamically, not only health.
	 * @return the current stats
	 */
	public CreatureStats getCurrentStats()
	{
		return this.currentStats;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStatValueChange(CreatureStatsEvent event)
	{
		EntityEvent ev = this.fireEvent(new CreatureStatChangeEvent(EVENT_ID_STAT_CHANGE, this, event.getStat(), event.getOldValue(), event.getNewValue()));
		if(ev.isCancelled())
		{
			event.setCancelled(true);
			return;
		}
		event.setNewValue(((CreatureStatChangeEvent)ev).getNewValue());
		
		if(event.getStat() == CreatureStatsAttribs.HEALTH)
		{
			if(event.getNewValue() <= 0.0D)
			{
				this.isDead = true;
			}
		}
	}
	
	/**
	 * Returns whether this creature can receive damage at this moment.
	 * This is always true for a short time after a creature has been hit.
	 * @return whether this creature can receive damage at this moment
	 */
	public boolean isInvulnerable()
	{
		return this.invulnerableTicks > 0;
	}
	
	public double getCurrentHealth()
	{
		return this.getCurrentStats().getStat(CreatureStatsAttribs.HEALTH);
	}
	
	public double getMaxHealth()
	{
		return this.getMaxStats().getStat(CreatureStatsAttribs.HEALTH);
	}
	
	public void damage(double damage, DamageType damageType, DamageSource damageSource, boolean ignoreInvincibleTicks)
	{
		if(!ignoreInvincibleTicks && this.isInvulnerable())
		{
			return;
		}
		
		if(this.isDead)
		{
			return;
		}
		
		//====================CALCULATE====================
		//-----Knockback-----
		Vector2 knockbackDirection = new Vector2(this.getX() - damageSource.getX(), this.getY() - damageSource.getY());
		knockbackDirection.setLength((float)damageSource.getCurrentStats().getStat(CreatureStatsAttribs.KNOCKBACK));
		knockbackDirection.scl(1.0F - (float)this.getCurrentStats().getStat(CreatureStatsAttribs.KNOCKBACK_RES));
		
		//-----Damage-----
		double actualDamage = damageType.getDamageAgainst(damage, this.getCurrentStats());
		
		//-----Event-----
		CreatureDamageEvent event = (CreatureDamageEvent)this.fireEvent(
			new CreatureDamageEvent(EVENT_ID_TAKE_DAMAGE, this,
				damageSource, damageType, damage, actualDamage, knockbackDirection.x, knockbackDirection.y));
		
		if(event.isCancelled())
		{
			//Don't take damage if event is cancelled
			return;
		}
		
		//====================APPLY====================
		
		this.getCurrentStats().addStat(CreatureStatsAttribs.HEALTH, -event.getDamageActual());
		this.setVelocity(event.getKnockbackX(), event.getKnockbackY());
		this.setBeingMoved();
		
		//-----Misc-----
		this.invulnerableTicks = this.getInvulnerabilityTicksWhenHit();
		
		this.spawnDamageParticles(event.getDamageActual());
		this.animationHandler.playAnimation(this.lookingDirection == LookingDirection.LEFT ? ANIM_NAME_HIT_L : ANIM_NAME_HIT_R, ANIM_PRIO_HIT, false);
		
		//Death is handled in onStatValueChange()
	}
	
	private void spawnDamageParticles(double damage)
	{
		String dmgStr = String.valueOf(Math.round(damage));
		for(int i = 0; i < dmgStr.length(); ++i)
		{
			this.level.getParticleSystem().addParticle(
				new ParticleFightText(Type.NUMBER, dmgStr.charAt(i) - '0', this.getX() + i * 0.3F, this.getY() + 0.5F, null),
				new Levitate());
		}
	}
	
	/**
	 * 
	 * @param target
	 */
	public void hit(Creature target, DamageType damageType)
	{
		double distance = Math.sqrt(Math.pow(this.getX() - target.getX(), 2) + Math.pow(this.getY() - target.getY(), 2)) - this.getRadius()
			- target.getRadius();
		if(distance > this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_REACH))
		{
			return;
		}
		
		if(this.level.getRNG().nextFloat() <= this.getCurrentStats().getStat(CreatureStatsAttribs.MISS_CHANCE))
		{
			this.level.getParticleSystem().addParticle(new ParticleFightText(Type.MISS, target.getX(), target.getY() + 0.5F, null),
				new Splash());
			return;
		}
		
		double damage = this.getCurrentStats().getStat(damageType.getSourceDamageStat());
		
		if(this.level.getRNG().nextFloat() <= this.getCurrentStats().getStat(CreatureStatsAttribs.CRIT_CHANCE))
		{
			//Crit
			damage *= 2.0D;
		}
		
		CreatureHitTargetEvent event = (CreatureHitTargetEvent)this
			.fireEvent(new CreatureHitTargetEvent(EVENT_ID_HIT_TARGET, this, target, damageType, damage));
		
		if(event.isCancelled())
		{
			//Don't proceed is event is cancelled
			return;
		}
		
		target.damage(event.getDamage(), event.getDamageType(), this, false);
		
		this.fireEvent(new CreatureHitTargetPostEvent(EVENT_ID_HIT_TARGET_POST, this, target, damageType, damage));
		//Canceling target post event has no effect
	}
	
	public void attack()
	{
		if(this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_COOLDOWN) > 0.0D)
		{
			return;
		}
		this.getCurrentStats().setStat(CreatureStatsAttribs.HIT_COOLDOWN, this.getMaxStats().getStat(CreatureStatsAttribs.HIT_COOLDOWN));
		
		if(this.showWeaponOnAttack())
		{
			final float swingSpeed = 3.5F;
			final float weaponWidth = 1F * 0.5F;
			final float weaponHeight = 2.5F * 0.5F;
			final float weaponTime = 0.25F;
			final Point weaponOffset = this.getWeaponHoldOffset();
			SwingOrientation swingDir = this.lookingDirection == LookingDirection.RIGHT ? SwingOrientation.RIGHT : SwingOrientation.LEFT;
			Swing weaponMovement = new Swing(swingDir, swingSpeed);
			this.level.getParticleSystem().addParticle(
				new ParticleWeapon(ParticleWeapon.Type.SWORD, weaponWidth, weaponHeight, weaponTime, this.getX() + weaponOffset.x, this.getY() + weaponOffset.y, this),
				weaponMovement);
		}
		
		List<Entity> entitiesInRadius = this.level.getEntitiesInRadius(this.getX(), this.getY(), (float)this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_REACH) + this.getRadius(), this);
		for(Entity e : entitiesInRadius)
		{
			if(e instanceof Creature)
			{
				this.hit((Creature)e, DamageType.PHYSICAL);
			}
		}
		
		if(this.useAttackAnimation())
		{
			this.animationHandler.playAnimation(this.lookingDirection == LookingDirection.LEFT ? ANIM_NAME_ATTACK_L : ANIM_NAME_ATTACK_R, ANIM_PRIO_ATTACK, false);
		}
	}
	
	/**
	 * Returns the amount of ticks this creature is invincible after it was hit.
	 * @return amount of invincible ticks when hit
	 */
	public int getInvulnerabilityTicksWhenHit()
	{
		return 20;
	}
	
	/**
	 * The amount of ticks since this entity has been marked as dead.
	 * @return amount of ticks since death
	 */
	public int getDeadTicks()
	{
		return this.deadTicks;
	}
	
	/**
	 * The amount of ticks this entity remains in the level before it gets removed.
	 * @return amount of ticks until removal after death
	 */
	public int getDeadTicksBeforeDelete()
	{
		return 30;
	}
	
	public boolean isDead()
	{
		return this.isDead;
	}
	
	@Override
	public boolean isInvisible()
	{
		return this.deadTicks % 2 != 0 || this.invulnerableTicks % 2 != 0;
	}
	
	@Override
	public boolean deleteableWorkaround()
	{
		return this.isDead && this.deadTicks >= this.getDeadTicksBeforeDelete();
	}
	
	public abstract Point getWeaponHoldOffset();
	
	public boolean showWeaponOnAttack()
	{
		return false;
	}
}
