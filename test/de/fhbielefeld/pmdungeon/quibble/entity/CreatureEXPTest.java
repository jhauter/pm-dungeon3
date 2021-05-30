package de.fhbielefeld.pmdungeon.quibble.entity;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CreatureEXPTest
{
	private static Creature creature;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		creature = new Goblin();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		creature = null;
	}
	
	@Parameters
	public static Collection<Object> parameters()
	{
		Object[] param = new Object[100];
		for(int i = 0; i < param.length; ++i)
		{
			param[i] = i;
		}
		return Arrays.asList(param);
	}
	
	@Parameter(0)
	public int totalExp;
	
	@Before
	public void setUp() throws Exception
	{
		creature.setTotalExp(totalExp);
	}
	
	@After
	public void tearDown() throws Exception
	{
		
	}
	
	@Test
	public void testExpFunctions()
	{
		int level = creature.expLevelFunction(creature.getTotalExp());
		int minExp = creature.totalExpFunction(level);
		assertTrue("totalExpFunction for lv." + level + " => " + minExp + "xp; Creature is lv."
			+ level + " with " + creature.getTotalExp() + "xp",
			creature.getTotalExp() >= minExp);
	}
}
