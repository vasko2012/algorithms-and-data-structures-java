import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Evaluates a fully parenthesised expression built from single digits, '+' and '-',
 * e.g. ((1+2)-(3-(4+5))) = 9.
 *
 * Grammar:  expr := digit | '(' expr op expr ')'      op := '+' | '-'
 *
 * Divide and conquer: find the operator that sits directly inside the outer
 * parentheses, evaluate the left and right halves recursively, and combine them.
 */
public class ArithmeticExpression {

    /** Evaluates the sub-expression c[l..r] (both ends inclusive). */
    static int evaluate(char[] c, int l, int r) {
        // Base case: a single digit.
        if (l == r) {
            if (!Character.isDigit(c[l])) {
                throw new IllegalArgumentException("Expected a digit at position " + l);
            }
            return c[l] - '0';
        }

        if (c[l] != '(' || c[r] != ')') {
            throw new IllegalArgumentException("Expected '(...)' between positions " + l + " and " + r);
        }

        // Find the top-level operator: the one not inside any inner parentheses.
        int depth = 0;
        for (int i = l + 1; i < r; i++) {
            if (c[i] == '(') {
                depth++;
            } else if (c[i] == ')') {
                depth--;
            } else if (depth == 0 && (c[i] == '+' || c[i] == '-')) {
                int left = evaluate(c, l + 1, i - 1);
                int right = evaluate(c, i + 1, r - 1);
                return c[i] == '+' ? left + right : left - right;
            }
        }
        throw new IllegalArgumentException("No operator found between positions " + l + " and " + r);
    }

    static int evaluate(String expression) {
        char[] c = expression.replaceAll("\\s+", "").toCharArray();
        return evaluate(c, 0, c.length - 1);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(evaluate(br.readLine()));
    }
}
