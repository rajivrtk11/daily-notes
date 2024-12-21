import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DisjointSet {
    int[] parent;
    int[] size;

    public int findParent(int v) {
        if(v == parent[v]) return v;
        return parent[v] = findParent(parent[v]);
    }

    public void merge(int p1, int p2) {
        if(size[p1] > size[p2]) {
            parent[p2] = parent[p1];
            size[p1] += size[p2];
        }
        else {
            parent[p1] = parent[p2];
            size[p2] += size[p1];
        }
    }

    // https://leetcode.com/problems/number-of-operations-to-make-network-connected/description/
    public int makeConnected(int n, int[][] connections) {
        parent = new int[n];
        size = new int[n]

        for(int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        int redundantcount = 0;
        for(int[] edge: connections) {
            int u = edge[0];
            int v = edge[1];

            int p1 = findParent(u);
            int p2 = findParent(v);
            if(p1 != p2) {
                merge(p1, p2);
            }
            else redundantcount++;
        }

        int groupCount = 0;
        for(int i = 0; i < n; i++) {
            if(parent[i] == i) groupCount++;
        }

        if(redundantcount < groupCount-1) return -1;
        return groupCount - 1;
    }

    // https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
    HashMap<String, String> parent;
    String findParent(String s) {
        if (!parent.get(s).equals(s)) {
            parent.put(s, find(parent, parent.get(s)));
        }
        return parent.get(s);
    }

    public int removeStones(int[][] stones) {
      int n = stones.length;
      parent = new HashMap<>();

      for(int[] s: stones) {
        String name = s[0]+" "+s[1];
        parent.put(name, name);
      }

      int count = n;
      for(int[] s1: stones) {
        for(int[] s2: stones) {
            if(s1[0] == s2[0] || s1[1] == s2[1]) {
                String ss1 = s1[0]+" "+s1[1];
                String ss2 = s2[0]+" "+s2[1];

                String p1 = findParent(ss1);
                String p2 = findParent(ss2);
                if(!p1.equals(p2)) {
                    parent.put(p1, p2);
                    count--;
                }
            }
        }
      }

      return n - count;
    }

    // https://leetcode.com/problems/accounts-merge/
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        HashMap<String, Integer> emailVsParent = new HashMap<>();
        parent = new int[n];
        size = new int[n];

        for(int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        for(int i = 0; i < accounts.size(); i++) {
            for(int j = 1; j < accounts.get(i).size(); j++) {
                if(!emailVsParent.containsKey(accounts.get(i).get(j))){
                    emailVsParent.put(accounts.get(i).get(j), i);
                }
                else {
                    int p1 = findParent(i);
                    int p2 = findParent(emailVsParent.get(accounts.get(i).get(j)));
                    merge(p1, p2);
                }
            }
        }

        HashMap<Integer, List<String>> nameVsEmailMapping = new HashMap<>();
        for(String keys: emailVsParent.keySet()) {
            int personIndex = emailVsParent.get(keys);
            int parentIndex = findParent(personIndex);

            if(!nameVsEmailMapping.containsKey(parentIndex)) {
                nameVsEmailMapping.put(parentIndex, new ArrayList<>());
            }

            nameVsEmailMapping.get(parentIndex).add(keys);
        }

        List<List<String>> ans = new ArrayList<>();
        for(int keys: nameVsEmailMapping.keySet()) {
            String name = accounts.get(keys).get(0);
            List<String> emailList = nameVsEmailMapping.get(name);

            if(emailList.size() > 0) {
                Collections.sort(emailList);
                emailList.add(0, name);
                ans.add(emailList);
            }
        }

        return ans;
    }

    // https://leetcode.com/problems/making-a-large-island/
    int[][] fourDirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        parent = new int[n*n];
        size = new int[n*n];

        for(int i = 0; i < n*n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1) {
                    for(int dir: fourDirs) {
                        int x = i + dir[0];
                        int y = j + dir[1];

                        if(x >= 0 && x < n && y >= 0 && y < n && grid[x][y] == 1) {
                            int p1 = findParent(i*n + j);
                            int p2 = findParent(x*n + y);

                            if(p1 != p2) {
                                merge(p1, p2);
                            }
                        }
                    }
                }
            }
        }

        int globalMax = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 0) {
                    int localMax = 0;
                    Map<Integer> trackDirection = new HashSet<>();

                    for(int dir: fourDirs) {
                        int x = i + dir[0];
                        int y = j + dir[1];

                        if(x >= 0 && x < n && y >= 0 && y < n && grid[x][y] == 1) {
                            int p2 = findParent(x*n + y);

                            if(!trackDirection.contains(p2))
                                localMax += size[p2];

                            trackDirection.add(p2);
                        }
                    }
                    globalMax = Math.max(localMax+1, globalMax);
                }
            }
        }

        return globalMax;
    }

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int ans = Math.max(grid[0][0], grid[n-1][n-1]);

        boolean[][] vis = new boolean[n][n];
        Queue<int[]> q = new ArrayDeque<>();
        // PriorityQueue<int[]> q = new PriorityQueue<>();
        q.add(new int[]{ans, 0, 0});
        vis[0][0] = true;

        while (q.size() > ) {
            int[] node = q.remove();
            ans = Math.max(ans, node[0]);

            for(int[] dir: fourDirs) {
                int r = node[1] + dir[0];
                int c = node[2] + dir[1];

                if(r >= 0 && r < n && c >= 0 && c < n && !vis[r][c]) {
                    if(r == n-1 && c == n-1) return ans;
                    q.add(new int[]{grid[r][c], r, c});
                    vis[r][c] = true;
                }
            }
        }

        return -1;
    }

    public int minimumEffortPath(int[][] heights) {
        int n = heights.length, m = heights[0].length;

        int[][] dist = new int[n][m];
        for(int i = 0; i < n; i++) Arrays.fill(dist[i], (int)1e9);
        // Queue<int[]> q = new ArrayDeque<>();
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> {
            return a[2] - b[2];
        });
        q.add(new int[]{0, 0, 0}); // r, c, diff

        int minDiff = (int)1e9;
        while (q.size() > 0) {
            int[] node = q.remove();

            int row = node[0], col = node[1], diff = node[2];
            for(int[] dir: fourDirs) {
                int nr = row + dir[0], nc = col + dir[1];

                if(nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    int ndiff = Math.max(diff, Math.abs(heights[nr][nc] - heights[row][col]));
                    if(nr == n-1 && nc == m-1) {
                        minDiff = Math.min(minDiff, ndiff);
                        continue;
                    }
                    
                    if(dist[nr][nc] < ndiff)
                     q.add(new int[]{nr, nc, ndiff});
                }
            }
        }

        return minDiff;
    }

    public static void main(String[] args) {

    }
}
