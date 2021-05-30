package de.fhbielefeld.pmdungeon.quibble.quest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import junit.framework.TestCase;

public class RQuestDungeonStageTest extends TestCase
{
	
	private Player p;
	private QuestDungeonStage quest;
	
	@Before
	public void setUp() throws Exception
	{
		p = new Knight();
		this.quest = new QuestDungeonStage("", 0);
		this.p.addQuest(quest);
	}
	
	@After
	public void tearDown() throws Exception
	{
		this.p = null;
		this.quest = null;
	}
	
	@Test
	public void test()
	{
		this.p.fireEvent(new EntityEvent(Player.EVENT_ID_DUNGEON_LEVEL_CHANGED, p));
		if(!quest.isCompleted())
		{
			fail("Quest was completed even it should not");
		}
	}
	
}
