package de.fhbielefeld.pmdungeon.quibble.animation;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;

public interface AnimationHandler
{
	/**
	 * String that is inserted into the file path according to documentation of {@link #addAnimation(String, int, int, String, String)}.
	 */
	public static final String FILENAME_SUFFIX = "anim_f";
	
	/**
	 * File extension of the texture files.
	 */
	public static final String FILENAME_EXT = "png";
	
	/**
	 * Registers a new animation for this animation handler.
	 * Each animation is a set of textures which is assigned a name.
	 * The number of textures is specified by <code>numFrames</code>.
	 * Every frame of the animation will last <code>frameDuration</code> frames.
	 * The nth texture file of this set is expected to be at the following file path:
	 * 
	 * <br>
	 * <blockquote><code>&ltpathToTexDir&gt;/&ltfileName&gt;_&ltanimName&gt;_&ltFILENAME_SUFFIX&gt;&lt;n&gt;.&ltFILENAME_EXT&gt;</code></blockquote>
	 * 
	 * This method stores the supplied parameters internally.
	 * The files can be loaded by calling <code>loadAnimations()</code>.
	 * The animations can be played after loading by calling the <code>playAnimation()</code> method by
	 * passing the here specified animName.
	 * An animation with an already registered name cannot be added, because then an <code>IllegalArgumentException</code>
	 * will be thrown.
	 * @param animName the name of the animation (names are case sensitive)
	 * @param numFrames number of textures in this animation
	 * @param frameDuration how long a single frame will be displayed in the animation
	 * @param pathToTexDir path to folder which contains the textures
	 * @param fileName part of the animation file format (see above)
	 * @throws IllegalArgumentException if an animation with the same <code>animName</code>
	 * has already been registered
	 */
	public void addAnimation(String animName, int numFrames, int frameDuration, String pathToTexDir, String fileName);
	
	/**
	 * Sets the default animation which should be fallen back to if no other animation is currently playing.
	 * This needs to be set, otherwise no animation is allowed to be played.
	 * If a default animation has already been set, the previous default animation will become a regular
	 * animation and the the newly added default animation will become the default animation.
	 * @param animName the name of the animation
	 * @param numFrames number of textures in this animation
	 * @param frameDuration how long a single frame will be displayed in the animation
	 * @param pathToTexDir path to folder which contains the textures
	 * @param fileName part of the animation file format (see above)
	 * @throws IllegalArgumentException if an animation with the same <code>animName</code>
	 * has already been registered
	 * @see AnimationHandler#addAnimation(String, int, String, String)
	 */
	public void addAsDefaultAnimation(String animName, int numFrames, int frameDuration, String pathToTexDir, String fileName);
	
	/**
	 * Tries to load all textures of all registered animations from the file system.
	 * Before loading, no animation can be played.
	 * If this method fails, all animations that have been loaded to to the point of failure should
	 * be cleared, in order to allow retrying to load the animations.
	 * @return true if all animations have been loaded successfully
	 * @throws IllegalStateException if no default animation has been added
	 * @throws IllegalStateException if the animations have already been loaded
	 */
	public boolean loadAnimations();
	
	/**
	 * Starts playing the animation with the specified <code>animName</code>.
	 * Multiple animations are allowed to be played at the same time.
	 * In case multiple animations happen to run at the same time, the priority of each animation determines
	 * which animation is actually returned by <code>getCurrentAnimation()</code>, where an animation with a greater
	 * priority is displayed over an animation with a lower priority. An animation with equal priority will replace the old animation.
	 * A animation can be cyclic or not. A cyclic animation will be played as long as this method keeps playing
	 * said animation. A cyclic animation will stop playing if this method does not "keep alive" the cyclic
	 * animation every frame anymore.
	 * A non-cyclic animation does only need to be called once and will play until it runs out of frames.
	 * Calling this method if the non-cyclic animation has already started will reset the duration the animation
	 * but will not reset it so that the animation starts over again at the first frame. It would have the
	 * same effect as a cyclic animation apart from that a non-cyclic animation will play out after this method stops
	 * reseting the animation.
	 * <br><br>
	 * DISCLAIMER: as there is no real way known by me to get any useful information out of the Animation class,
	 * the duration for non-cyclic animations are just estimates and may differ from how long they would actually last.
	 * @param name name of the registered animation
	 * @param priority priority in regards to already playing animations
	 * @param cyclic whether the animation should be played infinitely (requires calling this method every frame)
	 * @throws IllegalStateException if no default animation was set
	 */
	public void playAnimation(String animName, int priority, boolean cyclic);
	
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
