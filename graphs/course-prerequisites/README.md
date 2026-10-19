# Course prerequisites (topological sort)

A university has subjects, and some subjects require others to be passed first. Given the last subject a student passed, print the **next subject** they should take, i.e. the one that follows it in an order where every subject comes after all of its prerequisites.

```
Subjects:      Z1 Z2 I1 I2 I3
I1 needs  Z1, Z2
I2 needs  I1                 order: Z1 → Z2 → I1 → I2 → I3
I3 needs  Z2, I2             last passed I2  →  next is I3
```

## Input / output

- Line 1: number of subjects `N`, then `N` lines with subject codes
- Next: number of dependency lines `M`, then `M` lines `B A1 A2 …` meaning *B requires A1, A2, …*
- Last line: the last passed subject

Output: the code of the next subject.

## How it works

1. **Prerequisites → directed graph.** "A before B" becomes an edge `A → B`. Each subject's **in-degree** is the number of prerequisites it still waits on.
2. **Kahn's algorithm.** Put every subject with in-degree 0 (no prerequisites) in a queue. Repeatedly take one out, append it to the order, and decrease the in-degree of the subjects that depend on it; any that drop to 0 join the queue.
3. **Cycle check.** If the final order is missing subjects, some subjects depend on each other in a loop and no valid order exists.
4. Find the last passed subject in the order and print the one after it. Ties are broken by input order (`LinkedHashMap`), so the answer is deterministic.

**Complexity:** *O(V + E)*.

## Run

```bash
javac CoursePrerequisites.java
java CoursePrerequisites < sample-input.txt
```

The expected output is in `sample-output.txt`.
