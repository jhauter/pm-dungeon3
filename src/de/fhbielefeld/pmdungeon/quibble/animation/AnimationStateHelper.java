package de.fhbielefeld.pmdungeon.quibble.animation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
	
	public AnimationStateHelper(AnimationHandler animHandlerRef)
	{
		this.animHandlerRef = animHandlerRef;
		this.animationSwitches = new HashMap<Integer, SwitchData>();
	}
	
	public void addSwitch(int index, String animation, int priority)
	{
		this.animationSwitches.put(index, new SwitchData(animation, priority));
	}
	
	public void setSwitchValue(int index, boolean on)
	{
		this.animationSwitches.get(index).isOn = on;
	}
	
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
