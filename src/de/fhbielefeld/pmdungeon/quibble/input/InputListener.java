package de.fhbielefeld.pmdungeon.quibble.input;

@FunctionalInterface
public interface InputListener
{
	/**
	 * This is called every time a user input is received with the registered <code>eventName</code>
	 * @param key The specified button that was pressed by the player.
	 */
	public void onInputRecieved(KEY eventName);
}
