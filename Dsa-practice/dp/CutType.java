package dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class CutType {

    // https://www.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1?utm_source=youtube&utm_medium=collab_striver_ytdescription&utm_campaign=matrix-chain-multiplication
    static int findMatrixMultiplication(int si, int ei, int arr[], int[][] dp) {
        if(si+1 == ei) return 0;

        if(dp[si][ei] != -1) return dp[si][ei];

        int minCost = (int)1e9;
        for(int cut = si+1; cut < ei; cut++) {
            int leftCost = findMatrixMultiplication(si, cut, arr, dp);
            int rightCost = findMatrixMultiplication(cut, ei, arr, dp);
            int myCost = leftCost + arr[si]*arr[cut]*arr[ei] + rightCost;
            minCost = Math.min(minCost, myCost);
        }

        return dp[si][ei] = minCost;
    }

    static int findMatrixMultiplicationTab(int Si, int Ei, int arr[], int[][] dp) {
        int n = arr.length;

        for(int gap = 1; gap < n; gap++) {
            for(int si = 0, ei = gap; si < n && ei < n; si++, ei++) {
                if(si+1 == ei) {
                    dp[si+1][ei] = 0;
                    continue;
                }

                int minCost = (int)1e9;
                for(int cut = si+1; cut < ei; cut++) {
                    int leftCost = dp[si][cut];
                    int rightCost = dp[cut][ei];
                    int myCost = leftCost + arr[si]*arr[cut]*arr[ei] + rightCost;
                    minCost = Math.min(minCost, myCost);
                }

                dp[si][ei] = minCost;
            }
        }

        return dp[Si][Ei];
    }

    static int matrixMultiplication(int arr[]) {
        // code here
        int n = arr.length;
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);

        return findMatrixMultiplication(0, n-1, arr, dp);
    }

    // https://www.geeksforgeeks.org/problems/optimal-binary-search-tree2214/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
    static int optimalSearchTreeRec(int si, int ei, int level, int freq[], int[][][] dp) {
        if(si > ei) return 0;

        if(dp[si][ei][level] != -1) return dp[si][ei][level];

        int minCost = (int)1e9;
        for(int cut = si; cut <= ei; cut++) {
            int leftCost = si == cut ? 0 : optimalSearchTreeRec(si, cut-1, level+1, freq, dp);
            int rightCost = ei == cut ? 0 : optimalSearchTreeRec(cut+1, ei, level+1, freq, dp);

            int myCost = leftCost + rightCost + freq[cut]*level;
            minCost = Math.min(myCost, minCost);
        }
        return dp[si][ei][level] = minCost;
    }

    static int optimalSearchTree(int keys[], int freq[], int n)
    {
        // code here
        int[][][] dp = new int[n][n][n+1];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }


        int res = optimalSearchTreeRec(0, n-1, 1, freq, dp);
        return res;
    }

    public int minimumOperations(int[] nums) {
        int n = nums.length;
        int duplicateCount = 0;

        HashMap<Integer, Integer> hm = new HashMap<>();
        for(int ele: nums) {
            if(hm.containsKey(ele)) {
                hm.put(ele, hm.get(ele) + 1);
            }
            else hm.put(ele, 1);
        }
        
        int operations = 0;
        int si = 0;
        while(si < n) {
            int ei = si + 3;
            for(int i = si; i < ei && i+1 < n; i++) {
                hm.put(nums[i], hm.get(nums[i])-1);
            }
            
            // check duplicate still exist
            int duplicate = 0
            for(int keys: hm.keySet()) {
                if(hm.get(keys) > 1) duplicate++;
            }
            if(duplicate == 0) return operations+1;

            si = ei;
            operations++;
        }
        return operations;
    }

    public int maxDistinctElements(int[] nums, int k) {
        int n = nums.length;
        HashMap<Integer, Integer> hm = new HashMap<>();
        for(int ele: nums) {
            if(hm.containsKey(ele)) {
                hm.put(ele, hm.get(ele) + 1);
            }
            else hm.put(ele, 1);
        }

        int duplicate = 0
        for(int keys: hm.keySet()) {
            if(hm.get(keys) > 1) duplicate += hm.get(keys)-1;
        }

        HashSet<Integer> hs = new HashSet<>();
        int si = 0; 
        while (si <= k) {
            hs.add(si++);
        }

        // iterate through array and remove elements present in hs
        for(int ele: nums) {
            if(hs.contains(ele)) hs.remove(ele);
        }

        int uniqueCount = n - duplicate + hs.size();

        return uniqueCount >= n ? n : uniqueCount;
    }

    public int findMaxCoins(int si, int ei, int[] nums, int n) {
        int maxCoins = -(int)1e9;

        for(int cut = si; cut <= ei; cut++) {
            int leftVal = si-1 >= 0 ? nums[si-1] : 1;
            int rightVal = ei+1 < n ? nums[ei+1] : 1;

            int leftAns = si == cut ? 0 : findMaxCoins(si, cut-1, nums, n);
            int rightAns = ei == cut ? 0 : findMaxCoins(cut+1, ei, nums, n);
            
            int currentMaxCoins = leftAns + leftVal*nums[cut]*rightVal + rightAns;
            currentMaxCoins = Math.max(currentMaxCoins, maxCoins);
        }

        return maxCoins;
    }

    public int findMaxCoinsMemoized(int si, int ei, int[] nums, int n, int[][] dp) {

        if(dp[si][ei] != -1) return dp[si][ei];
        int maxCoins = -(int)1e9;

        for(int cut = si; cut <= ei; cut++) {
            int leftVal = si-1 >= 0 ? nums[si-1] : 1;
            int rightVal = ei+1 < n ? nums[ei+1] : 1;

            int leftAns = si == cut ? 0 : findMaxCoinsMemoized(si, cut-1, nums, n, dp);
            int rightAns = ei == cut ? 0 : findMaxCoinsMemoized(cut+1, ei, nums, n, dp);
            
            int currentMaxCoins = leftAns + leftVal*nums[cut]*rightVal + rightAns;
            maxCoins = Math.max(currentMaxCoins, maxCoins);
        }

        return dp[si][ei] = maxCoins;
    }

    public int findMaxCoinsTabulation(int Si, int Ei, int[] nums, int n, int[][] dp) {

        for(int gap = 0; gap < n; gap++) {
            for(int si = 0, ei = gap; si < n && ei < n; si++, ei++) {
                
                int maxCoins = -(int)1e9;
                for(int cut = si; cut <= ei; cut++) {
                    int leftVal = si-1 >= 0 ? nums[si-1] : 1;
                    int rightVal = ei+1 < n ? nums[ei+1] : 1;
        
                    int leftAns = si == cut ? 0 : dp[si][cut-1];
                    int rightAns = ei == cut ? 0 : dp[cut+1][ei];
                    
                    int currentMaxCoins = leftAns + leftVal*nums[cut]*rightVal + rightAns;
                    maxCoins = Math.max(currentMaxCoins, maxCoins);
                }
        
                dp[si][ei] = maxCoins;
            }
        }

        return dp[Si][Ei];
    }

    // https://leetcode.com/problems/burst-balloons/
    public int maxCoins(int[] nums) {
        int n = nums.length;

        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        return findMaxCoinsMemoized(0, n-1, nums, n, dp);
    }
    public static void main(String[] args) {
        System.out.println("Hello cut type dp");
    }
}
