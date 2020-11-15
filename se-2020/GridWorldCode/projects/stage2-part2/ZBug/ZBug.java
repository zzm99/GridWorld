import info.gridworld.actor.Bug;

/**
 * A ZBug traces out a Z pattern of a given size.
 */
public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private int tflag; // the flag that how to turn

    /**
     * Constructs a ZBug bug that traces a Z pattern in which each segment of the z has the  given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
        steps = 0;
        tflag = 0;
        sideLength = length;
        setDirection(90); // set the direction to EAST
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {   
        if (tflag < 3) {
            if (steps < sideLength && canMove())
            {
                move();
                steps++;
            }
            else if(steps >= sideLength && tflag == 0)
            {
                setDirection(getDirection() + 135); // turn right 90 plus 45 degree
                steps = 0;
                tflag++;
            }
            else if(steps >=sideLength && tflag == 1) {
                setDirection(getDirection() - 135); // turn left 90 plus 45 degree
                steps = 0;
                tflag++;
            }
        }
    }
}
