import java.util.Scanner;

/**
 * Soldiers stand in a line stored as a doubly linked list of IDs. The commander picks
 * a sub-interval (from the soldier with ID a to the soldier with ID b) and reverses it.
 *
 * The reversal only re-links existing nodes - no nodes are created, copied or deleted.
 */

class DLLNode<E> {
    E element;
    DLLNode<E> pred, succ;

    DLLNode(E element, DLLNode<E> pred, DLLNode<E> succ) {
        this.element = element;
        this.pred = pred;
        this.succ = succ;
    }
}

class DLL<E> {
    DLLNode<E> first, last;

    void insertLast(E element) {
        DLLNode<E> node = new DLLNode<>(element, last, null);
        if (last == null) {
            first = node;
        } else {
            last.succ = node;
        }
        last = node;
    }

    DLLNode<E> find(E element) {
        for (DLLNode<E> n = first; n != null; n = n.succ) {
            if (n.element.equals(element)) return n;
        }
        return null;
    }

    /**
     * Reverses the part of the list from node `from` to node `to` (inclusive).
     * `from` must come before `to` in the list.
     */
    void reverse(DLLNode<E> from, DLLNode<E> to) {
        DLLNode<E> before = from.pred;
        DLLNode<E> after = to.succ;

        // 1. Inside the interval, every node swaps its pred and succ pointers.
        DLLNode<E> cur = from;
        while (true) {
            DLLNode<E> next = cur.succ;
            cur.succ = cur.pred;
            cur.pred = next;
            if (cur == to) break;
            cur = next;
        }

        // 2. `to` is now the start of the interval and `from` its end - reconnect both sides.
        to.pred = before;
        from.succ = after;

        if (before != null) before.succ = to; else first = to;  // interval started at the head
        if (after != null) after.pred = from; else last = from; // interval ended at the tail
    }

    /** Forward: first -> last. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DLLNode<E> n = first; n != null; n = n.succ) {
            if (sb.length() > 0) sb.append("<->");
            sb.append(n.element);
        }
        return sb.toString();
    }

    /** Backward: last -> first. Checks that the pred links are correct too. */
    String toStringReversed() {
        StringBuilder sb = new StringBuilder();
        for (DLLNode<E> n = last; n != null; n = n.pred) {
            if (sb.length() > 0) sb.append("<->");
            sb.append(n.element);
        }
        return sb.toString();
    }
}

public class ReverseSublist {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        DLL<Integer> soldiers = new DLL<>();
        for (int i = 0; i < n; i++) {
            soldiers.insertLast(in.nextInt());
        }

        int a = in.nextInt();
        int b = in.nextInt();
        soldiers.reverse(soldiers.find(a), soldiers.find(b));

        System.out.println(soldiers);
        System.out.println(soldiers.toStringReversed());
    }
}
