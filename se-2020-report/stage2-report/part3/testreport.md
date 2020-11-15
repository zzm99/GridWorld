# Jumper testreport 测试报告

18342138 郑卓民

主要测试Jumper类中各个功能函数是否如设计思路那样正常实现功能，以及测试designreport部分的几个问题的情况（即具体情况对应的act函数的执行情况）。

## turn

测试turn方法。每调用一次turn方法，jumper会向右旋转90度。

```java
@Test
public void testTurn() {
    ActorWorld world = new ActorWorld();
    world.add(new Location(5, 5), alice);
    int direction = (alice.getDirection() + Location.RIGHT) % 360;
    alice.turn();
    assertEquals(alice.getDirection(), direction);
}
```

## jump

当可以进行jump动作的时候，我们希望得到的效果是jumper跳跃到当前方向上的第二个格子。

```java
@Test
public void testJump() {
    ActorWorld world = new ActorWorld();
    world.add(new Location(5, 5), alice); // alice default direction is NORTH
    alice.jump();
    assertEquals(new Location(3, 5), alice.getLocation());
}
```

## canJump

此处测试三种情况：
1. 前面一格是Rock，前面第二格是空，对应结果是true
2. 前面一格是Rock，前面第二格是flower，对应结果是true
3. 前面一格是Rock，前面第二格是Rock，对应结果是false

```java
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
```

## act

### What will a jumper do if the location in front of it is empty, but the location two cells in front contains a flower or a rock?

当第二个格子是rock的时候调用turn方法；当第二个格子是flower的时候，允许jump。

```java
@Test
public void testA() {
    ActorWorld world = new ActorWorld();
    // alice:  the rock condition
    world.add(new Location(3, 1), alice);
    world.add(new Location(1, 1), new Rock());	
    alice.act();
    assertEquals((alice.getDirection() + Location.RIGHT) % 360, alice.getDirection());

    // bob: the flower condition
    world.add(new Location(6, 1), bob);
    world.add(new Location(4, 1), new Flower());
    bob.act();
    assertEquals(new Location(4, 1), bob.getLocation());
} 
```

### What will a jumper do if the location two cells in front of the jumper is out of the grid?

调用turn方法

```java
@Test
public void testB() {
    ActorWorld world = new ActorWorld();
    world.add(new Location(1, 1), alice);
    alice.act();
    assertEquals((alice.getDirection() + Location.RIGHT) % 360, alice.getDirection());
}
```

### What will a jumper do if it is facing an edge of the grid?

调用turn方法

```java
@Test
public void testC() {
    ActorWorld world = new ActorWorld();
    world.add(new Location(0, 1), alice);
    alice.act();
    assertEquals((alice.getDirection() + Location.RIGHT) % 360, alice.getDirection());
}
```

### What will a jumper do if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper?

调用turn方法

```java
@Test
public void testD() {
    ActorWorld world = new ActorWorld();

    world.add(new Location(6, 6), alice);
    world.add(new Location(4, 6), new Actor());
    alice.act();
    assertEquals((alice.getDirection() + Location.RIGHT) % 360, alice.getDirection());

    world.add(new Location(2, 0), bob);
    world.add(new Location(0, 0), new Jumper()); 
    bob.act();
    assertEquals((bob.getDirection() + Location.RIGHT) % 360, bob.getDirection());	
}
```

### What will a jumper do if it encounters another jumper in its path?

如果它们跳到的位置不是同一个，那么都可以jump，如果跳到的位置是同一个，即产生矛盾，那么一个能跳，另一个转向。

```java
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
    bob.act(); // the first act's jumper can jump
    alice.act(); // the second should turn 
    assertEquals((alice.getDirection() + Location.RIGHT) % 360, alice.getDirection());
    assertEquals(new Location(2, 4), bob.getLocation());
}
```