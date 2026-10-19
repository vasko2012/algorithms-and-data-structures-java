# Postfix expression evaluation (stack)

Evaluate an integer expression written in **postfix** (Reverse Polish) notation, where each operator comes *after* its two operands, so no parentheses are needed.

| Postfix | Usual (infix) form | Result |
|---|---|---|
| `1 2 +` | 1 + 2 | 3 |
| `1 2 3 * + 5 -` | 1 + 2·3 − 5 | 2 |
| `28 72 * 13 + 20 67 * +` | 28·72 + 13 + 20·67 | 3369 |
| `60 80 * 20 40 * /` | (60·80) / (20·40) | 6 |
| `8 9 * 4 3 - + 2 3 6 * + -` | (8·9 + (4−3)) − (2 + 3·6) | 53 |

## How it works

Read the tokens left to right:

- **Number:** push it onto the stack.
- **Operator:** pop two values, apply the operator, push the result.

At the end exactly one value is left, the answer. Walking through `1 2 3 * + 5 -`:

```
1      → [1]
2      → [1, 2]
3      → [1, 2, 3]
*      → [1, 6]        2·3
+      → [7]           1+6
5      → [7, 5]
-      → [2]           7−5
```

The order matters for `-` and `/`: the value on **top** of the stack is the *right* operand, so `7 4 -` is `7 − 4`, not `4 − 7`.

The input is split on whitespace, so numbers of any length work. Malformed input (too few operands, leftover numbers, division by zero) gives a clear error instead of a crash.

**Complexity:** *O(n)* time and *O(n)* space for *n* tokens.

## Run

```bash
javac PostfixEvaluation.java
java PostfixEvaluation < sample-input.txt
```

The expected output is in `sample-output.txt`; all examples in the table above give the listed results.
