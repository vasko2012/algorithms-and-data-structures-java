# Shaker (cocktail) sort

Sort an array with **shaker sort**, a two-way variant of bubble sort, printing the array after every pass.

Each round makes two passes over the still-unsorted part of the array:

1. **Right to left:** compare neighbours and swap when out of order, so the **smallest** element drifts to the front.
2. **Left to right:** the same, so the **largest** element drifts to the back.

After each round both ends are final, and the unsorted window shrinks by one element on each side.

```
input:   6 10 13 5 8 17 2 5
round 1  ←  2 6 10 13 5 8 17 5     smallest (2) moved to the front
         →  2 6 10 5 8 13 5 17     largest (17) moved to the back
round 2  ←  2 5 6 10 5 8 13 17
         →  2 5 6 5 8 10 13 17
round 3  ←  2 5 5 6 8 10 13 17     already sorted
         …
```

## Why go both ways?

Plain bubble sort only moves elements one way per pass. A small element near the end (a "turtle", like the `2` above) needs one pass for every step it moves left. Shaker sort carries it all the way to the front in a single right-to-left pass.

**Complexity:** still *O(n²)* comparisons in the worst and average case, *O(1)* extra memory, and it's **stable** (equal elements keep their order). The problem wants every pass printed, so this version always runs ⌊n/2⌋ rounds; a practical version would stop as soon as a round makes no swaps.

## Run

```bash
javac ShakerSort.java
java ShakerSort < sample-input.txt
```

The expected output is in `sample-output.txt`.
