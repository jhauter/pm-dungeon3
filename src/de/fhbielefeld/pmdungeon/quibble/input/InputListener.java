package de.fhbielefeld.pmdungeon.quibble.input;

@FunctionalInterface
public interface InputListener
{
	/**
	 * @param key the button that was pressed by the player.
	 */
	public void onInputRecieved(Key key);
}
