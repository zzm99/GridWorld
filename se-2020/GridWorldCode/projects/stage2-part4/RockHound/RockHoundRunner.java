import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains RockHound. 
 * This class is not tested on the AP CS A and AB exams.
 */
public final class RockHoundRunner
{   
    private RockHoundRunner() {}
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        // new 4 Rock in the world
        world.add(new Location(2, 8), new Rock(Color.BLUE));
        world.add(new Location(5, 5), new Rock(Color.PINK));
        world.add(new Location(1, 5), new Rock(Color.RED));
        world.add(new Location(7, 2), new Rock(Color.YELLOW));
        // new 2 RockHound in the world
        world.add(new Location(4, 4), new RockHound());
        world.add(new Location(5, 8), new RockHound());
        world.show();
    }
}