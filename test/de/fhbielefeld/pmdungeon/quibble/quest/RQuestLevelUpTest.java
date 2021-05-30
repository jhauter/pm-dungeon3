package de.fhbielefeld.pmdungeon.quibble.quest;

import static org.junit.Assume.assumeTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import junit.framework.TestCase;

public class RQuestLevelUpTest extends TestCase
{
	public Player player;
	
	public QuestLevelUp quest;
	
	private static final int LEVEL_TO_REACH = 1;
	
	@Before
	public void setUp() throws Exception
	{
		this.player = new Knight();
		this.quest = new QuestLevelUp("Level Up", LEVEL_TO_REACH, 5); // Player currently on lv. 0
		this.player.addQuest(quest);
	}
	
	@After
	public void tearDown() throws Exception
	{
		this.player = null;
		this.quest = null;
	}
	
	@Test
	public void test()
	{
		this.player.rewardExp(10);
		assumeTrue("Not enough exp was given to test", this.player.getCurrentExpLevel() >= LEVEL_TO_REACH);
		if(!this.quest.isCompleted())
		{
			fail("Quest was not completed even though it should.");
		}
	}
	
}
