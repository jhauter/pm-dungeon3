
public interface InputHandler
{
	/**
	 * Optional init stuff.
	 */
	public void init();
	
	/**
	 * Registers a key with an associated event name.
	 * Every time the key is pressed, an event with the assigned name should be fired to
	 * the registered listeners.
	 * @param key the key
	 * @param eventName name of the event
	 */
	public void registerKeyEvent(int key, String eventName);
	
	/**
	 * Adds a listener to be notified when a key is pressed.
	 */
	public void addListener();
	
	public void removeListener();
	
	public void notityListeners();
}
