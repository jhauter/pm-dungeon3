package de.fhbielefeld.pmdungeon.quibble.animation;

import java.io.IOException;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;

public interface AnimationHandler
{
	public static final String FILENAME_SUFFIX = "anim_f";
	public static final String FILENAME_EXT = "png";
	
	/**
	 * Registers a new animation for this animation handler.
	 * Each animation is a set of textures which is assigned a name.
	 * The number of textures is specified by <code>numFrames</code>.
	 * The nth texture file of this set is expected to be at the following filepath:
	 * 
	 * <br>
	 * <blockquote><code>&ltpathToTexDir&gt/&ltfileName&gt_&ltanimName&gt_&ltFILENAME_SUFFIX&gt_&ltn&gt.&ltFILENAME_EXT&gt</code></blockquote>
	 * 
	 * This method stores the supplied parameters internally.
	 * The files can be loaded by calling <code>loadAnimations()</code>.
	 * The animations be played after loading by calling the <code>playAnimation()</code> method by
	 * passing the here specified animName.
	 * An animation with an already registered name cannot be added, because then an <code>IllegalArgumentException</code>
	 * will be thrown.
	 * @param animName the name of the animation
	 * @param numFrames number of textures in this animation
	 * @param pathToTexDir path to folder which contains the textures
	 * @param fileName part of the animation file format (see above)
	 * @throws IllegalArgumentException if an animation with the same <code>animName</code>
	 * has already been registered
	 */
	public void addAnimation(String animName, int numFrames, String pathToTexDir, String fileName);
	
	/**
	 * Sets the default animation which should be fallen back to if no other animation is currently playing.
	 * This needs to be set, otherwise no animation is allowed to be played.
	 * @param animName the name of the animation
	 * @param numFrames number of textures in this animation
	 * @param pathToTexDir path to folder which contains the textures
	 * @param fileName part of the animation file format (see above)
	 * @throws IllegalArgumentException if an animation with the same <code>animName</code>
	 * has already been registered
	 * @see AnimationHandler#addAnimation(String, int, String, String)
	 */
	public void setDefaultAnimation(String animName, int numFrames, String pathToTexDir, String fileName);
	
	/**
	 * Tries to load all textures of all registered animations from the file system.
	 * Before loading, no animation should be allowed to play.
	 * If this method fails, all animations that have been loaded to to the point of failure should
	 * be cleared, in order to allow retrying to load the animations.
	 * @throws IOException if an I/O error occurs while reading the files
	 */
	public void loadAnimations() throws IOException;
	
	/**
	 * Starts playing the animation with the specified <code>animName</code>.
	 * Every frame of the animation should last <code>frameDuration</code> frames.
	 * Multiple animations are allowed to be played at the same time.
	 * In case multiple animations happen to run at the same time, the priority of each animation determines
	 * which animation is actually returned by <code>getCurrentAnimation()</code>, where an animation with a greater
	 * priority is displayed over an animation with a lower priority.
	 * A animation can be cyclic or not. A cyclic animation will be played as long as this method keeps playing
	 * said animation. A cyclic animation will stop playing if this method does not "keep alive" the cyclic
	 * animation every frame anymore.
	 * A non-cyclic animation does only need to be called once and will play until it runs out of frames.
	 * Calling this method if the non-cyclic animation has already started will restart this animation.
	 * @param name name of the registered animation
	 * @param frameDuration how long a single frame will be displayed in the animation
	 * @param priority priority in regards to already playing animations
	 * @param cyclic whether the animation should be played infinitely (requires calling this method every frame)
	 * @throws IllegalStateException if this method is called before the animations have been successfully loaded
	 * @throws IllegalStateException if no default animation was set
	 */
	public void playAnimation(String animName, int frameDuration, int priority, boolean cyclic);
	
	/**
	 * This is called every frame per animation handler and is used to calculate the animations' states
	 * each frame.
	 */
	public void frameUpdate();
	
	/**
	 * Returns the currently played animation that has the greatest priority.
	 * This method will not normally return null because a default animation must be set.
	 * The only case this will return null is if the animations have not been loaded yet by calling
	 * <code>loadAnimations()</code>.
	 * @return currently visible animation with greatest priority
	 */
	public Animation getCurrentAnimation();
}
