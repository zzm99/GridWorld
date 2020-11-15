import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

/**
 * A QuickCrab act like a critter
 * This class is not tested on the AP CS A and AB exams.
 */
public class KingCrab extends CrabCritter
{
    public KingCrab()
    {
        setColor(Color.RED);
    }

    // try to push away an actor
    private boolean pushAway(Actor a) {
    	ArrayList<Location> locs = getGrid().getEmptyAdjacentLocations(a.getLocation());
    	for (Location loc : locs) {
            // KingCrab causes each actor that it processes to move one location further away from the KingCrab. 
            int x = getLocation().getRow() - loc.getRow();
            int y = getLocation().getCol() - loc.getCol();
    		if ((x*x+y*y) > 1) {
    			a.moveTo(loc);
    			return true;
    		}
    	}
    	return false;
    }

    // override the processActors method
    public void processActors(ArrayList<Actor> actors) {
    	for (Actor a : actors) {
    		if (!pushAway(a)) { // if a cannot move away then remove it
    			a.removeSelfFromGrid();
    		}
    	}
    }
}
