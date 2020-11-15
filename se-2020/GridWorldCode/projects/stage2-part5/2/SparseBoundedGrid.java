import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/*
 * SparseBoundedGrid implemented with HashMap
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E> 
{
	private Map<Location, E> occMap;	// the element of the grid
	private int numRows;				// row of the grid
	private int numCols;				// column of the grid

	// constructor with rows and cols of the grid
	public SparseBoundedGrid(int rows, int cols) {
		// rows must be more than zero
		if (rows <= 0) {
			throw new IllegalArgumentException("rows <= 0");
		}
		// cols must be more than zero
		if (cols <= 0) {
			throw new IllegalArgumentException("cols <= 0");
		}
		occMap = new HashMap<Location, E>();
		numRows = rows;
		numCols = cols;
	}

	// get method of variable numRows
	public int getNumRows() {
		return numRows;
	}
	// get method of variable numCols
	public int getNumCols() {
		return numCols;
	}

	// isValid method of the grid: return if the location is in the size of grid.
	public boolean isValid(Location loc) {
		int row = loc.getRow(), col = loc.getCol();
		return ((row>=0) && (row<getNumRows()) && (col>=0) && (col<getNumCols()));
	}

	// override the getOccupiedLocations method , return the location which is occupied.
	public ArrayList<Location> getOccupiedLocations() {
		ArrayList<Location> result = new ArrayList<Location>();
		for (Location loc : occMap.keySet()) {
			result.add(loc);
		}
		return result;
	}

	// override the get method , return the location in the grid (E) 
	public E get(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		return occMap.get(loc);
	}

	// remove the old obj in loc if it exists, put the new obj in loc, return the old obj.
	public E put(Location loc, E obj) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		if (obj == null) {
			throw new IllegalArgumentException("NULL New Object");
		}
		return occMap.put(loc, obj);
	}

	// remove the object in loc and return it.
	public E remove(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		return occMap.remove(loc);
	}
}

