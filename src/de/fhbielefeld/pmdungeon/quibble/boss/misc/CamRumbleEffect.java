package de.fhbielefeld.pmdungeon.quibble.boss.misc;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class CamRumbleEffect
{
	private static float time = 0;
	private static float currentTime = 0;
	private static float power = 0;
	private static float currentPower = 0;
	private static Random rand;
	private static Vector2 pos = new Vector2();
	
	/**
	 * Initializes the shake effect
	 * @param _power Shakepower
	 * @param _length Lenght of shake effect (ticks)
	 */
	public static void shake(float _power, float _length)
	{
		rand = new Random();
		power = _power;
		time = _length;
		currentTime = 0;
	}
	
	/**
	 * Updates the position of the shake effect based on power and length. Users of this function should check
	 * if time is still left when calling this
	 * @return Position of the rumble
	 */
	public static Vector2 update()
	{
		if(currentTime <= time)
		{
			currentPower = power * ((time - currentTime) / time);
			
			pos.x = (rand.nextFloat() - 0.5f) * 2 * currentPower;
			pos.y = (rand.nextFloat() - 0.5f) * 2 * currentPower;
			
			currentTime++;
		}
		else
		{
			time = 0;
		}
		return pos;
	}
	
	/**
	 * @return Amount of ticks left to rumble
	 */
	public static float getTimeLeft()
	{
		return time;
	}
	
	/**
	 * @return Current position of the effect
	 */
	public static Vector2 getPos()
	{
		return pos;
	}
}
