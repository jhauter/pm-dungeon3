package de.fhbielefeld.pmdungeon.quibble.input;

import com.badlogic.gdx.Gdx;

public class KeyJustPressed extends Key
{
	
	/**
	 * A key that was just pressed. Contains a string representing the event to be
	 * triggered by it, but only once a time. <br>
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
	public KeyJustPressed(String event, int key)
	{
		super(event, key);
	}
	
	@Override
	protected boolean isPressed()
	{
		return Gdx.input.isKeyJustPressed(getKey());
	}
	
}
