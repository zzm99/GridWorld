/*
 *  implement the linked list in using raw list nodes.
 */
public class SparseGridNode
{
 	private Object occupant;
	private int col;
 	private SparseGridNode next;

 	public SparseGridNode(Object ooc, int c, SparseGridNode n) {
 		occupant = ooc;
 		col = c;
 		next = n;
 	}
 	/*
 	 * get and set methods of variable occupant.
 	 */
	public Object getOccupant() {
		return occupant;
	}
	public void setOccupant(Object ooc) {
		occupant = ooc;
	}

	/*
 	 * get methods of variable col.
 	 */
	public int getColumn() {
		return col;
	}
	
	/*
 	 * get and set methods of variable next.
 	 */
	public SparseGridNode getNext() {
		return next;
	}
	public void setNext(SparseGridNode n){
		next = n;
	}
} 