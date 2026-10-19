import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Finds the path from S to E in a character maze.
 *
 * 1. Every open cell becomes a vertex (numbered row by row).
 * 2. Two vertices are connected if their cells are next to each other
 *    (up, down, left, right) - stored in an adjacency matrix.
 * 3. BFS from S records each vertex's parent; following the parents back
 *    from E gives the path.
 */
class Graph {
    private final boolean[][] adj;

    Graph(int vertices) {
        adj = new boolean[vertices][vertices];
    }

    void addEdge(int a, int b) {
        adj[a][b] = true;
        adj[b][a] = true; // undirected
    }

    int size() {
        return adj.length;
    }

    /** Breadth-first search from start; returns parent[v] for every vertex (-1 = not reached / start). */
    int[] bfs(int start) {
        int[] parent = new int[size()];
        boolean[] visited = new boolean[size()];
        Arrays.fill(parent, -1);

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int u = 0; u < size(); u++) {
                if (adj[v][u] && !visited[u]) {
                    visited[u] = true;
                    parent[u] = v;
                    queue.add(u);
                }
            }
        }
        return parent;
    }
}

public class MazePath {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] size = br.readLine().trim().split(",");
        int rows = Integer.parseInt(size[0].trim());
        int cols = Integer.parseInt(size[1].trim());

        char[][] maze = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = br.readLine();
            for (int c = 0; c < cols; c++) {
                // Treat anything past the end of a short line as a wall.
                maze[r][c] = (line != null && c < line.length()) ? line.charAt(c) : '#';
            }
        }

        // Step 1: number the open cells.
        int[][] id = new int[rows][cols];
        List<int[]> cells = new ArrayList<>();
        int start = -1, end = -1;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (maze[r][c] == '#') {
                    id[r][c] = -1;
                    continue;
                }
                id[r][c] = cells.size();
                if (maze[r][c] == 'S') start = id[r][c];
                if (maze[r][c] == 'E') end = id[r][c];
                cells.add(new int[]{r, c});
            }
        }

        // Step 2: connect neighbouring open cells.
        Graph graph = new Graph(cells.size());
        for (int v = 0; v < cells.size(); v++) {
            int r = cells.get(v)[0], c = cells.get(v)[1];
            for (int[] d : DIRECTIONS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && id[nr][nc] != -1) {
                    graph.addEdge(v, id[nr][nc]);
                }
            }
        }

        // Step 3: BFS from S, then walk the parents back from E.
        int[] parent = graph.bfs(start);
        Deque<Integer> path = new ArrayDeque<>();
        for (int v = end; v != -1; v = parent[v]) {
            path.push(v);
        }

        if (path.peek() != start) {
            System.out.println("No path");
            return;
        }
        for (int v : path) {
            System.out.println(cells.get(v)[0] + "," + cells.get(v)[1]);
        }
    }
}
