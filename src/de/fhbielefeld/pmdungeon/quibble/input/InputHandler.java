package de.fhbielefeld.pmdungeon.quibble.input;

public interface InputHandler
{
	/**
	 * Adds an <code>InputListener</code> that will be notified when user input happens.
	 * @param listener the listener that handles input
	 * @throws IllegalArgumentException if this listener was already added
	 */
	public void addInputListener(InputListener listener);
	
	/**
	 * Removes an already registered <code>InputListener</code>. If this <code>InputListener</code> is not registered
	 * this method does nothing.
	 * @param listener the listener to be removed
	 */
	public void removeInputListener(InputListener listener);
	
	/**
	 * Notifies all listeners about an input event. This should only be called internally.
	 * @param key the button that was pressed by the player.
	 */
	public void notifyListeners(DungeonInput key);
	
	/*
	 * Called once per frame and retrieves the state of registered buttons and fires events accordingly.
	 */
	public void updateHandler();
	
}
