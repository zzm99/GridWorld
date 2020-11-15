import info.gridworld.grid.Grid;
import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;
import java.util.ArrayList;

/*
 * SparseBoundedGrid implemented with Linked list with the SparseGridNode designed in SparseGridNode.java
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E> 
{
	private SparseGridNode[] occArray;	// the element of the grid
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
		numRows = rows;
		numCols = cols;
		occArray = new SparseGridNode[rows];
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
		for (int r = 0; r < getNumRows() ; r++) {
			SparseGridNode p = occArray[r];
			while (p != null) {
				Location temp = new Location(r, p.getColumn());
				result.add(temp);
				p = p.getNext();
			}
		}
		return result;
	}

	// override the get method , return the location in the grid (E) 
	public E get(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		
		SparseGridNode p = occArray[loc.getRow()];
		while (p != null) {
			if (loc.getCol() == p.getColumn()) {
				return (E)p.getOccupant();
			}
			p = p.getNext();
		}
		return null;
	}

	// remove the old obj in loc if it exists, put the new obj in loc, return the old obj.
	public E put(Location loc, E obj) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}
		if (obj == null) {
			throw new IllegalArgumentException("NULL New Object");
		}
		
		// remove the old one
		E old = remove(loc);
		
		// put in the new one
		SparseGridNode p = occArray[loc.getRow()];
		occArray[loc.getRow()] = new SparseGridNode(obj, loc.getCol(), p); 
		
		// return the old one
		return old;
	}

	// remove the object in loc and return it.
	public E remove(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location InValid.");
		}

		// try to get the object in loc first
		E obj = get(loc);
		// the loc hasn't been occupied
		if (obj == null) {
			return null;
		}

		// try to remove the object second
		SparseGridNode p = occArray[loc.getRow()];
		if (p.getColumn() == loc.getCol()) {	// the row header is the loc  
			occArray[loc.getRow()] = p.getNext();
		}
		else {
			SparseGridNode q = p.getNext();
			while (q != null && q.getColumn() != loc.getCol()) {
				p = p.getNext();
				q = q.getNext();
			}
			// remove the object in loc
			if (q != null) {
				p.setNext(q.getNext());
			}
		}
		return obj;
	}
}
