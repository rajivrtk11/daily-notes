import java.util.*;

public class Solution {
    public static int countSuccessfulSelections(List<Integer> styleType, List<Integer> topicType, int k) {
        int n = styleType.size();
        int m = topicType.size();
        
        // Base case checks
        if (n < m || m == 0 || k > m || k <= 0) {
            return 0;
        }
        
        int ans = 0;
        
        // Try each possible starting position for consecutive windows
        for (int start = 0; start <= n - m; start++) {
            int matches = 0;
            boolean[] usedCreator = new boolean[m];
            boolean[] usedTopic = new boolean[m];
            
            // Try matching each topic with a creator in the current window
            for (int t = 0; t < m; t++) {  // For each topic
                for (int c = 0; c < m; c++) {  // Try each creator position in window
                    if (!usedCreator[c] && !usedTopic[t] && 
                        styleType.get(start + c) == topicType.get(t)) {
                        matches++;
                        usedCreator[c] = true;
                        usedTopic[t] = true;
                        break;
                    }
                }
            }
            
            // If we found enough matches in this window
            if (matches >= k) {
                ans++;
            }
        }
        
        return ans;
    }
       public static void main(String[] args) {
        // Test Case Runner
        runTestCases();
    }
    
    public static void runTestCases() {
        // Sample Test Case 0
        testCase("Sample Test Case 0",
                Arrays.asList(4, 2, 2, 5, 2, 3),  // styleType
                Arrays.asList(2, 2, 4),           // topicType
                2,                                // k
                2);                               // expected output
        
        // Sample Test Case 1
        testCase("Sample Test Case 1",
                Arrays.asList(4, 1, 5, 6, 1),     // styleType
                Arrays.asList(6),                  // topicType
                1,                                 // k
                1);                               // expected output
        
        // Test Case 2: Example from problem description
        testCase("Test Case 2",
                Arrays.asList(4, 1, 2, 3, 4, 5, 6),  // styleType
                Arrays.asList(1, 2, 3, 4),           // topicType
                3,                                   // k
                4);                                  // expected output
        
        // Test Case 3: Edge case with minimum values
        testCase("Test Case 3 (Edge Case - Minimum Values)",
                Arrays.asList(1, 1),                 // styleType
                Arrays.asList(1),                    // topicType
                1,                                   // k
                1);                                  // expected output
        
        // Test Case 4: Edge case with no possible matches
        testCase("Test Case 4 (No Possible Matches)",
                Arrays.asList(1, 2, 3),              // styleType
                Arrays.asList(4, 4),                 // topicType
                1,                                   // k
                0);                                  // expected output
        
        // Test Case 5: Large values within constraints
        testCase("Test Case 5 (Large Values)",
                Arrays.asList(100000, 90000, 80000, 70000),  // styleType
                Arrays.asList(60000, 50000),                 // topicType
                2,                                           // k
                0);                                          // expected output
    }
    
    private static void testCase(String testName, List<Integer> styleType, List<Integer> topicType, int k, int expected) {
        System.out.println("\nRunning " + testName + ":");
        System.out.println("Input:");
        System.out.println("styleType: " + styleType);
        System.out.println("topicType: " + topicType);
        System.out.println("k: " + k);
        
        int result = countSuccessfulSelections(styleType, topicType, k);
        
        System.out.println("Expected Output: " + expected);
        System.out.println("Actual Output: " + result);
        System.out.println("Test " + (result == expected ? "PASSED" : "FAILED"));
    }
}