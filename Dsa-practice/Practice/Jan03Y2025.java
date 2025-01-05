import java.util.Arrays;

public class Jan03Y2025 {
    // problem - 1
    // https://leetcode.com/problems/edit-distance/
    public int minDistanceRec(int n, int m, String s1, String s2, int[][] dp) {
        if(n < 0 || m < 0) {
            return n < 0 ? m+1 : n+1;
        }

        if(dp[n][m] != -1) return dp[n][m];

        if(s1.charAt(n) == s2.charAt(m)) return dp[n][m] = minDistanceRec(n-1, m-1, s1, s2, dp);
        else {
            return dp[n][m] = 1 + Math.min(minDistanceRec(n-1, m, s1, s2, dp),
                Math.min(minDistanceRec(n, m-1, s1, s2, dp), minDistanceRec(n-1, m-1, s1, s2, dp))
            );
        }
    }

    public int minDistanceTab(int N, int M, String s1, String s2, int[][] dp) {
        for(int n = 0; n <= N; n++) {
            for(int m = 0; m <= M; m++) {
                if(n == 0 || m == 0) {
                    dp[n][m] = n == 0 ? m : n;
                    continue;
                }

                if(s1.charAt(n-1) == s2.charAt(m-1)) dp[n][m] = dp[n-1][m-1];
                else {
                    dp[n][m] = 1 + Math.min(dp[n-1][m],
                        Math.min(dp[n][m-1], dp[n-1][m-1])
                    );
                }
            }
        }

        return dp[N][M];
    }

    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n+1][m+1];
        // for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        // return minDistanceRec(n-1, m-1, word1, word2, dp);

        return minDistanceTab(n, m, word1, word2, dp);
    }

    // problem - 2
    // https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
    private static int knapSackRec(int idx, int capacity, int[] val, int[] wt, int[][] dp) {
        if(idx < 0 || capacity <= 0) return 0;

        if(dp[idx][capacity] != -1) return dp[idx][capacity];

        int take = 0;
        if(capacity-wt[idx] >= 0)
            take = knapSackRec(idx-1, capacity-wt[idx], val, wt, dp) + val[idx];
        int notTake = knapSackRec(idx-1, capacity, val, wt, dp) + 0;

        return dp[idx][capacity] = Math.max(take, notTake);
    }
    
    static int knapSack(int capacity, int val[], int wt[]) {
        // code here
        int n = wt.length;
        int[][] dp = new int[n][capacity+1];
        for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);

        return knapSackRec(n-1, capacity, val, wt, dp);
    }


    // problem - 3
    // https://leetcode.com/problems/longest-increasing-subsequence/
    public int lisRec(int idx, int[] nums, int[] dp) {

        if(dp[idx] != -1) return dp[idx];

        int len = 0;
        for(int i = idx-1; i >= 0; i--) {
            if(nums[i] < nums[idx])
                len = Math.max(len, lisRec(i, nums, dp));
        }

        return dp[idx] = len+1;
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        int len  = 0;
        for(int i = n-1; i >= 0; i--) {
            len = Math.max(len, lisRec(i, nums, dp));
        }
        
        return len;
    }

    // tabulation method
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        int len = 1;
        Arrays.fill(dp, 1);
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = 1 + dp[j];
                    len = Math.max(len, dp[i]);
                }
            }
        }

        return len;
    }

    // problem - 4
    // https://leetcode.com/problems/number-of-longest-increasing-subsequence/description/
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];

        int len = 1;
        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);
        for(int i = 1; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = 1 + dp[j];
                    count[i] = count[j];
                    len = Math.max(len, dp[i]);
                }
                else if(nums[i] > nums[j] && dp[j] + 1 == dp[i]) {
                    count[i] += count[j];
                }
            }
        }

        int ans = 0;
        for(int i = 0; i < n; i++) {
            if(dp[i] == len) {
                ans += count[i];
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("Hello world"+ ans);
    }
}
