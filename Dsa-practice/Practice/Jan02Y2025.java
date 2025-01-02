import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jan02Y2025 {
    
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

    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
