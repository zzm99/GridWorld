# Question 1

18342138 郑卓民

## Step1：

1. Does the bug always move to a new location? Explain.

不是，虫子能否在当前方向上前进到新的位置，取决于当前方向前面的格子是否存在，且是否为空或者是花朵。（此部分判断逻辑可见于canMove方法中）

2. In which direction does the bug move?

虫子沿它的当前方向（角度）前进。

3. What does the bug do if it does not move?

虫子会向右旋转45度（即顺时针方向旋转45度）

4. What does a bug leave behind when it moves?

虫子会留下一个花朵，花朵的颜色和虫子一样。

5. What happens when the bug is at an edge of the grid? (Consider whether the bug is facing the edge as well as whether the bug is facing some other direction when answering this question.)

当虫子位于边缘且触角方向朝向边缘时，调用turn方法沿顺时针旋转45度，直至触角方向朝向grid后，正常移动。

当虫子位于边缘但触角方向不朝向边缘时，若触角方向没有障碍物，则沿触角方向正常移动，反之turn。

6. What happens when a bug has a rock in the location immediately in front of it?

沿顺时针旋转45度。

7. Does a flower move?

在Run的情况下是不能移动的。除非强行使用moveTo方法将flower移动到其他位置。

8. What behavior does a flower have?

花朵会枯萎。花朵的颜色会不断变黑。

9. Does a rock move or have any other behavior?

不能，没有其他行为。岩石就是一直存在于它的位置。除非强行使用moveTo方法将岩石移动到其他位置。

10. Can more than one actor (bug, flower, rock) be in the same location in the grid at the same time?

不能，一个格子在同一时间只能有一个actor。