package contest;

import java.util.ArrayList;
import java.util.HashMap;

public class Contest430 {
    public int minimumOperations(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        if(n == 1) return 0;

        int count = 0;
        for(int j = 0; j < m; j++) {
            for(int i = 1; i < n; i++) {
                if(grid[i-1][j] > grid[i][j]) {
                    int newElement = grid[i-1][j] - grid[i][j] + 1;
                    count += newElement;
                    grid[i][j] = newElement;
                }
            }
        }

        return count;
    }

    public String answerString(String word, int numFriends) {
        int n = word.length();
        if(numFriends == 1) return word;
        
        int idxOfLargestChar = 0;

        for(int i = 1; i < n; i++) {
            if(word.charAt(i) > word.charAt(idxOfLargestChar)) {
                idxOfLargestChar = i;
            }
        }    

        // find all index of largest character
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(word.charAt(i) == word.charAt(idxOfLargestChar)) list.add(i);
        }
       
        String ans = "";
        for(int idx: list) {
            int diff = n - numFriends;
            String currAns = word.substring(idx, Math.min(n, idx+diff+1));
            if (currAns.compareTo(ans) > 0) {
                ans = currAns;
            }
        }

        return ans;
    }

    public long countNumberOfSubSequence(int idx, int[] nums, ArrayList<Integer> list, HashMap<String, Long> memo) {
        // Base case: if idx is out of bounds
        if (idx >= nums.length) {
            if (list.size() == 4) {
                if (nums[list.get(0)] * nums[list.get(2)] == nums[list.get(1)] * nums[list.get(3)]) {
                    return 1;
                }
            }
            return 0;
        }

        // Create a key for memoization
        String key = idx + "-" + list.toString();
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // Include the current index in the subsequence
        list.add(idx);
        long take = countNumberOfSubSequence(idx + 2, nums, list, memo);
        list.remove(list.size() - 1); // Backtrack

        // Exclude the current index from the subsequence
        long notTake = countNumberOfSubSequence(idx + 1, nums, list, memo);

        // Store result in memo
        long result = take + notTake;
        memo.put(key, result);
        return result;
    }

    public long numberOfSubsequences(int[] nums) {
        int n = nums.length;
        long[][] dp = new long[n + 1][5]; // dp[i][j] represents the count of ways to pick j elements from first i elements

        // Base case: 0 ways to pick any subsequence except for picking 0 elements
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1; // 1 way to pick 0 elements: pick none
        }

        // Populate the DP table
        for (int i = 1; i <= n; i++) {
            for (int count = 1; count <= 4; count++) {
                // Case 1: Include nums[i-1] in the subsequence
                dp[i][count] += dp[i - 2][count - 1];

                // Case 2: Exclude nums[i-1] from the subsequence
                dp[i][count] += dp[i - 1][count];
            }
        }

        // Check all combinations of 4-element subsequences
        long validSubsequences = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                for (int k = j + 1; k <= n; k++) {
                    for (int l = k + 1; l <= n; l++) {
                        if (nums[i - 1] * nums[k - 1] == nums[j - 1] * nums[l - 1]) {
                            validSubsequences++;
                        }
                    }
                }
            }
        }

        return validSubsequences;
    }
    public long numberOfSubsequences(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<String, Long> memo = new HashMap<>();
        return countNumberOfSubSequence(0, nums, list, memo);
    }

    public static void main(String[] args) {
        System.out.println("Hello leetcode contest 430");
    }
}
