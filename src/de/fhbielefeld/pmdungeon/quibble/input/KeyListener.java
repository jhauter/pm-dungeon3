package de.fhbielefeld.pmdungeon.quibble.input;


@FunctionalInterface
public interface InputListener
{
	/**
	 * This is called every time a user input is received with the registered <code>eventName</code>
	 * @param eventName name of the input event that was assigned upon registration
	 */
	public void onInputRecieved(KEY key);
	
}
