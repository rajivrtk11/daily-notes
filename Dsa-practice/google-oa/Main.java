import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.swing.tree.TreeNode;

public class Main {
    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int level = 1;
        int sum = root.val;
        int maxLevel = 1;

        while (q.size() > 0) {
            int size = q.size();
            int currentSum = 0;
            while (size-- > 0) {
                TreeNode rmNode = q.remove();
                currentSum += rmNode.val;

                if(rmNode.left != null ) q.add(rmNode.left);
                if(rmNode.right != null) q.add(rmNode.right);
            }
            if(currentSum > sum){
                sum = currentSum;
                maxLevel = level;
            }
            level = level+1;
        }
        return maxLevel;
    }

    public String licenseKeyFormatting(String s, int k) {
        String tStr = s.replaceAll("-", "");
        // abcdefghij => 10
        StringBuilder sb = new StringBuilder("");
        int n = tStr.length();

        while (n - k > 0) {
            if (sb.length() > 0)
                sb.insert(0, '-');
            sb.insert(0, tStr.substring(n - k, n));
            n -= k;
        }
        sb.insert(0, '-');
        sb.insert(0, tStr.substring(n - k, n));

        return sb.toString().toUpperCase();
    }

    public int dfs(int src, int n, String psf, int[][] graph, boolean[] vis) {
        if (psf.length() == n)
            vis[src] = true;
        int path = (int) 1e9;
        for (int e : graph[src]) {
            if (!vis[e])
                path = Math.min(path, dfs(e, n, psf + "" + e, graph, vis));
        }
    }

    public int shortestPathLength(int[][] graph) {
        int shortestPath = (int) 1e9;

        int n = graph.length;
        boolean[] vis = new boolean[n];

        for (int i = 0; i < n; i++) {
            shortestPath = Math.min(shortestPath, dfs(i, n, "", graph, vis));
        }

        return shortestPath;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int n = nums.length;
        HashMap<Integer, Integer> hm = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if (hm.containsKey(nums[i])) {
                int idx = hm.get(nums[i]);
                if (idx - i <= k)
                    return true;
            }
            hm.put(nums[i], i);
        }

        return false;
    }

    public List<String> summaryRanges(int[] nums) {
        int n = nums.length;
        List<String> ans = new ArrayList<>();

        int si = 0, ei = 0;
        while (ei < n) {
            if (ei + 1 < n && nums[ei] + 1 == nums[ei + 1]) {
                ei++;
            } else {
                if (ei == si) {
                    ans.add(ei + "");
                } else {
                    ans.add(si + "->" + ei);
                }
            }
        }

        return ans;
    }

    public String smallestSubsequence(String s) {
        int n = s.length();
        HashMap<Character, Integer> freq = new HashMap<>();
        boolean[] vis = new boolean[26];
        Stack<Character> st = new Stack<>();

        // make freq map for character and count
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < n; i++) {
            char currCh = s.charAt(i);
            freq.put(ch, freq.get(ch) - 1);
            if (vis[currCh - 'a'])
                continue;
            while (st.size() > 0 && st.peek() > currCh && freq.get(currCh) > 1) {
                char ch = st.pop();
                vis[ch - 'a'] = false;
                freq.put(ch, freq.get(ch) - 1);
            }
            st.push(currCh);
            vis[currCh - 'a'] = true;
        }

        StringBuilder sb = new StringBuilder("");
        while (st.size() > 0) {
            sb.append(st.pop());
        }

        return sb.reverse().toString();
    }

    public int fib(int N, int[] dp) {
        int prev1 = 1, prev2 = 0;
        for(int n = 0; n <= N; n++) {
            int b = prev2;
            int a = prev1;
            
            int curr = a + b;
            dp[n] = a + b;
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public int fib(int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, 0);
        return fib(n, dp);
    }

    
    public int[] findMaxGuests(int[] Entry,int Exit[], int N){
        // add code here.

	}
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
