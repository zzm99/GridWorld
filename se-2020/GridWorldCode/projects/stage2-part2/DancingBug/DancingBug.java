import info.gridworld.actor.Bug;

/**
 * A DancingBug traces out dancing path
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class DancingBug extends Bug
{
    private int currentTurn;
    private int[] turnArray;

    /**
     * Constructs a Dancing bug 
     */
    public DancingBug(int[] tArray)
    {
        turnArray = new int[tArray.length];
        System.arraycopy(tArray, 0, turnArray, 0, tArray.length);
        currentTurn = 0;
    }

    /**
     *defined turn(int n) for turning right of n*45 degrees
     */
    public void turn(int times) {
        for (int i = 0; i < times; i++) {
            turn();
        }
    }

    /**
     * Moves action of the bug
     */
    public void act()
    {   
        if (currentTurn == turnArray.length) {
            currentTurn = 0;
        }
        turn (turnArray[currentTurn]);
        currentTurn++;
        if (canMove()) {
            move();
        }
    }
}
