package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;

public class QuestDummy extends Entity
{
	private final StringBuilder sb = new StringBuilder();
	private final StringBuffer stars = new StringBuffer();
	private final int logLenght = 97;
	
	private Quest quest;
	
	private boolean shouldDespawn;
	
	/**
	 * Will create a visible Quest Entity
	 * 
	 * @param quest the quest which should be visible
	 * @param x     x float of the Position
	 * @param y     y float of the Position
	 */
	public QuestDummy(Quest quest, float x, float y)
	{
		super(x, y);
		this.quest = quest;
		this.animationHandler.addAsDefaultAnimation("", 1, 999, 1, 1,
			Quest.QUEST_TEXTURE_PATH + quest.getIconPath() + ".png");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDespawn()
	{
		return shouldDespawn;
	}
	
	/**
	 * Shows the quest description in the console
	 */
	public void showQuestDescription()
	{
		LoggingHandler.logger.info(msg());
	}
	
	/**
	 * Called when the player either accepts or declines the quest
	 * @param b <code>true</code> if the quest was accepted
	 * @param player the player that made the decision
	 */
	public void onPlayerDecision(boolean b, Player player)
	{
		if(b)
		{
			player.addQuest(quest);
		}
		this.shouldDespawn = true;
	}
	
	/**
	 * 
	 * @return a log message for a easy reading
	 */
	private String msg()
	{
		String msg_1 = "!You have activated a Quest Flag!";
		String msg_2 = " This Quest is a: " + quest.getQuestName() + " ";
		String msg_3 = " You have to : " + quest.getTask() + " ";
		// String msg_4 is a method below
		String msg_5 = " If you would like to Accept press J, else N ";
		
		// 1. line
		msg_stars(getSize(msg_1));
		sb.append("\n" + createMsg(msg_1));
		deleteStarsInSB();
		// 2. line
		msg_stars(getSize(msg_2));
		sb.append(createMsg(msg_2));
		deleteStarsInSB();
		// 3. line
		msg_stars(getSize(msg_3));
		sb.append(createMsg(msg_3));
		deleteStarsInSB();
		// 4. line
		msg_stars(getSize(msg_4()));
		sb.append(createMsg(msg_4()));
		deleteStarsInSB();
		// 5. line
		msg_stars(getSize(msg_5));
		sb.append(createMsg(msg_5));
		deleteStarsInSB();
		// 6. line
		msg_stars(this.logLenght);
		sb.append(stars.toString());
		deleteStarsInSB();
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @return String with get an ItemReward if a quest provides one
	 */
	private String msg_4()
	{
		return " Reward: " + quest.getRewardText() + " ";
	}
	
	private String createMsg(String msg)
	{
		return stars.toString() + msg + stars.toString() + "\n";
	}
	
	private int getSize(String msg)
	{
		return (logLenght - msg.length()) / 2;
	}
	
	private void msg_stars(int length)
	{
		for(int i = 0; i < length; i++)
		{
			stars.append("*");
		}
	}
	
	private void deleteStarsInSB()
	{
		stars.delete(0, stars.length());
	}
	
}
