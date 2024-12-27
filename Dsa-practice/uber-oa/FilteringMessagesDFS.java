public class FilteringMessagesDFS {

    private static final int MOD = 1000000007;

    public static int filteringMessages(String messageA, String messageB, String virusC) {
        if (messageA.compareTo(messageB) > 0) {
            return 0; // Invalid range
        }
        if (messageA.equals(messageB)) {
            return messageA.contains(virusC) ? 0 : 1; // Single string check
        }
        return dfs(messageA, messageB, virusC, new StringBuilder(), 0, false, false) % MOD;
    }

    private static int dfs(String messageA, String messageB, String virusC, StringBuilder current, int position, boolean exceededA, boolean exceededB) {
        // If the current string is fully formed
        if (current.length() == messageA.length()) {
            String currentStr = current.toString();
            return (!currentStr.contains(virusC)) ? 1 : 0;
        }

        int count = 0;

        // Determine the range of characters to explore
        char startChar = exceededA ? 'a' : messageA.charAt(position);
        char endChar = exceededB ? 'z' : messageB.charAt(position);

        // Explore all valid characters at the current position
        for (char c = startChar; c <= endChar; c++) {
            current.append(c);
            count = (count + dfs(
                messageA, 
                messageB, 
                virusC, 
                current, 
                position + 1, 
                exceededA || c > messageA.charAt(position), 
                exceededB || c < messageB.charAt(position)
            )) % MOD;
            current.deleteCharAt(current.length() - 1); // Backtrack
        }

        return count;
    }

    // Main method to test the solution
    public static void main(String[] args) {
        // Test cases
        System.out.println("Test Case 0:");
        System.out.println(filteringMessages("b", "f", "c")); // Should print 4

        System.out.println("\nTest Case 1:");
        System.out.println(filteringMessages("m", "q", "a")); // Should print 5

        System.out.println("\nTest Case 2:");
        System.out.println(filteringMessages("ab", "da", "c")); // Should print 50

        System.out.println("\nTest Case 3:");
        System.out.println(filteringMessages("aaa", "aba", "aa")); // Correct output: 675
    }
}
