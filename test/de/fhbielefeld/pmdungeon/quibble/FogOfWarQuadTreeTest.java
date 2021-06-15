package de.fhbielefeld.pmdungeon.quibble;

import static org.junit.Assert.*;

import org.junit.Test;

import de.fhbielefeld.pmdungeon.quibble.FogOfWarQuadTree.Node;

public class FogOfWarQuadTreeTest
{
	@Test
	public void testNodeCanCreateChildren1()
	{
		Node node = new Node(0, 0, 1, 1, 0);
		assertEquals(false, node.canCreateChildren());
	}
	
	@Test
	public void testNodeCanCreateChildren2()
	{
		Node node = new Node(0, 0, 2, 1, 0);
		assertEquals(false, node.canCreateChildren());
	}
	
	@Test
	public void testNodeCanCreateChildren3()
	{
		Node node = new Node(0, 0, 1, 2, 0);
		assertEquals(false, node.canCreateChildren());
	}
	
	@Test
	public void testNodeCanCreateChildren4()
	{
		Node node = new Node(0, 0, 2, 2, 0);
		assertEquals(true, node.canCreateChildren());
	}
	
	@Test
	public void testNodeCreateChildrenBounds1()
	{
		Node node = new Node(0, 0, 4, 4, 0);
		node.createChildren();
		Node ne = node.getNode(FogOfWarQuadTree.NODE_NE);
		Node nw = node.getNode(FogOfWarQuadTree.NODE_NW);
		Node sw = node.getNode(FogOfWarQuadTree.NODE_SW);
		Node se = node.getNode(FogOfWarQuadTree.NODE_SE);
		if(!(ne.getMinX() == 2 && ne.getMinY() == 2 && ne.getMaxX() == 4 && ne.getMaxY() == 4))
		{
			fail("child of region ne has incorrect bounds");
		}
		if(!(nw.getMinX() == 0 && nw.getMinY() == 2 && nw.getMaxX() == 2 && nw.getMaxY() == 4))
		{
			fail("child of region nw has incorrect bounds");
		}
		if(!(sw.getMinX() == 0 && sw.getMinY() == 0 && sw.getMaxX() == 2 && sw.getMaxY() == 2))
		{
			fail("child of region sw has incorrect bounds");
		}
		if(!(se.getMinX() == 2 && se.getMinY() == 0 && se.getMaxX() == 4 && se.getMaxY() == 2))
		{
			fail("child of region se has incorrect bounds");
		}
	}
	
	@Test
	public void testNodeCreateChildrenBounds2()
	{
		Node node = new Node(8, 8, 16, 16, 0);
		node.createChildren();
		Node ne = node.getNode(FogOfWarQuadTree.NODE_NE);
		Node nw = node.getNode(FogOfWarQuadTree.NODE_NW);
		Node sw = node.getNode(FogOfWarQuadTree.NODE_SW);
		Node se = node.getNode(FogOfWarQuadTree.NODE_SE);
		if(!(ne.getMinX() == 12 && ne.getMinY() == 12 && ne.getMaxX() == 16 && ne.getMaxY() == 16))
		{
			fail("child of region ne has incorrect bounds");
		}
		if(!(nw.getMinX() == 8 && nw.getMinY() == 12 && nw.getMaxX() == 12 && nw.getMaxY() == 16))
		{
			fail("child of region nw has incorrect bounds");
		}
		if(!(sw.getMinX() == 8 && sw.getMinY() == 8 && sw.getMaxX() == 12 && sw.getMaxY() == 12))
		{
			fail("child of region sw has incorrect bounds");
		}
		if(!(se.getMinX() == 12 && se.getMinY() == 8 && se.getMaxX() == 16 && se.getMaxY() == 12))
		{
			fail("child of region se has incorrect bounds");
		}
	}
}
