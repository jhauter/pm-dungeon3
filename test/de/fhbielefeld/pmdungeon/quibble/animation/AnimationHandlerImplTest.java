package de.fhbielefeld.pmdungeon.quibble.animation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnimationHandlerImplTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}
	
	private AnimationHandlerImpl animationHandler;
	
	@Before
	public void setUp() throws Exception
	{
		this.animationHandler = new AnimationHandlerImpl();
	}
	
	@After
	public void tearDown() throws Exception
	{
		this.animationHandler = null;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDuplicateAnimation()
	{
		//Same animation name
		this.animationHandler.addAnimation("myAnim", 1, 0, 1, 1, "");
		this.animationHandler.addAnimation("myAnim", 1, 0, 1, 1, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNoFrames()
	{
		//numFrames = 0
		this.animationHandler.addAnimation("myAnim", 0, 0, 1, 1, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeFrames()
	{
		//numFrames < 0
		this.animationHandler.addAnimation("myAnim", -1, 0, 1, 1, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTooManyFrames()
	{
		//numFrames > rows * columns
		this.animationHandler.addAnimation("myAnim", 10, 0, 3, 3, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTooManyFrames2()
	{
		//numFrames + firstFrame > rows * columns
		this.animationHandler.addAnimation("myAnim", 8, 2, 0, 3, 3, "");
	}
}
