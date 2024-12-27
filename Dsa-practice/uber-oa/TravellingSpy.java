import java.util.*;

public class TravellingSpy {
    private static final int MOD = 1000000007;
    private static long factorialCache[]; // Cache for factorials mod MOD

    // tc-1
    // 4 6
    // 1 2
    // 3 2
    // 1 4
    // 3 4
    // 3 1
    // 2 4

    // tc-2
    // 4 4
    // 1 2
    // 1 3
    // 2 3
    // 2 4


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read inputs
        int N = sc.nextInt(); // Number of countries
        int M = sc.nextInt(); // Number of routes

        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adjList.add(new ArrayList<>());
        }

        // Read routes and populate adjacency list
        for (int i = 0; i < M; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();
            adjList.get(A).add(B);
            adjList.get(B).add(A);
        }

        // Precompute factorials mod MOD
        factorialCache = new long[N + 1];
        factorialCache[0] = 1;
        for (int i = 1; i <= N; i++) {
            factorialCache[i] = (factorialCache[i - 1] * i) % MOD;
        }

        // Find all connected components
        boolean[] visited = new boolean[N + 1];
        List<Integer> componentSizes = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                int size = dfs(i, adjList, visited);
                componentSizes.add(size);
            }
        }

        // Compute the total permutations as the product of permutations of each component
        long result = 1;
        for (int size : componentSizes) {
            result = (result * factorialCache[size]) % MOD;
        }

        System.out.println(result);
    }

    // DFS to find the size of a connected component
    private static int dfs(int node, List<List<Integer>> adjList, boolean[] visited) {
        visited[node] = true;
        int size = 1;
        for (int neighbor : adjList.get(node)) {
            if (!visited[neighbor]) {
                size += dfs(neighbor, adjList, visited);
            }
        }
        return size;
    }
}
