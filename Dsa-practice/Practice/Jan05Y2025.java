import java.util.Arrays;

public class Jan05Y2025 {
    public static void main(String[] args) {
        String str = "Rajiv";
        int[] map = new int[128];
        
        for(int i = 0; i < str.length(); i++) map[str.charAt(i)] = 1;

        System.out.println("Hello world"+" "+ Arrays.toString(map));
    }
}
