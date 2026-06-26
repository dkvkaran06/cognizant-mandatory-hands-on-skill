package exercise6;

import java.util.Arrays;
import java.util.Comparator;

// ─────────────────────────────────────────────
//  Book entity
// ─────────────────────────────────────────────
class Book {
    int    bookId;
    String title;
    String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title  = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | %-30s | %s]", bookId, title, author);
    }
}

// ─────────────────────────────────────────────
//  Library search engine
// ─────────────────────────────────────────────
class Library {

    /** O(n) — scans until match found */
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book b : books)
            if (b.title.equalsIgnoreCase(title)) return b;
        return null;
    }

    /**
     * O(log n) — array MUST be sorted by title.
     * Uses case-insensitive comparison.
     */
    public static Book binarySearchByTitle(Book[] books, String title) {
        int lo = 0, hi = books.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = books[mid].title.compareToIgnoreCase(title);
            if (cmp == 0) return books[mid];
            if (cmp < 0)  lo = mid + 1;
            else          hi = mid - 1;
        }
        return null;
    }
}

// ─────────────────────────────────────────────
//  Driver
// ─────────────────────────────────────────────
public class LibraryManagement {
    public static void main(String[] args) {
        System.out.println("═══ Exercise 6 : Library Management ═══\n");

        Book[] books = {
            new Book(3, "Clean Code",              "Robert C. Martin"),
            new Book(1, "Atomic Habits",           "James Clear"),
            new Book(5, "The Pragmatic Programmer","David Thomas"),
            new Book(2, "Design Patterns",         "Gang of Four"),
            new Book(4, "Thinking Fast and Slow",  "Daniel Kahneman"),
        };

        String target = "Design Patterns";

        // ── Linear Search (unsorted) ──
        System.out.println("── Linear Search (unsorted array) ──");
        Book result = Library.linearSearchByTitle(books, target);
        System.out.println("  Searching '" + target + "' → " +
                (result != null ? result : "not found"));

        // ── Sort then Binary Search ──
        Book[] sorted = books.clone();
        Arrays.sort(sorted, Comparator.comparing(b -> b.title.toLowerCase()));
        System.out.println("\n  Sorted titles:");
        for (Book b : sorted) System.out.println("    " + b);

        System.out.println("\n── Binary Search (sorted array) ──");
        result = Library.binarySearchByTitle(sorted, target);
        System.out.println("  Searching '" + target + "' → " +
                (result != null ? result : "not found"));

        // Not-found case
        result = Library.binarySearchByTitle(sorted, "Unknown Book");
        System.out.println("  Searching 'Unknown Book' → " +
                (result != null ? result : "not found"));

        System.out.println("""

  ── When to Use Which ──
  Linear Search :
    • Unsorted data
    • Small collections (< ~50 items)
    • One-time searches (sorting first would cost more)
    • Time: O(n)

  Binary Search :
    • Sorted data (or can afford one-time sort)
    • Large collections (thousands of books)
    • Repeated searches on the same dataset
    • Time: O(log n)   e.g. 1,000 books → max 10 comparisons
""");
    }
}
