import java.util.LinkedList;
import java.util.Queue;

public class BubbleExplosion {
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    public static int[][] explodeBubbles(int[][] bubbles) {
        int rows = bubbles.length;
        int cols = bubbles[0].length;
        boolean[][] initialMarked = new boolean[rows][cols];
        boolean hasExplosion = true;

        // Step 1: Mark all bubbles eligible for explosion in the initial configuration
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (bubbles[i][j] != 0 && shouldExplode(bubbles, i, j, rows, cols)) {
                    markForExplosion(bubbles, initialMarked, i, j, rows, cols, bubbles[i][j]);
                }
            }
        }

        // Step 2: Continue processing until no explosions occur
        while (hasExplosion) {
            hasExplosion = false;
            boolean[][] toExplode = new boolean[rows][cols];

            // Copy the initial explosion marks
            for (int i = 0; i < rows; i++) {
                System.arraycopy(initialMarked[i], 0, toExplode[i], 0, cols);
            }

            // Explode the marked bubbles
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (toExplode[i][j]) {
                        bubbles[i][j] = 0;
                        hasExplosion = true;
                    }
                }
            }

            // Make bubbles fall after the explosion
            dropBubbles(bubbles, rows, cols);

            // Clear the initialMarked array to prevent further explosions
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    initialMarked[i][j] = false;
                }
            }
        }

        return bubbles;
    }

    private static boolean shouldExplode(int[][] bubbles, int x, int y, int rows, int cols) {
        int color = bubbles[x][y];
        int count = 0;

        for (int k = 0; k < 4; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (nx >= 0 && ny >= 0 && nx < rows && ny < cols && bubbles[nx][ny] == color) {
                count++;
            }
        }

        return count >= 2; // Explode only if 2+ neighbors have the same color
    }

    private static void markForExplosion(int[][] bubbles, boolean[][] toExplode, int x, int y, int rows, int cols, int color) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int cx = cell[0];
            int cy = cell[1];

            if (toExplode[cx][cy]) continue;

            toExplode[cx][cy] = true;

            for (int k = 0; k < 4; k++) {
                int nx = cx + dx[k];
                int ny = cy + dy[k];
                if (nx >= 0 && ny >= 0 && nx < rows && ny < cols && bubbles[nx][ny] == color && !toExplode[nx][ny]) {
                    queue.add(new int[]{nx, ny});
                }
            }
        }
    }

    private static void dropBubbles(int[][] bubbles, int rows, int cols) {
        for (int j = 0; j < cols; j++) {
            int writeIndex = rows - 1; // Start writing bubbles from the bottom
            for (int i = rows - 1; i >= 0; i--) {
                if (bubbles[i][j] != 0) {
                    bubbles[writeIndex][j] = bubbles[i][j];
                    if (writeIndex != i) { // Clear the original spot if it was moved
                        bubbles[i][j] = 0;
                    }
                    writeIndex--;
                }
            }

            // Fill remaining cells in the column with 0
            while (writeIndex >= 0) {
                bubbles[writeIndex][j] = 0;
                writeIndex--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] bubbles = {
            {3, 1, 2, 1},
            {1, 1, 1, 4},
            {3, 1, 2, 2},
            {3, 3, 3, 4}
        };

        int[][] result = explodeBubbles(bubbles);

        for (int[] row : result) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
