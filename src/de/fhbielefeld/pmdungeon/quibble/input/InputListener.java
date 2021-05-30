package de.fhbielefeld.pmdungeon.quibble.input;

public interface InputListener
{
	/**
	 * Should be a KeyBoard Key
	 * 
	 * @param key the button that was pressed by the player. KeyMoveMent carries a
	 *            own Vector which is necessary to calculate the movement
	 */
	public void onMovement(KeyMovement key);
	
	/**
	 * 
	 * @param key that was pressed, contains a String to identify the certain event
	 */
	public void onEvent(Key key);
}
