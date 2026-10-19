# Shortest round trip (Dijkstra)

A map of **one-way** roads between cities is a directed weighted graph. Given two cities A and B, find the shortest route A → B, the shortest route back B → A, and the total length of both.

Because roads are one-way, the way back can be completely different from the way there:

```
Skopje → Veles     55
Veles → Strumica  110
Skopje → Strumica 200        there: Skopje Veles Strumica  (165, not 200)
Strumica → Shtip   70        back:  Strumica Shtip Skopje  (160, not 180)
Shtip → Skopje     90        total: 325.0
Strumica → Skopje 180
```

## Input / output

- Line 1: number of cities `N`; line 2: number of roads `M`
- Next `M` lines: `fromId fromName toId toName length`
- Last two lines: the start city and the destination city

Output: the route there, the route back (city names separated by spaces), then the total length.

## How it works

1. Store the graph as an **adjacency list** of `(to, weight)` edges.
2. **Dijkstra** from A gives the shortest distance from A to every city and, for each city, the city it was reached from (`parent`). A **priority queue** always hands out the closest city not finished yet. Entries that are out of date (a shorter way was already found) are skipped instead of removed.
3. Following `parent` back from B and reversing gives the route A → B.
4. Run Dijkstra **again from B** for the route back, because one run only gives paths *starting* at its source.

**Complexity:** *O((V + E) log V)* per Dijkstra run, so twice that in total.

## Run

```bash
javac ShortestRoundTrip.java
java ShortestRoundTrip < sample-input.txt
```

The expected output is in `sample-output.txt`.
