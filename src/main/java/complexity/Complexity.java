package complexity;

/**
 * @author Robert Schaedler III
 * @see I pledge my honor that I have abided by the Stevens Honor System.
 */

public class Complexity {

    /**
     * Has time complexity O(n^2).
     * 
     * @param n
     */
    public void method1(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Operation " + counter);
                counter++;
            }
        }
    }

    /**
     * Has time complexity O(log n).
     * 
     * @param n
     */
    public void method2(int n) {
        int counter = 0;
        for (int i = 0; i < n; i *= 2) {
            System.out.println("Operation " + counter);
            counter++;
        }
    }

    /**
     * Has time complexity O(n log n).
     * 
     * @param n
     */
    public void method3(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j *= 2) {
                System.out.println("Operation " + counter);
                counter++;
            }
        }
    }

    /**
     * Has time complexity O(n^3).
     * 
     * @param n
     */
    public void method4(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    System.out.println("Operation " + counter);
                    counter++;
                }
            }
        }
    }

    /**
     * Has time complexity O(log log n).
     * 
     * @param n
     */
    public void method5(int n) {
        int counter = 0;
        for (int i = 0; i < n; i = i ^ 2) {
            System.out.println("Operation " + counter);
            counter++;
        }
    }

    /**
     * Has time complexity O(2^n).
     * 
     * @param n
     */
    public int method6(int n) {
        if (n == 0) {
            return 1;
        }
        return method6(n - 1) + method6(n - 1);
    }

}