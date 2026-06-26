package exercise2;

import java.util.Arrays;
import java.util.Comparator;

// ─────────────────────────────────────────────
//  Product entity
// ─────────────────────────────────────────────
class Product {
    int    productId;
    String productName;
    String category;

    public Product(int productId, String productName, String category) {
        this.productId   = productId;
        this.productName = productName;
        this.category    = category;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | %-20s | %s]", productId, productName, category);
    }
}

// ─────────────────────────────────────────────
//  Search utilities
// ─────────────────────────────────────────────
class SearchEngine {

    /**
     * Linear Search — O(n)
     * Scans every element until a match is found.
     */
    public static int linearSearch(Product[] arr, String name) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].productName.equalsIgnoreCase(name)) return i;
        return -1;
    }

    /**
     * Binary Search — O(log n)
     * Requires the array to be sorted by productName.
     */
    public static int binarySearch(Product[] arr, String name) {
        int lo = 0, hi = arr.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = arr[mid].productName.compareToIgnoreCase(name);
            if (cmp == 0) return mid;
            if (cmp < 0)  lo = mid + 1;
            else          hi = mid - 1;
        }
        return -1;
    }
}

// ─────────────────────────────────────────────
//  Driver
// ─────────────────────────────────────────────
public class EcommerceSearch {
    public static void main(String[] args) {
        System.out.println("═══ Exercise 2 : E-commerce Search ═══\n");

        Product[] products = {
            new Product(1, "Laptop",     "Electronics"),
            new Product(2, "Headphones", "Electronics"),
            new Product(3, "Shoes",      "Fashion"),
            new Product(4, "Novel",      "Books"),
            new Product(5, "Blender",    "Kitchen"),
        };

        // ── Linear Search (unsorted array) ──
        System.out.println("── Linear Search ──");
        String target = "Shoes";
        int idx = SearchEngine.linearSearch(products, target);
        System.out.println("  Searching for '" + target + "' → " +
                (idx >= 0 ? "Found: " + products[idx] : "Not found"));

        // ── Sort array then Binary Search ──
        Product[] sorted = products.clone();
        Arrays.sort(sorted, Comparator.comparing(p -> p.productName.toLowerCase()));
        System.out.println("\n  Sorted array:");
        for (Product p : sorted) System.out.println("    " + p);

        System.out.println("\n── Binary Search ──");
        idx = SearchEngine.binarySearch(sorted, target);
        System.out.println("  Searching for '" + target + "' → " +
                (idx >= 0 ? "Found: " + sorted[idx] : "Not found"));

        System.out.println("""

  ── Complexity Analysis ──
  Linear Search : Best O(1)  | Avg O(n)    | Worst O(n)
  Binary Search : Best O(1)  | Avg O(log n)| Worst O(log n)

  For large catalogues (thousands of SKUs), Binary Search wins.
  Pre-sorting cost O(n log n) is a one-time hit; repeated queries then run in O(log n).
""");
    }
}
