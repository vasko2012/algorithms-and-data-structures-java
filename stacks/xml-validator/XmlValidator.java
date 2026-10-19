import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Checks whether the tags in a simplified XML document are correctly nested.
 *
 * Tags use square brackets: [name] opens a tag and [/name] closes it.
 * Every line is either a tag or plain data. Prints 1 if the document is valid, 0 otherwise.
 */
public class XmlValidator {

    static boolean isValid(String[] lines) {
        Deque<String> open = new ArrayDeque<>();

        for (String raw : lines) {
            String line = raw.trim();
            if (!line.startsWith("[") || !line.endsWith("]")) {
                continue; // plain data, not a tag
            }

            String inside = line.substring(1, line.length() - 1);
            if (inside.startsWith("/")) {
                // Closing tag: it must close the most recently opened tag.
                String name = inside.substring(1);
                if (open.isEmpty() || !open.pop().equals(name)) {
                    return false;
                }
            } else {
                open.push(inside);
            }
        }
        return open.isEmpty(); // every opened tag must have been closed
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        String[] lines = new String[n];
        for (int i = 0; i < n; i++) {
            lines[i] = br.readLine();
        }
        System.out.println(isValid(lines) ? 1 : 0);
    }
}
