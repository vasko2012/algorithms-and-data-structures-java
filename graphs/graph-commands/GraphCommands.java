import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Undirected, unweighted graph stored as an adjacency list.
 *
 * TreeMap / TreeSet keep the vertices and their neighbours sorted, so the
 * printed adjacency list is always in the same, predictable order.
 */
class AdjacencyListGraph<T extends Comparable<T>> {
    private final Map<T, Set<T>> adjacency = new TreeMap<>();

    void addVertex(T vertex) {
        adjacency.putIfAbsent(vertex, new TreeSet<>());
    }

    void removeVertex(T vertex) {
        for (Set<T> neighbours : adjacency.values()) {
            neighbours.remove(vertex);
        }
        adjacency.remove(vertex);
    }

    void addEdge(T a, T b) {
        addVertex(a);
        addVertex(b);
        adjacency.get(a).add(b);
        adjacency.get(b).add(a); // undirected: store the edge in both directions
    }

    void removeEdge(T a, T b) {
        if (adjacency.containsKey(a)) {
            adjacency.get(a).remove(b);
        }
        if (adjacency.containsKey(b)) {
            adjacency.get(b).remove(a);
        }
    }

    boolean adjacent(T a, T b) {
        return neighbours(a).contains(b);
    }

    Set<T> neighbours(T vertex) {
        return adjacency.getOrDefault(vertex, Collections.emptySet());
    }

    void print() {
        adjacency.forEach((vertex, neighbours) ->
                System.out.println(vertex + ": " + new ArrayList<>(neighbours)));
    }
}

public class GraphCommands {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();

        for (int i = 0; i < n; i++) {
            String[] parts = br.readLine().trim().split("\\s+");

            switch (parts[0]) {
                case "CREATE" -> graph = new AdjacencyListGraph<>();
                case "ADDEDGE" -> graph.addEdge(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                case "DELETEEDGE" -> graph.removeEdge(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                case "ADJACENT" -> System.out.println(
                        graph.adjacent(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                case "PRINTGRAPH" -> {
                    graph.print();
                    System.out.println();
                }
                default -> throw new IllegalArgumentException("Unknown command: " + parts[0]);
            }
        }
    }
}
