import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public int countKConstraintSubstrings(String s, int k) {
        int count = 0;
        int len = s.length();
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(isSatisfyingCondition(s, i, j, k)) count++;
            }
        }
        return count;
    }

    private boolean isSatisfyingCondition(String s, int i, int j, int k) {
        int zero = 0, one = 0;
        for(int itr = i; itr <= j; itr++){
            if(s.charAt(itr) == '0') zero++;
            else one++;
        }
        if(zero <= k || one <= k) return true;
        return false;
    }


    public int[] getFinalState(int[] nums, int k, int multiplier) {
        int length = nums.length;
        for(int i = 0; i < k; i++) {
            int minIdx = getMinIdx(nums);
            nums[minIdx] = nums[minIdx]*k;
        }
        return nums;
    }

    private int getMinIdx(int[] nums) {
        int minIdx = 0;
        for(int i = 1; i < nums.length; i++){
            if(nums[i] < nums[minIdx]) minIdx = i;
        }
        return minIdx;
    }

    public static String sortString(String inputString)
    {
        // Converting input string to character array
        char tempArray[] = inputString.toCharArray();

        // Sorting temp array using
        Arrays.sort(tempArray);

        // Returning new sorted string
        return new String(tempArray);
    }

    public int countPairs(int[] nums) {
        int length = nums.length;
        HashMap<String, Integer> hm = new HashMap<>();

        for(int i = 0; i < length; i++) {
            for(int j = i+1; j < length; j++) {
                if(isSatisfyingCondition(nums[i], nums[j])) {
                    String sortedKey = sortString(nums[i]+"");
                    if(hm.containsKey(sortedKey)) {
                        hm.put(sortedKey, hm.get(sortedKey)+1);
                    }
                    else 
                        hm.put(sortedKey, 1);
                }
            }
        }

        // count all the answers and return 
        int ans = 0;
        for(String key : hm.keySet()) {
            int value = hm.get(key);
            ans += value*(value-1)/2;
        }

        return ans;
    }

    private boolean isSatisfyingCondition(int i, int j) {
        String s1 = sortString(i+"");
        String s2 = sortString(j+"");

        if(s1.equals(s2)) {
            int count = 0;
            for(int k = 0; k < s1.length(); k++) {
                if(s1.charAt(k) != s2.charAt(k)) count++;
            }
            if(count <= 2) return true;
        }
        else if(Integer.parseInt(s1) == Integer.parseInt(s2)) return true;

        return false;
    }

    public static void main(String[] args) {
        
    }
}