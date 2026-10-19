import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Orders university subjects so every subject comes after its prerequisites
 * (a topological sort), then prints the subject that comes right after the
 * last one the student passed.
 *
 * Subjects are vertices; "A must be passed before B" is a directed edge A -> B.
 * Kahn's algorithm repeatedly takes a subject with no remaining prerequisites.
 */
class Digraph {
    private final Map<String, List<String>> adj = new LinkedHashMap<>();
    private final Map<String, Integer> inDegree = new LinkedHashMap<>();

    void addVertex(String v) {
        adj.putIfAbsent(v, new ArrayList<>());
        inDegree.putIfAbsent(v, 0);
    }

    void addEdge(String from, String to) {
        addVertex(from);
        addVertex(to);
        adj.get(from).add(to);
        inDegree.merge(to, 1, Integer::sum);
    }

    /** Kahn's algorithm. Ties are broken by the order the vertices were added. */
    List<String> topologicalOrder() {
        Map<String, Integer> remaining = new LinkedHashMap<>(inDegree);
        Queue<String> ready = new ArrayDeque<>();
        remaining.forEach((v, d) -> {
            if (d == 0) ready.add(v);
        });

        List<String> order = new ArrayList<>();
        while (!ready.isEmpty()) {
            String v = ready.poll();
            order.add(v);
            for (String next : adj.get(v)) {
                if (remaining.merge(next, -1, Integer::sum) == 0) {
                    ready.add(next);
                }
            }
        }

        if (order.size() != adj.size()) {
            throw new IllegalStateException("Prerequisites contain a cycle");
        }
        return order;
    }
}

public class CoursePrerequisites {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Digraph graph = new Digraph();

        int n = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < n; i++) {
            graph.addVertex(br.readLine().trim());
        }

        int m = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < m; i++) {
            // "B A1 A2 ..." means B needs A1, A2, ... first -> edges A1 -> B, A2 -> B, ...
            String[] p = br.readLine().trim().split("\\s+");
            for (int j = 1; j < p.length; j++) {
                graph.addEdge(p[j], p[0]);
            }
        }

        String lastPassed = br.readLine().trim();
        List<String> order = graph.topologicalOrder();
        int pos = order.indexOf(lastPassed);

        if (pos == -1) {
            System.out.println("Unknown subject: " + lastPassed);
        } else if (pos == order.size() - 1) {
            System.out.println("No more subjects");
        } else {
            System.out.println(order.get(pos + 1));
        }
    }
}
