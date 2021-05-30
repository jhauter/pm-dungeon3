package de.fhbielefeld.pmdungeon.quibble.entity;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreatureEXPValueRangeTest
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
	
	@Test
	public void testTotalExpZero()
	{
		creature.setTotalExp(0);
		assertTrue("total exp of 0 must result in level 1", creature.getCurrentExpLevel() == 1);
	}
	
	@Test
	public void testTotalExpNegative()
	{
		creature.setTotalExp(-1);
		assertTrue("negatve total exp must result in level 1", creature.getCurrentExpLevel() == 1);
	}
	
	@Test
	public void testTotalExpFuncZero()
	{
		assertTrue("total exp of 0 must result in level 1", creature.expLevelFunction(0) == 1);
	}
	
	@Test
	public void testTotalExpFuncNegative()
	{
		assertTrue("negatve total exp must result in level 1", creature.expLevelFunction(-1) == 1);
	}
}
