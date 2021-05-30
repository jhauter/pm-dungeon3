package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;

public class DungeonInputHandler implements InputHandler
{
	
	private ArrayList<InputListener> listener = new ArrayList<>();
	
	private KeyList defaultKeyList = new KeyList();
	
	@Override
	public void addInputListener(InputListener listener)
	{
		if(this.listener.contains(listener))
		{
			IllegalArgumentException e = new IllegalArgumentException("this listener was already added");
			LoggingHandler.logger.log(Level.SEVERE, e.toString());
			throw e;
		}
		this.listener.add(listener);
	}
	
	@Override
	public void removeInputListener(InputListener listener)
	{
		this.listener.remove(listener);
	}
	
	@Override
	public void notifyMovement(Key key)
	{
		listener.forEach(l -> l.onMovement((KeyMovement)key));
	}
	
	@Override
	public void notifyEvent(Key key)
	{
		listener.forEach(l -> l.onEvent(key));
	}
	
	@Override
	public void updateHandler()
	{
		for(int i = 0; i < defaultKeyList.getKeyList().size(); i++)
		{
			
			// if the button is pressed and is a move Button
			// KeyMovement has Vector units thats needed for movement Calculation
			if(defaultKeyList.getKeyList().get(i).isPressed() && defaultKeyList.getKeyList().get(i).isMovementKey())
				notifyMovement(defaultKeyList.getKeyList().get(i));
			
			// if the button is pressed and a Simple Button which supposed to have a String
			// which is
			// Recognized as an event
			if(defaultKeyList.getKeyList().get(i).isPressed() && !defaultKeyList.getKeyList().get(i).isMovementKey())
				notifyEvent(defaultKeyList.getKeyList().get(i));
		}
	}
}
