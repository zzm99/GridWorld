# stage2 - part2 - question

18342138 郑卓民

## Set 2

The source code for the BoxBug class can be found in the boxBug directory.

1. What is the role of the instance variable sideLength?

sizeLength 变量在BoxBug类中表示BoxBug沿某一方向能走的最远步长（在不遇到障碍或者边界的情况下），在到达sideLength之后，Boxbug就要调转运动方向，见如下代码即判断逻辑。

```java
// @file: /GridWorldCode/projects/boxBug/BoxBug.java
// @line: 43~56
public void act()
{
    if (steps < sideLength && canMove())
    {
        move();
        steps++;
    }
    else
    {
        turn();
        turn();
        steps = 0;
    }
}
```


2. What is the role of the instance variable steps?

steps 变量记录BoxBug在当前方向上已经走了的步数，steps在没有转向时增加1，初始化或转向时清0。

```java
// @file: /GridWorldCode/projects/boxBug/BoxBug.java
// @line: 34~38
public BoxBug(int length)
{
    steps = 0;
    sideLength = length;
}

// @file: /GridWorldCode/projects/boxBug/BoxBug.java
// @line: 43~56
public void act()
{
    if (steps < sideLength && canMove())
    {
        move();
        steps++;
    }
    else
    {
        turn();
        turn();
        steps = 0;
    }
}
```


3. Why is the turn method called twice when steps becomes equal to sideLength?

当steps等于sideLength的时候，需要进行一个90度的转向(BoxBug的特点)，而turn方法调用一次只转45度，所以要调用两次。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 59~65
/**
 * Turns the bug 45 degrees to the right without changing its location.
 */
public void turn()
{
    setDirection(getDirection() + Location.HALF_RIGHT);
}
```


1. Why can the move method be called in the BoxBug class when there is no move method in the BoxBug code?

因为BoxBug继承于Bug类，而Bug中有move方法，所以BoxBug也继承了Bug中的move方法，所以可以调用。下面找到bug类中move方法的定义。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 67~84
/**
 * Moves the bug forward, putting a flower into the location it previously
 * occupied.
 */
public void move()
{
    Grid<Actor> gr = getGrid();
    if (gr == null)
        return;
    Location loc = getLocation();
    Location next = loc.getAdjacentLocation(getDirection());
    if (gr.isValid(next))
        moveTo(next);
    else
        removeSelfFromGrid();
    Flower flower = new Flower(getColor());
    flower.putSelfInGrid(gr, loc);
}
```

5. After a BoxBug is constructed, will the size of its square pattern always be the same? Why or why not?

是一样的，因为sideLength在构造对象的时候就已经初始化并后续不做修改了，所以BoxBug移动形成的所有正方形大小不会被改变。

```java
// @file: /GridWorldCode/projects/boxBug/BoxBug.java
// @line: 30~38
/**
 * Constructs a box bug that traces a square of a given side length
 * @param length the side length
 */
public BoxBug(int length)
{
    steps = 0;
    sideLength = length;
}
```

6. Can the path a BoxBug travels ever change? Why or why not?

会，当当前方向遇到一个Rock或者Bug的时候，BoxBug会转向并开始新的道路。

这取决于canMove函数。canMove判断为false的时候就会进行转向90度。

```java
// @file: /GridWorldCode/projects/boxBug/BoxBug.java
// @line: 40~56
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
        turn();
        turn();
        steps = 0;
    }
}
```

7. When will the value of steps be zero?

刚被构造的时候；需要转向的时候（steps==sideLength 或 canMove()为false）

```java
// @file: /GridWorldCode/projects/boxBug/BoxBug.java
// @line: 30~56
/**
 * Constructs a box bug that traces a square of a given side length
 * @param length the side length
 */
public BoxBug(int length)
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
        turn();
        turn();
        steps = 0;
    }
}
```