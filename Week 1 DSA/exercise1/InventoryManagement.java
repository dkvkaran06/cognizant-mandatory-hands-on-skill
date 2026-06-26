package exercise1;

import java.util.HashMap;


class Product {
    int productId;
    String productName;
    int quantity;
    double price;

    public Product(int productId, String productName, int quantity, double price) {
        this.productId   = productId;
        this.productName = productName;
        this.quantity    = quantity;
        this.price       = price;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | %s | Qty:%d | ₹%.2f]",
                productId, productName, quantity, price);
    }
}

class Inventory {
    private final HashMap<Integer, Product> store = new HashMap<>();

    public void addProduct(Product p) {
        if (store.containsKey(p.productId)) {
            System.out.println("Product ID " + p.productId + " already exists.");
            return;
        }
        store.put(p.productId, p);
        System.out.println("Added: " + p);
    }

    public void updateProduct(int id, int newQty, double newPrice) {
        Product p = store.get(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        p.quantity = newQty;
        p.price = newPrice;
        System.out.println("Updated: " + p);
    }

    public void deleteProduct(int id) {
        Product removed = store.remove(id);
        if (removed == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println("Deleted: " + removed);
        }
    }

    public void display() {
        System.out.println("Inventory");
        if (store.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        for (Product p : store.values()) {
            System.out.println(p);
        }
    }
}

public class InventoryManagement {
    public static void main(String[] args) {
        System.out.println("Exercise 1: Inventory Management\n");

        Inventory inv = new Inventory();
        inv.addProduct(new Product(101, "Laptop", 50, 75000));
        inv.addProduct(new Product(102, "Mouse", 200, 500));
        inv.addProduct(new Product(103, "Keyboard", 150, 1200));
        inv.addProduct(new Product(101, "Duplicate", 5, 100));
        inv.display();

        System.out.println("\nUpdate & Delete");
        inv.updateProduct(102, 180, 450);
        inv.deleteProduct(103);
        inv.deleteProduct(999);
        inv.display();
    }
}
