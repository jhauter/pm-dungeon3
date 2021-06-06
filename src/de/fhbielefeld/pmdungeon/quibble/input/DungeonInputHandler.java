package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class DungeonInputHandler implements InputProcessor
{
	private KeyList keyList;
	
	private Map<String, List<InputStrategy>> eventStrategyMap;
	
	private ArrayList<Integer> pressedKeys;
	
	public DungeonInputHandler()
	{
		this.pressedKeys = new ArrayList<>();
		this.eventStrategyMap = new HashMap<>();
		this.keyList = new KeyList();
		this.setupEventMap();
	}
	
	public void setupEventMap()
	{
		//============ ENTER NEW INPUT HERE ============
		
		this.mapEventToStrategy("up", new InputStrategyPlayerController(0.0F, 1.0F));
		this.mapEventToStrategy("down", new InputStrategyPlayerController(0.0F, -1.0F));
		this.mapEventToStrategy("left", new InputStrategyPlayerController(-1.0F, 0.0F));
		this.mapEventToStrategy("right", new InputStrategyPlayerController(1.0F, 0.0F));
		this.mapEventToStrategy("use_item", new InputStrategyUseItem());
		this.mapEventToStrategy("interact", new InputStrategyOpenChest());
		this.mapEventToStrategy("interact", new InputStrategyActivateQuest());
		this.mapEventToStrategy("quest_accept", new InputStrategyAccept());
		this.mapEventToStrategy("quest_decline", new InputStrategyDecline());
		this.mapEventToStrategy("pick_up_drop", new InputStrategyPickUpDrops());
		this.mapEventToStrategy("close", new InputStrategyCloseChest());
		
		for(int i = 0; i < 9; ++i)
		{
			this.mapEventToStrategy("select_item_" + i, new InputStrategySelectItem(i));
		}
	}
	
	private void mapEventToStrategy(String event, InputStrategy strategy)
	{
		List<InputStrategy> s = this.eventStrategyMap.get(event);
		if(s == null)
		{
			s = new ArrayList<>();
			this.eventStrategyMap.put(event, s);
		}
		s.add(strategy);
	}
	
	private List<Key> getMatchingEntries(int triggerID, int triggerEvent)
	{
		List<Key> matching = new ArrayList<>();
		Key entry;
		for(int i = 0; i < this.keyList.getKeyList().size(); ++i)
		{
			entry = this.keyList.getKeyList().get(i);
			if(entry.getKey() != triggerID)
			{
				continue;
			}
			if(entry.getMode() == triggerEvent)
			{
				matching.add(entry);
			}
		}
		return matching;
	}
	
	private void handleMatchingEntries(int triggerID, int triggerEvent)
	{
		final List<Key> matching = this.getMatchingEntries(triggerID, triggerEvent);
		for(Key k : matching)
		{
			final List<InputStrategy> s = this.eventStrategyMap.get(k.getEvent());
			if(s != null)
			{
				s.forEach(e -> e.handle(DungeonStart.getDungeonMain().getPlayer()));
			}
		}
	}
	
	public void update()
	{
		for(int i = 0; i < this.pressedKeys.size(); ++i)
		{
			this.handleMatchingEntries(this.pressedKeys.get(i), Key.KEY_PRESSED);
		}
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		this.pressedKeys.add(keycode);
		
		this.handleMatchingEntries(keycode, Key.KEY_JUST_PRESSED);
		
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
		
		this.handleMatchingEntries(button, Key.MOUSE_CLICKED);
		
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
}
