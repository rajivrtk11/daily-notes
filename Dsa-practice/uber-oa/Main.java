
public class Main {
    public static int[] divideArray(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }

        // Initialize first and second arrays
        int[] first = new int[arr.length];
        int[] second = new int[arr.length];
        first[0] = arr[0];  // First element goes to first array
        second[0] = arr[1]; // Second element goes to second array
        
        int firstCount = 1;
        int secondCount = 1;

        // Process remaining elements
        for (int i = 2; i < arr.length; i++) {
            // Count elements greater than arr[i] in both arrays
            int greaterInFirst = countGreaterElements(first, firstCount, arr[i]);
            int greaterInSecond = countGreaterElements(second, secondCount, arr[i]);

            // Decide which array to put the element in
            if (greaterInFirst > greaterInSecond) {
                second[secondCount++] = arr[i];
            } else if (greaterInFirst < greaterInSecond) {
                first[firstCount++] = arr[i];
            } else {
                // If equal, put in array with fewer elements
                if (firstCount <= secondCount) {
                    first[firstCount++] = arr[i];
                } else {
                    second[secondCount++] = arr[i];
                }
            }
        }

        // Combine arrays into result
        int[] result = new int[arr.length];
        int index = 0;

        // Copy elements from first array
        for (int i = 0; i < firstCount; i++) {
            result[index++] = first[i];
        }

        // Copy elements from second array
        for (int i = 0; i < secondCount; i++) {
            result[index++] = second[i];
        }

        return result;
    }

    private static int countGreaterElements(int[] arr, int count, int value) {
        int greater = 0;
        for (int i = 0; i < count; i++) {
            if (arr[i] > value) {
                greater++;
            }
        }
        return greater;
    }

    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // question-2
    private static boolean findNextSequence(String s1, String s2) {
        // considering same length
        int n = s1.length();
        for(int i = 0; i < n; i++) {
            int numberInS1 = s1.charAt(i)-'a';
            int numberInS2 = s2.charAt(i) - 'a';
            
            if(Math.abs(numberInS2 - numberInS1) != 1 || Math.abs(numberInS2 - numberInS1) != 25 ) return false;
        }
        return true;
    }

    // Test the solution
    public static void main(String[] args) {
        // int[] arr = {5, 2, 8, 1, 9, 3, 7};
        // System.out.println("Original array:");
        // printArray(arr);

        // int[] result = divideArray(arr);
        // System.out.println("Result array:");
        // printArray(result);

        // question-2
        // s1 = "ack", s2 = "bdl"; ans: true
        // s1 = "ack", s2 = "cdl"; ans: false
        // s1 = "ackz", "bdla"; ans = true
        boolean test1 = findNextSequence("ack", "bdl");
        boolean test2 = findNextSequence("ack", "cdl");
        boolean test3 = findNextSequence("ackz", "bdla");
        System.out.println("Test1 : "+test1);
        System.out.println("Test2 : "+test2);
        System.out.println("Test3 : "+test3);
        System.out.println("Test3 : "+ ('z' -'a'));

    }

    
}
