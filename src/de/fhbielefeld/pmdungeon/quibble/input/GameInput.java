package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategy;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyAccept;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyActivateQuest;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyCloseChest;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyDecline;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyOpenChest;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyPickUpDrops;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyPlayerController;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategySelectItem;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyUseItem;

public class GameInput implements InputProcessor
{
	private ArrayList<InputEntry> inputEntries;
	
	private ArrayList<Integer> pressedKeys;

	private static final int KEY_JUST_PRESSED = 0;
	private static final int KEY_PRESSED = 1;
	private static final int MOUSE_CLICKED = 2;
	
	public GameInput()
	{
		this.pressedKeys = new ArrayList<>();
		this.inputEntries = new ArrayList<>();
		this.setupInput();
	}
	
	public void setupInput()
	{
		//============ ENTER NEW INPUT HERE ============
		
		this.inputEntries.add(new InputEntry(Input.Keys.J, new InputStrategyAccept(), KEY_JUST_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.E, new InputStrategyActivateQuest(), KEY_JUST_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.ESCAPE, new InputStrategyCloseChest(), KEY_JUST_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.N, new InputStrategyDecline(), KEY_JUST_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.E, new InputStrategyOpenChest(), KEY_JUST_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.SPACE, new InputStrategyPickUpDrops(), KEY_JUST_PRESSED));

		this.inputEntries.add(new InputEntry(Input.Keys.A, new InputStrategyPlayerController(-1.0F, 0.0F), KEY_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.D, new InputStrategyPlayerController(1.0F, 0.0F), KEY_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.W, new InputStrategyPlayerController(0.0F, 1.0F), KEY_PRESSED));
		this.inputEntries.add(new InputEntry(Input.Keys.S, new InputStrategyPlayerController(0.0F, -1.0F), KEY_PRESSED));
		
		this.inputEntries.add(new InputEntry(Input.Buttons.LEFT, new InputStrategyUseItem(), MOUSE_CLICKED));
		
		for(int i = 0; i < 9; ++i)
		{
			this.inputEntries.add(new InputEntry(Input.Keys.NUM_1 + i, new InputStrategySelectItem(i), KEY_JUST_PRESSED));
		}
	}
	
	private List<InputEntry> getMatchingEntries(int triggerID, int triggerEvent)
	{
		int i, j;
		List<InputEntry> matching = new ArrayList<>();
		InputEntry entry;
		for(i = 0; i < this.inputEntries.size(); ++i)
		{
			entry = this.inputEntries.get(i);
			if(entry.triggerID != triggerID)
			{
				continue;
			}
			for(j = 0; j < entry.triggerEvents.length; ++j)
			{
				if(entry.triggerEvents[j] == triggerEvent)
				{
					matching.add(entry);
				}
			}
		}
		return matching;
	}
	
	private void handleMatchingEntries(int triggerID, int triggerEvent)
	{
		final List<InputEntry> matching = this.getMatchingEntries(triggerID, triggerEvent);
		matching.forEach(e -> e.action.handle(DungeonStart.getDungeonMain().getPlayer()));
	}
	
	public void update()
	{
		for(int i = 0; i < this.pressedKeys.size(); ++i)
		{
			this.handleMatchingEntries(this.pressedKeys.get(i), KEY_PRESSED);
		}
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		this.pressedKeys.add(keycode);
		
		this.handleMatchingEntries(keycode, KEY_JUST_PRESSED);
		
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		this.pressedKeys.remove((Integer)keycode);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		this.pressedKeys.add(button);
		
		this.handleMatchingEntries(button, MOUSE_CLICKED);
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	private static class InputEntry
	{
		private final InputStrategy action;
		
		//keycode or mouse button
		private final int triggerID;
		
		//methods that "activate" the key action
		private final int[] triggerEvents;
		
		private InputEntry(int triggerID, InputStrategy action, int ...triggerEvents)
		{
			this.triggerID = triggerID;
			this.action = action;
			this.triggerEvents = triggerEvents;
		}
	}
}
