package de.fhbielefeld.pmdungeon.quibble.input;

public enum KEY
{
	
	UP("Up", 2, false, 90.0F),
	UP_RIGHT("UpRight", 2, false, 45.0F),
	RIGHT("Right", 2, false, 0.0F),
	DOWN_RIGHT("DownRight", 2, false, 315.0F),
	DOWN("Down", 2, false, 270.0F),
	DOWN_LEFT("DownLeft", 2, false, 225.0F),
	LEFT("Left", 2, false, 180.0F),
	UP_LEFT("UpLeft", 2, false, 135.0F),
	NO_KEY("noKey", 3, false, 0.0F);
	
	String keyDirection;
	int priority;
	boolean justPressed;
	float angle;
	
	KEY(String title, int prio, boolean justPressed, float angle)
	{
		this.keyDirection = title;
		this.priority = prio;
		this.justPressed = justPressed;
		this.angle = angle;
	}
	
	public float getAngle()
	{
		return this.angle;
	}
}
