# stage2-part3 question

18342138 郑卓民

## Set 3

Assume the following statements when answering the following questions.

```java
Location loc1 = new Location(4, 3);
Location loc2 = new Location(3, 4);
```

1. How would you access the row value for loc1?

代码如下：

```java
loc1.getRow();
```

2. What is the value of b after the following statement is executed?

```java
boolean b = loc1.equals(loc2);
```

b的值为false，原因是loc1和loc2不相等。

3. What is the value of loc3 after the following statement is executed?

```java
Location loc3 = loc2.getAdjacentLocation(Location.SOUTH);
```

loc的值为（4，4）。向南方向下移一位，即row+1。

4. What is the value of dir after the following statement is executed?

```java
int dir = loc1.getDirectionToward(new Location(6, 5));
```

dir的值为135（degrees），即东南方向。

5. How does the getAdjacentLocation method know which adjacent location to return?

getAdjacentLocation方法的参数中指定了临近的方向，然后方法返回该方向上的相邻位置。

## Set 4

1. How can you obtain a count of the objects in a grid? How can you obtain a count of the empty locations in a bounded grid?

得到有多少对象在网格中，即有多少位置被占据了。

```java
Grid.getOccupiedLocations().size()
```

得到有多少空位置在有限的网格中，即用网格的大小减去已被占据的。

```java
Grid.getNumRows()*getNumCols()-Grid.getOccupiedLocations().size()
```

2. How can you check if location (10,10) is in a grid?

调用isValid方法， 如果返回真，则是在网格里面的。

```java
Grid.isValid(new Location(10,10))
```

3. Grid contains method declarations, but no code is supplied in the methods. Why? Where can you find the implementations of these methods?

Grid是一个接口interface，声明了哪些方法是实现该接口的类必须要实现的。

其中在AbstractGrid.java BoundedGrid.java UnBoundedGrid.java 中都能找到实现这些方法。

其中AbstractGrid是实现Grid接口的抽象类，实现了部分方法，未实现的留给继承它的类实现。

而BoundedGrid和UnBoundedGrid继承AbstractGrid，实现了Grid中剩余的未被实现的方法。


4. All methods that return multiple objects return them in an ArrayList. Do you think it would be a better design to return the objects in an array? Explain your answer.

我认为返回array类型比ArrayList类型要好。

原因如下：
- array类型可以直接使用下标进行访问对应对象，而且更高效；而ArrayList则只能通过ArrayList类的方法如get来访问对象。
- array相比ArrayList多了需要提前分配array的大小，而这需要知道所需array的size应该是多少，但是我们可以设计一个变量，存储当前网格中actor的数量，或者其他变量来存储其他需要的信息，这样我们就可以轻松的不需要多一次遍历整个网格而可以知道array的size应该是多少，接下来的操作，就跟ArrayList无异了，并不会更加复杂。
- 综合考虑，设计成array的返回形式，不论是用户体验，还是性能考虑，都是有利的。


## Set 5

1. Name three properties of every actor.

颜色、方向、位置。（color、direction、location）

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Actor.java
// @line: 32~34
private Location location;
private int direction;
private Color color;
```

2. When an actor is constructed, what is its direction and color?

蓝色，北向。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Actor.java
// @line: 36~45
/**
 * Constructs a blue actor that is facing north.
 */
public Actor()
{
    color = Color.BLUE;
    direction = Location.NORTH;
    grid = null;
    location = null;
}
```

3. Why do you think that the Actor class was created as a class instead of an interface?

一个actor有它的状态以及行为，而interface不能声明变量或实现方法（行为）。

4. Can an actor put itself into a grid twice without first removing itself? Can an actor remove itself from a grid twice? Can an actor be placed into a grid, remove itself, and then put itself back? Try it out. What happens?

参考的源代码如下：
```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Actor.java
// @line: 107~146
/**
 * Puts this actor into a grid. If there is another actor at the given
 * location, it is removed. <br />
 * Precondition: (1) This actor is not contained in a grid (2)
 * <code>loc</code> is valid in <code>gr</code>
 * @param gr the grid into which this actor should be placed
 * @param loc the location into which the actor should be placed
 */
public void putSelfInGrid(Grid<Actor> gr, Location loc)
{
    if (grid != null)
        throw new IllegalStateException(
                "This actor is already contained in a grid.");

    Actor actor = gr.get(loc);
    if (actor != null)
        actor.removeSelfFromGrid();
    gr.put(loc, this);
    grid = gr;
    location = loc;
}

/**
 * Removes this actor from its grid. <br />
 * Precondition: This actor is contained in a grid
 */
public void removeSelfFromGrid()
{
    if (grid == null)
        throw new IllegalStateException(
                "This actor is not contained in a grid.");
    if (grid.get(location) != this)
        throw new IllegalStateException(
                "The grid contains a different actor at location "
                        + location + ".");
                        
    grid.remove(location);
    grid = null;
    location = null;
}
```

解答：

(1)不能将actor两次放在同一个位置而不把前者先去除掉。

使用如下代码，会出现IllegalStateException。

```java
ActorWorld world = new ActorWorld();
Bug b = new Bug();
world.add(b);
b.putSelfInGrid(b.getGrid(), b.getLocation());
world.show();
```

(2)不能去除一个actor两次

使用如下代码，会出现IllegalStateException。

```java
ActorWorld world = new ActorWorld();
Bug b = new Bug();
world.add(b);
world.show();
b.removeSelfFromGrid();
b.removeSelfFromGrid();
```

(3)可以先放一个actor，然后去除它，然后再放回去。

使用如下代码

```java
ActorWorld world = new ActorWorld();
Actor a = new Actor();
world.add(a);
Grid g = a.getGrid();
world.show();
a.removeSelfFromGrid();
a.pushSelfInGrid(g, new Location(3,4)); // new一个location 一来是因为函数参数需求，二来是因为前面remove的时候就把actor的location给去除了。
```

5. How can an actor turn 90 degrees to the right?

使用setDirection方法

```java
setDirection(getDirection()+Location.RIGHT)
// 或者
setDirection(getDirection()+90)
```

## Set 6

1. Which statement(s) in the canMove method ensures that a bug does not try to move out of its grid?

以下代码，确保了bug下一个移动的位置是合法的。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 98~99
if (!gr.isValid(next))
    return false;
```

2. Which statement(s) in the canMove method determines that a bug will not walk into a rock?

以下代码，确保了bug下一个移动的位置为空或者是花朵。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 100~101
Actor neighbor = gr.get(next);
return (neighbor == null) || (neighbor instanceof Flower);
```

3. Which methods of the Grid interface are invoked by the canMove method and why?

isValid 和 get方法。调用这两个方法的目的在于确保下一个移动的位置是存在的且有效合法的。

4. Which method of the Location class is invoked by the canMove method and why?

getAdjacentLocation 方法。 调用该方法获取某个方向的相邻位置。

5. Which methods inherited from the Actor class are invoked in the canMove method?

getLocation、getDirection、getGrid 方法。获取相关信息。

6. What happens in the move method when the location immediately in front of the bug is out of the grid?

将会将自己从grid中去除掉。

7. Is the variable loc needed in the move method, or could it be avoided by calling getLocation() multiple times?

loc 变量是需要的。后面插入花朵的时候需要用到旧的loc，而旧的loc不能通过临时的getLocation获得。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 83
flower.putSelfInGrid(gr, loc);
```

8. Why do you think the flowers that are dropped by a bug have the same color as the bug?

从视觉上，直接运行gridworld项目run或者step之后可以清楚看到bug后面留下的flower颜色一样。

从代码上，可见如下，留下的flower的初始化使用了当前bug的getColor方法获得到的颜色，所以flower的颜色和bug会一样。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 82~83
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```

9. When a bug removes itself from the grid, will it place a flower into its previous location?

要分情况而言。bug继承自actor类，如果是直接调用actor类的removeSelfFromGrid方法，那么是不会留下花朵的。但是如果是因为bug跑出边界了而在move方法中调用了removeSelfFromGrid方法，那么是会留下花朵的。即留不留下花朵，需要看有没有move方法的作用。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 78~83
if (gr.isValid(next))
    moveTo(next);
else
    removeSelfFromGrid();
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```

10. Which statement(s) in the move method places the flower into the grid at the bug's previous location?

flower所使用的loc的值即bug的旧坐标。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Bug.java
// @line: 76~83
Location loc = getLocation();
Location next = loc.getAdjacentLocation(getDirection());
if (gr.isValid(next))
    moveTo(next);
else
    removeSelfFromGrid();
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```

11. If a bug needs to turn 180 degrees, how many times should it call the turn method?

一个turn转45度，所以转180度要条用turn方法四次。

