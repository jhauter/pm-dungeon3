package de.fhbielefeld.pmdungeon.quibble.entity;

public class Knight extends Player
{
	/**
	 * Creates a knight instance at the given coordinates.
	 * The coordinates can be changed after creating the knight by calling
	 * {@link Knight#setPosition(float, float)}. This way it can be placed anywhere in the dungeon.
	 * @param x x position
	 * @param y y position
	 */
	public Knight(float x, float y)
	{
		super(x, y);
		//Default idle animation will always be played if no other animation is being played
		//This must be added or an exception will be thrown
		this.animationHandler.addAsDefaultAnimation("idle_right", 4, 5, "assets/textures/entity/knight", "knight_m");
		
		//Other animations
		this.animationHandler.addAnimation("idle_left", 4, 5, "assets/textures/entity/knight", "knight_m");
		
		this.animationHandler.addAnimation("run_right", 4, 2, "assets/textures/entity/knight", "knight_m");
		this.animationHandler.addAnimation("run_left", 4, 2, "assets/textures/entity/knight", "knight_m");
		this.animationHandler.addAnimation("hit_right", 1, 15, "assets/textures/entity/knight", "knight_m");
		this.animationHandler.addAnimation("hit_left", 1, 15, "assets/textures/entity/knight", "knight_m");
	}
	
	/**
	 * Creates a knight instance at <code>x = 0</code> and <code>y = 0</code>.
	 * The coordinates can be changed after creating the knight by calling
	 * {@link Knight#setPosition(float, float)}. This way it can be placed anywhere in the dungeon.
	 */
	public Knight()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public float getInitWalkingSpeed()
	{
		return 0.1F;
	}
	
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.35F, -0.5F, 0.7F, 1.0F);
	}
	
	/************** DEMO **************
	
	@Override
	public void updateAnimationState()
	{
		//Hitting space will play a hit animation and take over the running animation
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			this.animationHandler.playAnimation("hit_right", 10, false);
		}
		super.updateAnimationState();
	}
	
	**********************************/
}
