package de.fhbielefeld.pmdungeon.quibble.input;

import com.badlogic.gdx.Gdx;

public class Key {

	private final String event;

	private int key;

	/**
	 * A key that was pressed. Contains a string representing the event to be
	 * triggered by it. <br>
	 * 
	 * <h3>This should not be a Mouse Button. Use <code> KeyMouseButton </code>
	 * instead
	 * <h3>
	 * 
	 * 
	 * @param event the event that should be called if triggered
	 * @param key   the int which represent the key or Button to find in
	 *              <code>com.badlogic.gdx.Input.Keys<code>
	 * 
	 */
	public Key(String event, int key) {
		this.event = event;
		this.key = key;
	}

	/**
	 * 
	 * @return if a key is pressed
	 */
	protected boolean isPressed() {
		return Gdx.input.isKeyPressed(key);
	}

	/**
	 * Identifies the key or button as a normal key or a movement key. Movement keys
	 * include a vector in addition to the event.
	 * 
	 * @return false if it's not a movement Key
	 */
	protected boolean isMovementKey() {
		return false;
	}

	/**
	 * To Set afterwards a new Key
	 * 
	 * @param key the new Key which should be used
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * 
	 * @return String which represent the event that should start if pressed
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * 
	 * @return the certain Key
	 */
	public int getKey() {
		return key;
	}

}
