import java.util.*;

// Main class with test cases
public class Main1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case
        // int[][] bubbles = {
        //     {1, 1, 1},
        //     {2, 2, 2},
        //     {1, 1, 1}
        // };
        int[][] bubbles = {
            {3, 1, 2, 1},
            {1, 1, 1, 4},
            {3, 1, 2, 2,},
            {3, 3, 3, 4}
        };

        {
            {0, 0, 0, 1},
            {0, 0, 0, 4},
            {0, 0, 2, 2},
            {3, 0, 2, 4}
        };



        
        System.out.println("Initial board:");
        printBoard(bubbles);
        
        int[][] result = solution.popBubbles(bubbles);
        
        System.out.println("\nFinal board after explosions:");
        printBoard(result);
    }
    
    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}

// Solution class (previous implementation)
class Solution {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    public int[][] popBubbles(int[][] bubbles) {
        if (bubbles == null || bubbles.length == 0) return bubbles;
        
        int rows = bubbles.length;
        int cols = bubbles[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            result[i] = bubbles[i].clone();
        }
        
        boolean[][] marked = new boolean[rows][cols];
        boolean explosionOccurred;
        
        do {
            explosionOccurred = false;
            marked = new boolean[rows][cols];
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (result[i][j] != 0 && !marked[i][j] && shouldExplode(result, i, j)) {
                        markConnectedBubbles(result, marked, i, j, result[i][j]);
                        explosionOccurred = true;
                    }
                }
            }
            
            if (explosionOccurred) {
                removeBubbles(result, marked);
                applyGravity(result);
            }
            
        } while (explosionOccurred);
        
        return result;
    }
    
    private boolean shouldExplode(int[][] board, int row, int col) {
        int color = board[row][col];
        int sameColorNeighbors = 0;
        
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            if (isValid(board, newRow, newCol) && board[newRow][newCol] == color) {
                sameColorNeighbors++;
                if (sameColorNeighbors >= 2) return true;
            }
        }
        
        return false;
    }
    
    private void markConnectedBubbles(int[][] board, boolean[][] marked, int row, int col, int color) {
        if (!isValid(board, row, col) || board[row][col] != color || marked[row][col]) {
            return;
        }
        
        marked[row][col] = true;
        
        for (int[] dir : DIRECTIONS) {
            markConnectedBubbles(board, marked, row + dir[0], col + dir[1], color);
        }
    }
    
    private void removeBubbles(int[][] board, boolean[][] marked) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (marked[i][j]) {
                    board[i][j] = 0;
                }
            }
        }
    }
    
    private void applyGravity(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        
        for (int col = 0; col < cols; col++) {
            int writePos = rows - 1;
            
            for (int row = rows - 1; row >= 0; row--) {
                if (board[row][col] != 0) {
                    int temp = board[row][col];
                    board[row][col] = 0;
                    board[writePos][col] = temp;
                    writePos--;
                }
            }
        }
    }
    
    private boolean isValid(int[][] board, int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }
}