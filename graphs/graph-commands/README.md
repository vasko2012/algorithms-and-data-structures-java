# Graph from commands (adjacency list)

Builds an undirected, unweighted graph whose vertices are integers, driven by a list of commands:

| Command | What it does |
|---|---|
| `CREATE` | Starts a new, empty graph |
| `ADDEDGE a b` | Connects vertices `a` and `b` (adding them if they don't exist yet) |
| `DELETEEDGE a b` | Removes the edge between `a` and `b` |
| `ADJACENT a b` | Prints `true` if `a` and `b` are connected, otherwise `false` |
| `PRINTGRAPH` | Prints the adjacency list, one vertex per line, followed by an empty line |

## How it works

- The graph is a `Map<vertex, Set<neighbour>>`. Because it's undirected, every edge is stored twice: `b` in `a`'s set and `a` in `b`'s set.
- `TreeMap` and `TreeSet` keep vertices and neighbours **sorted**, so the output is deterministic. A `HashMap` happens to print small integers in order, but that's an implementation detail you can't rely on.
- Adding, removing and checking an edge are all *O(log n)*; printing is *O(V + E)*.

## Run

```bash
javac GraphCommands.java
java GraphCommands < sample-input.txt
```

The expected output is in `sample-output.txt`.
