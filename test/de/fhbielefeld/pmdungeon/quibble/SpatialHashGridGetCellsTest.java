package de.fhbielefeld.pmdungeon.quibble;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;

@RunWith(Parameterized.class)
public class SpatialHashGridGetCellsTest
{
	private static SpatialHashGrid<?> grid;
	
	@Parameter(0)
	public BoundingBox inputBB;
	
	@Parameter(1)
	public Vector2[] expectedCells;
	
	@Parameters
	public static Collection<Object[]> parameters()
	{
		return Arrays.asList(new Object[][]{
			{
				new BoundingBox(0, 0, 1, 1),
				new Vector2[]{
					new Vector2(0, 0)
				}
			},
			{
				new BoundingBox(-2, -2, 1, 1),
				new Vector2[]{}
			},
			{
				new BoundingBox(0, 0, 4.99F, 4.99F),
				new Vector2[]{
					new Vector2(0, 0)
				}
			},
			{
				new BoundingBox(0, 0, 5, 5),
				new Vector2[]{
					new Vector2(0, 0),
					new Vector2(0, 1),
					new Vector2(1, 0),
					new Vector2(1, 1),
				}
			},
			{
				new BoundingBox(0, 0, 6, 6),
				new Vector2[]{
					new Vector2(0, 0),
					new Vector2(0, 1),
					new Vector2(1, 0),
					new Vector2(1, 1),
				}
			}
		});
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		grid = new SpatialHashGrid<>(6, 6, 30.0F, 30.0F);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		grid = null;
	}
	
	@Test
	public void testGetCells()
	{
		assertArrayEquals(this.expectedCells, grid.getCells(inputBB).toArray());
	}
}
