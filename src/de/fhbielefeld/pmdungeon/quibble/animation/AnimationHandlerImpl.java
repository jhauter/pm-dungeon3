package de.fhbielefeld.pmdungeon.quibble.animation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;

public class AnimationHandlerImpl implements AnimationHandler
{
	private static class AnimationInfo
	{
		private final String name;
		private final int numFrames;
		private final int firstFrame;
		private final float frameDuration;
		private final int rows;
		private final int columns;
		private final String fileName;
		
		public AnimationInfo(String animName, int numFrames, int firstFrame, float frameDuration, int rows, int columns, String fileName)
		{
			this.name = animName;
			this.numFrames = numFrames;
			this.firstFrame = firstFrame;
			this.frameDuration = frameDuration;
			this.rows = rows;
			this.columns = columns;
			this.fileName = fileName;
		}
	}
	
	private static class LoadedAnimation
	{
		private final Animation<TextureRegion> animation;
		
		private float visibleDurationRemaining;
		private float stateTime;
		private boolean cyclicState;
		private int priority;
		
		private LoadedAnimation(Animation<TextureRegion> animation)
		{
			this.animation = animation;
		}
		
		private void fullDuration()
		{
			this.visibleDurationRemaining = this.calcFull();
		}
		
		private float calcFull()
		{
			return this.animation.getAnimationDuration();
		}
	}
	
	private List<AnimationInfo> registeredAnimations;
	
	private Map<String, LoadedAnimation> loadedAnimations;
	
	private LoadedAnimation defaultAnimation;
	
	private AnimationInfo defaultAnimInfo;
	
	private boolean isLoaded;
	
	private Animation<TextureRegion> currentAnimation;
	
	private float currentAnimationState;
	
	/**
	 * Creates an empty animation handler to which animations can be added.
	 */
	public AnimationHandlerImpl()
	{
		this.registeredAnimations = new ArrayList<AnimationInfo>();
		this.loadedAnimations = new HashMap<String, LoadedAnimation>();
	}
	
	private void addAnimation(AnimationInfo animInfo)
	{
		if(animInfo.numFrames <= 0)
		{
			IllegalArgumentException e = new IllegalArgumentException("numFrames cannot be <= 0");
			LoggingHandler.logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		if(animInfo.frameDuration < 0.0F)
		{
			IllegalArgumentException e = new IllegalArgumentException("frameDuration cannot be negative");
			LoggingHandler.logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		if(animInfo.numFrames + animInfo.firstFrame > animInfo.rows * animInfo.columns)
		{
			IllegalArgumentException e = new IllegalArgumentException("numFrames (+firstFrame) cannot be greater than rows * columns");
			LoggingHandler.logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		for(int i = 0; i < this.registeredAnimations.size(); ++i)
		{
			if(this.registeredAnimations.get(i).name.equals(animInfo.name))
			{
				IllegalArgumentException e = new IllegalArgumentException("animation with name " + animInfo.name + " is already registered");
				LoggingHandler.logger.log(Level.SEVERE, e.toString());
				throw e;
			}
		}
		this.registeredAnimations.add(animInfo);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnimation(String animName, int numFrames, float frameDuration, int rows, int columns, String fileName)
	{
		this.addAnimation(animName, numFrames, 0, frameDuration, rows, columns, fileName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnimation(String animName, int numFrames, int firstFrame, float frameDuration, int rows, int columns, String fileName)
	{
		this.addAnimation(new AnimationInfo(animName, numFrames, firstFrame, frameDuration, rows, columns, fileName));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAsDefaultAnimation(String animName, int numFrames, float frameDuration, int rows, int columns, String fileName)
	{
		this.addAsDefaultAnimation(animName, numFrames, 0, frameDuration, rows, columns, fileName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAsDefaultAnimation(String animName, int numFrames, int firstFrame, float frameDuration, int rows, int columns, String fileName)
	{
		AnimationInfo animInfo = new AnimationInfo(animName, numFrames, firstFrame, frameDuration, rows, columns, fileName);
		this.addAnimation(animInfo);
		this.defaultAnimInfo = animInfo;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean loadAnimations()
	{
		if(this.isLoaded)
		{
			IllegalStateException e = new IllegalStateException("animations have already been loaded");
			LoggingHandler.logger.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}
		
		//This must be the second 'if' as defaultAnimInfo is set to null after loading
		if(this.defaultAnimInfo == null)
		{
			IllegalStateException e = new IllegalStateException("a default animation must be added");
			LoggingHandler.logger.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}
		
		List<TextureRegion> frames = new ArrayList<TextureRegion>();
		for(AnimationInfo animInfo : this.registeredAnimations)
		{
			//This is faster than creating a new one every time
			frames.clear();
			
			final LoadedAnimation loadedAnim = this.loadAnimation(animInfo, frames);
			if(loadedAnim == null) //if an error occurred
			{
				return false;
			}
			
			this.loadedAnimations.put(animInfo.name, loadedAnim);
			
			if(animInfo == this.defaultAnimInfo) //If current animInfo is the default animation info
			{
				this.defaultAnimation = loadedAnim; //In addition to the normal process,
													//save this as default animation separately
				
				//Clear; not needed anymore
				this.defaultAnimInfo = null;
				continue;
			}
		}
		this.isLoaded = true;
		this.registeredAnimations.clear(); //Free space that is not needed anymore
		this.currentAnimation = this.defaultAnimation.animation;
		return true;
	}
	
	private LoadedAnimation loadAnimation(AnimationInfo animInfo, List<TextureRegion> frames)
	{
		DungeonResource<Texture> texRes = ResourceHandler.requestResourceInstantly(animInfo.fileName, ResourceType.TEXTURE);
		
		if(texRes.hasError())
		{
			this.loadedAnimations.clear(); //clear list to allow retry to load
			//No need to log here because the ResourceHandler already logs
			return null;
		}
		
		final int tileWidth = texRes.getResource().getWidth() / animInfo.columns;
		final int tileHeight = texRes.getResource().getHeight() / animInfo.rows;
		TextureRegion[][] grid = TextureRegion.split(texRes.getResource(), tileWidth, tileHeight);
		for(int n = animInfo.firstFrame; n < animInfo.numFrames + animInfo.firstFrame; ++n)
		{
			frames.add(grid[n / animInfo.columns][n % animInfo.columns]);
		}
		Animation<TextureRegion> anim = new Animation<>(animInfo.frameDuration, frames.toArray(new TextureRegion[0]));
		return new LoadedAnimation(anim);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void playAnimation(String animName, int priority, boolean cyclic)
	{
		LoadedAnimation anim = this.loadedAnimations.get(animName);
		if(anim == null)
		{
			return;
		}
		if(!cyclic)
		{
			anim.stateTime = 0.0F;
		}
		anim.fullDuration();
		anim.priority = priority;
		anim.cyclicState = cyclic;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void frameUpdate(float delta)
	{
		LoadedAnimation currentSelected = null;
		int priority = -1;
		
		Collection<LoadedAnimation> loadedAnims = this.loadedAnimations.values();
		for(LoadedAnimation anim : loadedAnims)
		{
			anim.stateTime += delta;
			//If the animation is cyclic and at least one frame has passed without the animation
			//getting refreshed, the animation should stop (remainingDuration == 0 means stopped)
			if(anim.cyclicState && anim.visibleDurationRemaining < anim.calcFull())
			{
				anim.visibleDurationRemaining = 0.0F;
			}
			
			//Count down remaining duration
			if(anim.visibleDurationRemaining > 0.0F)
			{
				anim.visibleDurationRemaining -= delta;
			}
			
			//If it has finished, it should not be candidate to be a visible animation, so continue
			if(anim.visibleDurationRemaining <= 0.0F)
			{
				continue;
			}
			
			if(anim.priority > priority) //If current anim has higher prio then overwrite old one
			{
				currentSelected = anim;
				priority = anim.priority;
			}
			else if(anim.priority == priority) //If prio is the same, the most recent anim wins
			{
				final float durationSinceStartSelected = currentSelected.calcFull() - currentSelected.visibleDurationRemaining;
				final float durationSinceStartAnim = anim.calcFull() - anim.visibleDurationRemaining;
				if(durationSinceStartAnim > durationSinceStartSelected)
				{
					currentSelected = anim;
					priority = anim.priority; //Even though the priority is the same anyway...
				}
			}
			//Nothing if anim.priority < priority
		}
		
		//If no anim was selected or is playing, the default anim gets selected automatically.
		//And we forced the user to add a default animation when loading
		if(currentSelected == null)
		{
			this.currentAnimation = this.defaultAnimation.animation;
			this.currentAnimationState = this.defaultAnimation.stateTime;
		}
		else
		{
			this.currentAnimation = currentSelected.animation;
			this.currentAnimationState = currentSelected.stateTime;
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Animation<TextureRegion> getCurrentAnimation()
	{
		return this.currentAnimation;
	}
	
	@Override
	public float getCurrentAnimationState()
	{
		return this.currentAnimationState;
	}
}
