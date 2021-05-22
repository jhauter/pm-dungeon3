package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandler;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleSource;
import de.fhbielefeld.pmdungeon.quibble.util.GeometryUtil;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Entity implements IEntity, IAnimatable, ParticleSource
{
	/**
	 * Event ID for entity spawn events.
	 */
	public static final int EVENT_ID_SPAWN = EntityEvent.genEventID();
	
	/**
	 * Event ID for entity despawn events.
	 */
	public static final int EVENT_ID_DESPAWN = EntityEvent.genEventID();
	
	private long ticks;
	
	private Point position;
	
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
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Entity(float x, float y)
	{
		this.position = new Point(x, y);
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
	 * This is intended to be used to load all resources that read files and thus block the thread.
	 * Hopefully this makes it easier to adapt this to a possible new file loading system.
	 * This is not called by the entity itself but by the system that is responsible for adding the entity to the level.
	 * @return true if all resources have been loaded successfully
	 */
	public boolean loadResources()
	{
		this.loadedResources = this.animationHandler.loadAnimations();
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
	 * {@inheritDoc}
	 */
	@Override
	public final Point getPosition()
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
	public final void setPosition(Point pos)
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
	 * {@inheritDoc}
	 */
	@Override
	public Animation getActiveAnimation()
	{
		return this.animationHandler.getCurrentAnimation();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deleteable()
	{
		return false; //This method does not work so we have a workaround
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void update()
	{
		this.updateBegin();
		this.updateLogic();
		
		//Apply the new values calculated in updateLogic
		//This is core stuff that has to be done for every entity
		this.move(this.velocity.x, this.velocity.y);
		this.velocity.x *= this.linearDamping;
		this.velocity.y *= this.linearDamping;
		
		final int numOtherEntities = this.level.getNumEntities();
		Entity cOther;
		for(int i = 0; i < numOtherEntities; ++i)
		{
			cOther = this.level.getEntity(i);
			if(cOther == this)
			{
				continue;
			}
			if(this.boundingBox.offset(this.getX(), this.getY()).intersects(cOther.boundingBox.offset(cOther.getX(), cOther.getY())))
			{
				//This is called on the other entity too by its own update() method
				this.onEntityCollision(cOther);
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
		
		this.animationHandler.frameUpdate();
		
		if(!this.isInvisible() && this.useDefaultDrawing())
		{
			final Point drawingOffsetOverride = this.getDrawingOffsetOverride();
			if(drawingOffsetOverride != null)
			{
				this.draw(drawingOffsetOverride.x, drawingOffsetOverride.y);
			}
			else
			{
				//I think this draws the entity texture with the center at the entity position
				//But it doesn't quite seem to fit...
				this.draw();
			}
		}
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
			if(this.level.getDungeon().isTileAccessible(newPosX))
			{
				this.position.x += x;
			}
			if(this.level.getDungeon().isTileAccessible(newPosY))
			{
				this.position.y += y;
			}
			//TODO add collision event
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
	 * A point of (0 | 0) renders the texture's bottom left corner at the entity's position
	 * @return
	 */
	protected Point getDrawingOffsetOverride()
	{
		return null;
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
	 * Whether this entity should not be rendered.
	 * @return true if invisible
	 */
	public boolean isInvisible()
	{
		return false;
	}
	
	/**
	 * Actual deleteable(). Once this returns true, the entity will despawn.
	 * @return whether this entity should despawn until the next frame
	 */
	public boolean deleteableWorkaround()
	{
		return false;
	}
	
	/**
	 * This method can be overridden to do custom rendering on entities by using a <code>SpriteBatch</code>.
	 * In order to disable the default rendering, {@link #useDefaultDrawing()} must be overridden.
	 * @param batch the batch that is used for custom rendering on all entities
	 * @param x the entity x-position in screen-coordinates after applying the camera position
	 * @param y the entity y-position in screen-coordinates after applying the camera position
	 */
	public void doCustomRendering(SpriteBatch batch, float x, float y)
	{
		
	}
	
	/**
	 * Returns whether the entity will be drawn by using the PM-Dungeon API.
	 * This can be disabled by overriding this method.
	 * By disabling this method, the entity will not be drawn at all and
	 * the user is entirely responsible for drawing the entity at the right position.
	 * @return whether the default drawing for this entity should be used
	 */
	public boolean useDefaultDrawing()
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
}
