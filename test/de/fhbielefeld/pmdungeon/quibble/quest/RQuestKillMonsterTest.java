package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Goblin;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import junit.framework.TestCase;

public class RQuestKillMonsterTest extends TestCase
{
	
	private Player p;
	private QuestKillMonster quest;
	Creature c;
	List<Creature> cList;
	
	@Before
	public void setUp() throws Exception
	{
		p = new Knight();
		this.quest = new QuestKillMonster("", 6, 0);
		this.p.addQuest(quest);
		cList = new ArrayList<Creature>();
		c = new Goblin();
		c.getCurrentStats().setStat(CreatureStatsAttribs.HEALTH, 0);
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
		if(quest.isCompleted())
		{
			fail("Test should not be completed here");
		}
		
		for(int i = 0; i < quest.getToKill(); i++)
		{
			p.fireEvent(new CreatureHitTargetPostEvent(Creature.EVENT_ID_HIT_TARGET_POST, p, c, DamageType.PHYSICAL,
				Double.MAX_VALUE));
		}
		if(!quest.isCompleted())
		{
			fail("Test should be Ok if counter is the same as toKill ");
		}
	}
	
}
