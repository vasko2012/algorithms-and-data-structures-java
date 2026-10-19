import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Shortest round trip between two cities on a map of one-way roads.
 *
 * The roads form a directed weighted graph, so the way there and the way back
 * can be different. Dijkstra is run twice: from A (to find A -> B) and from B
 * (to find B -> A).
 */
class WeightedDigraph {
    record Edge(int to, double weight) {}

    private final List<List<Edge>> adj = new ArrayList<>();

    WeightedDigraph(int vertices) {
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    void addEdge(int from, int to, double weight) {
        adj.get(from).add(new Edge(to, weight));
    }

    /** Result of Dijkstra: the shortest distance to every vertex and the vertex we came from. */
    record ShortestPaths(double[] dist, int[] parent) {}

    ShortestPaths dijkstra(int source) {
        int n = adj.size();
        double[] dist = new double[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        // Queue entries are {vertex, distance when added}; the closest vertex comes out first.
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
        pq.add(new double[]{source, 0});

        while (!pq.isEmpty()) {
            double[] top = pq.poll();
            int v = (int) top[0];
            if (top[1] > dist[v]) {
                continue; // outdated entry - we already found a shorter way to v
            }
            for (Edge e : adj.get(v)) {
                double candidate = dist[v] + e.weight();
                if (candidate < dist[e.to()]) {
                    dist[e.to()] = candidate;
                    parent[e.to()] = v;
                    pq.add(new double[]{e.to(), candidate});
                }
            }
        }
        return new ShortestPaths(dist, parent);
    }
}

public class ShortestRoundTrip {

    /** Walks the parent pointers back from target and returns the path in the right order. */
    static List<Integer> path(int[] parent, int target) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int v = target; v != -1; v = parent[v]) {
            stack.push(v);
        }
        return new ArrayList<>(stack);
    }

    static String join(List<Integer> path, String[] names) {
        StringBuilder sb = new StringBuilder();
        for (int v : path) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(names[v]);
        }
        return sb.toString();
    }

    static int indexOf(String[] names, String name) {
        for (int i = 0; i < names.length; i++) {
            if (name.equals(names[i])) return i;
        }
        throw new IllegalArgumentException("Unknown city: " + name);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int m = Integer.parseInt(br.readLine().trim());

        String[] names = new String[n];
        WeightedDigraph graph = new WeightedDigraph(n);

        for (int i = 0; i < m; i++) {
            String[] p = br.readLine().trim().split("\\s+");
            int from = Integer.parseInt(p[0]);
            int to = Integer.parseInt(p[2]);
            names[from] = p[1];
            names[to] = p[3];
            graph.addEdge(from, to, Double.parseDouble(p[4]));
        }

        int a = indexOf(names, br.readLine().trim());
        int b = indexOf(names, br.readLine().trim());

        WeightedDigraph.ShortestPaths fromA = graph.dijkstra(a);
        WeightedDigraph.ShortestPaths fromB = graph.dijkstra(b);

        if (Double.isInfinite(fromA.dist()[b]) || Double.isInfinite(fromB.dist()[a])) {
            System.out.println("No round trip possible");
            return;
        }

        System.out.println(join(path(fromA.parent(), b), names));
        System.out.println(join(path(fromB.parent(), a), names));
        System.out.println(fromA.dist()[b] + fromB.dist()[a]);
    }
}
