package num743;

import java.util.Arrays;

public class Solution {
//    public static void main(String[] args) {
//        Solution s = new Solution();
//        int i = s.networkDelayTime(new int[][]{
//                {1, 2, 1},{2,1,3}
//        }, 2, 2);
//        System.out.println(i);
//    }

    public int networkDelayTime(int[][] times, int N, int K) {
        //邻接矩阵表示距离
        long[][] edge = new long[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                edge[i][j] = i == j ? 0 : Integer.MAX_VALUE;
            }
        }
        for (int[] time : times) {
            edge[time[0]][time[1]] = time[2];
        }
        // visited 表示是否被拓展过
        boolean[] visited = new boolean[N + 1];

        // distance 表示源点到其他点的距离
        long[] distance = new long[N + 1];
        //初始化从源点到其他点的距离为无穷
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[K] = 0;
        // Dijkstra 算法
        //总共有 N 个结点，只需拓展 N - 1 次
        for (int i = 1; i <= N - 1; i++) {
            long min = Integer.MAX_VALUE;
            int u = -1;
            //找到离源点最近且没有被拓展过的点 u
            for (int v = 1; v <= N; v++) {
                if (!visited[v] && distance[v] < min) {
                    u = v;
                    min = distance[v];
                }
            }
            //能拓展的点已经被拓展完了，说明有点不可达
            if (u == -1) {
                break;
            }
            //标记该点已被拓展
            visited[u] = true;
            //以 u 点开始拓展
            for (int v = 1; v <= N; v++) {
                if (distance[v] > distance[u] + edge[u][v]) {
                    distance[v] = distance[u] + edge[u][v];
                }
            }
        }
        long max = Integer.MIN_VALUE;
        for (int v = 1; v <= N; v++) {
            if (distance[v] == Integer.MAX_VALUE) {
                return -1;
            }
            max = Math.max(max, distance[v]);
        }
        return (int)max;
    }

}