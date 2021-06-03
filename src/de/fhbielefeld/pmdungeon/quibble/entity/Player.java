package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerQuestsChangedEvent;
import de.fhbielefeld.pmdungeon.quibble.input.InputListener;
import de.fhbielefeld.pmdungeon.quibble.input.Key;
import de.fhbielefeld.pmdungeon.quibble.input.KeyList;
import de.fhbielefeld.pmdungeon.quibble.input.KeyMovement;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategy;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyAccept;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyActivateQuest;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyCloseChest;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyDecline;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyOpenChest;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyPickUpDrops;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategySelectItem;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyUseItem;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;

public abstract class Player extends Creature implements InputListener
{
	private boolean triggeredNextLevel;
	
	public static final int EVENT_ID_DUNGEON_LEVEL_CHANGED = EntityEvent.genEventID();
	
	private float controlMinX;
	private float controlMaxX;
	private float controlMinY;
	private float controlMaxY;
	
	private List<Quest> quests = new ArrayList<Quest>();
	
	private HashMap<String, InputStrategy> inputMap;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Player(float x, float y)
	{
		super(x, y);
		inputMap = fillInputMap();
	}
	
	/**
	 * Creates a player entity with a default position
	 */
	public Player()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public boolean isDisplayNameVisible()
	{
		return false;
	}
	
	@Override
	public void onMovement(KeyMovement key)
	{
		// This logic is to make a player stand still if keys of opposite directions are
		// pressed
		
		this.controlMinX = Math.min(this.controlMinX, key.getMovement().x);
		this.controlMaxX = Math.max(this.controlMaxX, key.getMovement().x);
		this.controlMinY = Math.min(this.controlMinY, key.getMovement().y);
		this.controlMaxY = Math.max(this.controlMaxY, key.getMovement().y);
	}
	
	@Override
	public void onEvent(Key key)
	{
		inputMap.get(key.getEvent()).handle();
	}
	
	/**
	 * This must be called to reset the <code>triggeredNextLevel</code> flag
	 * directly after the next level has been loaded.
	 */
	public void onNextLevelEntered()
	{
		// IMPORTANT never forget to set this or else all levels will be loaded so
		// quickly that you're at the end level immediately.
		this.triggeredNextLevel = false;
		this.fireEvent(new EntityEvent(Player.EVENT_ID_DUNGEON_LEVEL_CHANGED, this));
	}
	
	/**
	 * Whether the <code>triggeredNextLevel</code> flag is set. This is set when the
	 * player steps on the ladder that leads to the next level and will cause the
	 * next level to load.
	 * 
	 * @return the <code>triggeredNextLevel</code> flag
	 */
	public boolean triggeredNextLevel()
	{
		return this.triggeredNextLevel;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		if(this.level.getDungeon().getNextLevelTrigger() == this.level.getDungeon()
			.getTileAt((int)this.getPosition().x, (int)this.getPosition().y))
		{
			this.triggeredNextLevel = true;
		}
		
		float dirX = (this.controlMaxX + this.controlMinX) * 0.5F;
		float dirY = (this.controlMaxY + this.controlMinY) * 0.5F;
		
		if(dirX != 0.0D || dirY != 0.0D)
		{
			float angle = (float)Math.toDegrees(Math.atan2(dirY, dirX));
			
			this.walk(angle);
			if(angle > 90 || angle < -90)
			{
				this.setLookingDirection(LookingDirection.LEFT);
			}
			else if(angle < 90 && angle > -90)
			{
				this.setLookingDirection(LookingDirection.RIGHT);
			}
			// if it is exactly 90 or -90 do nothing
			// So that when you press up or down, the looking direction does not change
			
			LoggingHandler.logger.log(Level.FINE, "Movement input: " + angle + "deg");
		}
		
		Quest cQuest;
		for(int i = 0; i < quests.size(); i++)
		{
			cQuest = quests.get(i);
			if(cQuest.isCompleted())
			{
				cQuest.onReward(this);
				LoggingHandler.logger.log(Level.INFO, "The quest " + cQuest.getQuestName() + " was completed");
				this.quests.set(i, this.quests.get(this.quests.size() - 1));
				
				this.quests.remove(this.quests.size() - 1);
				i--;
				this.removeEntityEventHandler(cQuest);
				this.fireEvent(new PlayerQuestsChangedEvent(PlayerQuestsChangedEvent.EVENT_ID, this, cQuest));
			}
			
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateEnd()
	{
		super.updateEnd();
		
		// Clear the controls of this frame
		this.controlMinX = 0.0F;
		this.controlMaxX = 0.0F;
		this.controlMinY = 0.0F;
		this.controlMaxY = 0.0F;
	}
	
	/**
	 * Adds a quest to the player that the player can complete
	 * @param quest the quest to be added
	 */
	public void addQuest(Quest quest)
	{
		this.quests.add(quest);
		this.addEntityEventHandler(quest);
		quest.onAccept(this);
		this.fireEvent(new PlayerQuestsChangedEvent(PlayerQuestsChangedEvent.EVENT_ID, this, quest));
	}
	
	/**
	 * @return all quests that the player currently has accepted
	 */
	public List<Quest> getQuestList()
	{
		return this.quests;
	}
	
	private HashMap<String, InputStrategy> fillInputMap()
	{
		HashMap<String, InputStrategy> map = new HashMap<>();
		
		map.put(KeyList.PICK_UP_DROP, new InputStrategyPickUpDrops(this));
		
		map.put(KeyList.OPEN_CHEST, new InputStrategyOpenChest(this));
		
		map.put(KeyList.CLOSE_CHEST_INV, new InputStrategyCloseChest(this));
		
		map.put(KeyList.USE_ITEM, new InputStrategyUseItem(this));
		
		map.put(KeyList.INTERACT_QUEST, new InputStrategyActivateQuest(this));
		
		map.put(KeyList.ACCEPT, new InputStrategyAccept(this));
		
		map.put(KeyList.DECLINE, new InputStrategyDecline(this));
		
		//Will create so a List in the range of Inventory Slots
		for(int i = 0; i < 9; i++)
		{
			map.put(KeyList.SELECT_ITEM[i], new InputStrategySelectItem(this, i));
		}
		return map;
	}
	
}