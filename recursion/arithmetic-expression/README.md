# Arithmetic expression (recursion, divide and conquer)

Evaluate a fully parenthesised expression of the form `(A+B)` or `(A-B)`, where `A` and `B` are either single digits or other expressions of the same form.

| Expression | Result |
|---|---|
| `(1+2)` | 3 |
| `((9-3)+(2-8))` | 0 |
| `(5-(4-(3-(2-1))))` | 3 |
| `((1+2)-(3-(4+5)))` | 9 |

## How it works

The definition is recursive, so the solution is too:

```
expr := digit | ( expr op expr )
```

`evaluate(l, r)` works on the slice of the string between positions `l` and `r`:

1. **Base case:** one character means it's a digit, so return its value.
2. **Find the main operator:** scan inside the outer parentheses with a `depth` counter (+1 on `(`, −1 on `)`). The first `+` or `-` found at depth 0 is the operator that splits this expression into its two halves.
3. **Divide and conquer:** evaluate the left half and the right half recursively, then add or subtract.

For `((1+2)-(3-(4+5)))`:

```
((1+2)-(3-(4+5)))
 └─┬─┘ ^ └───┬───┘     main operator '-' at depth 0
   3        (3-9) = -6
          3 - (-6) = 9
```

**Complexity:** *O(n²)* in the worst case (each level scans its slice to find the operator), *O(n)* recursion depth for deeply nested input.

## Run

```bash
javac ArithmeticExpression.java
java ArithmeticExpression < sample-input.txt
```

The expected output is in `sample-output.txt`.
