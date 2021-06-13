package de.fhbielefeld.pmdungeon.quibble;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FogOfWarControllerTest
{
	private FogOfWarController fow;

	@Test
	public void testGetFogOfWarWidth1()
	{
		this.fow = new FogOfWarController(0.25F, 5, 2);
		assertEquals(20, this.fow.getFogOfWarWidth());
	}
	
	@Test
	public void testGetFogOfWarWidth2()
	{
		this.fow = new FogOfWarController(0.3F, 5, 2);
		assertEquals(17, this.fow.getFogOfWarWidth());
	}
	
	@Test
	public void testGetFogOfWarHeight1()
	{
		this.fow = new FogOfWarController(0.125F, 5, 2);
		assertEquals(16, this.fow.getFogOfWarHeight());
	}
	
	@Test
	public void testGetFogOfWarHeight2()
	{
		this.fow = new FogOfWarController(0.6F, 5, 2);
		assertEquals(4, this.fow.getFogOfWarHeight());
	}
	
	@Test
	public void testGetFogX1()
	{
		this.fow = new FogOfWarController(0.25F, 4, 8);
		assertEquals(8, this.fow.getFogX(2));
	}
	
	@Test
	public void testGetFogX2()
	{
		this.fow = new FogOfWarController(0.25F, 4, 8);
		assertEquals(7, this.fow.getFogX(1.9F));
	}
	
	@Test
	public void testGetFogY1()
	{
		this.fow = new FogOfWarController(0.25F, 4, 8);
		assertEquals(12, this.fow.getFogY(3));
	}
	
	@Test
	public void testGetFogY2()
	{
		this.fow = new FogOfWarController(0.25F, 4, 8);
		assertEquals(11, this.fow.getFogY(2.9F));
	}
	
	@Test
	public void testGetFogTileIndex1()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(12, this.fow.getFogTileIndex(0, 0, 4));
	}
	
	@Test
	public void testGetFogTileIndex2()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(15, this.fow.getFogTileIndex(7, 0, 4));
	}
	
	@Test
	public void testGetFogTileIndex3()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(8, this.fow.getFogTileIndex(0, 1, 4));
	}
	
	@Test
	public void testGetFogTileIndex4()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(11, this.fow.getFogTileIndex(7, 1, 4));
	}
	
	@Test
	public void testGetFogTileIndex5()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(8, this.fow.getFogTileIndex(8, 1, 4));
	}
	
	@Test
	public void testGetFogTileIndex6()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(8, this.fow.getFogTileIndex(8, 9, 4));
	}
	
	@Test
	public void testGetFogTileIndex7()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(3, this.fow.getFogTileIndex(3, 3, 4));
	}
	
	@Test
	public void testGetFogTileIndex8()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(3, this.fow.getFogTileIndex(-1, -1, 4));
	}
	
	@Test
	public void testGetFogTileIndex9()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(15, this.fow.getFogTileIndex(-1, -4, 4));
	}
	
	@Test
	public void testGetFogTileIndex10()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(0, this.fow.getFogTileIndex(-4, -1, 4));
	}
	
	@Test
	public void testGetFogTileIndex11()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(12, this.fow.getFogTileIndex(-4, -4, 4));
	}
	
	@Test
	public void testGetFogTileIndex12()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(3, this.fow.getFogTileIndex(-5, -5, 4));
	}
	
	@Test
	public void testGetFogTileIndex13()
	{
		this.fow = new FogOfWarController(0.25F, 1, 1);
		assertEquals(5, this.fow.getFogTileIndex(-7, -6, 4));
	}
}
