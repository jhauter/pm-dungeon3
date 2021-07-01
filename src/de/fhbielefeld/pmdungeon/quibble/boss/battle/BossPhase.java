package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BossPhase
{
	protected BossBattle battle;
	
	private int index;
	public boolean active = false;
	
	private List<BossAction> currentActions;
	
	public BossPhase(BossBattle battle)
	{
		this.battle = battle;
	}
	
	/**
	 * Called once after switche to this phase
	 * @param battle Reference to the battle
	 *
	 */
	public void init()
	{
		currentActions = new ArrayList<>();
		index = 0;
		var action = getActions().get(0);
		action.onActionBegin(battle);
		currentActions.add(action);
		this.active = true;
	}
	
	/**
	 * Adds the next action in the action pool to the active actions. Wraps to the first action
	 * if actions have been exhausted
	 */
	
	public void nextAction()
	{
		int maxActions = getActions().size();
		
		if((index + 1) < maxActions)
		{
			index++;
		}
		else
		{
			index = 0;
		}
		//        System.out.println(index);
		
		var newAction = getActions().get(index);
		if(!currentActions.contains(newAction))
		{
			currentActions.add(newAction);
			newAction.onActionBegin(battle);
		}
		else
		{
			//            System.out.println("ALREADY CONTAINS");
		}
		
	}
	
	/**
	 * @param action Action to be removed from active actions
	 */
	public void removeAction(BossAction action)
	{
		currentActions.remove(action);
	}
	
	/*
	    Called each frame while BossPhase is active
	 */
	public void run()
	{
		if(active)
		{
			if(currentActions.isEmpty())
			{
				nextAction();
			}
			for(int i = 0; i < currentActions.size(); ++i)
			{
				currentActions.get(i).execute();
			}
		}
	}
	
	/*
	    Called on end of fight or if switching to another phase
	 */
	public void cleanStage()
	{
		var actions = getActions();
		
		for(var a : actions)
		{
			a.onActionEnd();
		}
	}
	
	protected abstract List<BossAction> getActions();
}
