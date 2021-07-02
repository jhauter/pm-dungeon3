package de.fhbielefeld.pmdungeon.quibble.quest;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public abstract class QuestItemReward extends Quest
{
	private final Item[] rewardItems;
	
	public QuestItemReward(String questName, int rewardExp, Item... rewardItems)
	{
		super(questName, rewardExp);
		this.rewardItems = rewardItems;
	}
	
	public final Item[] getRewardItems()
	{
		return this.rewardItems;
	}
	
	@Override
	public void onReward(Player player)
	{
		super.onReward(player);
		for(Item i : this.rewardItems)
		{
			player.addItem(i);
		}
	}
	
	@Override
	public String getRewardText()
	{
		return Quest.makeRewardString(this.getRewardExp(), this.rewardItems);
	}
	
}
