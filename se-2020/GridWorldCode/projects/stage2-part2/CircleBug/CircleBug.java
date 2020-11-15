import info.gridworld.actor.Bug;

/**
 * A CircleBug traces out a cycle of a given size. 
 */
public class CircleBug extends Bug
{
    private int steps;
    private int sideLength;

    /**
     * Constructs a Circle bug that traces a cycle of a given side length
     * @param length the side length
     */
    public CircleBug(int length)
    {
        steps = 0;
        sideLength = length;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        if (steps < sideLength && canMove())
        {
            move();
            steps++;
        }
        else
        {
            turn(); // only turn once
            steps = 0;
        }
    }
}
