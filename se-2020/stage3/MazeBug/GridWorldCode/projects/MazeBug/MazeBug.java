import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;

// A MazeBug can exit the maze. 
public class MazeBug extends Bug {
	private Location next; // next location
	private Location last; // last location
	private Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>(); // A stack that records the nodes of a tree
	private Integer stepCount = 0; // record the number of steps taken by the maze to the exit

	private Stack<Location> path = new Stack<Location>(); // cur path

	private int directions[] = {Location.NORTH, Location.EAST, Location.SOUTH, Location.WEST}; // direction
	private int[] probility = {1,1,1,1}; // advanced part

	private boolean isEnd = false;	// wheter the game is over
	private boolean isShown = false; // whether it is shown "game over"

	// construct a green mazeBug
	public MazeBug() {
		setColor(Color.GREEN);
	}

	public void act() {
		// start
		if(stepCount == 0) {
			Location curLoc = this.getLocation();
			path.push(curLoc);
		}
		if (isEnd) {	
			// game over 
			if (!isShown) {
				JOptionPane.showMessageDialog(null, "use "+stepCount.toString()+" steps");
				isShown = true;
			}
		} else {
			if (canMove()) {
				// can move : move to next location
				move();
			} else {
				// can't move : go back
				goBack();
				stepCount++;
			}
		}
	}

	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return null;
		}

		ArrayList<Location> valid = new ArrayList<Location>();

		// Traverse four directions
		for (int i = 0; i < 4; i++) {
			Location nextLoc = loc.getAdjacentLocation(directions[i]);
			if(gr.isValid(nextLoc)) {
				Actor nextActor = gr.get(nextLoc);
				if (nextActor instanceof Rock && nextActor.getColor().equals(Color.RED)) {
					// find the exit, only return the exit.
					next = nextLoc;
					ArrayList<Location> exit = new ArrayList<Location>();
					exit.add(next);
					return exit;
				}
				else if(nextActor == null) {
					// find a empty loc
					valid.add(nextLoc);
				}
			}
		}
		return valid;
	}

	public boolean canMove() {
		ArrayList<Location> valid = new ArrayList<Location>();
		valid = getValid(this.getLocation());
		if(valid.size() > 0) {
			return true;
		}
		return false;
	}

	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}

		// get the valid next location
		Location curLoc = this.getLocation();
		ArrayList<Location> valid = new ArrayList<Location>();
		valid = getValid(curLoc);

		if(valid.size() == 1) {
			next = valid.get(0);
			int direction = curLoc.getDirectionToward(next);
			// increase the direction probility in array
			probility[direction/90]++;
		} else {
			// int n = valid.size();
			int max_probility = 0, max_probility_index = 0, location_index = 0, max_probility_location_index = 0;

			// find the first max probility
			for(Location l : valid) {
				int direction = curLoc.getDirectionToward(l);
				if(probility[direction/90] > max_probility) {
					max_probility_index = (int)direction/90;
					max_probility = probility[direction/90];
					max_probility_location_index = location_index;
				}
				location_index++;
			}

			//double r = (double)(Math.random());
			//if(r >= 0 && r < 0.618) {
			next = valid.get(max_probility_location_index);
			probility[max_probility_index]++;
			//}
			// else {
			// 	int nr = (int)(Math.random()*n);
			// 	next = valid.get(nr);
			// 	int direction = curLoc.getDirectionToward(next);
			// 	probility[direction/90]++;
			// }
		}

		if (gr.isValid(next)) {
			Actor actor = (Actor)gr.get(next);
			// game over 
			if(actor instanceof Rock && actor.getColor().equals(Color.RED)) {
				isEnd = true;
			}
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
			stepCount++;
			path.push(next);
		} else {
			removeSelfFromGrid();
		}

		// leave flower in old loc
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, curLoc);
	}

	public void goBack() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}

		// move to the last loaction
		Location curLoc = this.getLocation();
		path.pop();
		last = path.peek();
		setDirection(getLocation().getDirectionToward(last));
		moveTo(last);
		
		// decrease the direction probility in array
		int direction = last.getDirectionToward(curLoc);
		probility[direction/90]--;
		
		// leave flower in old loc
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, curLoc);
	}
}