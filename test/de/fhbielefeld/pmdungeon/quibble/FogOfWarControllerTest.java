package de.fhbielefeld.pmdungeon.quibble;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FogOfWarControllerTest
{
	private FogOfWarController fow;

	@Test
	public void testGetSectorsX1()
	{
		this.fow = new FogOfWarController(8, 1, 8, 2, 0.0F);
		assertEquals(1, this.fow.getSectorsX());
	}
	
	@Test
	public void testGetSectorsX2()
	{
		this.fow = new FogOfWarController(8, 1, 9, 2, 0.0F);
		assertEquals(2, this.fow.getSectorsX());
	}
	
	@Test
	public void testGetSectorsY1()
	{
		this.fow = new FogOfWarController(8, 1, 5, 8, 0.0F);
		assertEquals(1, this.fow.getSectorsY());
	}
	
	@Test
	public void testGetFogOfWarWidth()
	{
		this.fow = new FogOfWarController(8, 32, 9, 5, 0.0F);
		assertEquals(64, this.fow.getFogOfWarWidth());
	}
	
	@Test
	public void testGetFogOfWarHeight()
	{
		this.fow = new FogOfWarController(8, 32, 5, 9, 0.0F);
		assertEquals(64, this.fow.getFogOfWarHeight());
	}
	
	@Test
	public void testGetSectorsY2()
	{
		this.fow = new FogOfWarController(8, 1, 5, 9, 0.0F);
		assertEquals(2, this.fow.getSectorsY());
	}
	
	@Test
	public void testGetFogX1()
	{
		this.fow = new FogOfWarController(8, 32, 4, 8, 0.0F);
		assertEquals(8, this.fow.getFogX(2));
	}
	
	@Test
	public void testGetFogX2()
	{
		this.fow = new FogOfWarController(8, 32, 4, 8, 0.0F);
		assertEquals(7, this.fow.getFogX(1.9F));
	}
	
	@Test
	public void testGetFogY1()
	{
		this.fow = new FogOfWarController(8, 32, 4, 8, 0.0F);
		assertEquals(12, this.fow.getFogY(3));
	}
	
	@Test
	public void testGetFogY2()
	{
		this.fow = new FogOfWarController(8, 32, 4, 8, 0.0F);
		assertEquals(11, this.fow.getFogY(2.9F));
	}
	
	@Test
	public void testGetFogTileIndex1()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(12, this.fow.getFogTileIndex(0, 0, 4));
	}
	
	@Test
	public void testGetFogTileIndex2()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(15, this.fow.getFogTileIndex(7, 0, 4));
	}
	
	@Test
	public void testGetFogTileIndex3()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(8, this.fow.getFogTileIndex(0, 1, 4));
	}
	
	@Test
	public void testGetFogTileIndex4()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(11, this.fow.getFogTileIndex(7, 1, 4));
	}
	
	@Test
	public void testGetFogTileIndex5()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(8, this.fow.getFogTileIndex(8, 1, 4));
	}
	
	@Test
	public void testGetFogTileIndex6()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(8, this.fow.getFogTileIndex(8, 9, 4));
	}
	
	@Test
	public void testGetFogTileIndex7()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(3, this.fow.getFogTileIndex(3, 3, 4));
	}
	
	@Test
	public void testGetFogTileIndex8()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(3, this.fow.getFogTileIndex(-1, -1, 4));
	}
	
	@Test
	public void testGetFogTileIndex9()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(15, this.fow.getFogTileIndex(-1, -4, 4));
	}
	
	@Test
	public void testGetFogTileIndex10()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(0, this.fow.getFogTileIndex(-4, -1, 4));
	}
	
	@Test
	public void testGetFogTileIndex11()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(12, this.fow.getFogTileIndex(-4, -4, 4));
	}
	
	@Test
	public void testGetFogTileIndex12()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(3, this.fow.getFogTileIndex(-5, -5, 4));
	}
	
	@Test
	public void testGetFogTileIndex13()
	{
		this.fow = new FogOfWarController(8, 32, 1, 1, 0.0F);
		assertEquals(5, this.fow.getFogTileIndex(-7, -6, 4));
	}
}
