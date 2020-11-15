# stage2-part5 question

18342138 郑卓民

## Set 10

The source code for the AbstractGrid class is in Appendix D.

1. Where is the isValid method specified? Which classes provide an implementation of this method?

isValid方法在Grid类中声明，在BoundedGrid类和UnboundedGrid类中被实现。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/Grid.java
// @line: 43~50
/**
* Checks whether a location is valid in this grid. <br />
* Precondition: <code>loc</code> is not <code>null</code>
* @param loc the location to check
* @return <code>true</code> if <code>loc</code> is valid in this grid,
* <code>false</code> otherwise
*/
boolean isValid(Location loc);

// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 60~64
public boolean isValid(Location loc)
{
    return 0 <= loc.getRow() && loc.getRow() < getNumRows()
            && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}

// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 53~56
public boolean isValid(Location loc)
{
    return true;
}
```

2. Which AbstractGrid methods call the isValid method? Why don't the other methods need to call it?

getValidAdjacentLocations 方法调用了 isValid方法，其他方法通过调用getValidAdjacentLocations方法间接调用了isValid，所以其他方法不需要直接调用isValid。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/AbstractGrid.java
// @line: 44~45
if (isValid(neighborLoc))
    locs.add(neighborLoc);
```

3. Which methods of the Grid interface are called in the getNeighbors method? Which classes provide implementations of these methods?

get 和 getOccupiedAdjacentLocations 

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/AbstractGrid.java
// @line: 31~32
for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
    neighbors.add(get(neighborLoc));
```

getOccupiedAdjacentLocations在AbstractGrid类中实现。

get在BoundedGrid类和UnboundedGrid类中实现。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 85~91
public E get(Location loc)
{
  if (!isValid(loc))
      throw new IllegalArgumentException("Location " + loc
              + " is not valid");
  return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
}

// @file: /GridWorldCode/framework/info/gridworld/grid/UnboundedGrid.java
// @line: 66~71
public E get(Location loc)
{
  if (loc == null)
      throw new NullPointerException("loc == null");
  return occupantMap.get(loc);
}

// @file: /GridWorldCode/framework/info/gridworld/grid/AbstractGrid.java
// @line: 62~71
public ArrayList<Location> getOccupiedAdjacentLocations(Location loc)
{
  ArrayList<Location> locs = new ArrayList<Location>();
  for (Location neighborLoc : getValidAdjacentLocations(loc))
  {
      if (get(neighborLoc) != null)
          locs.add(neighborLoc);
  }
  return locs;
}
```


4. Why must the get method, which returns an object of type E, be used in the getEmptyAdjacentLocations method when this method returns locations, not objects of type E?

为了判断一个位置是为空还是已被占据。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/AbstractGrid.java
// @line: 56~57
if (get(neighborLoc) == null)
    locs.add(neighborLoc);
```

5. What would be the effect of replacing the constant Location.HALF_RIGHT with Location.RIGHT in the two places where it occurs in the getValidAdjacentLocations method?

替换之后的结果就是，只检测了前后左右四个方向。

## Set 11

The source code for the BoundedGrid class is in Appendix D.

1. What ensures that a grid has at least one valid location?

BoundedGrid类的构造方法会对row和col参数进行判断，不可以有小于等于0的情况出现。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 39~46
public BoundedGrid(int rows, int cols)
{
    if (rows <= 0)
        throw new IllegalArgumentException("rows <= 0");
    if (cols <= 0)
        throw new IllegalArgumentException("cols <= 0");
    occupantArray = new Object[rows][cols];
}
```

2. How is the number of columns in the grid determined by the getNumCols method? What assumption about the grid makes this possible?

`occupantArray[0].length`。根据构造函数中对row的判断，可以假设grid至少含有一行，所以用下标0访问数组没有问题。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 53~58
public int getNumCols()
{
  // Note: according to the constructor precondition, numRows() > 0, so
  // theGrid[0] is non-null.
  return occupantArray[0].length;
}
```


3. What are the requirements for a Location to be valid in a BoundedGrid?

row大于等于0，小于grid的行数；col大于等于0，小于grid的列数。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 60~64
public boolean isValid(Location loc)
{
  return 0 <= loc.getRow() && loc.getRow() < getNumRows()
          && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}
```

In the next four questions, let r = number of rows, c = number of columns, and n = number of occupied locations.

4. What type is returned by the getOccupiedLocations method? What is the time complexity (Big-Oh) for this method?

返回类型：`ArrayList<Location>`

时间复杂度：`O(r*c)`

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 66~83
public ArrayList<Location> getOccupiedLocations()
{
  ArrayList<Location> theLocations = new ArrayList<Location>();

  // Look at all grid locations.
  for (int r = 0; r < getNumRows(); r++)
  {
      for (int c = 0; c < getNumCols(); c++)
      {
          // If there's an object at this location, put it in the array.
          Location loc = new Location(r, c);
          if (get(loc) != null)
              theLocations.add(loc);
      }
  }

  return theLocations;
}
```

5. What type is returned by the get method? What parameter is needed? What is the time complexity (Big-Oh) for this method?

返回类型：`E`

时间复杂度：`O(1)`

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 85~91
public E get(Location loc)
{
  if (!isValid(loc))
      throw new IllegalArgumentException("Location " + loc
              + " is not valid");
  return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
}
```

6. What conditions may cause an exception to be thrown by the put method? What is the time complexity (Big-Oh) for this method?

要插入的位置是不合法的 或者 要插入的对象是空的。

时间复杂度是：`O(1)`

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 93~105
public E put(Location loc, E obj)
{
  if (!isValid(loc))
      throw new IllegalArgumentException("Location " + loc
              + " is not valid");
  if (obj == null)
      throw new NullPointerException("obj == null");

  // Add the object to the grid.
  E oldOccupant = get(loc);
  occupantArray[loc.getRow()][loc.getCol()] = obj;
  return oldOccupant;
}
```

7. What type is returned by the remove method? What happens when an attempt is made to remove an item from an empty location? What is the time complexity (Big-Oh) for this method?

返回类型：`E`

后果：返回一个null

时间复杂度：`O(1)`

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 107~117
public E remove(Location loc)
{
  if (!isValid(loc))
      throw new IllegalArgumentException("Location " + loc
              + " is not valid");
  
  // Remove the object from the grid.
  E r = get(loc);
  occupantArray[loc.getRow()][loc.getCol()] = null;
  return r;
}
```

8. Based on the answers to questions 4, 5, 6, and 7, would you consider this an efficient implementation? Justify your answer.

是的，只有getOccupiedLocations方法的时间复杂度是`O(r*c)`，其他方法的时间复杂度都是`O(1)`，可以说是很高效了。当然，使用hashmap的策略提到array的策略会更加高效。

## Set 12

The source code for the UnboundedGrid class is in Appendix D.

1. Which method must the Location class implement so that an instance of HashMap can be used for the map? What would be required of the Location class if a TreeMap were used instead? Does Location satisfy these requirements?

hashCode和equals方法。

compareTo方法。TreeMaps需要keys都是能比较的。

满足。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 205, 218, 234
public boolean equals(Object other)
public int hashCode()
public int compareTo(Object other)
```

2. Why are the checks for null included in the get, put, and remove methods? Why are no such checks included in the corresponding methods for the BoundedGrid?

在BoundedGrid中，isValid方法检查位置是否为非null且在边界内。但对于unboundGrid类，它使用HashMap，它假定每个位置都有效，但null是不可接受的。与BoundedGrid不同，isValid方法在UnboundedGrid中永远返回true，因此有必要在get、put、remove方法中检查位置是否非空。

```java
// @file: /GridWorldCode/framework/info/gridworld/grid/BoundedGrid.java
// @line: 53~56
public boolean isValid(Location loc)
{
    return true;
}
```


3. What is the average time complexity (Big-Oh) for the three methods: get, put, and remove? What would it be if a TreeMap were used instead of a HashMap?

HashMap的情况：`O(1)`

TreeMap的情况：`O(logn)`。

4. How would the behavior of this class differ, aside from time complexity, if a TreeMap were used instead of a HashMap?

使用TreeMap的话，会导致get、put、remove等方法的时间复杂度升高，因为需要进行遍历。

5. Could a map implementation be used for a bounded grid? What advantage, if any, would the two-dimensional array implementation that is used by the BoundedGrid class have over a map implementation?

可以。如果使用hashmap，getOccupiedLocations 的时间复杂度会下降到`O(n)`，能够提升性能。

当grid中的空间几乎都被占据的时候，使用二维数组进行存储所需要的空间会比hashmap要少，hashmap的实际空间容量会比其真正需要存储的数量要多一倍以上。


## Coding Exercises

2. Consider using a HashMap or TreeMap to implement the SparseBoundedGrid. How could you use the UnboundedGrid class to accomplish this task? Which methods of UnboundedGrid could be used without change? Fill in the following chart to compare the expected Big-Oh efficiencies for each implementation of the SparseBoundedGrid.

Let r = number of rows, c = number of columns, and n = number of occupied locations

|Methods|SparseGridNode version|LinkedList<OccupantInCol> version|HashMap version|TreeMap version|
| --- | --- | --- | --- | --- |
|getNeighbors|O(c)|O(c)|O(1)|O(logn)|
|getEmptyAdjacentLocations|O(c)|O(c)|O(1)|O(logn)|
|getOccupiedAdjacentLocations|O(c)|O(c)|O(1)|O(logn)|
|getOccupiedLocations|O(r+n)|O(r+n)|O(n)|O(n)|
|get|O(c)|O(c)|O(1)|O(logn)|
|put|O(c)|O(c)|O(1)|O(logn)|
|remove|O(c)|O(c)|O(1)|O(logn)|

3. Consider an implementation of an unbounded grid in which all valid locations have non-negative row and column values. The constructor allocates a 16 x 16 array. When a call is made to the put method with a row or column index that is outside the current array bounds, double both array bounds until they are large enough, construct a new square array with those bounds, and place the existing occupants into the new array. 
Implement the methods specified by the Grid interface using this data structure. 
What is the Big-Oh efficiency of the get method?          
What is the efficiency of the put method when the row and column index values are within the current array bounds?            
What is the efficiency when the array needs to be resized?      

- O(1) : 直接get即可
- O(1) ：直接put即可
- O(row*column) ：row和column是原先的行和列，需要遍历原来的array，复制对应的东西到新的array中。

