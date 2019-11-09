package notes.stacks;

public class WellBalanced {
    private static final String OPEN = "({[";
    private static final String CLOSE = ")}]";

    public static boolean isBalanced(String expression) {
        StackSLL<Character> s = new StackSLL<>();
        int i = 0;
        while (i < expression.length()) {
            if (isOpen(expression.charAt(i))) {
                s.push(expression.charAt(i));
            } else if (isClose(expression.charAt((i)))) {
                try {
                    if (OPEN.indexOf(s.peek()) == CLOSE.indexOf(expression.charAt(i))) {
                        s.pop();
                    } else {
                        return false;
                    }
                } catch (IllegalStateException e) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException();
            }
            i++;
        }
        return s.size() == 0;
    }

    private static boolean isOpen(char ch) {
        return OPEN.indexOf(ch) > -1;
    }

    private static boolean isClose(char ch) {
        return CLOSE.indexOf(ch) > -1;
    }
}