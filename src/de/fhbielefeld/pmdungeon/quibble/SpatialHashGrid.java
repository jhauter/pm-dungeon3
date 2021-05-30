package de.fhbielefeld.pmdungeon.quibble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;

public class SpatialHashGrid<T>
{
	/**
	 * The handle class represents an object that has been inserted into the spatial hash grid.
	 * A handle is returned when an object is inserted into the spatial hash grid with the
	 * {@link SpatialHashGrid#add(BoundingBox, Object)} method.
	 * The handle can then be used to update or remove the inserted object, or to track nearby objects.
	 * @author Andreas
	 *
	 * @param <T> the type of object that has been inserted into the SpatialHashGrid
	 */
	public static final class Handle<T>
	{
		private BoundingBox boundingBox;
		private final T obj;
		private List<Vector2> cells;
		
		private Handle(BoundingBox bb, T obj)
		{
			this.boundingBox = bb;
			this.obj = obj;
		}
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + boundingBox.hashCode();
			result = prime * result + obj.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj) return true;
			if(obj == null) return false;
			if(this.getClass() != obj.getClass()) return false;
			if(!this.boundingBox.equals(((Handle<?>)obj).boundingBox)) return false;
			if(!this.obj.equals(((Handle<?>)obj).obj)) return false;
			return true;
		}
		
		/**
		 * Creates an array containing the cell positions of this handle.
		 * @return an array of cell positions
		 */
		public Vector2[] cellsArray()
		{
			return this.cells.toArray(new Vector2[0]);
		}
	}
	
	private final int rows;
	private final int columns;
	private final float width;
	private final float height;
	
	private final HashMap<Vector2, Set<Handle<T>>> cells;
	
	/**
	 * Creates a spatial hash grid, which a specified number of cells, width and height.
	 * This grid stores with which cells an added object with a bounding box overlaps.
	 * A spatial hash grid allows to retrieve all objects that overlap the same cells as another object,
	 * to make collision detection more efficient.
	 * To achieve this, a spatial hash grid uses a hash table that maps every cell position to
	 * a cell containing the objects that overlap with the cell.
	 * @param rows the number of rows in the grid
	 * @param columns the number of column in the grid
	 * @param width the width of the grid
	 * @param height the height of the grid
	 */
	public SpatialHashGrid(int rows, int columns, float width, float height)
	{
		this.rows = rows;
		this.columns = columns;
		this.width = width;
		this.height = height;
		this.cells = new HashMap<>();
	}
	
	/**
	 * Calculates in which column the given x-position is.
	 * This will also return columns for out of bounds positions.
	 * @param posX the x-position
	 * @return the column in which the given x-position is.
	 */
	public int getColumn(float posX)
	{
		int column = (int)(posX / this.width * this.columns);
		if(posX < 0)
		{
			--column;
		}
		return column;
	}
	
	/**
	 * Calculates in which row the given y-position is.
	 * This will also return rows for out of bounds positions.
	 * @param posY the y-position
	 * @return the row in which the given y-position is.
	 */
	public int getRow(float posY)
	{
		int row = (int)(posY / this.height * this.rows);
		if(posY < 0)
		{
			--row;
		}
		return row;
	}
	
	/**
	 * Checks whether the specified cell coordinate is out of bounds or not.
	 * @param row a row of the grid
	 * @param column a column of the grid
	 * @return <code>true</code> if the cell coordinate is not out of bounds
	 */
	public boolean inBounds(int row, int column)
	{
		return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
	}
	
	/**
	 * Creates a list of the coordinates of all cells that overlap with the specified bounding box.
	 * @param b the bounding box to be checked for cell overlaps
	 * @return the list of coordinates of cells overlapping with the bounding box
	 */
	public List<Vector2> getCells(BoundingBox b)
	{
		List<Vector2> intersectingCells = new ArrayList<>();
		final int minCol = this.getColumn((int)b.x);
		final int maxCol = this.getColumn((int)(b.x + b.width));
		final int minRow = this.getRow((int)b.y);
		final int maxRow = this.getRow((int)(b.y + b.height));
		int x, y;
		for(x = minCol; x <= maxCol; ++x)
		{
			for(y = minRow; y <= maxRow; ++y)
			{
				if(inBounds(y, x))
				{
					intersectingCells.add(new Vector2(x, y));
				}
			}
		}
		return intersectingCells;
	}
	
	/**
	 * Adds the object <code>obj</code> to all cells that overlap with the specified bounding box.
	 * @param bb the bounding box that belongs to the object
	 * @param obj the object to add to cells
	 * @return a handle that can be used to update, remove or track the object
	 */
	public Handle<T> add(BoundingBox bb, T obj)
	{
		Handle<T> handle = new Handle<T>(bb, obj);
		this.addHandle(handle);
		return handle;
	}
	
	private void addHandle(Handle<T> handle)
	{
		List<Vector2> intersectingCells = this.getCells(handle.boundingBox);
		handle.cells = intersectingCells;
		
		Set<Handle<T>> currentCell;
		for(int i = 0; i < intersectingCells.size(); ++i)
		{
			currentCell = this.cells.get(intersectingCells.get(i));
			if(currentCell == null)
			{
				currentCell = new HashSet<>();
				this.cells.put(intersectingCells.get(i), currentCell);
			}
			currentCell.add(handle);
		}
	}
	
	/**
	 * Creates a set of all objects whose bounding boxes overlap with the same cells as the
	 * handle's bounding box overlaps.
	 * @param handle the handle of the object to be checked for nearby objects
	 * @return a set of all objects in the same cells as the handle's object.
	 */
	public Set<T> nearby(Handle<T> handle)
	{
		return this.nearby(handle.boundingBox);
	}
	
	/**
	 * Creates a set of all objects whose bounding boxes overlap with the same cells as the
	 * specified bounding box overlaps.
	 * @param bb the bounding box used to check for nearby objects
	 * @return a set of all objects in the same cells as the bounding box.
	 */
	public Set<T> nearby(BoundingBox bb)
	{
		Set<T> nearbyObjects = new HashSet<>();
		List<Vector2> intersectingCells = this.getCells(bb);
		Set<Handle<T>> currentCell;
		for(int i = 0; i < intersectingCells.size(); ++i)
		{
			currentCell = this.cells.get(intersectingCells.get(i));
			if(currentCell == null)
			{
				continue;
			}
			for(Handle<T> c : currentCell)
			{
				nearbyObjects.add(c.obj);
			}
		}
		return nearbyObjects;
	}
	
	/**
	 * Removes the handle's object from all cells that it is currently in.
	 * @param handle the handle of the object that should be removed
	 */
	public void remove(Handle<T> handle)
	{
		for(int i = 0; i < handle.cells.size(); ++i)
		{
			this.cells.get(handle.cells.get(i)).remove(handle);
		}
	}
	
	/**
	 * Updates which cells contain the handle's object so that only
	 * cells that overlap with the specified bounding box will contain the handle's object.
	 * @param handle the handle of the object that should be updated
	 * @param bb the bounding box that determines what cells will contain the object
	 */
	public void update(Handle<T> handle, BoundingBox bb)
	{
		this.remove(handle);
		handle.boundingBox = bb;
		this.addHandle(handle);
	}
	
	/**
	 * @return the number of rows in this grid
	 */
	public int getRows()
	{
		return this.rows;
	}
	
	/**
	 * @return the number of columns in this grid
	 */
	public int getColumns()
	{
		return this.columns;
	}
	
	/**
	 * @return the width of this grid
	 */
	public float getWidth()
	{
		return this.width;
	}
	
	/**
	 * @return the height of this grid
	 */
	public float getHeight()
	{
		return this.height;
	}
}
