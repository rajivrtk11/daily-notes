public class Feb08Y2025 {

    public static void findPrimeNo(int n) {
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                System.out.println(i);
            }
        }
    }
        
    private static boolean isPrime(int m) {
        for(int i = 2; i*i <= m; i++) {
            if(m%i == 0) return false;
        }

        return true;
    }
    
    public static void main(String[] args) {
        findPrimeNo(12);
    }
}