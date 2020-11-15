# stage2-part4 question

18342138 郑卓民

## Set 7

The source code for the Critter class is in the critters directory

1. What methods are implemented in Critter?

act、getActors、processActors、getMoveLocations、selectMoveLocation、makeMove。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Critter.java
// @line: 38
public void act() {}
// @line: 56
public ArrayList<Actor> getActors() {}
// @line: 71
public void processActors(ArrayList<Actor> actors) {}
// @line: 88
public ArrayList<Location> getMoveLocations() {}
// @line: 104
public Location selectMoveLocation(ArrayList<Location> locs) {}
// @line: 125
public void makeMove(Location loc) {}
```

2. What are the five basic actions common to all critters when they act?

getActors、processActors、getMoveLocations、selectMoveLocation、makeMove。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Critter.java
// @line: 38~47
public void act()
{
    if (getGrid() == null)
        return;
    ArrayList<Actor> actors = getActors();
    processActors(actors);
    ArrayList<Location> moveLocs = getMoveLocations();
    Location loc = selectMoveLocation(moveLocs);
    makeMove(loc);
}
```

3. Should subclasses of Critter override the getActors method? Explain.

应该，getActors的行为取决于特定的Critter，在Critter中给出的是默认的效果，即取当前方向的最邻近的一格，而有的Critter子类是有不同的需求的，此时就应该重写getActors方法。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Critter.java
// @line: 51
/** ...
 * Override this method in subclasses to
 * look elsewhere for actors to process.<br />
 * ...
 */
public ArrayList<Actor> getActors() {}
```

4. Describe the way that a critter could process actors.

critter对于actors的行为是多变的，自由选择设定的。例如：改变周边actors的颜色；让周边actors进行move动作；吃掉（消除）周边actors。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Critter.java
// @line: 63
/** ...
 * Implemented to "eat" (i.e. remove) selected actors
 * that are not rocks or critters. Override this method in subclasses to
 * process actors in a different way. <br />
 * ...
 */
public void processActors(ArrayList<Actor> actors) {}
```

5. What three methods must be invoked to make a critter move? Explain each of these methods.

getMoveLocations、selectMoveLocation、makeMove

getMoveLocations的默认功能是返回周围空格的链表集合。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Critter.java
// @line: 88~91
public ArrayList<Location> getMoveLocations()
{
    return getGrid().getEmptyAdjacentLocations(getLocation());
}
```

selectMoveLocation的默认功能是从上面返回的链表集合中随机选一个位置返回，若集合为空，那么就返回自身的位置。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Critter.java
// @line: 104~111
public Location selectMoveLocation(ArrayList<Location> locs)
{
    int n = locs.size();
    if (n == 0)
        return getLocation();
    int r = (int) (Math.random() * n);
    return locs.get(r);
}
```

makeMove的默认功能是将critter移动到对应的位置。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Critter.java
// @line: 125~131
public void makeMove(Location loc)
{
    if (loc == null)
        removeSelfFromGrid();
    else
        moveTo(loc);
}
```

6. Why is there no Critter constructor?

无特殊需求可以不实现构造函数，Java会调用父类的默认构造函数，这里的父类是actor，所以会默认构造一格蓝色朝向北向的critter。

```java
// @file: /GridWorldCode/framework/info/gridworld/actor/Actor.java
// @line: 39
public Actor() {}
```

## Set 8 

The source code for the ChameleonCritter class is in the critters directory

1. Why does act cause a ChameleonCritter to act differently from a Critter even though ChameleonCritter does not override act?

虽然没有重写act方法，但是重写了act中调用的方法（processActors、makeMove），所以就会有不同的效果。

```java
// @file: GridWorldCode/projects/critters/ChameleonCritter.java
// @line: 36, 50
public void processActors(ArrayList<Actor> actors) {}
public void makeMove(Location loc) {}
```

2. Why does the makeMove method of ChameleonCritter call super.makeMove?

ChameleonCritter和其父类在makeMove方法上的不同点只有move之前ChameleonCritter会调整它的方向，而后面的move动作是和父类一样的，就没必要写冗余的代码了，可以直接调用父类的makeMove。

```java
// @file: GridWorldCode/projects/critters/ChameleonCritter.java
// @line: 50
public void makeMove(Location loc) {
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
}
```

3. How would you make the ChameleonCritter drop flowers in its old location when it moves?

要想在ChameleonCritter的旧位置种一朵花，类似前面Bug中的做法，先保存旧位置的坐标，然后当对象移动到新的位置后，就可以利用刚刚保存的旧坐标，种下花朵。

可得如下makeMove修改后的代码：

```java
public void makeMove(Location loc)
{
    Location oldloc = getLocation();
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
    if(!oldloc.equals(loc)) {
        Flower flower = new Flower(getColor());
        flower.pushSelfInGrid(getGrid(), oldloc);
    } 
}
```

4. Why doesn't ChameleonCritter override the getActors method?

因为ChameleonCritter对于getActorrs的功能需求和父类一样，所以就没必要重写父类的getActors方法了。

5. Which class contains the getLocation method?

Actor类有getLocation方法，所有子类都继承了这个方法。

6. How can a Critter access its own grid?

使用getGrid方法。

## Set 9

The source code for the CrabCritter class is reproduced at the end of this part of GridWorld.

1. Why doesn't CrabCritter override the processActors method?

因为CrabCritter的processActors需要的功能和Critter的一样，都是吃掉动物。

```java
// @file: GridWorldCode/projects/critters/Critter.java
// @line: 73
for (Actor a : actors) {
    if (!(a instanceof Rock) && !(a instanceof Critter))
        a.removeSelfFromGrid();
}
```

2. Describe the process a CrabCritter uses to find and eat other actors. Does it always eat all neighboring actors? Explain.

CrabCritter找相邻actor的时候只会查找前方、左前方、右前方三个方向上的临近格子中的actor。CrabCritter会吃掉上述所找到的actors（岩石和其他昆虫除外）

```java
// @file: GridWorldCode/projects/critters/CrabCritter.java
// @line: 46
ArrayList<Actor> actors = new ArrayList<Actor>();
int[] dirs =
    { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
for (Location loc : getLocationsInDirections(dirs) {
    Actor a = getGrid().get(loc);
    if (a != null)
        actors.add(a);
}
```

3. Why is the getLocationsInDirections method used in CrabCritter?

为CrabCritter找到合法的、所需要的当前方向的临近位置。

```java
// @file: GridWorldCode/projects/critters/CrabCritter.java
// @line: 49
for (Location loc : getLocationsInDirections(dirs) {
    Actor a = getGrid().get(loc);
    if (a != null)
        actors.add(a);
}
```

4. If a CrabCritter has location (3, 4) and faces south, what are the possible locations for actors that are returned by a call to the getActors method?

（4，3）、（4，4）、（4，5）

5. What are the similarities and differences between the movements of a CrabCritter and a Critter?

相同点：它们移动之前都不会转向，它们都是随机向能移动的位置移动。

不同点：CrabCritter只能移动到左边或者右边，如果都不能移动，CrabCritter会转向。而Critter能向所有方向移动，而如果都不能移动的时候，也不会转向。

6. How does a CrabCritter determine when it turns instead of moving?

当makeMove的参数loc与它的当前位置相同的时候，意味着左右都不能动，那么它就会转向。

```java
// @file: GridWorldCode/projects/critters/CrabCritter.java
// @line: 79
if (loc.equals(getLocation())) {
    double r = Math.random();
    int angle;
    if (r < 0.5)
        angle = Location.LEFT;
    else
        angle = Location.RIGHT;
    setDirection(getDirection() + angle);
}
```

7. Why don't the CrabCritter objects eat each other?

CrabCritter的processActors方法是继承自Critter的，而Critter中的processActors所选取的actor就是非rock非critter的，所以CrabCritter是不能吃其他CrabCritter的。

```java
// @file: GridWorldCode/projects/critters/Critter.java
// @line: 73
for (Actor a : actors) {
    if (!(a instanceof Rock) && !(a instanceof Critter))
        a.removeSelfFromGrid();
}
```