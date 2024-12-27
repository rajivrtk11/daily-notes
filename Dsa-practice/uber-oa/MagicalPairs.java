import java.util.*;

public class MagicalPairs {
    public static int solution(int[] numbers) {
        int count = 0;

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (areMagical(numbers[i], numbers[j])) {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean areMagical(int num1, int num2) {
        // Convert numbers to string for digit comparison
        String s1 = String.valueOf(num1);
        String s2 = String.valueOf(num2);

        // If lengths differ, they cannot be magical
        if (s1.length() != s2.length()) return false;

        // Count mismatches
        int mismatchCount = 0;
        List<Integer> mismatchIndices = new ArrayList<>();

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                mismatchCount++;
                mismatchIndices.add(i);
            }
        }

        // Magical condition: exactly 2 mismatches, and swapping the mismatched digits makes the numbers equal
        if (mismatchCount == 2) {
            char[] arr1 = s1.toCharArray();
            char[] arr2 = s2.toCharArray();

            // Swap mismatched digits in the first number
            char temp = arr1[mismatchIndices.get(0)];
            arr1[mismatchIndices.get(0)] = arr1[mismatchIndices.get(1)];
            arr1[mismatchIndices.get(1)] = temp;

            return Arrays.equals(arr1, arr2);
        }

        // Numbers are magical if they are identical
        return mismatchCount == 0;
    }

    public static void main(String[] args) {
        int[] numbers1 = {1, 23, 156, 1650, 651, 165, 32};
        int[] numbers2 = {123, 321, 123};

        System.out.println(solution(numbers1)); // Output: 3
        System.out.println(solution(numbers2)); // Output: 3
    }
}
