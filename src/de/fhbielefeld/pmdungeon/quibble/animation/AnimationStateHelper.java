package de.fhbielefeld.pmdungeon.quibble.animation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles switching between animations automatically based on boolean switches.
 * @author Andreas
 */
public class AnimationStateHelper
{
	private static class SwitchData
	{
		private final String animation;
		private final int priority;
		
		private boolean isOn;
		
		private SwitchData(String animation, int priority)
		{
			super();
			this.animation = animation;
			this.priority = priority;
		}
	}
	
	private AnimationHandler animHandlerRef;
	
	private Map<Integer, SwitchData> animationSwitches;
	
	/**
	 * Creates an <code>AnimationStateHelper</code> with a reference to an <code>AnimationHandler</code>
	 * The reference is used to play the animations. Usually this is the <code>AnimationHandler</code>
	 * of an <code>Entity</code>.
	 * @param animHandlerRef the <code>AnimationHandler</code> to use for animations
	 */
	public AnimationStateHelper(AnimationHandler animHandlerRef)
	{
		this.animHandlerRef = animHandlerRef;
		this.animationSwitches = new HashMap<Integer, SwitchData>();
	}
	
	/**
	 * Adds a switch that can be turned on and off.
	 * If a switch is turned on then the corresponding animation is played.
	 * If more then one switch is active then the priority of the switch determines which switch's
	 * animation is played. 
	 * @param index ID of the switch
	 * @param animation animation name which must match the animation name of the <code>AnimationHelper</code>
	 * @param priority priority if multiple switches are active
	 */
	public void addSwitch(int index, String animation, int priority)
	{
		this.animationSwitches.put(index, new SwitchData(animation, priority));
	}
	
	/**
	 * Turns the switch with the specified <code>index</code> on or of
	 * @param index the switch ID
	 * @param on new switch state
	 * @see #addSwitch(int, String, int)
	 */
	public void setSwitchValue(int index, boolean on)
	{
		this.animationSwitches.get(index).isOn = on;
	}
	
	/**
	 * Must be called every frame to calculate which animation to play based on the switches' states.
	 */
	public void update()
	{
		Collection<SwitchData> switches = this.animationSwitches.values();
		String currentAnimation = null;
		int currentPriority = -1;
		for(SwitchData data : switches)
		{
			if(data.isOn && data.priority > currentPriority)
			{
				currentPriority = data.priority;
				currentAnimation = data.animation;
			}
		}
		if(currentAnimation != null)
		{
			this.animHandlerRef.playAnimation(currentAnimation, currentPriority, true);
		}
	}
}
