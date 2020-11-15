import info.gridworld.grid.Location;
import info.gridworld.actor.ActorWorld;
import java.awt.Color;

/**
 * This class runs a world that contains Dancing bugs. 
 * This class is not tested on the AP CS A and AB exams.
 */
public final class DancingBugRunner
{
	private DancingBugRunner() {}
	
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();        // new a actorworld
        int[] turnArray = {1,2,3,4,5};                  // an array entry of 5 represents a turn of 225 degrees (recall one turn is 45 degrees)
        DancingBug alice = new DancingBug(turnArray);   // new a DancingBug
        world.add(new Location(5, 5), alice);
        world.show();
    }
}