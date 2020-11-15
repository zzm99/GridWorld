import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Actor;
import java.awt.Color;

/**
 * This class runs a world that contains Jumper. 
 * This class is not tested on the AP CS A and AB exams.
 */
public final class JumperRunner
{
	private JumperRunner() {}
	
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();        // new a actorworld
        Jumper alice = new Jumper();                // new a jumper
        world.add(new Location(5, 6), new Rock());  // add a rock
        world.add(new Location(4, 6), new Flower());// add a flower
        world.add(new Location(6, 6), alice);       // add the jumper
        world.show();
    }
}