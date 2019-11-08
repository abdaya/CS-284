package notes.recursion;

public class Examples {

    public static double factorial(int n) {
        return (n == 0) ? 1 : n * factorial(n - 1);
    }

    public static double fib(int n) {
        return (n <= 1) ? 1 : fib(n - 1) + fib(n - 2);
    }

    private static double fast_fib(double old, double current, int n) {
        return (n <= 1) ? current : fast_fib(current, old + current, n - 1);
    }

    public static double fast_fib(int n) {
        return fast_fib(1, 1, n);
    }

}