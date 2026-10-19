import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Pharmacy stock lookup backed by a hand-written hash table with separate chaining.
 *
 * Medicines are stored by name. The hash only looks at the first three letters of the
 * (upper-cased) name, as the problem requires, so names with the same prefix land in the
 * same bucket and are told apart by equals().
 */

class Medicine {
    final String name;
    final boolean positiveList;
    final int price;
    int stock;

    Medicine(String name, boolean positiveList, int price, int stock) {
        this.name = name.toUpperCase();
        this.positiveList = positiveList;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name + "\n" + (positiveList ? "POZ" : "NEG") + "\n" + price + "\n" + stock;
    }
}

/** Key used in the hash table: a case-insensitive medicine name. */
class MedicineKey {
    private static final int MOD = 102780;
    final String name;

    MedicineKey(String name) {
        this.name = name.toUpperCase();
    }

    /** h(w) = (29 * (29 * (29 * 0 + c1) + c2) + c3) % 102780, using the first three letters. */
    @Override
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < Math.min(3, name.length()); i++) {
            h = 29 * h + name.charAt(i);
        }
        return h % MOD;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MedicineKey other && name.equals(other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}

/** Closed-bucket (separate chaining) hash table: every bucket is a singly linked list. */
class ChainedHashTable<K, V> {
    private static final class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private final Node<K, V>[] buckets;

    @SuppressWarnings("unchecked")
    ChainedHashTable(int capacity) {
        buckets = (Node<K, V>[]) new Node[capacity];
    }

    private int bucketOf(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    void put(K key, V value) {
        int b = bucketOf(key);
        for (Node<K, V> n = buckets[b]; n != null; n = n.next) {
            if (n.key.equals(key)) {
                n.value = value; // key already present: replace the value
                return;
            }
        }
        buckets[b] = new Node<>(key, value, buckets[b]); // insert at the head of the chain
    }

    V get(K key) {
        for (Node<K, V> n = buckets[bucketOf(key)]; n != null; n = n.next) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }
}

public class Pharmacy {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        ChainedHashTable<MedicineKey, Medicine> stock = new ChainedHashTable<>(2 * n + 1);

        for (int i = 0; i < n; i++) {
            String[] p = br.readLine().trim().split("\\s+");
            Medicine m = new Medicine(p[0], p[1].equals("1"), Integer.parseInt(p[2]), Integer.parseInt(p[3]));
            stock.put(new MedicineKey(m.name), m);
        }

        // Queries come in pairs of lines: a medicine name, then how many pieces the customer wants.
        String name;
        while ((name = br.readLine()) != null && !(name = name.trim()).equals("KRAJ")) {
            int wanted = Integer.parseInt(br.readLine().trim());
            Medicine m = stock.get(new MedicineKey(name));

            if (m == null) {
                System.out.println("Nema takov lek");
                continue;
            }
            System.out.println(m);
            if (wanted > m.stock) {
                System.out.println("Nema dovolno lekovi");
            } else {
                m.stock -= wanted;
                System.out.println("Napravena naracka");
            }
        }
    }
}
