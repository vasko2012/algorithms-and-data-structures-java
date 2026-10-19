# Windows Explorer (general tree)

A minimal file explorer simulated with a general tree. The program starts in the root folder `c:` and reads a list of commands:

| Command | What it does |
|---|---|
| `CREATE name` | Adds a folder/file to the current folder, keeping the children in alphabetical order |
| `OPEN name` | Moves into a child folder |
| `DELETE name` | Removes a child folder/file together with everything inside it |
| `BACK` | Goes up to the parent folder |
| `PATH` | Prints the path of the current folder, e.g. `c:\a\c\` |
| `PRINT` | Prints the whole tree, one entry per line, indented by depth |

## How it works

- Each node stores **parent**, **first child** and **next sibling** pointers (first-child / next-sibling representation), so a folder can have any number of children.
- `CREATE` is a **sorted insert** into the sibling list: walk the siblings until the next one should come after the new name, then link the new node in. *O(k)* for a folder with *k* children.
- `PATH` walks the parent pointers up to the root and builds the string from right to left. *O(depth)*.
- `PRINT` is a **pre-order traversal** with the depth used as indentation. *O(n)*.

## Run

```bash
javac WindowsExplorer.java
java WindowsExplorer < sample-input.txt
```

The expected output is in `sample-output.txt`.
