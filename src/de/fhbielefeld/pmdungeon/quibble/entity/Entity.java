package de.fhbielefeld.pmdungeon.quibble.entity;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.Level;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandler;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Entity implements IEntity, IAnimatable
{
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
	protected Level level;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Entity(float x, float y)
	{
		this.position = new Point(x, y);
		this.velocity = new Vector2();
		this.animationHandler = new AnimationHandlerImpl();
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
	
	@Override
	public final Point getPosition()
	{
		return this.position;
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
	
	@Override
	public Animation getActiveAnimation()
	{
		return this.animationHandler.getCurrentAnimation();
	}
	
	@Override
	public boolean deleteable()
	{
		return false;
	}
	
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
		}
	}
	
	/**
	 * This is executed when the entity is added to a level and allows the entity to obtain
	 * a <code>DungeonWorld</code> reference.
	 * @param world the <code>DungeonWorld</code> that this entity was added to
	 */
	public void onSpawn(Level level)
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
	public void setNoclip(boolean noclip)
	{
		this.noclip = noclip;
	}
	
	/**
	 * Returns the linear damping of this entity.
	 * The velocity is multiplied by this value every frame to ensure that a moving object loses speed and
	 * does not slide infinitely.
	 * @return the linear damping of this entity
	 */
	public float getLinearDamping()
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
	public void setLinearDamping(float v)
	{
		this.linearDamping = v;
	}
	
	/**
	 * A point of (0 | 0) renders the textures bottom left corner at the entities' position
	 * @return
	 */
	protected Point getDrawingOffsetOverride()
	{
		return null;
	}
}
