package de.fhbielefeld.pmdungeon.quibble.input;

import com.badlogic.gdx.Gdx;

public class KeyMouseButton extends Key
{
	
	/**
	 * /** A Mouse Button that was pressed. Contains a string representing the event
	 * to be triggered by it. <br>
	 * 
	 * 
	 * @param event the event that should be called if triggered
	 * @param key   the int which represent the key or Button to find in
	 *              <code>com.badlogic.gdx.Input.Keys<code>
	 * 
	 */
	public KeyMouseButton(String event, int key)
	{
		super(event, key);
	}
	
	@Override
	protected boolean isPressed()
	{
		return Gdx.input.isButtonJustPressed(getKey());
	}
	
}
