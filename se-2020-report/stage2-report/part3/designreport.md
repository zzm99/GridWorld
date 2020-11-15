# Jumper designreport

18342138 郑卓民

Inception: clarify the details of the problem:

1. What will a jumper do if the location in front of it is empty, but the location two cells in front contains a flower or a rock?

当第二个格子是rock的时候调用turn方法；当第二个格子是flower的时候，允许jump。

2. What will a jumper do if the location two cells in front of the jumper is out of the grid?

调用turn方法

3. What will a jumper do if it is facing an edge of the grid?

调用turn方法

4. What will a jumper do if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper?

调用turn方法

5. What will a jumper do if it encounters another jumper in its path?

如果它们跳到的位置不是同一个，那么都可以jump，如果跳到的位置是同一个，即产生矛盾，那么一个能跳，另一个转向。

6. Are there any other tests the jumper needs to make?

- Jumper可以跨过rock和flower，那么它能不能跨过其他Jumper？
- 当Jumper前面一格的位置被占据的时候，Jumper应该如何应对？