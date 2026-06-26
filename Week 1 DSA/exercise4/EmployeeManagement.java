package exercise4;

// ─────────────────────────────────────────────
//  Employee entity
// ─────────────────────────────────────────────
class Employee {
    int    employeeId;
    String name;
    String position;
    double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name       = name;
        this.position   = position;
        this.salary     = salary;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | %-12s | %-15s | ₹%.2f]",
                employeeId, name, position, salary);
    }
}

// ─────────────────────────────────────────────
//  Array-backed Employee store
// ─────────────────────────────────────────────
class EmployeeStore {
    private Employee[] data;
    private int size = 0;

    public EmployeeStore(int capacity) { data = new Employee[capacity]; }

    /** O(1) — append at next free slot */
    public void add(Employee e) {
        if (size == data.length) {
            System.out.println("  ✗ Store full – resize needed.");
            return;
        }
        data[size++] = e;
        System.out.println("  ✔ Added: " + e);
    }

    /** O(n) — linear scan */
    public Employee search(int id) {
        for (int i = 0; i < size; i++)
            if (data[i].employeeId == id) return data[i];
        return null;
    }

    /** O(n) — visit every element */
    public void traverse() {
        System.out.println("  --- Employee List ---");
        if (size == 0) { System.out.println("  (empty)"); return; }
        for (int i = 0; i < size; i++) System.out.println("  " + data[i]);
    }

    /**
     * O(n) — find element then shift remaining left.
     * Preserves order; no gaps.
     */
    public void delete(int id) {
        for (int i = 0; i < size; i++) {
            if (data[i].employeeId == id) {
                System.out.println("  ✔ Deleted: " + data[i]);
                // shift left
                for (int j = i; j < size - 1; j++) data[j] = data[j + 1];
                data[--size] = null;
                return;
            }
        }
        System.out.println("  ✗ Employee ID " + id + " not found.");
    }
}

// ─────────────────────────────────────────────
//  Driver
// ─────────────────────────────────────────────
public class EmployeeManagement {
    public static void main(String[] args) {
        System.out.println("═══ Exercise 4 : Employee Management ═══\n");

        EmployeeStore store = new EmployeeStore(10);
        store.add(new Employee(1, "Ankit",  "Developer",  85000));
        store.add(new Employee(2, "Meera",  "Designer",   72000));
        store.add(new Employee(3, "Rohan",  "Manager",   110000));
        store.add(new Employee(4, "Pooja",  "Analyst",    68000));

        System.out.println();
        store.traverse();

        System.out.println("\n── Search ──");
        Employee found = store.search(3);
        System.out.println("  Search ID=3 → " + (found != null ? found : "not found"));

        System.out.println("\n── Delete ──");
        store.delete(2);
        store.delete(99);
        store.traverse();

        System.out.println("""

  ── Complexity Analysis ──
  add      → O(1)  (append to end)
  search   → O(n)  (linear scan; O(1) if index known)
  traverse → O(n)
  delete   → O(n)  (scan + shift)

  Array advantages : O(1) random access by index, cache-friendly, low overhead.
  Limitations      : Fixed size, O(n) insert/delete due to shifting.
  When to use      : Fixed-size datasets, frequent index-based access, memory-tight scenarios.
""");
    }
}
