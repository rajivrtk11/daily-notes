import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jan04Y2025 {
    // Problem-1
    // https://leetcode.com/problems/maximum-product-subarray/description/
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int max = 1;
        int curr = 1;

        for(int i = 0; i < n; i++) {
            curr = nums[i]*curr;
            max = Math.max(max, curr);
            if(nums[i] == 0) curr = 1;
        }

        curr = 1;

        for(int i = n-1; i >= 0; i--) {
            curr = nums[i]*curr;
            max = Math.max(max, curr);
            if(nums[i] == 0) curr = 1;
        }

        return max;
    }   

    // Problem-2
    // https://leetcode.com/problems/maximum-product-subarray/description/
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int buy = nums[0], profit = 0;

        for(int ele: nums) {
            profit = Math.max(ele - buy, profit);
            buy = Math.min(buy, ele);
        }

        return profit;
    }

    // Problem-3
    private int maxProfitRec(int idx, int buy, int[] prices, int[][] dp) {
        if(idx == prices.length) return 0;
    
        if(dp[idx][buy] != -1) return dp[idx][buy];

        if(buy == 0) {
            return dp[idx][buy] = Math.max(
                -prices[idx] + maxProfitRec(idx+1, 1, prices, dp),
                0 + maxProfitRec(idx+1, buy, prices, dp)
            );
        }
        else {
            return dp[idx][buy] =  Math.max(
                prices[idx] + maxProfitRec(idx+1, 0, prices, dp),
                0 + maxProfitRec(idx+1, 1, prices, dp)
            );
        }
    }

    private int maxProfitTab(int Idx, int buy, int[] prices, int[][] dp) {
        
        for(int idx = Idx; idx >= 0; idx--) {
            for(int buy = 1; buy >= 0; buy--) {
                if(idx == prices.length){
                    dp[idx][buy] = 0;
                    continue;
                }
        
                if(buy == 0) {
                    dp[idx][buy] = Math.max(
                        -prices[idx] + dp[idx+1][1],
                        0 + dp[idx+1][buy]
                    );
                }
                else {
                    dp[idx][buy] =  Math.max(
                        prices[idx] + dp[idx+1][0],
                        0 + dp[idx+1][1]
                    );
                }
            }
        }

        return dp[0][0];
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];

        // for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        // return maxProfitRec(n-1, 0, prices, dp);
        return maxProfitRec(n, 0, prices, dp);
    }

    // Problem-4
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
    private int maxProfitRec(int idx, int buy, int k, int[] prices) {
        if(k == 0 || idx == prices.length) return 0;

        if(buy == 0) {
            return Math.max(
                -prices[idx] + maxProfitRec(idx+1, 1, k, prices),
                0 + maxProfitRec(idx+1, 0, k, prices)
            );
        }
        else {
            return Math.max(
                prices[idx] + maxProfitRec(idx+1, 0, k-1, prices),
                0 + maxProfitRec(idx+1, 1, k, prices)
            );
        }
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;

        return maxProfitRec(0, 0, 2, prices);
    }

    private int maxProfitMem(int idx, int buy, int k, int[] prices, int[][][] dp) {
        if(k == 0 || idx == prices.length) return 0;

        if(dp[idx][buy][k] != -1) return dp[idx][buy][k];

        if(buy == 0) {
            return dp[idx][buy][k] = Math.max(
                -prices[idx] + maxProfitRec(idx+1, 1, k, prices, dp),
                0 + maxProfitRec(idx+1, 0, k, prices, dp)
            );
        }
        else {
            return dp[idx][buy][k] = Math.max(
                prices[idx] + maxProfitRec(idx+1, 0, k-1, prices, dp),
                0 + maxProfitRec(idx+1, 1, k, prices, dp)
            );
        }
    }

    // Problem-5
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        for(int i = 0; i < n+1; i++) {
            for(int j = 0; j < 2; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        int[][][] dp = new int[n+1][2][k+1];
        return maxProfitRec(0, 0, k, prices, dp);
    }

    // Problem-6
    // https://www.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1?utm_source=youtube&utm_medium=collab_striver_ytdescription&utm_campaign=matrix-chain-multiplication
    public static int matrixMultiplicationRec(int si, int ei, int[] arr, int[][] dp) {
        if(si+1 == ei) return 0;

        if(dp[si][ei] != -1) return dp[si][ei];

        int minCost = (int) 1e9;
        for(int cut = si+1; cut < ei; cut++) {
            int leftCost = matrixMultiplicationRec(si, cut, arr, dp);
            int rightCost = matrixMultiplicationRec(cut, ei, arr, dp);
            int myCost = leftCost + arr[si]*arr[cut]*arr[ei] + rightCost;

            minCost = Math.min(minCost, myCost);
        }

        return dp[si][ei] = minCost;
    }
    
    public static int matrixMultiplicationTab(int Si, int Ei, int[] arr, int[][] dp) {
        
        int n = arr.length;
        for(int gap = 1; gap < n; gap++) {
            for(int si = 0, ei = gap; si < n && ei < n; si++, ei++) {
                if(si+1 == ei) {
                    dp[si][ei] = 0;
                    continue;
                }

                int minCost = (int) 1e9;
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
        // for(int i = 0; i < n; i++) Arrays.fill(dp[i], -1);

        return matrixMultiplicationRec(0, n-1, arr, dp);
    }

    public long maxSubarraySum(int[] arr, int k) {
        int res = arr[0];
        int maxEnding = arr[0];

        for (int i = 1; i < arr.length; i++) {
            
            // Find the maximum sum ending at index i by either extending 
            // the maximum sum subarray ending at index i - 1 or by
            // starting a new subarray from index i
            int ele = arr[i];
            if(ele = k) ele = ele*(-1);

            maxEnding = Math.max(maxEnding + ele, ele);
          
            // Update res if maximum subarray sum ending at index i > res
            res = Math.max(res, maxEnding);
        }
        return res;
    }

    public long maxSubarraySum(int[] nums) {
        List<Integer> negativeNumber = new ArrayList<>();
        for(int ele: nums) {
            if(ele < 0) negativeNumber.add(ele);
        }

        long maxSum = -(long) 1e11;
        for(int ele : negativeNumber) {
            maxSum = Math.max(maxSum, maxSubarraySum(nums, ele));
        }

        return maxSum;
    }
    
    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
