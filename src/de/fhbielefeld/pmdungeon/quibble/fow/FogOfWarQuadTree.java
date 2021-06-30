package de.fhbielefeld.pmdungeon.quibble.fow;

import java.util.function.Consumer;

public class FogOfWarQuadTree
{
	protected static final int NODE_NE = 0;
	protected static final int NODE_NW = 1;
	protected static final int NODE_SW = 2;
	protected static final int NODE_SE = 3;
	
	public static class Node
	{
		private float fogValue;
		private final int minX;
		private final int minY;
		private final int maxX;
		private final int maxY;
		private Node[] children;
		
		protected Node(int minX, int minY, int maxX, int maxY, float fogValue)
		{
			this.minX = minX;
			this.minY = minY;
			this.maxX = maxX;
			this.maxY = maxY;
			this.fogValue = fogValue;
		}
		
		protected Node getNode(int region)
		{
			return this.children[region];
		}
		
		protected boolean hasChildren()
		{
			return this.children != null;
		}
		
		protected void removeChildren()
		{
			this.children = null;
		}
		
		protected boolean canCreateChildren()
		{
			return this.maxX - this.minX > 1 && this.maxY - this.minY > 1;
		}
		
		protected void createChildren(float ne, float nw, float sw, float se)
		{
			final int cX = this.getCenterX();
			final int cY = this.getCenterY();
			this.children = new Node[4];
			this.children[NODE_NE] = new Node(cX, cY, this.maxX, this.maxY, ne);
			this.children[NODE_NW] = new Node(this.minX, cY, cX, this.maxY, nw);
			this.children[NODE_SW] = new Node(this.minX, this.minY, cX, cY, sw);
			this.children[NODE_SE] = new Node(cX, this.minY, this.maxX, cY, se);
		}
		
		protected void createChildren()
		{
			this.createChildren(this.fogValue, this.fogValue, this.fogValue, this.fogValue);
		}
		
		protected void createChildren(int node, float fogValue)
		{
			this.createChildren();
			this.children[node].fogValue = fogValue;
		}
		
		protected int getCenterX()
		{
			return (this.maxX + this.minX) / 2;
		}
		
		protected int getCenterY()
		{
			return (this.maxY + this.minY) / 2;
		}
		
		public int getMinX()
		{
			return this.minX;
		}
		
		public int getMaxX()
		{
			return this.maxX;
		}
		
		public int getMinY()
		{
			return this.minY;
		}
		
		public int getMaxY()
		{
			return this.maxY;
		}
		
		public float getFogValue()
		{
			return this.fogValue;
		}
		
		protected int getRegionOf(int x, int y)
		{
			final int cX = this.getCenterX();
			final int cY = this.getCenterY();
			if(x >= cX)
			{
				if(y >= cY)
				{
					return NODE_NE;
				}
				else
				{
					return NODE_SE;
				}
			}
			else
			{
				if(y >= cY)
				{
					return NODE_NW;
				}
				else
				{
					return NODE_SW;
				}
			}
		}
	}
	
	private Node rootNode;
	
	/**
	 * Creates a quad tree that is used by the <code>FogOfWarController</code>.
	 * @param minX minimum x-value of the area that this quad tree covers
	 * @param minY minimum y-value of the area that this quad tree covers
	 * @param maxX maximum x-value of the area that this quad tree covers
	 * @param maxY maximum y-value of the area that this quad tree covers
	 * @param fogValue the starting fog value of the root area
	 * @throws IllegalArgumentException if the area of the quad tree is <= 0
	 * @throws IllegalArgumentException if the area of the quad tree is not square
	 * @throws IllegalArgumentException if the width of the area of the quad tree is not a power of two
	 */
	public FogOfWarQuadTree(int minX, int minY, int maxX, int maxY, float fogValue)
	{
		final int width = maxX - minX;
		final int height = maxY - minY;
		if(width <= 0 || height <= 0)
		{
			throw new IllegalArgumentException("quad tree dimensions cannot be <= 0");
		}
		if(width != height)
		{
			throw new IllegalArgumentException("quad tree dimensions must be square");
		}
		if((width & (width - 1)) != 0)
		{
			throw new IllegalArgumentException("quad tree dimensions must power of 2");
		}
		this.rootNode = new Node(minX, minY, maxX, maxY, fogValue);
	}
	
	/**
	 * Removes all children of this quad tree and sets the fog value of the root area.
	 * @param clearFogValue  new fog value of the root area
	 */
	public void clear(float clearFogValue)
	{
		this.rootNode.removeChildren();
		this.rootNode.fogValue = clearFogValue;
	}
	
	/**
	 * Sets a fog value in this quad tree.
	 * @param x x-position
	 * @param y y-position
	 * @param fogValue fog value at the specified position
	 */
	public void set(int x, int y, float fogValue)
	{
		this.set(this.rootNode, x, y, fogValue);
	}
	
	private void set(Node node, int x, int y, float fogValue)
	{
		final int region = node.getRegionOf(x, y);
		if(node.hasChildren())
		{
			this.set(node.getNode(region), x, y, fogValue);
		}
		else if(node.canCreateChildren())
		{
			node.createChildren();
			this.set(node.getNode(region), x, y, fogValue);
		}
		else
		{
			node.fogValue = fogValue;
		}
	}
	
	/**
	 * @param x x-position
	 * @param y y-position
	 * @return fog value at the specified position
	 */
	public float get(int x, int y)
	{
		return this.get(this.rootNode, x, y);
	}
	
	private float get(Node node, int x, int y)
	{
		if(node.hasChildren())
		{
			return this.get(node.getNode(node.getRegionOf(x, y)), x, y);
		}
		return node.fogValue;
	}
	
	/**
	 * Executes the <code>Consumer</code> argument for each node.
	 * @param traverser consumer to execute
	 */
	public void traversePreorder(Consumer<Node> traverser)
	{
		this.traversePreorder(this.rootNode, traverser);
	}
	
	private void traversePreorder(Node node, Consumer<Node> traverser)
	{
		traverser.accept(node);
		if(node.hasChildren())
		{
			for(int i = 0; i < node.children.length; ++i)
			{
				this.traversePreorder(node.children[i], traverser);
			}
		}
	}
}
