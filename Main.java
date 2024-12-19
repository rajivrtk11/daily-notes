import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    // start from find in sorted and rotated array. Explain and disscuss approach
    // for 10-15 min
    // then solve or start code.
    
    // analyze binary search problem and patterns 1hrs 
    // solve stack questions => 9am - 11am
    // implement stack using different ways, lru, hashmap => 3hours => 3pm - 6pm

    // analyze Array problem and patterns 1hrs
    // Revise stack problems
    // 1. implement stack 
    // 2. implement lru with queue and linkedlist
    // 3. implement lfu
    
    public String smallestSubsequence(String s) {
     int n = s.length();
     HashMap<Character, Integer> map = new HashMap<>();
     for(int i = 0; i < n; i++) {
        char ch = s.charAt(i);
        map.put(ch, map.getOrDefault(ch, 0) + 1);
     }   

     StringBuilder sb = new StringBuilder("");

     for(int i = 0; i < n; i++) {
        char ch = s.charAt(i);
        
     }
     return sb.toString();
    }

    public int fn(int idx, int buy, int cap, int[] prices, int[][][] dp) {
        if(idx == prices.length || cap == 0) return 0;

        if(dp[idx][buy][cap] != -1) return dp[idx][buy][cap];

        int maxProfit = 0;
        if(buy == 1) {
            return dp[idx][buy][cap] = Math.max(-prices[idx] + fn(idx+1, 0, cap, prices, dp),
             0 + fn(idx+1, buy, cap, prices, dp)
            );
        }

        return dp[idx][buy][cap] = Math.max(prices[idx] + fn(idx+1, 1, cap-1, prices, dp), 
            0 + fn(idx+1, buy, cap, prices, dp)
        );
    }

    

    public int minFallingPathSumRec(int row, int col, int[][] matrix, int[][] dp) {
        int n = matrix.length;

        if(row < 0 || col < 0 || row >= n || col >= n) return (int)1e9;
        if(row == matrix.length-1) return matrix[row][col];

        if(dp[row][col] != -1) return dp[row][col];
        int a = matrix[row][col] + minFallingPathSumRec(row+1, col, matrix, dp);
        int b = matrix[row][col] + minFallingPathSumRec(row+1, col-1, matrix, dp);
        int c = matrix[row][col] + minFallingPathSumRec(row+1, col+1, matrix, dp);

        return dp[row][col] = Math.min(a, Math.min(c, b));
    }
    
    public static boolean checkIfTargetExist(int i, int tar, int[] arr, int[][] dp) {
        if(tar == 0) return true;
        if(i >= arr.length || tar < 0) return false;

        if(dp[i][tar] != -1) return dp[i][tar] == 1 ? true : false;
        boolean res = false;
        res = checkIfTargetExist(i+1, tar-arr[i], arr, dp) || checkIfTargetExist(i+1, tar, arr, dp);
        dp[i][tar] = res ? 1 : 0;
        return res;
    }

    public int minimumDifference(int[] nums) {
        int n = nums.length;
        int target = 0;
        for(int ele: nums) target += ele;
       
        int[][] dp = new int[n][target+1];
        for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        checkIfTargetExist(0, target, nums, dp);
        int min = (int) 1e9;

        for(int i = 0; i < target/2; i++) {
            int s1 = i;
            int s2 = target - i;
            min = Math.min(min, Math.abs(s1-s2));
        }
    }

    public boolean canPartition(int[] nums) {
        int n = arr.length;
        int target = 0;
        for(int ele: nums) target += ele;
        if(target & 1 != 0) return false;
        target /= target/2;
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        return checkIfTargetExist(0, target, arr, dp);
    }
    static Boolean isSubsetSum(int arr[], int target) {
        // code here
        int n = arr.length;
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        return checkIfTargetExist(0, target, arr, dp);
    }

    static ArrayList<Integer> topologicalSort(ArrayList<ArrayList<Integer>> adj) {
        // Your code here
        int n = adj.size();
        int[] indegree = new int[n];

        // find indegree
        for(int i = 0; i < n; i++) {
            int vertexCount = adj.get(i).size();
            for(int j = 0; j < vertexCount; j++) {
                indegree[adj.get(i).get(j)]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        ArrayList<Integer> ans = new ArrayList<>();
        //add vertex with 0 indegree into queue
        for(int i = 0; i < n; i++) {
            if(indegree[i] == 0) {
                q.add(i);
            }
        }

        while(q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int vtx = q.remove();
                ans.add(vtx);

                for(int e : adj.get(vtx)) {
                    if(--indegree[e] == 0) {
                        q.add(e);
                    }
                }
            }
        }

        return ans;
    }

    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
        // code here
        int n = adj.size();

        int[] indegree = new int[n];
        // calculate indegree
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < adj.get(i).size(); j++) {
                indegree[adj.get(i).get(j)]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            if(indegree[i] == 0) q.add(i);
        }

        // Ans list
        ArrayList<Integer> ans = new ArrayList<>();
        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int vtx = q.remove();
                ans.add(vtx);

                for(int e : adj.get(vtx)) {
                    if(--indegree[e] == 0) q.add(e);
                }
            }
        }

        return q.size() != n ? true : false;
    }

    // https://leetcode.com/problems/course-schedule/
    public boolean canFinish(int n, int[][] prerequisites) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        int[] indegree = new int[n];

        for(int[] e : prerequisites) {
            g[e[0]].add(e[1]);
            indegree[e[1]]++;
        }

        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            if(indegree[i] == 0) q.add(i);
        }

        ArrayList<Integer> ans = new ArrayList<>();
        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int vtx = q.remove();
                ans.add(vtx);

                for(int e : graph[vtx]) {
                    if(--indegree[e] == 0) q.add(e);
                }
            }
        }

        return ans.size() != n ? false : true;
    }

    // https://leetcode.com/problems/course-schedule-ii/
    public int[] findOrder(int n, int[][] prerequisites) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        int[] indegree = new int[n];

        for(int[] e : prerequisites) {
            g[e[0]].add(e[1]);
            indegree[e[1]]++;
        }

        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            if(indegree[i] == 0) q.add(i);
        }

        ArrayList<Integer> ans = new ArrayList<>();
        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int vtx = q.remove();
                ans.add(vtx);

                for(int e : graph[vtx]) {
                    if(--indegree[e] == 0) q.add(e);
                }
            }
        }

        int[] arr = new int[ans.size()];
        for(int i = 0; i < ans.size(); i++) arr[i] = ans.get(i);
        return arr;
    }

    // https://leetcode.com/problems/find-eventual-safe-states/
    public List<Integer> eventualSafeNodes(int[][] g) {
        int n = g.length;
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < g[i].length; j++) {
                graph[g[i][j]].add(i);
            }
        }

        int[] indegree = new int[n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < graph[i].size(); j++) {
                indegree[graph[i].get(j)]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            if(indegree[i] == 0) q.add(i);
        }

        List<Integer> ans = new ArrayList<>();
        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int vtx = q.remove();
                ans.add(vtx);

                for(int e : graph[vtx]) {
                    if(--indegree[e] == 0) q.add(e);
                }
            }
        }

        Collections.sort(ans);
        return ans;
    }

    // shortest path algorith and problems

    public int[] shortestPath(ArrayList<ArrayList<Integer>> adj, int src) {
        // code here
        int n = adj.size();
        int[] dist = new int[n];
        Arrays.fill(dist, (int)1e9);

        Queue<int[]> q = new ArrayDeque<>();
        dist[src] = 0;
        q.add(new int[]{src, 0});

        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int[] node = q.remove();
                int vtx = node[0];
                int currDist = node[1];

                // dist[vtx] = dist;
                ArrayList<Integer> edge = adj.get(vtx);
                for(int e: adj.get(vtx)) {
                    int newDist = currDist + 1;

                    if(newDist < dist[e]) {
                        q.add(new int[] {e, newDist});

                        dist[e] = Math.min(dist[e], newDist);
                    }
                }
            
            }
        }

        for(int i = 0; i < n; i++) {
            if(dist[i] == (int)1e9) dist[i] = -1;
        }

        return dist;
    }

    // https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph/1?utm_source=youtube&utm_medium=collab_striver_ytdescription&utm_campaign=shortest-path-in-undirected-graph
    public int[] shortestPath(int V, int E, int[][] edges) {
        // Code here
        ArrayList<int[]> [] graph = new ArrayList[V];
        for(int i = 0; i < V; i++) graph[i] = new ArrayList<>();

        for(int[] edge: edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        // make dist array to store the dist for each node.
        // make priority que to sort data based on distance.
        int[] dist = new int[V];
        Arrays.fill(dist, (int)1e9);
        dist[0] = 0;
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        q.add(new int[]{0, 0});

        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int[] node = q.remove();
                int vtx = node[0];
                int weight = node[1];

                for(int[] edge: graph[vtx]) {
                    int newVtx = edge[0];
                    int newDist = edge[1] + weight;

                    if(newDist < dist[newVtx]) {
                        q.add(new int[]{newVtx, newDist});
                        dist[newVtx] = newDist;
                    }
                }
            }
        }

        for(int i = 0; i < V; i++) {
            if(dist[i] == (int)1e9) dist[i] = -1;
        }

        return dist;
    }

    // https://www.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1
    ArrayList<Integer> dijkstra(ArrayList<ArrayList<iPair>> adj, int src) {
        // Write your code here
        int n = adj.size();

        int[] dist = new int[n];
        Arrays.fill(dist, (int)1e9);
        dist[src] = 0;
        // PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{src, 0});

        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int[] node = q.remove();
                int vtx = node[0];
                int weight = node[1];

                for(iPair p: adj.get(vtx)) {
                    int newVtx = p.first;
                    int newDist = weight + p.second;

                    if(newDist < dist[newVtx]) {
                        q.add(new int[]{newVtx, newDist});
                        dist[newVtx] = newDist;
                    }
                }
            }
        }

        ArrayList<Integer> finalAns = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(dist[i] == (int)1e9){
                finalAns.add(-1);
            }
            else {
                finalAns.add(dist[i]);
            }
        }

        return finalAns;
    }


    // https://leetcode.com/problems/shortest-path-in-binary-matrix/
    int[][] dirs = new int[][] {{-1, 0}, {-1, 1}, {0, 1}, {1, 1},
                                    {1, 0}, {1, -1}, {0, -1}, {-1, -1}
                                };
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if(grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});

        int level = 0;
        while (q.size() > 0) {
            int size = q.size();
            while (size-- > 0) {
                int[] node = q.remove();
                int row = node[0];
                int col = node[1];
                if(row == n-1 && col == n-1) return level+1;

                for(int[] dir: dirs) {
                    int nRow = row + dir[0];
                    int nCol = col + dir[1];

                    if(nRow >= 0 && nRow < n && nCol >= 0 && nCol < n && grid[nRow][nCol] == 0) {
                        q.add(new int[]{nRow, nCol});
                    }
                }
            }
            level++;
        }
        return -1;
    }


    public static int largest(int[] arr) {
        // code here
        int max = -(int)1e9;
        for(int ele: arr) max = Math.max(max, ele);
        return max;
    }

    public int getSecondLargest1(int[] arr) {
        // Code Here
        int max = -1;
        int secondMax = -1;
        for(int ele: arr) {
            max = Math.max(max, ele);
        }

        for(int ele : arr) {
            if(ele > max && ele < secondMax) secondMax = Math.max(secondMax, ele);
        }
        return secondMax;
    }

    public boolean check(int[] nums) {
        int count = 0;
        for(int i = 0 ; i < nums.length-1; i++) {
            if(nums[i] > nums[i+1]) count++;
            if(count > 1) return false;
        }

        if(nums[n-1] > nums[0]) return false;
    }

    int[] parent;

    public static int findParent(int u) {
        if(u == parent[u]) return u;
        return parent[u] = findParent(parent[u]);
    }

    static int spanningTree(int V, int E, List<List<int[]>> adj) {
        // Code Here.
        List<int[]> edges = new ArrayList<>();
        parent = new int[V+1];
        for(int i = 0; i < V+1; i++) {
            parent[i] = i;
        }

        for(int i = 0; i < V; i++) {
            for(int j = 0; j < adj.get(i).size(); j++) {
                int[] edge = adj.get(i).get(j);
                edges.add(new int[]{i, edge[0], edge[1]});
            }
        }

        Collections.sort(edges, (a, b) -> a[2] - b[2]);
        int cost = 0;
        for(int[] e: edges) {
            int p1 = findParent(e[0]);
            int p2 = findParent(e[1]);

            if(p1 != p2) {
                parent[p1] = p2;
                cost += e[2];
            }
        }

        return cost;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        boolean[][] visited = new boolean[n][m];

        List<Integer> ans = new ArrayList<>();
        int idx = 0;
        int row = 0, col = 0;

        for(int i = 0; i < m*n; i++) {
            ans.add(matrix[row][col]);
            visited[row][col] = true;

            int nRow = row + dirs[idx][0];
            int nCol = col + dirs[idx][1];

            if(nRow >= 0 && nRow < n && nCol >= 0 && nCol < m && !visited[nRow][nCol]) {
                row = nRow;
                col = nCol;
            }
            else {
                idx = (idx + 1)%4;
                row += dirs[idx][0];
                col += dirs[idx][1];
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        
    }
}