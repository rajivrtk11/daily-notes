import java.util.Arrays;

public class FilteringMessages {
    public static int filteringMessages(String messageA, String messageB, String virusC) {
        // Trivial Case 1
        if (messageA.compareTo(messageB) > 0) {
            return 0;
        }

        // Trivial Case 2
        if (messageA.equals(messageB)) {
            return messageA.contains(virusC) ? 0 : 1;
        }

        // Storing virus and message size
        int messageSize = messageA.length();
        int virusSize = virusC.length();

        // dp[i] stores count of valid strings of length i
        int[] dp = new int[messageSize + 1];
        int idx = 0;
        final int mod = 1000000007;

        // Find first non-matching character
        while (idx < messageSize && messageA.charAt(idx) == messageB.charAt(idx)) {
            idx++;
        }

        // Starting with first non-matching prefixes
        int len = idx + 1;
        for (int i = len; i <= messageSize; i++) {
            if (i == len) {
                dp[i] = messageB.charAt(i - 1) - messageA.charAt(i - 1) - 1;
                System.out.println("val "+ dp[i]);
            } else {
                dp[i] = (int)(((long)dp[i - 1] * 26) % mod);
                System.out.println("dp i" + Arrays.toString(dp) + dp[i-1]);
                if (!messageA.substring(0, i - 1).contains(virusC)) {
                    dp[i] = (dp[i] + ('z' - messageA.charAt(i - 1))) % mod;
                    System.out.println("dp j" + ('z' - messageA.charAt(i - 1)));
                }
                
                if (!messageB.substring(0, i - 1).contains(virusC)) {
                    dp[i] = (dp[i] + (messageB.charAt(i - 1) - 'a')) % mod;
                    System.out.println("dp j" + (messageB.charAt(i - 1) - 'a'));
                }
            }

            if (i == virusSize) {
                String currBeg = messageA.substring(0, i);
                String currEnd = messageB.substring(0, i);
                if (currBeg.compareTo(virusC) < 0 && virusC.compareTo(currEnd) < 0) {
                    dp[i] = (int)((0L + dp[i] - 1 + mod) % mod);
                }
            } else if (i > virusSize) {
                dp[i] = (int)((0L + dp[i] - dp[i - virusSize] + mod) % mod);

                // Handle corner equality cases separately
                String currBeg = messageA.substring(0, i);
                String currEnd = messageB.substring(0, i);
                String beg = messageA.substring(0, i - virusSize) + virusC;
                String end = messageB.substring(0, i - virusSize) + virusC;
                
                if (currBeg.compareTo(beg) < 0 && beg.compareTo(currEnd) < 0) {
                    dp[i] = (int)((0L + dp[i] - 1 + mod) % mod);
                }
                
                if (currBeg.compareTo(end) < 0 && end.compareTo(currEnd) < 0) {
                    dp[i] = (int)((0L + dp[i] - 1 + mod) % mod);
                }
            }
        }

        // Calculate final answer
        int ans = dp[messageSize];
        if (!messageA.contains(virusC)) ans = (ans + 1) % mod;
        if (!messageB.contains(virusC)) ans = (ans + 1) % mod;

        return ans;
    }

    public static void main(String[] args) {
        // Test cases
        // System.out.println("Test Case 0:");
        // System.out.println(filteringMessages("b", "f", "c")); // Should print 4
        
        // System.out.println("\nTest Case 1:");
        // System.out.println(filteringMessages("m", "q", "a")); // Should print 5
        
        System.out.println("\nTest Case 2:");
        System.out.println(filteringMessages("ab", "da", "c")); // Should print 50
        System.out.println('z'-'a');
    }
}