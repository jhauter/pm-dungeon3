package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class QuestFactory
{
	private static final Random QUEST_RNG = new Random();
	
	public static Quest getRandomQuest()
	{
		int val = QUEST_RNG.nextInt(3);
		return switch (val) {
			case 0 -> new QuestLevelUp("Level Up Quest", DungeonStart.getDungeonMain().getPlayer().getCurrentExpLevel() + 2, 20, Item.BAG_DEFAULT);
			case 1 -> new QuestDungeonStage("Reach next Stage Quest", 20, Item.BLUE_MAGIC_STAFF);
			case 2 -> new QuestKillMonster("Kill Monster Quest", 5, 20, Item.POTION_YELLOW_BIG);
			
			default ->
			throw new IllegalArgumentException("Unexpected value: " + val);
		};
	}
}
