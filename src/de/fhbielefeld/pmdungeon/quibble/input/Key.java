package de.fhbielefeld.pmdungeon.quibble.input;

public enum Key {

	UP(2, false, 90.0F), UP_RIGHT(2, false, 45.0F), RIGHT(2, false, 0.0F), DOWN_RIGHT(2, false, 315.0F),
	DOWN(2, false, 270.0F), DOWN_LEFT(2, false, 225.0F), LEFT(2, false, 180.0F), UP_LEFT(2, false, 135.0F),
	NO_KEY(3, false, 0.0F);

	int priority;
	boolean justPressed;
	float angle;

	/**
	 * Specific buttons constant. Must be assigned to an event and then serves as
	 * identification.
	 * 
	 * @param priority    A value to determine which key is treated with priority.
	 * @param justPressed Returns a true if the button was not pressed for a long
	 *                    time but only activated for a short time.
	 * @param angleThe    angle at which the player is standing to the button.
	 *                    Starts with the right direction button at 0 and runs
	 *                    counter-clockwise. Values from 0 to 360
	 */
	Key(int priority, boolean justPressed, float angle) {
		this.priority = priority;
		this.justPressed = justPressed;
		this.angle = angle;
	}

	/**
	 * angle at which the player is standing to the button. Starts with the right
	 * direction button at 0 and runs counter-clockwise. Values from 0 to 360
	 * 
	 * @return Returns the angle represented by the specified button. <br>
	 *         </br>
	 *         For Example: <br>
	 *         KEY.Right.getAngle() returns float value : 0 </br>
	 *         
	 *         KEY.Left.getAngle() returns float value: 180 </br>
	 */
	public float getAngle() {
		return this.angle;
	}
}
