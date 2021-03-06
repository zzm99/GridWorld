package solution;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;
import java.util.*;

/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {
    // Jigsaw中的protected类型的属性之外的（即private类型的），要重新声明一下
    private List<JigsawNode> solutionPath;  // 解路径 ：用以保存从起始状态到达目标状态的移动路径中的每一个状态节点
    private int searchedNodesNum;           // 已访问节点数： 用以记录所有访问过的节点的数量
    private Vector<JigsawNode> exploreList;  // 用以保存已发现但未访问的节点
    private Vector<JigsawNode> visitedList;  // 用以保存已访问的节点
    
    /**
     * 拼图构造函数
     */
    public Solution() {
    }

    /**
     * 拼图构造函数
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
    }

    /**
     *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     * @return 搜索成功时为true,失败为false
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
        // 初始化
        this.beginJNode = new JigsawNode(bNode);
        this.endJNode = new JigsawNode(eNode);
        this.currentJNode = new JigsawNode(bNode);
        this.searchedNodesNum = 0;
        this.exploreList = new Vector<JigsawNode>();
        this.visitedList = new Vector<JigsawNode>();

        // 将初始节点加入到exploreList中
        exploreList.add(bNode);

        // 进行BFS
        while (!exploreList.isEmpty()) {
            // 弹出最前面的元素
            currentJNode = exploreList.elementAt(0);
            exploreList.remove(0);
            visitedList.add(currentJNode);
            searchedNodesNum++;

            if (currentJNode.equals(endJNode)) {
                solutionPath = getPath();
                break;
            } 

            // 下一节点状态
            JigsawNode[] nextNodes = new JigsawNode[]{
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode),
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode)
            };

            // 利用jigsawNode结构中的move方法，来改变当前节点的状态获取下一节点的状态
            for (int i = 0; i < 4; i++) {
                if (nextNodes[i].move(i) && !this.visitedList.contains(nextNodes[i]) && !this.exploreList.contains(nextNodes[i])) {
                    this.exploreList.add(nextNodes[i]);
                }
            }
        }

        // 格式化输出
        System.out.println("Jigsaw BFSearch Result:");
        System.out.println("Begin state:" + bNode.toString());
        System.out.println("End state:" + eNode.toString());
        System.out.println("Total number of searched nodes:" + this.searchedNodesNum);
        System.out.println("Depth of the current node is:" + this.getCurrentJNode().getNodeDepth());

        return isCompleted();
    }

    /**
     *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
     * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * @param jNode - 要计算代价估计值的节点
     */
    public void estimateValue(JigsawNode jNode) { 
        // 可以同时使用多个估价方法，f(n) = a*f1(n) + b*f2(n) + ... 通过适当调整权重(a,b,...)，能够加快搜索速度。
        // 这里采用了 后续节点不正确的数码个数 、 曼哈顿距离 和 欧几里得距离的结合
        // 计算后续节点不正确的数码个数
        int count = 0; 
        int dimension = JigsawNode.getDimension();
        for (int index = 1; index < dimension * dimension; index++) {
            if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
                count++;
            }
        }

        // 计算曼哈顿距离和欧几里得距离
        int manhattanDis = 0; // 曼哈顿距离
        int euclideanDis = 0; // 欧几里得距离
        for (int i = 1; i <=  dimension*dimension; i++) {
            for (int j = 1; j <= dimension*dimension; j++) {
                if (jNode.getNodesState()[i] != 0 && jNode.getNodesState()[i] == this.endJNode.getNodesState()[j]) {
                    int len = (int)(Math.sqrt(jNode.getNodesState().length - 1));
                    int x1 = (i-1) / len, y1 = (i+4) % len;
                    int x2 = (j-1) / len, y2 = (j+4) % len;
                    int absx = (x1 > x2) ? (x1-x2) : (x2-x1);
                    int absy = (y1 > y2) ? (y1-y2) : (y2-y1);  
                    manhattanDis += (int)(absx + absy);
                    euclideanDis += (int)(Math.sqrt(absx * absx + absy * absy));
                    break;
                }
            }
        }

        // 自行测试/设计各个参数的权重
        jNode.setEstimatedValue(manhattanDis * 2 + count + euclideanDis);
    }
}
