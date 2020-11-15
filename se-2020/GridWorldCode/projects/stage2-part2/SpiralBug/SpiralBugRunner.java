import info.gridworld.actor.Actor;
import info.gridworld.grid.UnboundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.ActorWorld;
import java.awt.Color;

/**
 * This class runs a world that contains Spiral bugs. 
 * This class is not tested on the AP CS A and AB exams.
 */
public final class SpiralBugRunner
{
    private SpiralBugRunner() {}
    
    public static void main(String[] args)
    {
        UnboundedGrid grid = new UnboundedGrid<Actor>(); // new a unboundedgrid
        ActorWorld world = new ActorWorld(grid);         // use the unboundedgrid to new a unboundedworld
        SpiralBug alice = new SpiralBug(6);              // new a SpiralBug alice
        alice.setColor(Color.ORANGE);
        SpiralBug bob = new SpiralBug(3);                // new a SpiralBug bob
        world.add(new Location(7, 8), alice);
        world.add(new Location(5, 5), bob);
        world.show();
    }
}