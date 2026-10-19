import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringJoiner;

/**
 * Shaker (cocktail) sort: bubble sort that goes both ways.
 *
 * Each round makes two passes:
 *   - right to left, carrying the smallest remaining element to the front;
 *   - left to right, carrying the largest remaining element to the back.
 * The unsorted window shrinks by one on each side every round.
 *
 * The array is printed after every pass, as the problem asks.
 */
public class ShakerSort {

    static void shakerSort(int[] a) {
        int n = a.length;

        for (int round = 0; round < n / 2; round++) {
            int lo = round;
            int hi = n - 1 - round;

            // Pass 1: right to left - the smallest element bubbles down to a[lo].
            for (int j = hi; j > lo; j--) {
                if (a[j - 1] > a[j]) swap(a, j - 1, j);
            }
            print(a);

            // Pass 2: left to right - the largest element bubbles up to a[hi].
            for (int j = lo; j < hi; j++) {
                if (a[j] > a[j + 1]) swap(a, j, j + 1);
            }
            print(a);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void print(int[] a) {
        StringJoiner line = new StringJoiner(" ");
        for (int x : a) line.add(String.valueOf(x));
        System.out.println(line);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        String[] parts = br.readLine().trim().split("\\s+");

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i]);
        }
        shakerSort(a);
    }
}
