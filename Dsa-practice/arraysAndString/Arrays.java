package arraysAndString;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
