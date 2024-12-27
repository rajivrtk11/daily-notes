public class MatrixWordSearch {

    // Method to count the number of valid words
    public static int countWords(String[] words, char[][] matrix) {
        int count = 0;
        for (String word : words) {
            if (canFormWord(word, matrix)) {
                count++;
            }
        }
        return count;
    }

    // Method to check if a word can be formed
    private static boolean canFormWord(String word, char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Check each position in the matrix as a starting point
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == word.charAt(0)) {
                    // Start DFS to find the word
                    if (dfs(matrix, word, 0, i, j, rows, cols, 0, false)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // DFS to search the word
    private static boolean dfs(char[][] matrix, String word, int index, int x, int y, int rows, int cols, int direction, boolean switchedDirection) {
        // Base case: if we have matched all characters
        if (index == word.length()) {
            return true;
        }

        // Check bounds and character match
        if (x < 0 || y < 0 || x >= rows || y >= cols || matrix[x][y] != word.charAt(index)) {
            return false;
        }

        // Temporarily mark the cell as visited
        char temp = matrix[x][y];
        matrix[x][y] = '#';

        boolean found = false;

        // Downward movement
        if (direction == 0 || direction == 1) { // Initial state or moving down
            found = dfs(matrix, word, index + 1, x + 1, y, rows, cols, 1, switchedDirection);
        }

        // Rightward movement
        if (!found && (direction == 0 || direction == 2)) { // Initial state or moving right
            found = dfs(matrix, word, index + 1, x, y + 1, rows, cols, 2, switchedDirection);
        }

        // Switching direction (only allowed once)
        if (!found && !switchedDirection) {
            if (direction == 1) { // Switch from down to right
                found = dfs(matrix, word, index + 1, x, y + 1, rows, cols, 2, true);
            } else if (direction == 2) { // Switch from right to down
                found = dfs(matrix, word, index + 1, x + 1, y, rows, cols, 1, true);
            }
        }

        // Restore the cell value after exploration
        matrix[x][y] = temp;
        return found;
    }

    // Main method to test the solution
    public static void main(String[] args) {
        char[][] matrix = {
            {'w', 'o', 'a', 'k'},
            {'r', 'r', 'a', 'm'},
            {'e', 'd', 'e', 'r'}
        };

        String[] words = {"word", "order", "worder"};
        int result = countWords(words, matrix);

        System.out.println("Number of valid words: " + result);
    }
}
