# Reverse a sub-interval of a doubly linked list

Soldiers stand in a line, stored as a **doubly linked list** of their IDs. The commander picks the soldier with ID `a` and the soldier with ID `b` and reverses everyone from `a` to `b` (inclusive). Print the new line-up forwards and backwards; printing backwards proves the `pred` links are right too.

```
Input                      Output
9                          1<->4<->3<->2<->999<->5<->6<->7<->888
1 2 3 4 999 5 6 7 888      888<->7<->6<->5<->999<->2<->3<->4<->1
2 4
```

The reversal must be done by **re-linking the existing nodes**, without creating, copying or deleting nodes.

## How it works

1. Remember the neighbours outside the interval: `before = from.pred` and `after = to.succ`.
2. Walk from `from` to `to` and **swap every node's `pred` and `succ`**. Inside the interval this reverses all the arrows at once.
3. Now `to` is the start of the interval and `from` is its end. Reconnect them to the outside: `before ⇄ to` and `from ⇄ after`.
4. **Edge cases:** if the interval started at the head (`before == null`), `to` becomes the new head; if it ended at the tail (`after == null`), `from` becomes the new tail.

```
before ⇄ [from ⇄ x ⇄ to] ⇄ after
before ⇄ [to ⇄ x ⇄ from] ⇄ after
```

**Complexity:** *O(n)* to find the two soldiers, *O(k)* to reverse *k* nodes, *O(1)* extra memory.

All 10 test cases from the course pass, including intervals at the head, at the tail, and the whole list.

## Run

```bash
javac ReverseSublist.java
java ReverseSublist < sample-input.txt
```

The expected output is in `sample-output.txt`.
