# Maze path (adjacency matrix + BFS)

Given a maze of characters (`#` wall, space = open, `S` start, `E` end), build an undirected graph stored as an **adjacency matrix** and print the path from `S` to `E` as `row,col` coordinates.

```
6,6
######
#S# E#        S → (1,1)   E → (1,4)
# # ##
#   ##
######
######
```

Output: `1,1  2,1  3,1  3,2  3,3  2,3  1,3  1,4` (one per line)

## How it works

1. **Cells → vertices.** Scan the maze row by row and give every open cell a number. Two lookups are kept: `id[row][col]` (cell → vertex) and `cells[v]` (vertex → cell), so the answer can be printed as coordinates.
2. **Neighbours → edges.** For each open cell, check the four directions (up, down, left, right); every open neighbour becomes an edge in the matrix.
3. **BFS + parent array.** Run breadth-first search from `S`, remembering which vertex each one was reached from. Walking those parents back from `E` and reversing gives the path.

BFS finds the **shortest** path in an unweighted graph. In a perfect maze there's only one path anyway, but the same code works for any maze.

**Complexity:** with *V* open cells, the matrix takes *O(V²)* memory and BFS over it takes *O(V²)* time. An adjacency list would bring both down to *O(V)*, but the problem asks for a matrix.

## Run

```bash
javac MazePath.java
java MazePath < sample-input.txt
```

The expected output is in `sample-output.txt`.
