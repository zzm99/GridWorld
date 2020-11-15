import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Actor;

public class JumperTest {
	private Jumper alice = new Jumper(); // Jumper alice used for test
	private Jumper bob = new Jumper();	 // Jumper bob used for test

	/*
	 * Test for the method turn().
	*/ 
	@Test
	public void testTurn() {
		ActorWorld world = new ActorWorld();
		world.add(new Location(5, 5), alice);
		int olddir = alice.getDirection();
		alice.turn();
		assertEquals(alice.getDirection(), (olddir + Location.RIGHT) % 360);
	}

	/*
	 * Test for the method jump().
	 */
	@Test
	public void testJump() {
		ActorWorld world = new ActorWorld();
		world.add(new Location(5, 5), alice); // alice default direction is NORTH
		alice.jump();
		assertEquals(new Location(3, 5), alice.getLocation());
	}

	
	/*
	 * Test for the method canJump().
	*/
	@Test
	public void testCanJump() {
		ActorWorld world = new ActorWorld();
		/* case1:
		 * next location is a Rock, next two cells location is empty.
		 */
		world.add(new Location(5, 1), alice);
		world.add(new Location(4, 1), new Rock());
		assertEquals(true, alice.canJump());

		/* case2:
		 * next location is Rock. next two cells location is flower. 
		 */
		alice.moveTo(new Location(5, 2));
		world.add(new Location(4, 2), new Rock());
		world.add(new Location(3, 2), new Flower());
		assertEquals(true, alice.canJump());

		/* case2:
		 * next location is Rock. next two cells location is Rock. 
		 */
		alice.moveTo(new Location(5, 3));
		world.add(new Location(4, 3), new Rock());
		world.add(new Location(3, 3), new Rock());
		assertEquals(false, alice.canJump());
	}

	/* 
	 * What will a jumper do if the location in front of it is empty, But the location two cells in front contains a flower or a rock?
	 * when it is a flower , do jump; when it is a rock, do turn.
	 */
	@Test
	public void testA() {
		ActorWorld world = new ActorWorld();
		// alice:  the rock condition
		world.add(new Location(3, 1), alice);
		world.add(new Location(1, 1), new Rock());	
		int olddir = alice.getDirection();
		alice.act();
		assertEquals((olddir + Location.RIGHT) % 360, alice.getDirection());

		// bob: the flower condition
		world.add(new Location(6, 1), bob);
		world.add(new Location(4, 1), new Flower());
		bob.act();
		assertEquals(new Location(4, 1), bob.getLocation());
	} 

	/* 
	 * What will a jumper do if the location two cells in front of the jumper is out of the grid?
	 * do turn 
	 */
	@Test
	public void testB() {
		ActorWorld world = new ActorWorld();
		world.add(new Location(1, 1), alice);
		int olddir = alice.getDirection();
		alice.act();
		assertEquals((olddir + Location.RIGHT) % 360, alice.getDirection());
	}

	/* 
	 * What will a jumper do if it is facing an edge of the grid?
	 * do turn 
	 */
	@Test
	public void testC() {
		ActorWorld world = new ActorWorld();
		world.add(new Location(0, 1), alice);
		int olddir = alice.getDirection();
		alice.act();
		assertEquals((olddir + Location.RIGHT) % 360, alice.getDirection());
	}

	/* 
	 * What will a jumper do if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper?
	 * do turn 
	 */
	@Test
	public void testD() {
		ActorWorld world = new ActorWorld();

		world.add(new Location(6, 6), alice);
		world.add(new Location(4, 6), new Actor());
		int olddir = alice.getDirection();
		alice.act();
		assertEquals((olddir + Location.RIGHT) % 360, alice.getDirection());

		world.add(new Location(2, 0), bob);
		world.add(new Location(0, 0), new Jumper()); 
		olddir = bob.getDirection();
		bob.act();
		assertEquals((olddir + Location.RIGHT) % 360, bob.getDirection());	
	}

	/* 
	 * What will a jumper do if it encounters another jumper in its path?
	 * if their destination is the same, one jump and one turn, else both can jump
	 */
	@Test
	public void testF(){
		ActorWorld world = new ActorWorld();
	
		world.add(new Location(6, 4), alice);
		world.add(new Location(3, 4), bob);
		bob.setDirection(Location.SOUTH);
		alice.act();
		bob.act();
		assertEquals(new Location(4, 4), alice.getLocation());
		assertEquals(new Location(5, 4), bob.getLocation());

		bob.moveTo(new Location(0, 4));
		int olddir = alice.getDirection();
		bob.act(); // the first act's jumper can jump
		alice.act(); // the second should turn 
		assertEquals((olddir + Location.RIGHT) % 360, alice.getDirection());
		assertEquals(new Location(2, 4), bob.getLocation());
	}
}