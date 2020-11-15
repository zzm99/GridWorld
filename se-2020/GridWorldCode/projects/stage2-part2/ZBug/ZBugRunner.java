import info.gridworld.grid.Location;
import info.gridworld.actor.ActorWorld;
import java.awt.Color;

/**
 * This class runs a world that contains Z bugs.
 * This class is not tested on the AP CS A and AB exams.
 */
public final class ZBugRunner
{
	private ZBugRunner() {}
	
	public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();  // new a actorworld
        ZBug alice = new ZBug(4);			  // new a ZBug
        world.add(new Location(0, 0), alice);
        world.show();
    }
}