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
	 * @param eventName the name of the registered input event
	 */
	public void notityListeners(KEY key);
	
	/*
	 * Called once per frame and retrieves the state of registered buttons and fires events accordingly.
	 */
	public KEY updateHandler();
	
	/**
	 * Registers a key event which is fired every time the specified <code>key</code> is pressed.
	 * Every key is assigned a name which will be passed to the listeners.
	 * If <code>justPressed</code> is <code>true</code> the event will only be
	 * triggered when the key was just pressed ("clicked") but will not be
	 * triggered when the key is held down.
	 * If a key event with the same name was already registered, an IllegalArgumentException is thrown.
	 * @param eventName name of the key event
	 * @param key the key that needs to be pressed
	 * @param justPressed whether this event fires as long as the button is held down or only when is was just pressed
	 * @throws IllegalArgumentException if a key event with the same name was already registered
	 */
	public void registerKeyEvent(KEY key);
	
	/**
	 * Registers a mouse button event which is fired every time the specified <code>button</code> is pressed.
	 * Every button is assigned a name which will be passed to the listeners.
	 * If <code>justPressed</code> is <code>true</code> the event will only be
	 * triggered when the button was just clicked  but will not be
	 * triggered when the button is held down.
	 * If a button event with the same name was already registered, an IllegalArgumentException is thrown.
	 * @param eventName name of the mouse button event
	 * @param button the button that needs to be pressed
	 * @param justPressed whether this event fires as long as the button is held down or only when is was just pressed
	 * @throws IllegalArgumentException if a button event with the same name was already registered
	 */
	public void registerMouseEvent(KEY key);
}
