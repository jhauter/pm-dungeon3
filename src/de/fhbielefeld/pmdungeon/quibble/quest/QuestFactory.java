package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.RandomItemGenerator;

public class QuestFactory
{
	private static final Random QUEST_RNG = new Random();
	
	public static Quest getRandomQuest()
	{
		int val = QUEST_RNG.nextInt(3);
		Item randomItem = RandomItemGenerator.getInstance().generateRandomItem(DungeonStart.getDungeonMain().getLevelCount());
		return switch (val) {
			case 0 -> new QuestLevelUp("Level Up Quest", DungeonStart.getDungeonMain().getPlayer().getCurrentExpLevel() + 2, 20, randomItem);
			case 1 -> new QuestDungeonStage("Reach next Stage Quest", 20, randomItem);
			case 2 -> new QuestKillMonster("Kill Monster Quest", 5, 20, randomItem);
			
			default ->
			throw new IllegalArgumentException("Unexpected value: " + val);
		};
	}
}
