import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jan02Y2025 {
    
    // problem-1
    // https://www.geeksforgeeks.org/problems/power-set4302/1
    /**
     * Generate all possible subsequence using power set
     * loop 1 -> 2^n-1
     *  inner loop for j = 0 -> n and make the current subsequence and add into list
     * @param s
     * @return
     */
    public List<String> AllPossibleStrings(String s)
    {
        // Code here
        int n = s.length();
        int subSeqCount = (int) Math.pow(2, n)-1;
        List<String> ans = new ArrayList<>();

        for(int i = 1; i <= subSeqCount; i++) {
            StringBuilder myAns = new StringBuilder("");

            for(int j = 0; j < n; j++) {
                if((i & (1 << j)) != 0) {
                    myAns.append(s.charAt(j));
                }
            }
            ans.add(myAns.toString());
        }

        Collections.sort(ans);
        return ans;
    }

    // https://leetcode.com/problems/delete-operation-for-two-strings/description/
    // problem-2
    private int findLcs(String s1, String s2) {
        int N = s1.length();
        int M = s2.length();

        int[][] dp = new int[N+1][M+1];

        for(int i = 0; i <= N; i++) {
            for(int j = 0; j <= M; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] == 0;
                    continue;
                }

                if(s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                }
                else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[N][M];
    }

    public int minDistance(String word1, String word2) {
        int lcs = findLcs(word1, word2);
        return word1.length() + word2.length() - 2*lcs;
    }

    // https://leetcode.com/problems/longest-common-subsequence/description/
    // problem-3
    public int longestCommonSubsequence(String text1, String text2) {
        return findLcs(text1, text2);
    }

    // problem-4
    // https://leetcode.com/discuss/interview-question/1273766/longest-common-substring
    /**
     * i and j: current indices in text1 and text2.
        count: length of the current matching substring.
        Base Case: If either string is fully traversed, return the current count.
        Recursive Calls: If characters match, increase the count and continue.
        Otherwise, reset the count and explore other branches (moving one index back in either string).
        eg. abcpedf/abcdf for dry run
     * @param text1
     * @param text2
     * @return
     */

    public int longestCommonSubstring(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][][] memo = new int[n + 1][m + 1][Math.min(n, m) + 1];
        for (int[][] layer : memo) {
            for (int[] row : layer) {
                Arrays.fill(row, -1);
            }
        }
        return solve(text1, text2, n, m, 0, memo);
    }

    private int solve(String s1, String s2, int i, int j, int count, int[][][] memo) {
        if (i == 0 || j == 0) {
            return count;
        }
        if (memo[i][j][count] != -1) {
            return memo[i][j][count];
        }

        int maxCount = count;
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            maxCount = solve(s1, s2, i - 1, j - 1, count + 1, memo);
        }
        
        int resetCount1 = solve(s1, s2, i - 1, j, 0, memo);
        int resetCount2 = solve(s1, s2, i, j - 1, 0, memo);

        memo[i][j][count] = Math.max(maxCount, Math.max(resetCount1, resetCount2));
        return memo[i][j][count];
    }

    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
