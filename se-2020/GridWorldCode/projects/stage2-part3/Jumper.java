import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * A Jumper jump for two cells. 
 */
public class Jumper extends Actor
{
    /**
     * Constructs a red Jumper
     */
    public Jumper()
    {
        setColor(Color.RED); // default color
    }

    /**
     * Constructs a Jumper in a given color
     */
    public Jumper(Color jumperColor) {
        setColor(jumperColor); 
    }
    
    /**
     * act method decide what a jumper will do
     */
    public void act() {
        if (canJump()) {
            jump();
        }
        else {
            turn();
        }
    }

    /**
     * when the jumper can't jump to next location, it will turn right for 90 degrees.
     */
    public void turn() {
        setDirection(getDirection() + Location.RIGHT);
    }

    /**
     * decide whether the Jumper can jump to the next location
     */
    public boolean canJump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return false;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location nextnext = next.getAdjacentLocation(getDirection());
        if ((!gr.isValid(nextnext)) || (!gr.isValid(next))) {
            return false;
        }
        Actor neighbor = gr.get(nextnext);
        return (neighbor == null) || (neighbor instanceof Flower); 
    }

    /*
     * define the action jump
     */
    public void jump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location nextnext = next.getAdjacentLocation(getDirection());
        if (gr.isValid(nextnext)) {
            moveTo(nextnext);
        }
        else {
            removeSelfFromGrid();
        }
    }
}
