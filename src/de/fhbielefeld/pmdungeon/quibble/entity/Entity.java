package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.SpatialHashGrid;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandler;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.util.GeometryUtil;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Entity
{
	/**
	 * Event ID for entity spawn events.
	 */
	public static final int EVENT_ID_SPAWN = EntityEvent.genEventID();
	
	/**
	 * Event ID for entity despawn events.
	 */
	public static final int EVENT_ID_DESPAWN = EntityEvent.genEventID();
	
	/**
	 * Event ID for entity collision with tiles events.
	 */
	public static final int EVENT_ID_COLLISION_TILE = EntityEvent.genEventID();
	
	private long ticks;
	
	private Vector2 position;
	
	/**
	 * The width of the rendered image.
	 * A value of <code>1.0</code> means the entity texture will be 1 tile wide.
	 */
	protected float renderWidth = 1.0F;
	
	/**
	 * The height of the rendered image.
	 * A value of <code>1.0</code> means the entity texture will be 1 tile high.
	 */
	protected float renderHeight = 1.0F;
	
	/**
	 * Render image scale on the x axis.
	 * How much to multiply the width when rendering.
	 * Negative values will flip the image.
	 */
	protected float renderScaleX = 1.0F;
	
	/**
	 * Render image scale on the y axis.
	 * How much to multiply the height when rendering.
	 * Negative values will flip the image.
	 */
	protected float renderScaleY = 1.0F;
	
	/**
	 * Render offset along the x axis. This value determines the image offset from the position.
	 * A value of <code>0.0</code> centers the image on the actual entity position.
	 */
	protected float renderOffsetX = 0.0F;
	
	/**
	 * Render offset along the x axis. This value determines the image offset from the position.
	 * A value of <code>0.0</code> centers the image on the actual entity position.
	 */
	protected float renderOffsetY = 0.0F;
	
	/**
	 * Rotation pivot relative to renderWidth.
	 * <code>0.5</code> means rotation around the center
	 */
	protected float renderPivotX = 0.5F;
	
	/**
	 * Rotation pivot relative to renderHeight.
	 * <code>0.5</code> means rotation around the center
	 */
	protected float renderPivotY = 0.5F;
	
	/**
	 * Rotation for rendering in degrees. Change the rotation origin with the
	 * <code>renderPivotX</code> and <code>renderPivotY</code> values.
	 */
	protected float renderRotation = 0.0F;
	
	private boolean noclip;
	
	private Vector2 velocity;
	
	private boolean loadedResources;
	
	/**
	 * The velocity is multiplied by this value every frame to ensure that a moving object loses speed and
	 * does not slide infinitely. Value must be <code>&lt;= 1.0</code> or else the entity will get exponentially faster every frame.
	 * Value must not be negative.
	 */
	private float linearDamping = 0.65F;
	
	/**
	 * <code>AnimationHandler</code> instance that will handle this entity's animations.
	 */
	protected AnimationHandler animationHandler;
	
	/**
	 * <code>Level</code> reference for this entity to allow interaction with the level.
	 */
	protected DungeonLevel level;
	
	/**
	 * Bounding box relative to this entity's position. Used for collision detection.
	 */
	protected BoundingBox boundingBox;
	
	private List<EntityEventHandler> eventHandlers;
	
	private SpatialHashGrid.Handle<Entity> spatialHashGridHandle;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Entity(float x, float y)
	{
		this.position = new Vector2(x, y);
		this.velocity = new Vector2();
		this.animationHandler = new AnimationHandlerImpl();
		this.boundingBox = this.getInitBoundingBox();
		this.eventHandlers = new ArrayList<EntityEventHandler>();
	}
	
	/**
	 * Creates an entity with a default position
	 */
	public Entity()
	{
		this(0.0F, 0.0F);
	}
	
	/**
	 * Returns the display name of the entity which may be shown at some points in the game,
	 * including being shown above creatures. This method may get removed in the future and be
	 * replaced by a name field in an entity register.
	 * @return display name of the entity
	 */
	public String getDisplayName()
	{
		return this.getClass().getSimpleName();
	}
	
	public boolean isDisplayNameVisible()
	{
		return false;
	}
	
	public String getDisplayNamePrefix()
	{
		return "";
	}
	
	/**
	 * This is intended to be used to load all resources that read files and thus block the thread.
	 * This is not called by the entity itself but by the system that is responsible for adding the entity to the level.
	 * @return true if enough resources have been loaded successfully to let the entity spawn
	 */
	public boolean loadResources()
	{
		if(this.useAnimationHandler())
		{
			this.loadedResources = this.animationHandler.loadAnimations();
		}
		else
		{
			this.loadedResources = true;
		}
		return this.loadedResources;
	}
	
	/**
	 * Whether all resources have been loaded successfully. This is a requirement
	 * for an entity to be added to a level.
	 * @return whether all resources have been loaded successfully
	 */
	public boolean isLoadedResources()
	{
		return this.loadedResources;
	}
	
	/**
	 * Returns the current position of this entity.
	 * The position is at at bottom center of the rendered entity.
	 * @return the current position
	 */
	public final Vector2 getPosition()
	{
		return this.position;
	}
	
	/**
	 * Convenience method for returning the position x.
	 * @return x position
	 */
	public float getX()
	{
		return this.position.x;
	}
	
	/**
	 * Convenience method for returning the position y.
	 * @return y position
	 */
	public float getY()
	{
		return this.position.y;
	}
	
	/**
	 * Instantly sets the position of this entity and thus teleporting it. No collision checks are made.
	 * @param pos the new position
	 */
	public final void setPosition(Vector2 pos)
	{
		//I think this is more efficient than this.position = pos;
		this.position.x = pos.x;
		this.position.y = pos.y;
	}
	
	/**
	 * Instantly sets the position of this entity and thus teleporting it. No collision checks are made.
	 * @param x new x-coordinate
	 * @param y new y-coordinate
	 */
	public final void setPosition(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
	}
	
	/**
	 * Returns the current velocity of this entity.
	 * @return current velocity of this entity
	 */
	public Vector2 getVelocity()
	{
		return this.velocity;
	}
	
	/**
	 * Sets the current velocity of this entity. Velocity is used to move an entity and to calculate its new position.
	 * @param xVel new velocity x-value
	 * @param yVel new velocity y-value
	 */
	public void setVelocity(float xVel, float yVel)
	{
		this.velocity.x = xVel;
		this.velocity.y = yVel;
	}
	
	/**
	 * Sets the velocity in x direction.
	 * @param x the new x velocity
	 */
	public void setVelocityX(float x)
	{
		this.velocity.x = x;
	}
	
	/**
	 * Sets the velocity in y direction.
	 * @param y the new y velocity
	 */
	public void setVelocityY(float y)
	{
		this.velocity.y = y;
	}
	
	/**
	 * Returns the amount of frames since this entity was added to the level.
	 * @return the amount of frames since this entity was added to the level
	 */
	public final long getTicks()
	{
		return this.ticks;
	}
	
	/**
	 * Returns the animation that should render at a given moment.
	 * @return the currently displayed animation
	 */
	public Animation<TextureRegion> getActiveAnimation()
	{
		return this.animationHandler.getCurrentAnimation();
	}
	
	/**
	 * If this returns <code>true</code> then this entity will be removed from the level
	 * on the next update.
	 * @return whether this entity should despawn on the next update
	 */
	public boolean shouldDespawn()
	{
		return false;
	}
	
	/**
	 * Calculates the entity's logic such as new position, collision detection, animation...
	 * Cannot be overridden. Override the other update methods instead.
	 */
	public final void update()
	{
		this.updateBegin();
		this.updateLogic();
		
		//Apply the new values calculated in updateLogic
		//This is core stuff that has to be done for every entity
		this.move(this.velocity.x, this.velocity.y);
		this.velocity.x *= this.linearDamping;
		this.velocity.y *= this.linearDamping;
		
		Set<Entity> nearbyEntities = this.level.getSpatialHashGrid().nearby(this.spatialHashGridHandle);
		for(Entity other : nearbyEntities)
		{
			if(other == this)
			{
				continue;
			}
			if(this.boundingBox.offset(this.getX(), this.getY()).intersects(other.boundingBox.offset(other.getX(), other.getY())))
			{
				//This is called on the other entity too by its own update() method
				this.onEntityCollision(other);
			}
		}
		
		this.ticks++;
		
		this.updateAnimationState();
		this.updateEnd();
	}
	
	/**
	 * Called every update and should be used for preparation for each frame
	 * This makes it easier to override because we don't have to think as much about where the super belongs.
	 */
	protected void updateBegin()
	{
		
	}
	
	/**
	 * Called every update and should be used for logic extension.
	 * This makes it easier to override because we don't have to think as much about where the super belongs.
	 */
	protected void updateLogic()
	{
		
	}
	
	/**
	 * Called every update and should be used for calculating animation states based on the logic
	 * that has been calculated.
	 * This makes it easier to override because we don't have to think as much about where the super belongs.
	 */
	protected void updateAnimationState()
	{
		
	}
	
	/**
	 * Called every update and should be used for post logic stuff such as drawing.
	 * This makes it easier to override because we don't have to think as much about where the super belongs.
	 */
	protected void updateEnd()
	{
		/******GRAPHICS******/
		
		if(this.useAnimationHandler())
		{
			this.animationHandler.frameUpdate(Gdx.graphics.getDeltaTime());
		}
	}
	
	public void render()
	{
		TextureRegion t = this.getActiveAnimation().getKeyFrame(this.animationHandler.getCurrentAnimationState(), true);
		
		DungeonStart.getGameBatch().setColor(1.0F, 1.0F, 1.0F, this.getTransparency());
		
		DungeonStart.getGameBatch().draw(t,
			this.getX() + this.getRenderOffsetX() - this.getRenderWidth() * 0.5F,
			this.getY() + this.getRenderOffsetY() - this.getRenderHeight() * 0.5F,
			this.getRenderPivotX() * this.getRenderWidth(),
			this.getRenderPivotY() * this.getRenderHeight(),
			this.getRenderWidth(),
			this.getRenderHeight(),
			this.getScaleX(),
			this.getScaleY(),
			this.getRotation());
		
		DungeonStart.getGameBatch().setColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	/**
	 * Tries to move the entity in the direction specified by the <code>x</code> and <code>y</code> parameters.
	 * Parameters are usually the velocity x and y values.
	 * If collision checks detect a collision along the way that the entity should be moved, the moving will be aborted
	 * and the entity will only be moved up until the point of collision. (This is complex collision detection
	 * and will be implemented at a later date)
	 * @param x units to be moved along the x-axis
	 * @param y units to be moved along the y-axis
	 */
	public void move(float x, float y)
	{
		if(this.noclip) //noclip toggles collision detection
		{
			this.position.x += x;
			this.position.y += y;
		}
		else
		{
			//Calculate the axis independently so that it doesn't get stuck if it moves diagonally
			Point newPosX = new Point(this.position.x + x, this.position.y);
			Point newPosY = new Point(this.position.x, this.position.y + y);
			boolean collsionX = !this.level.getDungeon().isTileAccessible(newPosX);
			boolean collsionY = !this.level.getDungeon().isTileAccessible(newPosY);
			if(!collsionX)
			{
				this.position.x += x;
			}
			if(!collsionY)
			{
				this.position.y += y;
			}
			if(collsionX || collsionY)
			{
				this.fireEvent(new EntityEvent(EVENT_ID_COLLISION_TILE, this));
				this.onTileCollision();
			}
		}
	}
	
	/**
	 * This is executed when the entity is added to a level and allows the entity to obtain
	 * a <code>DungeonWorld</code> reference.
	 * @param world the <code>DungeonWorld</code> that this entity was added to
	 */
	public void onSpawn(DungeonLevel level)
	{
		this.level = level;
	}
	
	/**
	 * This is executed when the entity is removed from a level.
	 */
	public void onDespawn()
	{
		
	}
	
	/**
	 * Returns true if noclip is active, i.e. collision detection should not be performed.
	 * @return whether noclip is active
	 */
	public final boolean isNoclip()
	{
		return this.noclip;
	}
	
	/**
	 * Sets the noclip value of this entity which determines whether collision detection should
	 * be performed or not.
	 * @param noclip whether noclip should be activated
	 */
	public final void setNoclip(boolean noclip)
	{
		this.noclip = noclip;
	}
	
	/**
	 * Returns the linear damping of this entity.
	 * The velocity is multiplied by this value every frame to ensure that a moving object loses speed and
	 * does not slide infinitely.
	 * @return the linear damping of this entity
	 */
	public final float getLinearDamping()
	{
		return this.linearDamping;
	}
	
	/**
	 * Sets the linear damping of this entity.
	 * The velocity is multiplied by this value every frame to ensure that a moving object loses speed and
	 * does not slide infinitely. Value must be <code>&lt;= 1.0</code> or else the entity will get exponentially faster every frame.
	 * Value must not be negative.
	 * @param v new linear damping
	 */
	public final void setLinearDamping(float v)
	{
		this.linearDamping = v;
	}
	
	/**
	 * Sets this entity's bounding box relative to the entity's position.
	 * @param bb bounding box which is relative to the entity's position.
	 */
	public final void setBoundingBox(BoundingBox bb)
	{
		this.boundingBox = bb;
	}
	
	/**
	 * Returns this entity's bounding box which is relative to the entity's position.
	 * The bounding box is used for collision detection.
	 * @return bounding box which is relative to the entity's position.
	 */
	public final BoundingBox getBoundingBox()
	{
		return this.boundingBox;
	}
	
	/**
	 * Returns the initial value for the bounding box. This can be overridden to change the initial value.
	 * The bounding box is in relative coordinates and therefore should be close to 0.<br><br>
	 * Additionally, the bounding box should be placed in such a way that the actual entity
	 * position if not too far away. Note that the entity position here is (0 | 0) because of the
	 * relative coordinates of the bounding box.<br><br>
	 * Ideally the bounding box should be centered on the entity at least on the x axis.
	 * @return the initial bounding box relative to the entity's position
	 */
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.5F, -0.5F, 1.0F, 1.0F);
	}
	
	/**
	 * Returns the radius of this entity.
	 * While there is a bounding box, some mechanics use the radius to do certain checks,
	 * for example hitting of creatures.
	 * @return the entity's radius
	 */
	public float getRadius()
	{
		return 0.5F;
	}
	
	/**
	 * This is called when collision between entities occurs.
	 * @param otherEntity the entity this entity is colliding with
	 */
	protected void onEntityCollision(Entity otherEntity)
	{
		
	}
	
	/**
	 * This is called when collision with tiles occurs.
	 */
	protected void onTileCollision()
	{
		
	}
	
	/**
	 * Whether this entity should not be rendered.
	 * @return true if invisible
	 */
	public boolean isInvisible()
	{
		return false;
	}
	
	/**
	 * Can be overridden in order to not use the animation handler to prevent it from automatically loading
	 * animations and throwing an exception if no animation is added.
	 * @return whether the animation handler should be used
	 */
	public boolean useAnimationHandler()
	{
		return true;
	}
	
	/**
	 * Fires an entity event and notifies all event listeners registered on this entity.
	 * @param event the event object to pass to the listeners
	 * @return the exact same argument that was passes for convenience
	 */
	public EntityEvent fireEvent(EntityEvent event)
	{
		this.eventHandlers.forEach(handler -> handler.handleEvent(event));
		return event;
	}
	
	/**
	 * Adds an event listener to this entity which will be notified when the entity fires an event.
	 * @param h the listener to add
	 * @throws IllegalArgumentException if the specified listener has already been added
	 */
	public void addEntityEventHandler(EntityEventHandler h)
	{
		if(this.eventHandlers.contains(h))
		{
			throw new IllegalArgumentException("this EntityEventHandler is already added");
		}
		this.eventHandlers.add(h);
	}
	
	/**
	 * Removes a previously added event listener.
	 * If the specified listener is not actually added, this does nothing.
	 * @param h the listener to remove
	 */
	public void removeEntityEventHandler(EntityEventHandler h)
	{
		this.eventHandlers.remove(h);
	}
	
	/**
	 * @return the level this entity was spawned into
	 */
	public DungeonLevel getLevel()
	{
		return this.level;
	}
	
	/**
	 * Checks whether there is a straight line between this entity and the specified point that
	 * is not blocked by an inaccessible tile like a wall, i. e. whether
	 * this entity can "see" the specified point.
	 * @param p the point at which the line of sight will end
	 * @return whether there is a line of sight between this entity and the specified point
	 */
	public boolean hasLineOfSightTo(Vector2 p)
	{
		int boundsMinX = Math.min((int)this.getX(), (int)p.x);
		int boundsMinY = Math.min((int)this.getY(), (int)p.y);
		
		int boundsMaxX = Math.max((int)Math.ceil(this.getX()), (int)Math.ceil(p.x));
		int boundsMaxY = Math.max((int)Math.ceil(this.getY()), (int)Math.ceil(p.y));
		
		for(int x = boundsMinX; x <= boundsMaxX; ++x)
		{
			for(int y = boundsMinY; y <= boundsMaxY; ++y)
			{
				if(this.level.getDungeon().getTileAt(x, y) == null || this.level.getDungeon().getTileAt(x, y).isAccessible())
				{
					continue;
				}
				
				if(GeometryUtil.intersectLineSegmentRectangle(this.getX(), this.getY(), p.x, p.y, x, y, 1, 1))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Handle used for spatial hash grid collision testing.
	 * @return spatial hash grid handle
	 */
	public final SpatialHashGrid.Handle<Entity> getSpatialHashGridHandle()
	{
		return this.spatialHashGridHandle;
	}
	
	/**
	 * Sets the handle used for spatial hash grid collision testing.
	 * Do not use this.
	 * @param the new spatial hash grid handle
	 */
	public final void setSpationHashGridHandle(SpatialHashGrid.Handle<Entity> handle)
	{
		this.spatialHashGridHandle = handle;
	}
	
	/**
	 * The width of the rendered image.
	 * A value of <code>1.0</code> means the entity texture will be 1 tile wide.
	 */
	public float getRenderWidth()
	{
		return this.renderWidth;
	}
	
	/**
	 * The height of the rendered image.
	 * A value of <code>1.0</code> means the entity texture will be 1 tile high.
	 */
	public float getRenderHeight()
	{
		return this.renderHeight;
	}
	
	/**
	 * Render image scale on the x axis.
	 * How much to multiply the width when rendering.
	 * Negative values will flip the image.
	 */
	public float getScaleX()
	{
		return this.renderScaleX;
	}
	
	/**
	 * Render image scale on the y axis.
	 * How much to multiply the height when rendering.
	 * Negative values will flip the image.
	 */
	public float getScaleY()
	{
		return this.renderScaleY;
	}
	
	/**
	 * Render offset along the x axis. This value determines the image offset from the position.
	 * A value of <code>0.0</code> centers the image on the actual entity position.
	 */
	public float getRenderOffsetX()
	{
		return this.renderOffsetX;
	}
	
	/**
	 * Render offset along the x axis. This value determines the image offset from the position.
	 * A value of <code>0.0</code> centers the image on the actual entity position.
	 */
	public float getRenderOffsetY()
	{
		return this.renderOffsetY;
	}
	
	/**
	 * Rotation pivot relative to renderWidth.
	 * <code>0.5</code> means rotation around the center
	 */
	public float getRenderPivotX()
	{
		return this.renderPivotX;
	}
	
	/**
	 * Rotation pivot relative to renderHeight.
	 * <code>0.5</code> means rotation around the center
	 */
	public float getRenderPivotY()
	{
		return this.renderPivotY;
	}
	
	/**
	 * Rotation for rendering in degrees. Change the rotation origin with the
	 * <code>renderPivotX</code> and <code>renderPivotY</code> values.
	 */
	public float getRotation()
	{
		return this.renderRotation;
	}
	
	public float getTransparency()
	{
		return 1.0F;
	}
}
