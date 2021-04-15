package de.fhbielefeld.pmdungeon.quibble.entity;

import de.fhbielefeld.pmdungeon.quibble.animation.AnimationStateHelper;

public abstract class Creature extends Entity
{
	private static final int ANIM_SWITCH_IDLE_L = 0;
	private static final int ANIM_SWITCH_IDLE_R = 1;
	private static final int ANIM_SWITCH_RUN_L = 2;
	private static final int ANIM_SWITCH_RUN_R = 3;

	protected static final String ANIM_NAME_IDLE_L = "idle_left";
	protected static final String ANIM_NAME_IDLE_R = "idle_right";
	protected static final String ANIM_NAME_RUN_L = "run_left";
	protected static final String ANIM_NAME_RUN_R = "run_right";
	
	/**
	 * The walking speed of this entity, measured in tiles per frame.
	 */
	protected float walkingSpeed;
	
	private boolean isWalking;
	
	private LookingDirection lookingDirection;
	
	private AnimationStateHelper defaultAnimationsHelper;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Creature(float x, float y)
	{
		super(x, y);
		
		this.walkingSpeed = this.getInitWalkingSpeed();
		
		//Default looking direction should be right
		this.lookingDirection = LookingDirection.RIGHT;
		
		if(this.useDefaultAnimation())
		{
			this.defaultAnimationsHelper = new AnimationStateHelper(this.animationHandler);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_IDLE_L, ANIM_NAME_IDLE_L, 0);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_IDLE_R, ANIM_NAME_IDLE_R, 0);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_RUN_L, ANIM_NAME_RUN_L, 5);
			this.defaultAnimationsHelper.addSwitch(ANIM_SWITCH_RUN_R, ANIM_NAME_RUN_R, 5);
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
	 * Returns the walking speed this entity should be initialized with.
	 * @return init walking speed
	 */
	protected abstract float getInitWalkingSpeed();
	
	/**
	 * Returns the current walking speed of this entity measured in tiles per frame.
	 * This is not the current speed.
	 * @return walking speed of this entity
	 */
	public final float getWalkingSpeed()
	{
		return this.walkingSpeed;
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
		this.isWalking = true;
		final float rad = (float)Math.toRadians(angle);
		this.setVelocityX((float)Math.cos(rad) * this.walkingSpeed * mult);
		this.setVelocityY((float)Math.sin(rad) * this.walkingSpeed * mult);
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
	
	public boolean useDefaultAnimation()
	{
		return true;
	}
	
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
	
	@Override
	protected void updateEnd()
	{
		//Reset walking state so that a creature only walks during frames in which walk() was executed.
		this.isWalking = false;
		
		super.updateEnd();
	}
}
