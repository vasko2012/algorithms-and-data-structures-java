import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Evaluates an integer expression written in postfix (Reverse Polish) notation,
 * e.g. "28 72 * 13 + 20 67 * +" = 28*72 + 13 + 20*67 = 3369.
 *
 * Numbers are pushed onto a stack; an operator pops its two operands,
 * applies itself and pushes the result back.
 */
public class PostfixEvaluation {

    static long evaluate(String expression) {
        Deque<Long> stack = new ArrayDeque<>();

        for (String token : expression.trim().split("\\s+")) {
            switch (token) {
                case "+", "-", "*", "/" -> {
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough operands for '" + token + "'");
                    }
                    long right = stack.pop(); // the top of the stack is the RIGHT operand
                    long left = stack.pop();
                    stack.push(apply(token, left, right));
                }
                default -> stack.push(Long.parseLong(token));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Malformed expression: " + expression);
        }
        return stack.pop();
    }

    private static long apply(String op, long left, long right) {
        return switch (op) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> {
                if (right == 0) throw new ArithmeticException("Division by zero");
                yield left / right;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(evaluate(br.readLine()));
    }
}
