import info.gridworld.grid.*;
import java.util.ArrayList;
import java.util.*;

/*
 * UnBoundedGrid implemented with array
 */
public class UnBoundedGrid2<E> extends AbstractGrid<E> {
	private Object[][] occArray;	// the element of the grid
	private int dimension;

	// constructor with dimension = 16 default
	public UnBoundedGrid2() {
		dimension = 16;
		occArray = new Object[dimension][dimension];
	}

	// get methods
	public int getNumRows() {
		return dimension;
	}
	public int getNumCols() {
		return dimension;
	}

	// isValid method of the grid: return if the location is in the size of grid.
	public boolean isValid(Location loc) {
		return loc.getCol() >= 0 && loc.getRow() >= 0;
	}

	// override the getOccupiedLocations method , return the location which is occupied.
	public ArrayList<Location> getOccupiedLocations() {
		ArrayList<Location> result = new ArrayList<Location>();
		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				Location loc = new Location(r,c);
				if (get(loc) != null) {
					result.add(loc);
				}
			}
		}
		return result;
	}

	// override the get method , return the location in the grid (E) 
	public E get(Location loc) {
		// check if it is valid
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		// check if it is out of the grid
		if (loc.getRow() >= dimension || loc.getCol() >= dimension) {
			return null;
		}
		return (E)occArray[loc.getRow()][loc.getCol()];
	}

	// remove the old obj in loc if it exists, put the new obj in loc, return the old obj.
	public E put(Location loc, E obj) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		if (obj == null) {
			throw new IllegalArgumentException("NULL New Object");
		}
		// if it is out of the grid, resize
		if (loc.getRow() >= dimension || loc.getCol() >= dimension) {
			resize(loc);
		}
		
		E old = get(loc);
		occArray [loc.getRow()][loc.getCol()] = obj;
		return old;
	}

	// remove the object in loc and return it.
	public E remove(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		if (loc.getRow() >= dimension || loc.getCol() >= dimension) {
			return null;
		}
		
		E old = get(loc);
		occArray[loc.getRow()][loc.getCol()] = null;
		return old;
	}

	// resize method is used to extend the grid array
	private void resize(Location loc) {
		int size = dimension;
		// one step is double
		while (loc.getRow() >= size || loc.getCol() >= size) {
			size *= 2;
		}
		Object[][] tmp = new Object[size][size];

		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				tmp[r][c] = occArray[r][c];
			}
		}

		occArray = tmp;
		dimension = size;
	}
}