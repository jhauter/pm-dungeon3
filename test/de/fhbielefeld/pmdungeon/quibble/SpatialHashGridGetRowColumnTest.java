package de.fhbielefeld.pmdungeon.quibble;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SpatialHashGridGetRowColumnTest
{
	private static SpatialHashGrid<?> grid;
	
	@Parameter(0)
	public float inputPosY;
	
	@Parameter(1)
	public Object expectedRow;
	
	@Parameter(2)
	public float inputPosX;
	
	@Parameter(3)
	public Object expectedColumn;
	
	@Parameters
	public static Collection<Object[]> parameters()
	{
		// {<input row>, <expected output row>, <input column>, <expected output column>}
		return Arrays.asList(new Object[][]{
			{-5.0F, -2, -5.0F, -2},
			{-4.0F, -1, -4.0F, -1},
			{-1.0F, -1, -1.0F, -1},
			{0.0F, 0, 0.0F, 0},
			{1.0F, 0, 1.0F, 0},
			{4.0F, 0, 4.0F, 0},
			{5.0F, 1, 5.0F, 1},
			{399.0F, 79, 199.0F, 39},
			{400.0F, 80, 200.0F, 40}
		});
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		grid = new SpatialHashGrid<>(80, 40, 200.0F, 400.0F);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		grid = null;
	}
	
	@Test
	public void testGetRow()
	{
		assertEquals(this.expectedRow, grid.getRow(this.inputPosY));
	}
	
	@Test
	public void testGetColumn()
	{
		assertEquals(this.expectedColumn, grid.getColumn(this.inputPosX));
	}
}
