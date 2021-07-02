package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerQuestsChangedEvent;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;

public abstract class Player extends Creature
{
	private boolean triggeredNextLevel;
	
	public static final int EVENT_ID_DUNGEON_LEVEL_CHANGED = EntityEvent.genEventID();
	
	private float controlMinX;
	private float controlMaxX;
	private float controlMinY;
	private float controlMaxY;
	
	private List<Quest> quests = new ArrayList<Quest>();
	
	private String name;
	
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
	public boolean isDisplayNameVisible()
	{
		return true;
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
		this.level.getFogOfWarController().light(this.getX(), this.getY(), (float)this.getCurrentStats().getStat(CreatureStatsAttribs.FOW_SIGHT));
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
	
	public void influenceControlAxisMinMax(float x, float y)
	{
		this.controlMinX = Math.min(this.controlMinX, x);
		this.controlMaxX = Math.max(this.controlMaxX, x);
		this.controlMinY = Math.min(this.controlMinY, y);
		this.controlMaxY = Math.max(this.controlMaxY, y);
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
	
	/**
	 * @return the name of the player
	 */
	@Override
	public String getDisplayName()
	{
		return name;
	}
	
	/**
	 * Sets a new player name
	 * @param name the new name for the player
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	public abstract String getPortraitImagePath();
}