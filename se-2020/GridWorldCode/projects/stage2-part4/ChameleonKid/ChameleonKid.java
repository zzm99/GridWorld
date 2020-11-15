import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A ChameleonKid takes on the color of neighboring actors immediately in front of or behind. 
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public final class ChameleonKid extends ChameleonCritter
{	 
    public ChameleonKid() {

    }

	/*
     * Get the actors that in the neighboring location and in front or behind as it moves
     */
    public ArrayList<Actor> getActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();

        // get the cur grid and location
        Grid gr = getGrid();
        Location loc = getLocation();

        // in front of
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next)) {
            Actor a = getGrid().get(next);
            if(a != null) {
                actors.add(a);
            }
        }
        
        // behind
        next = loc.getAdjacentLocation(getDirection() + Location.HALF_CIRCLE);
        if (gr.isValid(next)) {
            Actor a = getGrid().get(next);
            if(a != null) {
                actors.add(a);
            }
        }
        
        return actors;
    }  
}