import java.util.HashMap;

public class Jan09Y2025 {
    public int islandPerimeter(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int nbrs = 0;
        int oneCount = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 1) {
                    for(int[] dir: dirs) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        oneCount++;
                        if(x >= 0 && y >= 0 && x < n && y < m && grid[x][y] == 1) {
                            nbrs++;
                        }
                    }
                }
            }
        }

        return 4*oneCount - nbrs;
    }

    // https://leetcode.com/problems/surrounded-regions/
    int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public void markZeroesDfs(int r, int c, char[][] board) {

        board[r][c] = 'X';
        for(int[] dir: dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            oneCount++;
            if(x >= 0 && y >= 0 && x < n && y < m && board[x][y] == 'O') {
                markZeroesDfs(x, y, board);
            }
        }
    }

    public void solve(char[][] board) { 
        int n = board.length;
        int m = board[0].length;
        
        // traverse matrix and mark outer 0 => #
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(i == 0 || j == 0 || i == n-1 || j == m-1) {
                    board[i][j] = '#';
                }
            }
        }

        // run dfs
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] == 'O') {
                    markZeroesDfs(i, j, grid);
                }
            }
        }

        // unmark
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] = '#') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    int[] parent;
    private int findParent(int v) {
        if(parent[v] == v) return v;
        return parent[v] = findParent(parent[v]);
    }

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n+1];
        for(int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        for(int[] e: edges) {
            int p1 = findParent(e[0]);
            int p2 = findParent(e[1]);

            if(p1 != p2) {
                parent[p1] = p2;
            }
            else {
                return e;
            }
        }
        return new int[]{};
    }

    private HashMap<String, String> parent;
    private HashMap<String, Integer> size;

    private String findParent(String v) {
        if(!parent.get(v).equals(v)) {
            parent.put(v, findParent(v));
        }

        return parent.put(v);
    }

    private void merge(String p1, String p2) {
        if(size.get(p1) > size.get(p2)) {
            parent.put(p2, p1);
            size.put(p1, size.get(p2) + size.get(p1));
        }
        else {
            parent.put(p1, p2);
            size.put(p2, size.get(p2) + size.get(p1));
        }
    }

    public int removeStones(int[][] stones) {
        int n = stones.length;
        parent = new HashMap<>();
        size = new HashMap<>();

        for(int[] stone: stones) {
            String key = stone[0]+" "+stone[1];
            parent.put(key, key);
            size.put(key, 1);
        }

        int count = n;
        for(int[] s1: stones) {
            int key1 = s1[0]+" "+s1[1];
            for(int[] s2: stones) {
                int key2 = s2[0]+" "+s2[1];

                String p1 = findParent(key1);
                String p2 = findParent(key2);

                if(p1.equals(p2)) {
                    continue;
                }
                esle {
                    count--;
                    merge(p1, p2);
                }
            } 
        }
        return n - count;
    }

    public int removeStones(int[][] stones) {
        int row = 0, col = 0;
        for(int[] s: stones) {
            row = Math.max(row, s[0]);
            col = Math.max(col, s[1]);
        }

        row += 1;
        col += 1;
        int parentSize = row+col;
        parent = new int[parentSize+1];

        // initialize parent
        for(int i = 0; i <= parentSize; i++) {
            parent[i] = i;
        }

        int n = stones.length;
        int count = n;
        for(int[] s1: stones) {
            int key1 = row + s1[1];

            for(int[] s2: stones) {
                int key2 = row + s2[1];
                if(s1[0] == s2[0] || s1[1] == s2[1]) {
                    int p1 = findParent(key1);
                    int p2 = findParent(key2);
                    if(p1 != p2) {
                        count--;
                        parent[p1] = p2;
                    }
                    else continue;
                }
            }
        }

        return n - count;
    }

    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
