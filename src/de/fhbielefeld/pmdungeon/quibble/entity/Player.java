package de.fhbielefeld.pmdungeon.quibble.entity;

import de.fhbielefeld.pmdungeon.quibble.input.InputListener;

public abstract class Player extends Creature implements InputListener
{
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Player(float x, float y)
	{
		super(x, y);
	}
	
	/**
	 * Creates a player entity with a default position
	 */
	public Player()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public void onInputRecieved(KEY key) {
		commandMap().get(key).handleKey();
	}
	
	private interface Handle{
		void handleKey();
	}
	
	  private final Handle up = () ->          setPosition(getPosition().x, getPosition().y += getInitWalkingSpeed());
	  private final Handle upRight = () ->     setPosition(getPosition().x += getInitWalkingSpeed(), getPosition().y += getInitWalkingSpeed());
	  private final Handle right = () ->       setPosition(getPosition().x += getInitWalkingSpeed(), getPosition().y);
	  private final Handle downRight = () ->   setPosition(getPosition().x += getInitWalkingSpeed(), getPosition().y -= getInitWalkingSpeed());
	  private final Handle down = () ->        setPosition(getPosition().x, getPosition().y -= getInitWalkingSpeed());
	  private final Handle downLeft = () ->    setPosition(getPosition().x -= getInitWalkingSpeed(), getPosition().y -= getInitWalkingSpeed());
	  private final Handle left = () ->        setPosition(getPosition().x -= getInitWalkingSpeed(), getPosition().y);
	  private final Handle upLeft = () ->      setPosition(getPosition().x -= getInitWalkingSpeed(), getPosition().y += getInitWalkingSpeed()); 
	  private final Handle noKey = () ->	   setPosition(getPosition().x, getPosition().y);
		
	
	private HashMap<KEY, Handle> commandMap(){
		HashMap<KEY, Handle> map = new HashMap<>();
		map.put(KEY.UP, up);
		map.put(KEY.UP_RIGHT, upRight);
		map.put(KEY.RIGHT, right);
		map.put(KEY.DOWN_RIGHT, downRight);
		map.put(KEY.DOWN, down);
		map.put(KEY.DOWN_LEFT, downLeft);
		map.put(KEY.LEFT, left);
		map.put(KEY.UP_LEFT, upLeft);
		map.put(KEY.NO_KEY, noKey);
		return map;
	}
}