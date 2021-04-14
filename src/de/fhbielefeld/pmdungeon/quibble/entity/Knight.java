package de.fhbielefeld.pmdungeon.quibble.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Knight extends Player
{
	public Knight(float x, float y)
	{
		super(x, y);
		//Default idle animation will always be played if no other animation is being played
		//This must be added or an exception will be thrown
		this.animationHandler.addAsDefaultAnimation("idle", 4, 5, "assets/textures/entity/right/knight", "knight_m");
		
		//Other animations
		this.animationHandler.addAnimation("run", 4, 5, "assets/textures/entity/right/knight", "knight_m");
		this.animationHandler.addAnimation("hit", 1, 15, "assets/textures/entity/right/knight", "knight_m");
	}
	
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
	public void updateAnimationState()
	{
		if(this.isWalking())
		{
			this.animationHandler.playAnimation("run", 5, true);
			//TODO let looking direction affect the animation
		}
		
		//Hitting space will play a hit animation and take over the running animation
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			this.animationHandler.playAnimation("hit", 10, false);
		}
		super.updateAnimationState();
	}
}
