package de.fhbielefeld.pmdungeon.quibble.animation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;

public class AnimationHandlerImpl implements AnimationHandler
{
	private static class AnimationInfo
	{
		private final String name;
		private final int numFrames;
		private final int frameDuration;
		private final String pathToDir;
		private final String fileName;
		
		public AnimationInfo(String name, int numFrames, int frameDuration, String pathToDir, String fileName)
		{
			this.name = name;
			this.numFrames = numFrames;
			this.frameDuration = frameDuration;
			this.pathToDir = pathToDir;
			this.fileName = fileName;
		}
	}
	
	private static class LoadedAnimation
	{
		private final Animation animation;
		private final int numFrames;
		private final int frameDuration;

		private int remainingDuration;
		private boolean cyclicState;
		private int priority;
		
		private LoadedAnimation(Animation animation, int numFrames, int frameDuration)
		{
			this.animation = animation;
			this.numFrames = numFrames;
			this.frameDuration = frameDuration;
		}
		
		private void fullDuration()
		{
			this.remainingDuration = this.calcFull();
		}
		
		private int calcFull()
		{
			return this.frameDuration * this.numFrames;
		}
	}
	
	private List<AnimationInfo> registeredAnimations;
	
	private Map<String, LoadedAnimation> loadedAnimations;
	
	private LoadedAnimation defaultAnimation;
	
	private AnimationInfo defaultAnimInfo;
	
	private boolean isLoaded;
	
	private Animation currentAnimation;
	
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
		for(int i = 0; i < this.registeredAnimations.size(); ++i)
		{
			if(this.registeredAnimations.get(i).name.equals(animInfo.name))
			{
				IllegalArgumentException e = new IllegalArgumentException("animation with name " + animInfo.name + " is already registered");
				LoggingHandler.logger.log(Level.SEVERE, "animation with name " + animInfo.name + " is already registered", e);
				throw e;
			}
		}
		this.registeredAnimations.add(animInfo);
	}
	
	@Override
	public void addAnimation(String animName, int numFrames, int frameDuration, String pathToTexDir, String fileName)
	{
		this.addAnimation(new AnimationInfo(animName, numFrames, frameDuration, pathToTexDir, fileName));
	}
	
	@Override
	public void addAsDefaultAnimation(String animName, int numFrames, int frameDuration, String pathToTexDir, String fileName)
	{
		AnimationInfo animInfo = new AnimationInfo(animName, numFrames, frameDuration, pathToTexDir, fileName);
		this.addAnimation(animInfo);
		this.defaultAnimInfo = animInfo;
	}
	
	@Override
	public boolean loadAnimations()
	{
		if(this.isLoaded)
		{
			IllegalStateException e = new IllegalStateException("animations have already been loaded");
			LoggingHandler.logger.log(Level.SEVERE, "animations have already been loaded", e);
			throw e;			
		}
		if(this.defaultAnimInfo == null) //This must be second as defaultAnimation is set to null after loading
		{
			IllegalStateException e = new IllegalStateException("a default animation must be added");
			LoggingHandler.logger.log(Level.SEVERE, "animations have already been loaded", e);
			throw e;
		}
		StringBuilder pathBuilder = new StringBuilder();
		List<Texture> frames;
		for(AnimationInfo animInfo : this.registeredAnimations)
		{
			//Must be a new list every time because the API actually does not copy it
			frames = new ArrayList<Texture>();
			
			final LoadedAnimation loadedAnim = this.loadAnimation(animInfo, pathBuilder, frames);
			if(loadedAnim == null) //if an error occurred
			{
				return false;
			}
			
			this.loadedAnimations.put(animInfo.name, loadedAnim);
			
			if(animInfo == this.defaultAnimInfo) //If current animInfo is the default animation info
			{
				this.defaultAnimation = loadedAnim;	//In addition to the normal process,
													//save this as default animation separately
				
				//Clear; not needed anymore
				this.defaultAnimInfo = null;
				continue;
			}
		}
		this.isLoaded = true;
		this.registeredAnimations.clear(); //Free space that is not needed anymore
		return true;
	}
	
	private LoadedAnimation loadAnimation(AnimationInfo animInfo, StringBuilder pathBuilder, List<Texture> frames)
	{
		Texture currentTexture;
		for(int n = 0; n < animInfo.numFrames; ++n)
		{
			pathBuilder.append(animInfo.pathToDir);
			pathBuilder.append(File.separator);
			pathBuilder.append(animInfo.fileName);
			pathBuilder.append('_');
			pathBuilder.append(animInfo.name);
			pathBuilder.append('_');
			pathBuilder.append(AnimationHandler.FILENAME_SUFFIX);
			pathBuilder.append(n);
			pathBuilder.append('.');
			pathBuilder.append(AnimationHandler.FILENAME_EXT);
			try
			{
				currentTexture = new Texture(pathBuilder.toString());
			}
			catch(GdxRuntimeException e) //failure to load throws a GdxRuntimeException
			{
				this.loadedAnimations.clear(); //clear list to allow retry to load
				LoggingHandler.logger.log(Level.SEVERE, "Error", e);
				Gdx.app.log("Error", e.getMessage());
				return null;
			}
			frames.add(currentTexture);
			pathBuilder.setLength(0);
		}
		Animation anim = new Animation(frames, animInfo.frameDuration);
		return new LoadedAnimation(anim, animInfo.numFrames, animInfo.frameDuration);
	}
	
	@Override
	public void playAnimation(String animName, int priority, boolean cyclic)
	{
		LoadedAnimation anim = this.loadedAnimations.get(animName);
		anim.fullDuration();
		anim.priority = priority;
		anim.cyclicState = cyclic;
	}
	
	@Override
	public void frameUpdate()
	{
		LoadedAnimation currentSelected = null;
		int priority = -1;
		
		Collection<LoadedAnimation> loadedAnims = this.loadedAnimations.values();
		for(LoadedAnimation anim : loadedAnims)
		{
			//If the animation is cyclic and at least one frame has passed without the animation getting refreshed,
			//the animation should stop (remainingDuration == 0 means stopped)
			if(anim.cyclicState && anim.remainingDuration < anim.calcFull())
			{
				anim.remainingDuration = 0;
			}
			
			//Count down remaining duration
			if(anim.remainingDuration > 0)
			{
				--anim.remainingDuration;
			}
			
			//If it has finished, it should not be candidate to be a visible animation, so continue
			if(anim.remainingDuration <= 0)
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
				final int durationSinceStartSelected = currentSelected.calcFull() - currentSelected.remainingDuration;
				final int durationSinceStartAnim = anim.calcFull() - anim.remainingDuration;
				if(durationSinceStartAnim > durationSinceStartSelected)
				{
					currentSelected = anim;
					priority = anim.priority; //Even though the priority is the same...
				}
			}
			//Nothing if anim.priority < priority
		}
		
		//If no anim was selected or is playing, the default anim gets selected automatically.
		//And we forced the user to add a default animation when loading
		if(currentSelected == null)
		{
			this.currentAnimation = this.defaultAnimation.animation;
		}
		else
		{
			this.currentAnimation = currentSelected.animation;
		}
	}
	
	@Override
	public Animation getCurrentAnimation()
	{
		return this.currentAnimation;
	}
}
