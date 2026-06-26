package exercise5;

// ─────────────────────────────────────────────
//  Task entity (node payload)
// ─────────────────────────────────────────────
class Task {
    int    taskId;
    String taskName;
    String status;      // "PENDING" | "IN_PROGRESS" | "DONE"
    Task   next;        // singly-linked pointer

    public Task(int taskId, String taskName, String status) {
        this.taskId   = taskId;
        this.taskName = taskName;
        this.status   = status;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | %-20s | %s]", taskId, taskName, status);
    }
}

// ─────────────────────────────────────────────
//  Singly Linked List
// ─────────────────────────────────────────────
class TaskLinkedList {
    private Task head = null;
    private int  size = 0;

    /** O(1) — prepend to head */
    public void addAtHead(Task t) {
        t.next = head;
        head   = t;
        size++;
        System.out.println("  ✔ Added (head): " + t);
    }

    /** O(n) — append to tail */
    public void addAtTail(Task t) {
        if (head == null) { addAtHead(t); return; }
        Task cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = t;
        size++;
        System.out.println("  ✔ Added (tail): " + t);
    }

    /** O(n) — linear scan */
    public Task search(int id) {
        Task cur = head;
        while (cur != null) {
            if (cur.taskId == id) return cur;
            cur = cur.next;
        }
        return null;
    }

    /** O(n) — visit every node */
    public void traverse() {
        System.out.println("  --- Task List (size=" + size + ") ---");
        Task cur = head;
        int  i   = 1;
        while (cur != null) {
            System.out.println("  " + i++ + ". " + cur);
            cur = cur.next;
        }
        if (size == 0) System.out.println("  (empty)");
    }

    /** O(n) — unlink the node */
    public void delete(int id) {
        if (head == null) { System.out.println("  ✗ List empty."); return; }
        if (head.taskId == id) {
            System.out.println("  ✔ Deleted: " + head);
            head = head.next;
            size--;
            return;
        }
        Task prev = head, cur = head.next;
        while (cur != null) {
            if (cur.taskId == id) {
                System.out.println("  ✔ Deleted: " + cur);
                prev.next = cur.next;
                size--;
                return;
            }
            prev = cur; cur = cur.next;
        }
        System.out.println("  ✗ Task ID " + id + " not found.");
    }
}

// ─────────────────────────────────────────────
//  Driver
// ─────────────────────────────────────────────
public class TaskManagement {
    public static void main(String[] args) {
        System.out.println("═══ Exercise 5 : Task Management (Linked List) ═══\n");

        TaskLinkedList list = new TaskLinkedList();
        list.addAtTail(new Task(1, "Design DB Schema",    "DONE"));
        list.addAtTail(new Task(2, "Implement REST API",  "IN_PROGRESS"));
        list.addAtTail(new Task(3, "Write Unit Tests",    "PENDING"));
        list.addAtHead(new Task(0, "Project Kickoff",     "DONE"));

        System.out.println();
        list.traverse();

        System.out.println("\n── Search ──");
        Task t = list.search(2);
        System.out.println("  Search ID=2 → " + (t != null ? t : "not found"));

        System.out.println("\n── Delete ──");
        list.delete(1);
        list.delete(99);
        list.traverse();

        System.out.println("""

  ── Complexity Analysis ──
  addAtHead → O(1)
  addAtTail → O(n) [O(1) with tail pointer]
  search    → O(n)
  traverse  → O(n)
  delete    → O(n)

  Linked List vs Array:
  ✔ Dynamic size — no pre-allocation or copying needed
  ✔ O(1) insert/delete at known node (no shifting)
  ✗ No random access — must traverse from head
  ✗ Extra memory per node (the 'next' pointer)
  Best use: frequent insertions/deletions, unknown or rapidly changing size.
""");
    }
}
