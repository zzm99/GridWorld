import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

/**
 * A BlusterCritter act like a critter
 */
public class BlusterCritter extends Critter
{	
	private int courageFactor;

	// default constructor
	public BlusterCritter() {
		super();
		courageFactor = 2; // set default courageFactor to 2
	}

	// constructor with parameter c
	public BlusterCritter(int c) {
		super();
		courageFactor = c;
	}

    /**
     * choose the Actors in the location within two steps
     */
    public ArrayList<Actor> getActors() {
    	ArrayList<Actor> actors = new ArrayList<Actor>();
    	Location loc = getLocation();      // cur location
    	for (int r = loc.getRow()-2; r <= loc.getRow()+2; r++) {
    		for (int c = loc.getCol()-2; c <= loc.getCol()+2; c++) {
    			Location tmp = new Location(r, c);
    			if (getGrid().isValid(tmp)) {
    				Actor a = getGrid().get(tmp);
    				if (a != null && a != this) {
    					actors.add(a);
                    }
    			}
    		}
    	}
    	return actors;
    }

    /**
     * To count the number of the BlusterCritter near by
     * If the count number smaller than the parameter courageFactor, lighten , else darken
     */
    public void processActors(ArrayList<Actor> actors) {
    	int c = 0;
    	for (Actor a : actors) {
    		if (a instanceof Critter) {
    			c++;
            }
    	}
    	if (c < courageFactor) {// whether the c smaller than courageFactor
    		lighten();
        }
    	else {
    		darken();
        }
    }

    // Since that 1*0.9*1.1 = 0.99 != 1, we should change the darken and light algorithm, to make it equal.
    private void darken() {
    	Color c = getColor();  // get cur color
    	int red = c.getRed(), green = c.getGreen(), blue = c.getBlue();
    	if (red >= 10) { 
            red -= 10;
        }
    	if (green >= 10) {
            green -= 10;
        }
    	if (blue >= 10) {
            blue -= 10;
        }
    	setColor(new Color(red, green, blue)); // set the new color
    }

    private void lighten() {
    	Color c = getColor();  // get cur color
    	int red = c.getRed(), green = c.getGreen(), blue = c.getBlue();
    	if (red <= 245) {
            red += 10;
        }
    	if (green <= 245) {
            green += 10;
        }
    	if (blue <= 245) {
            blue += 10;
        }
    	setColor(new Color(red, green, blue)); // set the new color
    }
}
