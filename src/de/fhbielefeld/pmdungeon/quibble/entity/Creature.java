package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsEventListener;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.SimpleDamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureDamageEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureExpEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureStatChangeEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.particle.Levitate;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleFightText;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleFightText.Type;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Splash;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;

public abstract class Creature extends Entity implements DamageSource, CreatureStatsEventListener
{
	/**
	 * Event ID for <code>CreatureStatChangeEvent</code>.
	 */
	public static final int EVENT_ID_STAT_CHANGE = EntityEvent.genEventID();
	
	/**
	 * Event ID for <code>CreatureDamageEvent</code>.
	 */
	public static final int EVENT_ID_TAKE_DAMAGE = EntityEvent.genEventID();
	
	/**
	 * Event ID for <code>CreatureHitTargetEvent</code>.
	 */
	public static final int EVENT_ID_HIT_TARGET = EntityEvent.genEventID();
	
	/**
	 * Event ID for <code>CreatureHitTargetPostEvent</code>.
	 */
	public static final int EVENT_ID_HIT_TARGET_POST = EntityEvent.genEventID();
	
	/**
	 * Event ID for <code>CreatureExpEvent</code>.
	 */
	public static final int EVENT_ID_EXP_CHANGE = EntityEvent.genEventID();
	
	/**
	 * Event ID for <code>CreatureKilledEvent</code>.
	 */
	public static final int EVENT_KILLED_ENTITY = EntityEvent.genEventID();
	
	private static final int ANIM_PRIO_RUN = 10;
	private static final int ANIM_PRIO_HIT = 20;
	private static final int ANIM_PRIO_ATTACK = 30;
	
	/**
	 * Animation name constant used with <code>AnimationHandler</code>.
	 */
	protected static final String ANIM_NAME_IDLE = "idle";
	
	/**
	 * Animation name constant used with <code>AnimationHandler</code>.
	 */
	protected static final String ANIM_NAME_RUN = "run";
	
	/**
	 * Animation name constant used with <code>AnimationHandler</code>.
	 */
	protected static final String ANIM_NAME_HIT = "hit";
	
	/**
	 * Animation name constant used with <code>AnimationHandler</code>.
	 */
	protected static final String ANIM_NAME_ATTACK = "attack";
	
	private boolean isWalking;
	
	private LookingDirection lookingDirection;
	
	private final CreatureStats baseStats;
	
	private final CreatureStats statsFromEquipped;
	
	private final CreatureStats maxStats;
	
	private final CreatureStats currentStats;
	
	private int totalExp;
	
	private int invulnerableTicks;
	
	private int deadTicks;
	
	private boolean isDead;
	
	private boolean beingMoved;
	
	private float beingMovedThreshold = 0.005F;
	
	private GraphPath<Tile> currentPath;
	
	private int currentPathIndex;
	
	private Inventory<Item> inventory;
	
	private Inventory<Item> equippedItems;
	
	private int selectedEquipSlot;
	
	private List<StatusEffect> statusEffects;
	
	private Item renderedItem;
	private int renderedItemTicksRem;
	private ParticleMovement renderItemMovement;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Creature(float x, float y)
	{
		super(x, y);
		
		//Render properties - can be overridden by any creature
		this.renderWidth = 1.75F;
		this.renderHeight = 1.75F;
		this.renderOffsetY = this.renderHeight * 0.25F;
		
		//Default looking direction should be right
		this.lookingDirection = LookingDirection.RIGHT;
		
		//=====Stats=====
		this.baseStats = this.getBaseStatsForLevel(this.totalExp);
		this.statsFromEquipped = new CreatureStats();
		this.maxStats = new CreatureStats(this.baseStats);
		this.currentStats = new CreatureStats(this.maxStats);
		this.currentStats.setEventListener(this);
		//==============================
		
		this.statusEffects = new ArrayList<>();
		
		this.inventory = new DefaultInventory<Item>(this.getInventorySlots());
		this.equippedItems = new DefaultInventory<Item>(this.getEquipmentSlots());
		this.equippedItems.addInventoryListener((slot, oldItem, newItem) ->
		{
			this.updateMaxStats();
		});
	}
	
	/**
	 * Creates an entity with a default position
	 */
	public Creature()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public boolean isDisplayNameVisible()
	{
		return true;
	}
	
	@Override
	public String getDisplayNamePrefix()
	{
		return "Lv. " + this.getCurrentExpLevel();
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
	
	/**
	 * Returns whether this creature is being moved and cannot move on its own.
	 * For example when it is moved by knockback force.
	 * @return whether this creature is being moved and connot move on its own
	 */
	public boolean isBeingMoved()
	{
		return this.beingMoved;
	}
	
	/**
	 * Marks this creature as being moved. In this state creatures cannot move on their own.
	 * This state in only reset when the velocity² is less or equal than <code>beingMovedThreshold</code>.
	 * (<code>beingMovedThreshold</code> is not yet changeable)
	 */
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
	 * Whether this entity should automatically use default animation that
	 * has a walk and run animation.
	 * In order for this to work, an animation with the name {@value #ANIM_NAME_RUN}
	 * must be registered on this creature's animation handler.
	 * This method must be overridden to change this behavior.
	 * @return whether this entity should use default animation
	 */
	protected boolean useDefaultAnimation()
	{
		return true;
	}
	
	/**
	 * Whether this entity should use an attack animation when it attacks.
	 * This method must be overridden to change this behavior.
	 * In order for this to work, an animation with the name {@value #ANIM_NAME_ATTACK}
	 * must be registered on this creature's animation handler.
	 * The default for this method is false.
	 * @return whether this entity should use an attack animation
	 */
	protected boolean useAttackAnimation()
	{
		return false;
	}
	
	/**
	 * Whether this entity should use a hit animation when it gets hit.
	 * This method must be overridden to change this behavior.
	 * In order for this to work, an animation with the name {@value #ANIM_NAME_HIT}
	 * must be registered on this creature's animation handler.
	 * The default for this method is false.
	 * @return whether this entity should use a hit animation
	 */
	protected boolean useHitAnimation()
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
		
		if(this.renderedItemTicksRem > 0)
		{
			--this.renderedItemTicksRem;
			if(this.renderedItemTicksRem == 0)
			{
				this.renderedItem = null;
				this.renderItemMovement = null;
			}
		}
		
		if(this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_COOLDOWN) > 0.0D)
		{
			this.getCurrentStats().addStat(CreatureStatsAttribs.HIT_COOLDOWN, -1.0D);
		}
		
		StatusEffect currentEffect;
		for(int i = 0; i < this.statusEffects.size(); ++i)
		{
			currentEffect = this.statusEffects.get(i);
			if(this.statusEffects.get(i).isRemovable())
			{
				this.statusEffects.remove(i);
				currentEffect.onRemove();
				this.updateMaxStats();
				--i;
			}
			else
			{
				currentEffect = this.statusEffects.get(i);
				currentEffect.update();
			}
		}
		
		if(!this.statusEffects.isEmpty())
		{
			this.updateMaxStats();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateAnimationState()
	{
		super.updateAnimationState();
		this.renderScaleX = this.getLookingDirection().getAxisX();
		if(this.useDefaultAnimation())
		{
			if(this.isWalking)
			{
				//If this is not executed then the
				//default animation "idle" will automatically play
				this.animationHandler.playAnimation(ANIM_NAME_RUN, ANIM_PRIO_RUN, true);
			}
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
	
	@Override
	public void render()
	{
		super.render();
		
		if(this.renderedItem != null && renderItemMovement != null)
		{
			TextureRegion curFrame = null;
			final int maxTicks = this.renderedItem.getVisibleTicks();
			final float stateTime = (maxTicks - this.renderedItemTicksRem) * 1.0F / 30;
			if(this.renderedItem.getAnimation() != null)
			{
				curFrame = this.renderedItem.getAnimation().getKeyFrame(stateTime);
			}
			else
			{
				DungeonResource<Texture> tex = ResourceHandler.requestResource(this.renderedItem.getTextureFile(), ResourceType.TEXTURE);
				if(tex.isLoaded())
				{
					curFrame = new TextureRegion(tex.getResource());
				}
			}
			
			//curFrame can still be null
			if(curFrame != null)
			{
				Vector2 drawPos = new Vector2();
				drawPos.add(this.getX(), this.getY());
				drawPos.add(this.getRenderOffsetX(), this.getRenderOffsetY());
				
				drawPos.add(this.renderItemMovement.getOffsetX(stateTime) * this.getScaleX(), this.renderItemMovement.getOffsetY(stateTime) * this.getScaleY());
				drawPos.add(this.renderedItem.getRenderOffsetX() * this.renderedItem.getRenderWidth(), this.renderedItem.getRenderOffsetY() * this.renderedItem.getRenderHeight());
				drawPos.add(this.renderedItem.getHoldOffsetX() * this.getScaleX(), this.renderedItem.getHoldOffsetY() * this.getScaleY());
				drawPos.add(-this.renderedItem.getRenderWidth() * 0.5F, -this.renderedItem.getRenderHeight() * 0.5F);
				drawPos.add(this.getItemHoldOffset().scl(this.getScaleX(), this.getScaleY()));

				float appliedScaleX = this.getScaleX();
				float appliedScaleY = this.getScaleY();
				
				if(!this.renderedItem.getRenderAllowNegativeEntityScale())
				{
					appliedScaleX = Math.abs(appliedScaleX);
					appliedScaleY = Math.abs(appliedScaleY);
				}
				
				//SpriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
				DungeonStart.getGameBatch().draw(
					curFrame,
					drawPos.x,
					drawPos.y,
					this.renderedItem.getRenderPivotX() * this.renderedItem.getRenderWidth(),
					this.renderedItem.getRenderPivotY() * this.renderedItem.getRenderHeight(),
					this.renderedItem.getRenderWidth(),
					this.renderedItem.getRenderHeight(),
					appliedScaleX * this.renderItemMovement.getScaleX(stateTime),
					appliedScaleY * this.renderItemMovement.getScaleY(stateTime),
					this.getRotation() + this.renderItemMovement.getRotation(stateTime)
					);
			}
		}
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
	 * Calculates the total stats of all equipped items.
	 * @return the total stats of all equipped items
	 */
	public CreatureStats calculateStatsFromEquipped()
	{
		CreatureStats currentMax = new CreatureStats();
		InventoryItem<Item> curItem;
		for(int i = 0; i < this.equippedItems.getCapacity(); ++i)
		{
			curItem = this.equippedItems.getItem(i);
			
			if(curItem != null)
			{
				currentMax.addStats(curItem.getItemType().getItemStats());
			}
		}
		return currentMax;
	}
	
	public void applyStatsFromStatusEffects(CreatureStats prevStats)
	{
		for(StatusEffect effect : this.statusEffects)
		{
			prevStats.setStats(effect.getStatsModification(prevStats));
		}
	}
	
	/**
	 * Calculates the max stats which are used for max. health, etc.
	 * @return the base stats plus stats from equipped items
	 */
	public CreatureStats calculateMaxStats()
	{
		this.baseStats.setStats(this.getBaseStatsForLevel(this.expLevelFunction(this.totalExp)));
		CreatureStats equippedItemsStats = this.baseStats.addCopy(this.statsFromEquipped);
		this.applyStatsFromStatusEffects(equippedItemsStats);
		return equippedItemsStats;
	}
	
	/**
	 * This method updates the max stats by calculating the stats gained from equipped items and adding
	 * them to the base stats. The current stats are optionally filled up if the new max stats are greater than
	 * the current stats. If the new max stats are less, then the current stats are cut down.
	 */
	public void updateMaxStats()
	{
		this.statsFromEquipped.setStats(this.calculateStatsFromEquipped());
		this.maxStats.setStats(this.calculateMaxStats());
		this.currentStats.newMax(this.maxStats);
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
	 * Returns the current stats which move between 0 (mostly) and maxStats.
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
	
	public void updateInvulnerabilityTicks(int ticks)
	{
		if(ticks > this.invulnerableTicks)
		{
			this.invulnerableTicks = ticks;
		}
	}
	
	/**
	 * Returns the current health of the creature.
	 * @return current health
	 */
	public double getCurrentHealth()
	{
		return this.getCurrentStats().getStat(CreatureStatsAttribs.HEALTH);
	}
	
	/**
	 * Returns the maximum health of the creature.
	 * @return max. health
	 */
	public double getMaxHealth()
	{
		return this.getMaxStats().getStat(CreatureStatsAttribs.HEALTH);
	}
	
	/**
	 * Increases the health of the creature by the specified amount.
	 * The health cannot be in increased over maximum health.
	 * @param amount the amount to increase health by
	 */
	public void heal(double amount)
	{
		double newHealth = this.getCurrentHealth() + amount;
		if(newHealth > this.getMaxHealth())
		{
			newHealth = this.getCurrentHealth();
		}
		this.getCurrentStats().setStat(CreatureStatsAttribs.HEALTH, newHealth);
	}
	
	/**
	 * This is a convenience method. For more info see {@link #damage(DamageSource, DamageType, Creature, boolean, boolean)}.
	 * 
	 * @param damageSource the damage source (ex.: the entity that deals the damage)
	 * @param damageType the damage type which determines how damage is calculated according to stats
	 * @param cause the creature that calls this method. The "cause" is rewarded exp when the target is killed.
	 */
	public void damage(DamageSource damageSource, DamageType damageType, Creature cause)
	{
		this.damage(damageSource, damageType, cause, false, true);
	}
	
	/**
	 * This is a convenience method. For more info see {@link #damage(DamageSource, DamageType, Creature, boolean, boolean)}.
	 * 
	 * @param damageSource the damage source (ex.: the entity that deals the damage)
	 * @param damageType the damage type which determines how damage is calculated according to stats
	 * @param cause the creature that calls this method. The "cause" is rewarded exp when the target is killed.
	 * @param ignoreInvincibleTicks whether the entity should take damage even though
	 * it is actually invincible due to it being hit recently
	 */
	public void damage(DamageSource damageSource, DamageType damageType, Creature cause, boolean ignoreInvincibleTicks)
	{
		this.damage(damageSource, damageType, cause, ignoreInvincibleTicks, true);
	}
	
	/**
	 * Causes damage to this creature. The damage value is altered according to the stats of the creature.
	 * The creature is knocked back according to its stats and the stats of the damage source.
	 * Creatures that take damage are invincible for a short amount of time.
	 * Particles may be spawned indicating the outcome of this method.<br>
	 * The <code>damageSource</code> parameter affects the main damage calculation:
	 * <blockquote>
	 * <li>raw damage
	 * <li>critical strike chance
	 * <li>miss chance
	 * <li>knockback strength
	 * <li>knockback direction (origin is <code>DamageSource.getX()</code> and <code>DamageSource.getY()</code>)
	 * </blockquote>
	 * <code>damageSource</code> usually contains the creature's stats plus
	 * the attack stats of the item used to attack when performing a melee attack.<br>
	 * When using projectiles, the projectile calls this damage method instead of the attacking creature.
	 * In this case the projectile usually copies the creature's stats plus the stats of the item used to
	 * spawn the projectile.<br>
	 * On impact the projectile creates a <code>DamageSource</code> with the copied stats
	 * and with its own x and y values und uses the owner of the projectile as <code>cause</code>.
	 * @param damageSource the damage source (ex.: the entity that deals the damage)
	 * @param damageType the damage type which determines how damage is calculated according to stats
	 * @param cause the creature that calls this method. The "cause" is rewarded exp when the target is killed.
	 * @param ignoreInvincibleTicks whether the entity should take damage even though
	 * it is actually invincible due to it being hit recently
	 * @param makeInvulnerable whether the creature should be invulnerable
	 * for a short amount of time after being damaged
	 */
	public void damage(DamageSource damageSource, DamageType damageType, Creature cause, boolean ignoreInvincibleTicks, boolean makeInvulnerable)
	{
		if(!ignoreInvincibleTicks && this.isInvulnerable())
		{
			return;
		}
		
		if(this.isDead)
		{
			return;
		}
		
		if(this.level.getRNG().nextFloat() <= damageSource.getCurrentStats().getStat(CreatureStatsAttribs.MISS_CHANCE))
		{
			this.level.getParticleSystem().addParticle(new ParticleFightText(Type.MISS, this.getX(), this.getY() + 0.5F, null),
				new Splash());
			return;
		}
		
		//====================CALCULATE====================
		//-----Knockback-----
		Vector2 knockbackDirection = new Vector2(this.getX() - damageSource.getX(), this.getY() - damageSource.getY());
		knockbackDirection.setLength((float)damageSource.getCurrentStats().getStat(CreatureStatsAttribs.KNOCKBACK));
		knockbackDirection.scl(1.0F - (float)this.getCurrentStats().getStat(CreatureStatsAttribs.KNOCKBACK_RES));
		
		//-----Damage-----
		double damage = damageSource.getCurrentStats().getStat(damageType.getSourceDamageStat());
		if(this.level.getRNG().nextFloat() <= damageSource.getCurrentStats().getStat(CreatureStatsAttribs.CRIT_CHANCE))
		{
			//Crit
			damage *= 2.0D;
		}
		double actualDamage = damageType.getDamageAgainst(damage, this.getCurrentStats());
		
		//-----Event-----
		
		if(cause != null)
		{
			CreatureHitTargetEvent eventHTP = (CreatureHitTargetEvent)cause
				.fireEvent(new CreatureHitTargetEvent(EVENT_ID_HIT_TARGET, cause, this, damageType, actualDamage));
			
			if(eventHTP.isCancelled())
			{
				//Don't proceed if event is cancelled
				return;
			}
			
			actualDamage = eventHTP.getDamage();
			damageType = eventHTP.getDamageType();
		}
		
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
		if(makeInvulnerable && this.getInvulnerabilityTicksWhenHit() > this.invulnerableTicks)
		{
			this.invulnerableTicks = this.getInvulnerabilityTicksWhenHit();
		}
		
		this.spawnDamageParticles(event.getDamageActual());
		if(this.useHitAnimation())
		{
			this.animationHandler.playAnimation(ANIM_NAME_HIT, ANIM_PRIO_HIT, false);
		}
		
		//====================AFTERMATH====================
		
		if(cause != null)
		{
			cause.fireEvent(new CreatureHitTargetPostEvent(EVENT_ID_HIT_TARGET_POST, cause, this, damageType, damage));
			//Canceling target post event has no effect
		}
		
		if(cause != null && this.isDead)
		{
			cause.rewardExp(this.getExpDrop());
		}
		
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
	 * Makes this creature hit another creature.
	 * The damage, knockback, miss chance and other factors are all determined by this creature's stats.
	 * The actual damage is calculated in {@link #damage(DamageSource, DamageType, Creature, boolean)}.
	 * If the target is out of reach then the hit will not succeed.
	 * @param target the target creature to hit
	 * @param damageType the type of damage to use (usually physical damage)
	 */
	public void hit(Creature target, DamageType damageType)
	{
		if(this.isDead || target.isDead)
		{
			return;
		}
		double distance = Math.sqrt(Math.pow(this.getX() - target.getX(), 2) + Math.pow(this.getY() - target.getY(), 2)) - this.getRadius()
			- target.getRadius();
		if(distance > this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_REACH))
		{
			return;
		}
		
		InventoryItem<Item> selectedItem = null;
		if(this.getSelectedEquipSlot() >= 0 && this.getSelectedEquipSlot() < this.equippedItems.getCapacity())
		{
			selectedItem = this.getEquippedItems().getItem(this.getSelectedEquipSlot());
		}
		
		CreatureStats attackStats = this.getCurrentStats();
		if(selectedItem != null)
		{
			attackStats.addStats(selectedItem.getItemType().getAttackStats());
		}
		
		DamageSource attackDamageSrc = new SimpleDamageSource(this.getX(), this.getY(), attackStats);
		
		target.damage(attackDamageSrc, damageType, this, false);
	}
	
	/**
	 * Attacks only a single entity.
	 * @see #attackEntities(List)
	 */
	public void attack(Creature target)
	{
		this.attackEntities(Arrays.asList(target));
	}
	
	/**
	 * Attacks all entities within the radius determined by this creatures hit reach stat.
	 * @see #attackEntities(List)
	 */
	public void attackAoE()
	{
		List<Entity> entitiesInRadius = this.level.getEntitiesInRadius(this.getX(), this.getY(),
			(float)this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_REACH) + this.getRadius(), this);
		List<Creature> creatures = new ArrayList<Creature>();
		for(Entity e : entitiesInRadius)
		{
			if(e instanceof Creature)
			{
				creatures.add((Creature)e);
			}
		}
		this.attackEntities(creatures);
	}
	
	/**
	 * Makes this creature hit all specified entities and also takes care of the attack/weapon animation.
	 * @param targets list containing all entities to hit
	 */
	public void attackEntities(List<Creature> targets)
	{
		if(this.isDead)
		{
			return;
		}
		if(this.getHitCooldown() > 0.0D)
		{
			return;
		}
		this.getCurrentStats().setStat(CreatureStatsAttribs.HIT_COOLDOWN, this.getMaxStats().getStat(CreatureStatsAttribs.HIT_COOLDOWN));
		
		for(Creature e : targets)
		{
			this.hit(e, DamageType.PHYSICAL);
		}
		
		if(this.useAttackAnimation())
		{
			this.animationHandler.playAnimation(ANIM_NAME_ATTACK, ANIM_PRIO_ATTACK, false);
		}
	}
	
	/**
	 * @return the amount of ticks that has to pass until this creature can attack again
	 */
	public double getHitCooldown()
	{
		return this.getCurrentStats().getStat(CreatureStatsAttribs.HIT_COOLDOWN);
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
	
	/**
	 * Whether this entity has been marked as dead by losing all health
	 */
	public boolean isDead()
	{
		return this.isDead;
	}
	
	@Override
	public float getTransparency()
	{
		return this.deadTicks % 2 != 0 || this.invulnerableTicks % 2 != 0 ? 0.0F : super.getTransparency();
	}
	
	@Override
	public boolean shouldDespawn()
	{
		return this.isDead && this.deadTicks >= this.getDeadTicksBeforeDelete();
	}
	
	/**
	 * Returns the point at which the item should be rendered if this creature uses an item.
	 * The point is relative to the render position of the entity (the center).
	 * @return the point at which the item should be rendered
	 */
	public Vector2 getItemHoldOffset()
	{
		return new Vector2(0, 0);
	}
	
	/**
	 * Makes the creature walk along the specified path.
	 * This method needs to be called every frame until the end of the path is reached.
	 * If this method detects a different path reference then it will start to follow the new path reference
	 * from the beginning.<br><br>
	 * The method indicates that the end of the path has been reached by returning <code>true</code>.
	 * If this method is called after <code>true</code> has been returned, this method will do nothing and keep
	 * returning <code>true</code>.
	 * @param path the path to follow
	 * @return whether the end of the path has been reached
	 */
	public boolean followPath(GraphPath<Tile> path)
	{
		int currentX = (int)this.getX();
		int currentY = (int)this.getY();
		if(path.getCount() == 0)
		{
			return true;
		}
		if(this.currentPath != path)
		{
			this.currentPath = path;
			Tile t0 = path.get(0);
			if(t0.getX() == currentX && t0.getY() == currentY && path.getCount() > 1)
			{
				this.currentPathIndex = 1;
			}
			else
			{
				this.currentPathIndex = 0;
			}
		}
		if(this.currentPathIndex < this.currentPath.getCount() - 1)
		{
			Tile pathTileTarget = this.currentPath.get(this.currentPathIndex);
			if(currentX == pathTileTarget.getX() && currentY == pathTileTarget.getY())
			{
				++this.currentPathIndex;
			}
			else
			{
				float angle = (float)Math.toDegrees(Math.atan2(pathTileTarget.getY() + 0.5F - this.getY(), pathTileTarget.getX() + 0.5F - this.getX()));
				this.walk(angle);
				if(angle > 90 || angle < -90)
				{
					this.setLookingDirection(LookingDirection.LEFT);
				}
				else if(angle < 90 && angle > -90)
				{
					this.setLookingDirection(LookingDirection.RIGHT);
				}
			}
		}
		return this.currentPathIndex == this.currentPath.getCount() - 1;
	}
	
	/**
	 * @return the inventory of the creature
	 */
	public final Inventory<Item> getInventory()
	{
		return this.inventory;
	}
	
	/**
	 * Returns the equipped items inventory of the creature.
	 * Items in this inventory affect the stats of the creature.
	 * @return equipped items inventory
	 */
	public final Inventory<Item> getEquippedItems()
	{
		return this.equippedItems;
	}
	
	/**
	 * @return number of slots the inventory of this creature should have
	 */
	public int getInventorySlots()
	{
		return 0;
	}
	
	/**
	 * @return number of slots the equipment inventory of this creature should have
	 */
	public int getEquipmentSlots()
	{
		return 0;
	}
	
	/**
	 * Transfers the item in the specified inventory slot into a free equipment slot.
	 * @param invSlot index of the inventory slot
	 * @return whether the transfer was successful (whether there was a free equipment slot)
	 */
	public boolean equip(int invSlot)
	{
		return Inventory.transfer(this.getInventory(), invSlot, this.getEquippedItems());
	}
	
	/**
	 * Swaps the item in the specified inventory slot with the item in the specified equipment slot.
	 * @param invSlot index of the inventory slot
	 * @param equipSlot index of the equipment slot
	 */
	public void equipSwap(int invSlot, int equipSlot)
	{
		Inventory.swap(this.getInventory(), invSlot, this.getEquippedItems(), equipSlot);
	}
	
	/**
	 * Transfers the item in the specified equipment slot into a free inventory slot.
	 * @param equipSlot index of the equipment slot
	 * @return whether the transfer was successful (whether there was a free inventory slot)
	 */
	public boolean unequip(int equipSlot)
	{
		return Inventory.transfer(this.getEquippedItems(), equipSlot, this.getInventory());
	}
	
	/**
	 * Removes the item in the specified inventory slot and drops it on the ground, spawning
	 * an item entity in the level that contains the dropped item.
	 * If the specified inventory slot is empty, this does nothing.
	 * @param invSlot index of the inventory slot
	 * @return whether the specified inventory slot was not empty
	 */
	public boolean drop(int invSlot)
	{
		InventoryItem<Item> item = this.getInventory().removeItem(invSlot);
		if(item == null)
		{
			return false;
		}
		this.level.spawnEntity(new ItemDrop(item, this.getX(), this.getY()));
		return true;
	}
	
	/**
	 * Removes the item in the specified equipment slot and drops it on the ground, spawning
	 * an item entity in the level that contains the dropped item.
	 * If the specified equipment slot is empty, this does nothing.
	 * @param equipSlot index of the equipment slot
	 * @return whether the specified equipment slot was not empty
	 */
	public boolean dropEquipment(int equipSlot)
	{
		InventoryItem<Item> item = this.getEquippedItems().removeItem(equipSlot);
		if(item == null)
		{
			return false;
		}
		this.level.spawnEntity(new ItemDrop(item, this.getX(), this.getY()));
		return true;
	}
	
	/**
	 * Makes the creature use the item in the specified equipment slot.
	 * In order to achieve this, the {@link Item#onUse(Creature, float, float)} method is called.
	 * If the item can be consumed, the item will be removed from the slot.
	 * If the specified equipment slot is empty, this will do nothing
	 * @param slot index of the equipment slot
	 * @param targetX the x-position in the dungeon that the creature targets (mouse click for players)
	 * @param targetY the y-position in the dungeon that the creature targets (mouse click for players)
	 */
	public void useEquippedItem(int slot, float targetX, float targetY)
	{
		if(this.isDead)
		{
			return;
		}
		InventoryItem<Item> item = this.equippedItems.getItem(slot);
		if(item == null)
		{
			return;
		}
		Item itemType = item.getItemType();
		if(itemType.onUse(this, targetX, targetY))
		{
			this.playUseItemAnimation(itemType, targetX, targetY);
		}
		if(itemType.canBeConsumed())
		{
			this.equippedItems.removeItem(slot);
		}
	}
	
	public void playUseItemAnimation(Item item, float targetX, float targetY)
	{
		this.renderedItem = item;
		this.renderItemMovement = item.getOnUseMovement(this, targetX, targetY);
		this.renderedItemTicksRem = item.getVisibleTicks();
	}
	
	/**
	 * Sets the equipment slot that this creature has currently selected.
	 * The item in the selected equipment slot determines how much damage will be dealt
	 * when the creature attacks.
	 * @param slot the slot index to be selected
	 */
	public void setSelectedEquipSlot(int slot)
	{
		this.selectedEquipSlot = slot;
	}
	
	/**
	 * Returns the equipment slot that this creature has currently selected.
	 * The item in the selected equipment slot determines how much damage will be dealt
	 * when the creature attacks.
	 * @return the equipment slot index that this creature has currently selected
	 */
	public int getSelectedEquipSlot()
	{
		return this.selectedEquipSlot;
	}
	
	/**
	 * Tries to add the given itemType to the equipped items.
	 * If the equipped items are full, then tries to add the item
	 * to the normal inventory.
	 * @param itemType the item type to add.
	 * @return <code>true</code> if adding the item was successful
	 */
	public boolean addItem(Item itemType)
	{
		if(!this.equippedItems.addItem(itemType))
		{
			return this.inventory.addItem(itemType);
		}
		return true;
	}
	
	/**
	 * Tries to add the given item to the equipped items.
	 * If the equipped items are full, then tries to add the item
	 * to the normal inventory.
	 * @param item the item to add.
	 * @return <code>true</code> if adding the item was successful
	 */
	public boolean addItem(InventoryItem<Item> item)
	{
		if(!this.equippedItems.addItem(item))
		{
			return this.inventory.addItem(item);
		}
		return true;
	}
	
	/**
	 * Adds a status effect to the creature. Status effects have a set duration after which they disappear.
	 * While the status effect is on the creature, its update method is executed continuously.
	 * Status effects can alter the creature's stats.
	 * @param effect the status effect to be added
	 * @param durationTicks the duration in ticks until the effect should be removed
	 */
	public final void addStatusEffect(StatusEffect effect, int durationTicks)
	{
		if(this.statusEffects.contains(effect))
		{
			throw new IllegalArgumentException("Cannot add the same effect instance more than once");
		}
		this.statusEffects.add(effect);
		effect.setRemainingTicks(durationTicks);
	}
	
	/**
	 * @return the amount of status effects this creature currently has
	 */
	public final int getNumStatusEffects()
	{
		return this.statusEffects.size();
	}
	
	/**
	 * @param index index of the status effects in the status effects list
	 * @return the creature's status effect at the specified index
	 */
	public StatusEffect getStatusEffect(int index)
	{
		return this.statusEffects.get(index);
	}
	
	/**
	 * Can be overridden to change the number of exp killing this creature is rewarded.
	 * @return the number of exp killing this creature is rewarded
	 */
	public int getExpDrop()
	{
		return 0;
	}
	
	/**
	 * Function used to calculate the level from the total exp.
	 * A total exp <= 0 must result in level 1.
	 * @param totalExp the total exp
	 * @return the level that this creature would have with the given total exp
	 */
	public int expLevelFunction(int totalExp)
	{
		if(totalExp < 0)
		{
			return 1;
		}
		return (int)(1.0D / 3.0D * Math.sqrt(totalExp) + 1);
	}
	
	/**
	 * Function used to calculate the required exp for every level
	 * @param expLevel the level
	 * @return the total exp required to reach the given level
	 */
	public int totalExpFunction(int expLevel)
	{
		return (int)Math.pow((3 * expLevel - 3), 2);
	}
	
	/**
	 * Sets the total exp of this creature. The level will update automatically.
	 * @param exp the new total exp
	 */
	public void setTotalExp(int exp)
	{
		CreatureExpEvent event = (CreatureExpEvent)this.fireEvent(new CreatureExpEvent(EVENT_ID_EXP_CHANGE, this, this.totalExp, exp));
		if(!event.isCancelled())
		{
			this.totalExp = event.getNewTotalExp();
			this.updateMaxStats();
		}
	}
	
	/**
	 * @return total exp of the creature
	 */
	public int getTotalExp()
	{
		return this.totalExp;
	}
	
	/**
	 * Adds exp to the total exp of this creature.
	 * @param exp the exp to add
	 */
	public void rewardExp(int exp)
	{
		this.setTotalExp(this.totalExp + exp);
	}
	
	/**
	 * @return the exp level of the creature
	 */
	public int getCurrentExpLevel()
	{
		return this.expLevelFunction(this.totalExp);
	}
}
