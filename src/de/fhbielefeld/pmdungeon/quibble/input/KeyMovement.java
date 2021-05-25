package de.fhbielefeld.pmdungeon.quibble.input;

import com.badlogic.gdx.math.Vector2;

/**
 * In inherit from Key. Also has a vector to calculate the motion
 *
 */
public class KeyMovement extends Key {

	private Vector2 v2;

	/**
	 * A key or a mouse button that was clicked. Contains a string representing the
	 * event to be triggered by it. The KeyMovement are also contains a Vector which
	 * is used to calculate the movement Most Times the event name isn't used by
	 * this buttons
	 * 
	 * @param event the event that should be called if triggered
	 * @param key   the int which represent the key or Button to find in
	 *              <code>com.badlogic.gdx.Input.Keys<code>
	 * @param axisX the x - axis a button represent
	 * @param axisY the y - axis a button represent
	 */
	public KeyMovement(String event, int key, float axisX, float axisY) {
		super(event, key);
		v2 = new Vector2(axisX, axisY);
	}

	@Override
	protected boolean isMovementKey() {
		return true;
	}

	/**
	 * @return a Vector of size 2
	 */
	public Vector2 getMovement() {
		return v2;
	}

}
