import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A tiny Windows-Explorer-like file system built on a general tree.
 *
 * Each node keeps a pointer to its parent, its first child and its next sibling
 * (the "first-child / next-sibling" representation), and the children of every
 * folder are always kept in lexicographic order.
 */

interface Tree<E> {
    Node<E> root();

    Node<E> parent(Node<E> node);

    int childCount(Node<E> node);

    void makeRoot(E elem);

    Node<E> addChild(Node<E> node, E elem);

    void remove(Node<E> node);

    Iterator<E> children(Node<E> node);
}

interface Node<E> {
    E getElement();

    void setElement(E elem);
}

class SLLNode<E> implements Node<E> {
    SLLNode<E> parent, sibling, firstChild;
    E element;

    SLLNode(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }
}

class SLLTree<E extends Comparable<E>> implements Tree<E> {
    SLLNode<E> root;

    public Node<E> root() {
        return root;
    }

    public Node<E> parent(Node<E> node) {
        return ((SLLNode<E>) node).parent;
    }

    public int childCount(Node<E> node) {
        int count = 0;
        for (SLLNode<E> c = ((SLLNode<E>) node).firstChild; c != null; c = c.sibling) {
            count++;
        }
        return count;
    }

    public void makeRoot(E elem) {
        root = new SLLNode<>(elem);
    }

    /** Adds a child so that the parent's children stay sorted. */
    public Node<E> addChild(Node<E> node, E elem) {
        SLLNode<E> parent = (SLLNode<E>) node;
        SLLNode<E> newNode = new SLLNode<>(elem);
        newNode.parent = parent;

        // Empty folder, or the new name comes before the first child: insert at the front.
        if (parent.firstChild == null || elem.compareTo(parent.firstChild.element) < 0) {
            newNode.sibling = parent.firstChild;
            parent.firstChild = newNode;
            return newNode;
        }

        // Otherwise walk until the next sibling should come after the new name.
        SLLNode<E> prev = parent.firstChild;
        while (prev.sibling != null && elem.compareTo(prev.sibling.element) > 0) {
            prev = prev.sibling;
        }
        newNode.sibling = prev.sibling;
        prev.sibling = newNode;
        return newNode;
    }

    /** Unlinks a node (and with it its whole subtree) from its parent. */
    public void remove(Node<E> node) {
        SLLNode<E> target = (SLLNode<E>) node;
        if (target.parent == null) {
            root = null;
            return;
        }
        if (target.parent.firstChild == target) {
            target.parent.firstChild = target.sibling;
            return;
        }
        SLLNode<E> prev = target.parent.firstChild;
        while (prev.sibling != target) {
            prev = prev.sibling;
        }
        prev.sibling = target.sibling;
    }

    /** Returns the direct child with the given element, or null. */
    SLLNode<E> findChild(SLLNode<E> parent, E elem) {
        for (SLLNode<E> c = parent.firstChild; c != null; c = c.sibling) {
            if (c.element.equals(elem)) {
                return c;
            }
        }
        return null;
    }

    public Iterator<E> children(Node<E> node) {
        return new Iterator<E>() {
            SLLNode<E> current = ((SLLNode<E>) node).firstChild;

            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                E elem = current.element;
                current = current.sibling;
                return elem;
            }
        };
    }

    /** Prints every node on its own line, indented by its depth. */
    void print() {
        print(root, 0);
    }

    private void print(SLLNode<E> node, int level) {
        if (node == null) {
            return;
        }
        System.out.println(" ".repeat(level) + node.element);
        for (SLLNode<E> c = node.firstChild; c != null; c = c.sibling) {
            print(c, level + 1);
        }
    }
}

public class WindowsExplorer {

    /** Builds the path from the root down to the given folder, e.g. c:\a\b\ */
    static String path(SLLNode<String> node) {
        StringBuilder sb = new StringBuilder();
        for (SLLNode<String> n = node; n != null; n = n.parent) {
            sb.insert(0, n.element + "\\");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        SLLTree<String> tree = new SLLTree<>();
        tree.makeRoot("c:");
        SLLNode<String> current = tree.root;

        for (int i = 0; i < n; i++) {
            String[] parts = br.readLine().trim().split(" ");
            String command = parts[0];
            String name = parts.length > 1 ? parts[1] : null;

            switch (command) {
                case "CREATE" -> tree.addChild(current, name);
                case "OPEN" -> {
                    SLLNode<String> child = tree.findChild(current, name);
                    if (child != null) {
                        current = child;
                    }
                }
                case "DELETE" -> {
                    SLLNode<String> child = tree.findChild(current, name);
                    if (child != null) {
                        tree.remove(child);
                    }
                }
                case "BACK" -> {
                    if (current.parent != null) {
                        current = current.parent;
                    }
                }
                case "PATH" -> System.out.println(path(current));
                case "PRINT" -> tree.print();
                default -> throw new IllegalArgumentException("Unknown command: " + command);
            }
        }
    }
}
