import java.util.ArrayList;
import java.util.List;

public class Jan12Y2025 {
    public List<Integer> zigzagTraversal(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        List<Integer> ans = new ArrayList<>();
        int col = 0;
        int dir = 0;
        int j = 0;
        for(int i = 0; i < n; i++) {
            if(dir == 0) {
                // left to right
                for(j = col; j < m; j += 2) {
                    ans.add(grid[i][j]);
                }  
                col = j == m-1 ? m - 2 : m-1; 
            }
            else {
                // right to left
                for(j = col; j >= 0; j -= 2) {
                    ans.add(grid[i][j]);
                }
                col = j == 0 ? 1 : 0;
            }
        }

        return ans;
    }

    public int findCoin(int r, int c, int netralization, int[][] coins) {
        if(r >= coins.length || c >= coins[0].length) {
            if(r == coins.length-1 && c == coins[0].length) {
                if(coins[r][c] < 0 && netralization > 0) return 0;
                return coins[r][c];
            }
            return -2000;
        }   

        int leftWithoutNeutral = coins[r][c] + findCoin(r, c+1, netralization, coins);
        int leftWithNeutral = coins[r][c] < 0 && netralization > 0 ? 0 + findCoin(r, c+1, netralization-1, coins) ? leftWithoutNeutral;
        int left = Math.max(leftWithoutNeutral, leftWithNeutral);

        int rightWithoutNeutral = coins[r][c] + findCoin(r+1, c, netralization, coins);
        int rightNeutral = coins[r][c] < 0 && netralization > 0 ? 0 + findCoin(r+1, c, netralization-1, coins);
        int right = Math.max(rightWithoutNeutral, rightNeutral);

        return Math.max(left, right);
    }

    public int maximumAmount(int[][] coins) {
        return findCoin(0, 0, 2, coins);
    }
    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
