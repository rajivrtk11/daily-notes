import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class LinkedList {
    
    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    Node head;
    Node tail;
    int size = 0;

    public LinkedList(Node head, Node tail) {
        this.head = head;
        head.next = tail;
        this.tail = tail;
        this.size = 2;
    }

    // 1. add first 
    // 2. add last 

    public void addFirst(Node node) {
        node.next = head;
        this.head = node;
    }

    // https://leetcode.com/problems/plus-one/?envType=problem-list-v2&envId=array
    public int[] plusOne(int[] digits) {
        int n = digits.length; 
        List<Integer> al = new ArrayList<>();

        int carry = 1;
        for(int i = n-1; i >= 0; i--){
            int sum = carry + digits[i];
            int newNum = sum%10;
            carry = sum/10;
            al.add(newNum);
        }
        if(carry != 0) al.add(carry);

        int alSize = al.size();
        int[] ans = new int[alSize];

        for(int i = 0; i < alSize; i++){
            ans[i] = al.get(alSize-1-i);
        }

        return ans;
    }

    public static int countAndMerge(int si, int mid, int ei, int[] arr) {
        int l = si, r = ei, k = 0;
        int[] temp = new int[ei-si+1];

        int count = 0;
        while(l < mid && r <= ei) {
            if(arr[l] <= arr[r]) {
                temp[k++] = arr[l++];
            }
            else {
                count += mid - l;
                temp[k++] = arr[r++];
            }
        }

        // copy all left 
        while(l < mid) {
            temp[k++] = arr[l++];
        }
        
        // copy all right
        while (r <= ei) {
            temp[k++] = arr[r++];
        }

        System.arraycopy(temp, 0, arr, si, ei-si+1);

        return count;
    }

    public static int countInversion(int si, int ei, int[] arr) {
        if(si >= ei) return 0;

        int mid = ei + (ei - si)/2;
        int leftCount = countInversion(si, mid, arr);
        int rightCount = countInversion(mid+1, ei, arr);
        int mergeCount = countAndMerge(si, mid+1, ei, arr);

        return leftCount + rightCount + mergeCount;
    }

    static int inversionCount(int arr[]) {
        // Your Code Here
        int n = arr.length;
        return countInversion(0, n-1, arr);
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        for(int i = 0; i < numRows; i++) {
            temp.add(0,1);
            for(int j = 1; j < temp.size()-1; j++) {
                temp.set(j, temp.get(j) + temp.get(j+1));
            }
            ans.add(new ArrayList<>(temp));
        }

        return ans;
    }

    // https://www.geeksforgeeks.org/problems/maximum-intervals-overlap5708/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
    public int[] findMaxGuests(int[] Entry,int Exit[], int N){
        // add code here.
        TreeMap<Integer, Integer> map = new TreeMap<>();

        for(int i = 0; i < N; i++) {
            map.put(Entry[i], map.getOrDefault(Entry[i], 0)+1);
            map.put(Exit[i], map.getOrDefault(Exit[i]+1, 0)-1);
        }

        int ans = 0;
        int time = -1, maxCount = 0;
        for(int keys: map.keySet()) {
            ans += map.get(keys);
            if(maxCount < ans) {
                maxCount = ans;
                time = keys;
            }
        }

        return { maxCount, time };
	}
   
    // find the length of longest non decreasing subarray
    // find the lengh of longest non decreasing subarray if one change can be done.
    public int maxLenNonDecreasingSubarray(int[] arr) {
        int n = arr.length;
        if(n == 1 ) return 1;

        int si = 0, ei = 1;
        int len = 1;
        while (ei < n) {
            if(arr[ei-1] <= arr[ei]) {
                len = Math.max(ei-si+1, len);
            }
            else {
                si = ei;
            }

            ei++;
        }

        return len;
    }

    public int minimumOperations(int[] nums) {
       int n = nums.length;
       HashMap<Integer, Integer> hm = new HashMap<>();

       for(int i = 0; i < n; i++) {
         hm.put(nums[i], hm.getOrDefault(hm.get(nums[i]), 0)+1);
       } 
       int duplicateCount = 0;

       for(int key: hm.keySet()) {
        if(hm.get(key) > 1) duplicateCount += hm.get(key)-1;
       }

       if(duplicateCount == 0) return 0;

       int ei = 0;
       while (ei < n) {
        int ele = nums[ei];

        if(hm.get(ele > 1)) {
            hm.put(ele, hm.get(ele)-1);
            duplicateCount--;
        }
        if(duplicateCount == 0) {
            int idx = ei+1;
            if(idx%3 == 0) return idx/3;
            else return (idx/3)+1;
        }
       }

       int idx = ei+1;
       if(idx%3 == 0) return idx/3;
       else return (idx/3)+1;
    }

    public int maxDistinctElements(int[] nums, int k) {
     int n = nums.length;
     if(n <= 2*k+1 || n == 0) return n;
     
     int duplicate = 0, last = nums[0]-k-1;
     for(int i = 0; i < n; i++) {
        int ele = nums[i];
        int min = ele-k, max = ele+k;
        int val = Math.max(last+1, min);

        if(val <= max) {
            nums[i] = val;
        }
        else {
            duplicate++;
        }
     }

     return n - duplicate;
    }

    public int findLenOfSubstringAndGetMaxLen(String s, ArrayList<Integer> lenOfSubstrings) {
        int maxLen = 1;
        int len = 1;
        int n = s.length();

        for(int i = 1; i < n; i++) {
            if(s.charAt(i) == s.charAt(i-1)) len++;
            else {
                maxLen = Math.max(maxLen, len);
                lenOfSubstrings.add(len);
                len = 1;
            }
        }

        return maxLen;
    }

    public int getMinLenSubString(ArrayList<Integer> lenOfSubstrings, int maxLen, int numOps) {
        int si = 1, ei = maxLen;

        while (si < ei) {
            int mid = (si + ei)/2;
            if(canSatisfyThisLen(lenOfSubstrings, mid, ops)) ei = mid;
            else si = mid+1;
        }

        return ei;
    }

    public boolean canSatisfyThisLen(ArrayList<Integer> lenOfSubstrings, int mid, int numOps) {
        int myOps = 0;
        for(int len: lenOfSubstrings) {
            myOps += len/numOps;
            if(myOps > numOps) return false;
        }

        return true;
    }

    public int minLength(String s, int numOps) {
        ArrayList<Integer> lenOfSubstrings = new ArrayList<>();
        int maxLen = findLenOfSubstringAndGetMaxLen(s, lenOfSubstrings); 
        int minLen = getMinLenSubString(lenOfSubstrings, maxLen, numOps);

        return minLen;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList(new Node(1), null);
        list.addFirst(new Node(2));
        list.addFirst(new Node(3));

        Node curr = list.head;
        while(curr != null) {
            System.out.println("Data is : "+ curr.data);
            curr = curr.next;
        }
        System.out.println("hello world");
    }
}
