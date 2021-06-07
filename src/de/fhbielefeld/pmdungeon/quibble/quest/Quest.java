package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public abstract class Quest implements EntityEventHandler
{
	public final static String QUEST_TEXTURE_PATH = "assets/textures/quest/";
	public final static String QUEST_REACHED = "You will gain: ";
	
	private boolean isCompleted;
	
	private final String questName;
	
	private final int rewardExp;
	
	/**
	 * Creates a quest object that can track the player's progress on an objective.
	 * @param questName the display name of the quest
	 * @param player the player that should have the quest
	 * @param rewardExp the exp that are rewarded when the quest is completed
	 */
	public Quest(String questName, int rewardExp)
	{
		if(rewardExp < 0)
		{
			throw new IllegalArgumentException("rewardExp cannot be negative");
		}
		this.questName = questName;
		this.rewardExp = rewardExp;
	}
	
	/**
	 * This is called when a player accepts the quest.
	 * @param player the player that accepted the quest
	 */
	public void onAccept(Player player)
	{
		
	}
	
	/**
	 * 
	 * @return the task or objective that the player has to complete as user friendly string
	 */
	public abstract String getTask();
	
	/**
	 * 
	 * @return the progress of the quest as a user fiendly string
	 */
	public abstract String getProgressText();
	
	/**
	 * @return the quest reward as a user friendly string
	 */
	public abstract String getRewardText();
	
	/**
	 * @return name of the quest
	 */
	public final String getQuestName()
	{
		return this.questName;
	}
	
	/**
	 * Executed when the quest has been completed.
	 * @param the player that completed the quest
	 */
	public void onReward(Player player)
	{
		if(this.rewardExp > 0)
		{
			player.rewardExp(this.rewardExp);
		}
	}
	
	/**
	 * @return whether the quest has been completed and is marked for removal
	 */
	public boolean isCompleted()
	{
		return isCompleted;
	}
	
	/**
	 * Marks this quest for removal.
	 * @param isCompleted whether this quest should be removed
	 */
	public void setCompleted(boolean isCompleted)
	{
		this.isCompleted = isCompleted;
	}
	
	/**
	 * @return the amount of exp that the player gets when the quest is completed
	 */
	public int getRewardExp()
	{
		return this.rewardExp;
	}
	
	/**
	 * @return the name of the icon file in the "assets/textures/quest/" folder without the ".png" extension
	 */
	public String getIconFile()
	{
		//default icon; can be overridden
		return "gray_flag";
	}
	
	public static String makeRewardString(int rewardExp, Item ... rewardItems)
	{
		StringBuilder b = new StringBuilder();
		if(rewardExp > 0)
		{
			b.append("+ " + rewardExp + " xp");
			if(rewardItems.length > 0)
			{
				b.append(", ");
			}
		}
		for(int i = 0; i < rewardItems.length; ++i)
		{
			b.append(rewardItems[i].getDisplayName());
			if(i < rewardItems.length - 1)
			{
				b.append(", ");
			}
		}
		if(b.isEmpty())
		{
			b.append("Nothing");
		}
		return b.toString();
	}
}
