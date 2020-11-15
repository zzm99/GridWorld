import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

/**
 * A QuickCrab act like a critter
 */
public class QuickCrab extends CrabCritter
{
    public QuickCrab()
    {
        setColor(Color.RED); // default color
    }

    public ArrayList<Location> getMoveLocations() {
    	ArrayList<Location> locs = new ArrayList<Location>();
    	Grid g = getGrid(); // get cur grid
    	Location loc = getLocation(); // get cur location
    	
        // judge left and leftleft
    	Location left = loc.getAdjacentLocation(getDirection() + Location.LEFT);
    	if (g.isValid(left) && g.get(left)==null) {
    		Location leftleft = left.getAdjacentLocation(getDirection() + Location.LEFT);
    		if (g.isValid(leftleft) && g.get(leftleft) == null)  {
    			locs.add(leftleft);
            }
    	}
    	
        // judge right and rightright
    	Location right = loc.getAdjacentLocation(getDirection() + Location.RIGHT);
    	if (g.isValid(right) && g.get(right)==null) {
    		Location rightright = right.getAdjacentLocation(getDirection() + Location.RIGHT);
    		if (g.isValid(rightright) && g.get(rightright) == null) {
    			locs.add(rightright);
            }
    	}

    	return locs;
    }  
}
