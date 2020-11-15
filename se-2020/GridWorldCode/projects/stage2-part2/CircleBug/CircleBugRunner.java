import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * This class runs a world that contains Circle bugs. 
 * This class is not tested on the AP CS A and AB exams.
 */
public final class CircleBugRunner
{
    private CircleBugRunner() {}
    
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld(); // new the World
        CircleBug alice = new CircleBug(6);  // new the CircleBug alice
        alice.setColor(Color.ORANGE);
        CircleBug bob = new CircleBug(3);    // new the CircleBug bob
        world.add(new Location(7, 8), alice);
        world.add(new Location(5, 5), bob);
        world.show();
    }
}