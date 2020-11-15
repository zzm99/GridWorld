import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A ChameleonCritter takes on the color of neighboring actors as it moves through the grid. 
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ChameleonCritter extends Critter
{	
	private static final double DARKENING_FACTOR = 0.05; // lose 5% of color value in each darken
    
	public ChameleonCritter() {

	}

    /**
     * Randomly selects a neighbor and changes this critter's color to be the
     * same as that neighbor's. If there are no neighbors, no action is taken.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n == 0) {
        	darken();
        	return;
        }
        int r = (int) (Math.random() * n);
        Actor other = actors.get(r);
        setColor(other.getColor());
    }

    /**
     * Turns towards the new location as it moves.
     */
    public void makeMove(Location loc)
    {
        setDirection(getLocation().getDirectionToward(loc));
        super.makeMove(loc);
    }

    public void darken() {
    	Color c = getColor();
 		int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
 		int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
 		int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));
 		setColor(new Color(red, green, blue)); 
    }
}