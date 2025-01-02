package arraysAndString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Arrays {

    public int[][] merge(int[][] intervals) {
        Arrays.sort((a, b) -> a[1] - b[1]);

        int n = intervals.length;
        ArrayList<int[]> ans = new ArrayList<>();

        for(int si = 0; si < n; ) {
            int ei = i+1;
            while(ei < n && intervals[ei-1][1] <= intervals[ei][0]) {
                ei++;
            }
            ans.add(new int[]{intervals[si][0], intervals[ei-1][1]})
        }

        int[][] ansArr = new int[ans.size()][2];
        for(int i = 0; i < ans.size(); i++) {
            ansArr[i][0] = ans.get(i)[0];
            ansArr[i][1] = ans.get(i)[1];
        }

        return ansArr;
    }

    public boolean containsDuplicate(int[] nums) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        for(int ele: nums) {
            freq.put(ele, freq.getOrDefault(freq, 0)+1);
        }

        for(int keys: freq.keySet()) {
            if(freq.get(keys) > 1) return false;
        }

        return true;
    }

    public boolean containsDuplicate(int[] nums) {
        int sum = 0;
        for(int ele: nums) sum += ele;

        return n*(n+1)/2 != sum;
    }

    public int singleNumber(int[] nums) {
        int ans = 0, n = nums.length;
        for(int ele : nums) ans ^= ele;
        return ans;
    }

    public int findTargetSumWaysRec(int idx, int sum, int tar, int[] nums) {
        if(idx == nums.length) {
            if(sum == tar) return 1;
            return 0;
        }

        int count = 0;
        cout += findTargetSumWaysRec(idx+1, sum+nums[idx], tar, nums);
        cout += findTargetSumWaysRec(idx+1, sum-nums[idx], tar, nums);

        return count;
    }

    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int 
        return findTargetSumWaysRec(0, target, 2*target, nums);
    }

    public int countSubarrays(int[] nums) {
        int n = nums.length;
        int count = 0;
        for(int i = 1; i < n-2; i++) {
            if(nums[i] == 2*(nums[i-1] + nums[i+1])) count++;
        }    

        return count;
    }

    int mod = (int)1e9+7;
    public int countPath(int r, int c, int xor, int[][] grid, int k, int[][][] dp) {
        if(r == grid.length-1 && c == grid[0].length-1) {
            if((grid[r][c]^xor) == k) return 1;
            return 0; 
        }
        if(r >= grid.length || c >= grid[0].length) return 0;

        if(dp[r][c][xor] != -1) return dp[r][c];

        int count = 0;
        count += countPath(r, c+1, xor^grid[r][c], grid, k, dp);
        count += countPath(r+1, c, xor^grid[r][c], grid, k, dp);

        return dp[r][c][xor] = count%mod;
    }

    public int countPathTab(int R, int C, int XOR, int[][] grid, int K, int[][][] dp) {
        
        for(int r = R; r >= 0; r--) {
            for(int c = C; c >= 0; c--) {
                for(int xor = K; xor >= 0; xor--) {
                    if(r == grid.length-1 && c == grid[0].length-1 && xor == K) {
                        dp[r][c][grid[r][c]^K] = 1;
                        continue; 
                    }
            
                    int count = 0;
                    count += dp[r][c+1][xor^grid[r][c]];
                    count += dp[r+1][c][xor^grid[r][c]];
            
                    dp[r][c][xor] = count%mod;
                }
            }
        }

        return dp[0][0][0];
    }

    public int countPathsWithXorValue(int[][] grid, int k) {
        int n = grid.length, m = grid[0].length;
        int[][][] dp = new int[n+1][m+1][100000];
        
        return countPathTab(n-1, m-1, k, grid, 1000, dp);
    }

    public List<String> AllPossibleStrings(String s)
    {
        // Code here
        int n = s.length();
        int subSeqCount = Math.pow(2, n)-1;
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

    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}

