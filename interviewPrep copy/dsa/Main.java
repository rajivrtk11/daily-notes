import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static int binarySearch(int[] arr, int data) {
        int si = 0, ei = arr.length-1;

        while (si <= ei) {
            int mid = (si + ei)/2;
            if(arr[mid] == data) return mid;
            else if (arr[mid] < data) si = mid + 1;
            else ei = mid - 1;
        }

        return -1;
    }
    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);

        int idx = binarySearch(arr, 7);
        System.out.println("Search index "+ idx);
        int index = Collections.binarySearch(list, 11);
        System.out.println("index "+ index);
    }
}