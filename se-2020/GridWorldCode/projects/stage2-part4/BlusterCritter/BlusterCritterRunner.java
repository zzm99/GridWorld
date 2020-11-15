import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * This class runs a world that contains BlusterCritter.
 * This class is not tested on the AP CS A and AB exams.
 */
public final class BlusterCritterRunner
{   
    private BlusterCritterRunner() {}
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        for (int c = 0; c <= 9; c++) {
            world.add(new Location(6, c), new Rock());
        }
        for (int r = 8; r <= 9; r++) {
            for (int c = 0; c <= 9; c++) {
                world.add(new Location(r,c), new BlusterCritter(2));
            }
        }
        for (int c = 0; c <= 3; c++) {
            world.add(new Location(0, c), new BlusterCritter(2));
        }
        world.show();
    }
}