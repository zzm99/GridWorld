import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Critter;
import java.util.ArrayList;

/**
 * A RockHound act as a critter but can clear the rock nearby 
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class RockHound extends Critter
{	
	public RockHound() {}

    /*
     * choose the Rock in the neighbourdhood locations and then clean it.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        // check all actors in ArrayList
        for (Actor a : actors) { 
            // check if it is Rock
        	if (a instanceof Rock) { 
                // remove
        		a.removeSelfFromGrid(); 
        	}
        }
    }
}
