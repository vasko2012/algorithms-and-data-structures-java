# XML tag validator (stack)

Check whether the tags in a simplified XML document are **correctly nested**. Tags use square brackets: `[name]` opens a tag, `[/name]` closes it. Each line is either a tag or plain data. Print `1` if the document is valid, `0` if not.

```
Valid                 Invalid (tags cross)
[tag1]                [tag1]
[tag2]                [tag2]
Data                  Data
[/tag2]               [/tag1]   ← tag2 is still open
[/tag1]               [/tag2]
```

## How it works

The same idea as checking balanced parentheses, with tag names instead of `(` and `)`:

- **Opening tag:** push its name onto a stack.
- **Closing tag:** pop the top name; it must match. If the stack is empty or the names differ, the document is invalid.
- **Data line:** ignore it.
- **At the end:** the stack must be empty, otherwise some tags were never closed.

The stack works because the **last tag opened must be the first one closed**, which is exactly LIFO order.

Cases it catches:

| Input | Result | Why |
|---|---|---|
| `[a] [b] [/b] [/a]` | 1 | Properly nested |
| `[a] [b] [/a] [/b]` | 0 | `[/a]` arrives while `b` is on top |
| `[a]` | 0 | Stack not empty at the end |
| `[/a]` | 0 | Closing tag with nothing open |

**Complexity:** *O(n)* time and space for *n* lines.

## Run

```bash
javac XmlValidator.java
java XmlValidator < sample-input.txt
```

The expected output is in `sample-output.txt`.
