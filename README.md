# Algorithms and Data Structures in Java

Solutions to classic data-structure and algorithm problems, written in Java. Each problem has its own folder with a short write-up, the solution, and sample input/output.

| Topic | Problem | Key idea |
|---|---|---|
| Trees | [Windows Explorer](trees/windows-explorer) | General tree (first-child / next-sibling), sorted insert, pre-order print |
| Graphs | [Graph from commands](graphs/graph-commands) | Undirected graph as an adjacency list (`TreeMap` of `TreeSet`s) |
| Graphs | [Maze path](graphs/maze-path) | Maze → adjacency matrix, BFS with a parent array to rebuild the path |
| Graphs | [Shortest round trip](graphs/shortest-round-trip) | Dijkstra with a priority queue, run from both ends of a directed graph |
| Graphs | [Course prerequisites](graphs/course-prerequisites) | Topological sort with Kahn's algorithm, plus cycle detection |
| Hashing | [Pharmacy stock lookup](hashing/pharmacy) | Hand-written hash table with separate chaining, custom `hashCode`/`equals` |
| Stacks | [Postfix evaluation](stacks/postfix-evaluation) | Evaluate Reverse Polish notation with a stack |
| Stacks | [XML tag validator](stacks/xml-validator) | Nested-tag matching, the balanced-parentheses idea with names |

More problems are on the way: graphs (BFS, Dijkstra, topological sort), hashing, stacks, linked lists and sorting.

## Running a solution

Every solution is a single Java file that reads from standard input:

```bash
cd trees/windows-explorer
javac WindowsExplorer.java
java WindowsExplorer < sample-input.txt
```
