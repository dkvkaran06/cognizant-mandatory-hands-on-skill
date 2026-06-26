package exercise3;

import java.util.Arrays;

// ─────────────────────────────────────────────
//  Order entity
// ─────────────────────────────────────────────
class Order {
    int    orderId;
    String customerName;
    double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId      = orderId;
        this.customerName = customerName;
        this.totalPrice   = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("[#%d | %-12s | ₹%.2f]", orderId, customerName, totalPrice);
    }
}

// ─────────────────────────────────────────────
//  Sorting algorithms
// ─────────────────────────────────────────────
class Sorter {

    /**
     * Bubble Sort — O(n²) average & worst
     * Simple but slow; useful only for tiny datasets.
     */
    public static Order[] bubbleSort(Order[] arr) {
        Order[] a = arr.clone();
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j].totalPrice > a[j + 1].totalPrice) {
                    Order tmp = a[j]; a[j] = a[j + 1]; a[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;   // early exit optimisation
        }
        return a;
    }

    /**
     * Quick Sort — O(n log n) average, O(n²) worst (rare with good pivot)
     * In-place, cache-friendly, generally fastest in practice.
     */
    public static Order[] quickSort(Order[] arr) {
        Order[] a = arr.clone();
        qSort(a, 0, a.length - 1);
        return a;
    }

    private static void qSort(Order[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = partition(a, lo, hi);
        qSort(a, lo, p - 1);
        qSort(a, p + 1, hi);
    }

    private static int partition(Order[] a, int lo, int hi) {
        double pivot = a[hi].totalPrice;
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (a[j].totalPrice <= pivot) {
                i++;
                Order tmp = a[i]; a[i] = a[j]; a[j] = tmp;
            }
        }
        Order tmp = a[i + 1]; a[i + 1] = a[hi]; a[hi] = tmp;
        return i + 1;
    }
}

// ─────────────────────────────────────────────
//  Driver
// ─────────────────────────────────────────────
public class SortOrders {
    static void print(String label, Order[] orders) {
        System.out.println("  " + label);
        for (Order o : orders) System.out.println("    " + o);
    }

    public static void main(String[] args) {
        System.out.println("═══ Exercise 3 : Sorting Customer Orders ═══\n");

        Order[] orders = {
            new Order(1, "Ravi",    4500.00),
            new Order(2, "Priya",  12300.00),
            new Order(3, "Arjun",   780.50),
            new Order(4, "Sneha",  9999.99),
            new Order(5, "Dev",    3200.00),
        };

        print("Original:", orders);
        System.out.println();
        print("Bubble Sort (asc):", Sorter.bubbleSort(orders));
        System.out.println();
        print("Quick Sort  (asc):", Sorter.quickSort(orders));

        System.out.println("""

  ── Complexity Analysis ──
  Bubble Sort : Best O(n) [optimised] | Avg O(n²) | Worst O(n²)  — Space O(1)
  Quick Sort  : Best O(n log n)       | Avg O(n log n) | Worst O(n²) — Space O(log n)

  Quick Sort is preferred because:
   • Average case is O(n log n) vs Bubble's O(n²)
   • In-place (no extra array needed)
   • CPU cache-friendly access pattern
   • Worst case (already sorted) avoided with random/median-of-3 pivot
""");
    }
}
