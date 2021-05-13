package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;

public interface IQuestHandler {
	
	/**
	 * Adds an <code>InputListener</code> that will be notified when user input happens.
	 * @param listener the listener that handles input
	 * @throws IllegalArgumentException if this listener was already added
	 */
	public void addInputListener(OnRewardListener listener);
	
	/**
	 * Removes an already registered <code>InputListener</code>. If this <code>InputListener</code> is not registered
	 * this method does nothing.
	 * @param listener the listener to be removed
	 */
	public void removeInputListener(OnRewardListener listener);
	
	/**
	 * Notifies all listeners about an input event. This should only be called internally.
	 * @param c the creature which will be effected by a reward
	 */
	public void notifyListeners(Creature c);
	
	/*
	 * Called once per frame and retrieves the state of registered buttons and fires events accordingly.
	 */
	public void updateHandler();

}
