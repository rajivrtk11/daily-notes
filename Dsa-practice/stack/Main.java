

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    
    // public int[] nextGreaterElement(int[] nums1, int[] nums2) {
    //     int n1 = nums1.length;
    //     int n2 = nums2.length;
    //     Stack<Integer> st = new Stack<>();
    //     HashMap<Integer, Integer> numberVsNextGreaterOnRight = new HashMap<>();

    //     st.push(-1);

    //     for(int i = 0; i < n2; i++) {
    //         while(st.peek() != -1 && nums2[i] > nums2[st.peek()]) {
    //             numberVsNextGreaterOnRight.put(nums2[st.pop()], nums2[i]);
    //         }
    //         st.push(i);
    //     }

    //     int[] ans = new int[n1];
    //     // find the element in nums2 and make ans
    //     for(int i = 0; i < n1; i++) {
    //         if(numberVsNextGreaterOnRight.containsKey(nums1[i])) {
    //             int nextGreaterElement = numberVsNextGreaterOnRight.get(nums1[i]);
    //             ans[i] = nextGreaterElement;
    //         }
    //         else ans[i] = -1;
    //     }

    //     return ans;
    // }

    // public int[] nextGreaterElements(int[] nums) {
    //     int n = nums.length;
    //     Stack<Integer> st = new Stack<>();
    //     st.push(-1);

    //     int[] ans = new int[n];
    //     Arrays.fill(ans, -1);

    //     for(int i = 0; i < 2*n; i++) {
    //         while (st.peek() != -1 && nums[i%n] > nums[st.peek()]) {
    //             ans[st.pop()] = nums[i%n];
    //         }

    //         if(i < n) st.push(i);
    //     }

    //     return ans;
    // }

    // public int minAddToMakeValid(String s) {
    //  int n = s.length();
    //  Stack<Character> st = new Stack<>();
     
    //  for(int i = 0; i < n; i++) {
    //     Character ch = s.charAt(i);
    //     if(st.size() > 0 && st.peek() == ')') {
    //         st.pop();
    //     }
    //     else {
    //         st.push(ch);
    //     }
    //  }

    //  return st.size();
    // }

    // public int minAddToMakeValid(String s) {
    //    int n = s.length();
    //    int reqObc = 0;
    //    int reqCbc = 0;
       
    //    for(int i = 0; i < n; i++) {
    //     Character ch = s.charAt(i);
    //     if(ch == '(') reqCbc++;
    //     else if(ch == ')' && reqCbc > 0) reqCbc--;
    //     else reqObc++;
    //    }

    //    return reqCbc+reqObc;
    // }

    // public int largestRectangleArea(int[] heights) {
    //  int n = heights.length;
    //  int maxArea = 0;
     
    //  for(int si = 0; si < n; si++) {
    //     for(int ei = si; ei < n; ei++) {
    //         int min = heights[si];
    //         for(int i = si; i <= ei; i++) {
    //             int width = i - si + 1;
    //             min = Math.min(heights[i], min);
    //             maxArea = Math.max(maxArea, width*min);

    //         }
    //     }
    //  }

    //  return maxArea;
    // }

    // public int largestRectangleArea(int[] heights) {
    //   int n = heights.length;
    //   int[] nsor = new int[n];
    //   int[] nsol = new int[n];

    //   Stack<Integer> st = new Stack<>();
    //   st.push(-1);

    //   Arrays.fill(nsor, n);
    //   for(int i = 0; i < n; i++) {
    //     while (st.peek() != -1 && heights[i] < heights[st.peek()]) {
    //         nsor[st.pop()] = i;
    //     }
    //     st.push(i);
    //   }
      
    //   Arrays.fill(nsol, -1);
    //   for(int i = n-1; i >= 0; i--) {
    //     while (st.peek() != -1 && heights[i] < heights[st.peek()]) {
    //         nsol[st.pop()] = i;
    //     }
    //     st.push(i);
    //   }

    //   // calculate the area
    //   int area = 0;
    //   for(int i = 0; i < n; i++) {
    //     int width = nsor[i] - nsol[i] - 1;
    //     area = Math.max(area, width*heights[i]);
    //   }

    //   return area;
    // }

    // public int maximalRectangle(char[][] matrix) {
    //     int n = matrix.length, m = matrix[0].length;

    //     int[] cummulatedArr = new int[n];
    //     int area = 0;

    //     for(int row = 0; row < n; row++) {
    //         for(int col = 0; col < n; col++) {
    //             int ele = matrix[row][col];
    //             if(ele == 0) cummulatedArr[col] = 0;
    //             else cummulatedArr[col] += ele;
    //         }
    //         area = Math.max(area, largestRectangleArea(cummulatedArr));
    //     }
    //     return area;

    // }

    // public int largestRectangleArea(int[] heights) {
    //     int n = heights.length;
  
    //     Stack<Integer> st = new Stack<>();
    //     st.push(-1);
  
    //     int area = 0;

    //     for(int i = 0; i < n; i++) {
    //       while (st.peek() != -1 && heights[i] < heights[st.peek()]) {
    //         int poppedItem = heights[st.pop()];
    //         int width = i - st.peek() - 1;
    //         area = Math.max(area, width*poppedItem);
    //       }
    //       st.push(i);
    //     }
        
    //     while (st.peek() != -1 ) {
    //         int poppedItem = heights[st.pop()];
    //         int width = n - st.peek() - 1;
    //         area = Math.max(area, width*poppedItem);
    //     }

    //     return area;
    //   }
  
    public static void main(String[] args) {
        System.out.println("Hello stack"+ Integer.toBinaryString(5));
    }
}
