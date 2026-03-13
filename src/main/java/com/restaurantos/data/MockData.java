package com.restaurantos.data;

import com.restaurantos.model.*;
import com.restaurantos.model.enums.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.util.List;

public final class MockData {

    private MockData() {}

    private static LocalDateTime minsAgo(int minutes) {
        return LocalDateTime.now().minusMinutes(minutes);
    }

    // ── Products (22 items) ──────────────────────────────────────────────
    public static ObservableList<Product> products() {
        return FXCollections.observableArrayList(
            new Product("p1",  "Espresso",           ProductCategory.DRINKS,     2.50,  3, true,  "\u2615"),
            new Product("p2",  "Cappuccino",          ProductCategory.DRINKS,     3.50,  5, true,  "\u2615"),
            new Product("p3",  "Latte",               ProductCategory.DRINKS,     4.00,  5, true,  "\u2615"),
            new Product("p4",  "Fresh Orange Juice",   ProductCategory.DRINKS,     4.50,  4, true,  "\uD83E\uDD43"),
            new Product("p5",  "Water",               ProductCategory.DRINKS,     1.50,  1, true,  "\uD83D\uDCA7"),
            new Product("p6",  "Croissant",           ProductCategory.PASTRIES,   3.00,  8, true,  "\uD83E\uDD50"),
            new Product("p7",  "Pain au Chocolat",     ProductCategory.PASTRIES,   3.50,  8, true,  "\uD83E\uDD50"),
            new Product("p8",  "Blueberry Muffin",     ProductCategory.PASTRIES,   3.00, 10, true,  "\uD83E\uDDC1"),
            new Product("p9",  "Cinnamon Roll",        ProductCategory.PASTRIES,   4.00, 12, true,  "\uD83C\uDF6A"),
            new Product("p10", "Club Sandwich",        ProductCategory.SANDWICHES, 8.50, 12, true,  "\uD83E\uDD6A"),
            new Product("p11", "BLT",                  ProductCategory.SANDWICHES, 7.50, 10, true,  "\uD83E\uDD6A"),
            new Product("p12", "Grilled Cheese",       ProductCategory.SANDWICHES, 6.50,  8, true,  "\uD83E\uDDC0"),
            new Product("p13", "Caesar Salad",         ProductCategory.MEALS,     10.50, 10, true,  "\uD83E\uDD57"),
            new Product("p14", "Pasta Carbonara",      ProductCategory.MEALS,     12.00, 18, true,  "\uD83C\uDF5D"),
            new Product("p15", "Grilled Salmon",       ProductCategory.MEALS,     16.50, 22, true,  "\uD83D\uDC1F"),
            new Product("p16", "Steak Frites",         ProductCategory.MEALS,     18.00, 25, true,  "\uD83E\uDD69"),
            new Product("p17", "Margherita Pizza",     ProductCategory.MEALS,     11.00, 15, true,  "\uD83C\uDF55"),
            new Product("p18", "Chicken Burger",       ProductCategory.MEALS,      9.50, 14, true,  "\uD83C\uDF54"),
            new Product("p19", "Tiramisu",             ProductCategory.DESSERTS,   6.50,  5, true,  "\uD83C\uDF70"),
            new Product("p20", "Cr\u00e8me Br\u00fbl\u00e9e", ProductCategory.DESSERTS, 7.00, 15, true, "\uD83C\uDF6E"),
            new Product("p21", "Chocolate Fondant",    ProductCategory.DESSERTS,   8.00, 20, false, "\uD83C\uDF6B"),
            new Product("p22", "Fruit Tart",           ProductCategory.DESSERTS,   6.00,  5, true,  "\uD83C\uDF53")
        );
    }

    // ── Orders (7 orders with items) ─────────────────────────────────────
    public static ObservableList<Order> orders() {
        ObservableList<Order> list = FXCollections.observableArrayList();

        // Order 1
        Order o1 = new Order("ord-001", "t1", 1, "Alice Martin", "Jean", minsAgo(35), OrderStatus.PREPARING, Priority.NORMAL, "", 25);
        o1.getItems().addAll(
            new OrderItem("oi-001", "p2",  "Cappuccino",     2,  3.50, ItemStatus.READY,     "", 5),
            new OrderItem("oi-002", "p14", "Pasta Carbonara", 1, 12.00, ItemStatus.PREPARING, "", 18),
            new OrderItem("oi-003", "p19", "Tiramisu",        1,  6.50, ItemStatus.PENDING,   "", 5)
        );
        list.add(o1);

        // Order 2
        Order o2 = new Order("ord-002", "t3", 3, "Pierre Dupont", "Marie", minsAgo(20), OrderStatus.NEW, Priority.HIGH, "Nut allergy", 22);
        o2.getItems().addAll(
            new OrderItem("oi-004", "p1",  "Espresso",       1, 2.50, ItemStatus.PENDING, "", 3),
            new OrderItem("oi-005", "p16", "Steak Frites",   2, 18.00, ItemStatus.PENDING, "Medium rare", 25),
            new OrderItem("oi-006", "p13", "Caesar Salad",   1, 10.50, ItemStatus.PENDING, "", 10)
        );
        list.add(o2);

        // Order 3
        Order o3 = new Order("ord-003", "t5", 5, "Sophie Bernard", "Jean", minsAgo(45), OrderStatus.READY, Priority.NORMAL, "", 15);
        o3.getItems().addAll(
            new OrderItem("oi-007", "p6",  "Croissant",       2, 3.00, ItemStatus.READY, "", 8),
            new OrderItem("oi-008", "p3",  "Latte",           2, 4.00, ItemStatus.READY, "", 5),
            new OrderItem("oi-009", "p22", "Fruit Tart",      1, 6.00, ItemStatus.READY, "", 5)
        );
        list.add(o3);

        // Order 4
        Order o4 = new Order("ord-004", "t2", 2, "Lucas Moreau", "Marie", minsAgo(10), OrderStatus.NEW, Priority.CRITICAL, "VIP client", 20);
        o4.getItems().addAll(
            new OrderItem("oi-010", "p15", "Grilled Salmon",  1, 16.50, ItemStatus.PENDING, "", 22),
            new OrderItem("oi-011", "p4",  "Fresh Orange Juice", 1, 4.50, ItemStatus.PENDING, "", 4),
            new OrderItem("oi-012", "p20", "Cr\u00e8me Br\u00fbl\u00e9e", 1, 7.00, ItemStatus.PENDING, "", 15)
        );
        list.add(o4);

        // Order 5
        Order o5 = new Order("ord-005", "t7", 7, "Emma Leroy", "Jean", minsAgo(55), OrderStatus.SERVED, Priority.NORMAL, "", 12);
        o5.getItems().addAll(
            new OrderItem("oi-013", "p10", "Club Sandwich",   1, 8.50, ItemStatus.READY, "", 12),
            new OrderItem("oi-014", "p5",  "Water",           2, 1.50, ItemStatus.READY, "", 1)
        );
        list.add(o5);

        // Order 6
        Order o6 = new Order("ord-006", "t4", 4, "Hugo Petit", "Marie", minsAgo(15), OrderStatus.PREPARING, Priority.HIGH, "Gluten free bread", 18);
        o6.getItems().addAll(
            new OrderItem("oi-015", "p12", "Grilled Cheese",  1, 6.50,  ItemStatus.PREPARING, "GF bread", 8),
            new OrderItem("oi-016", "p17", "Margherita Pizza", 1, 11.00, ItemStatus.PREPARING, "GF crust", 15),
            new OrderItem("oi-017", "p2",  "Cappuccino",      1, 3.50,  ItemStatus.READY,     "", 5)
        );
        list.add(o6);

        // Order 7
        Order o7 = new Order("ord-007", "t9", 9, "Camille Roux", "Jean", minsAgo(60), OrderStatus.COMPLETED, Priority.NORMAL, "", 10);
        o7.getItems().addAll(
            new OrderItem("oi-018", "p1", "Espresso",  2, 2.50, ItemStatus.READY, "", 3),
            new OrderItem("oi-019", "p9", "Cinnamon Roll", 1, 4.00, ItemStatus.READY, "", 12)
        );
        list.add(o7);

        return list;
    }

    // ── Tables (10 tables) ───────────────────────────────────────────────
    public static ObservableList<RestaurantTable> tables() {
        return FXCollections.observableArrayList(
            new RestaurantTable("t1",  1, 2, 2, TableStatus.OCCUPIED, "ord-001", minsAgo(35)),
            new RestaurantTable("t2",  2, 4, 1, TableStatus.ORDERING, "ord-004", minsAgo(10)),
            new RestaurantTable("t3",  3, 4, 3, TableStatus.WAITING,  "ord-002", minsAgo(20)),
            new RestaurantTable("t4",  4, 6, 4, TableStatus.OCCUPIED, "ord-006", minsAgo(15)),
            new RestaurantTable("t5",  5, 2, 2, TableStatus.PAY,      "ord-003", minsAgo(45)),
            new RestaurantTable("t6",  6, 8, 0, TableStatus.FREE,     null,       null),
            new RestaurantTable("t7",  7, 4, 2, TableStatus.OCCUPIED, "ord-005", minsAgo(55)),
            new RestaurantTable("t8",  8, 2, 0, TableStatus.FREE,     null,       null),
            new RestaurantTable("t9",  9, 6, 0, TableStatus.FREE,     null,       null),
            new RestaurantTable("t10",10, 4, 0, TableStatus.FREE,     null,       null)
        );
    }

    // ── Clients (10 clients) ─────────────────────────────────────────────
    public static ObservableList<Client> clients() {
        return FXCollections.observableArrayList(
            new Client("c1",  "Alice Martin",    "+33 6 12 34 56 78", "alice@email.com",    42, 1250.00, minsAgo(35)),
            new Client("c2",  "Pierre Dupont",   "+33 6 23 45 67 89", "pierre@email.com",   28,  890.00, minsAgo(20)),
            new Client("c3",  "Sophie Bernard",  "+33 6 34 56 78 90", "sophie@email.com",   55, 2100.00, minsAgo(45)),
            new Client("c4",  "Lucas Moreau",    "+33 6 45 67 89 01", "lucas@email.com",    35, 1580.00, minsAgo(10)),
            new Client("c5",  "Emma Leroy",      "+33 6 56 78 90 12", "emma@email.com",     12,  340.00, minsAgo(55)),
            new Client("c6",  "Hugo Petit",      "+33 6 67 89 01 23", "hugo@email.com",     19,  620.00, minsAgo(15)),
            new Client("c7",  "Camille Roux",    "+33 6 78 90 12 34", "camille@email.com",   8,  180.00, minsAgo(60)),
            new Client("c8",  "Nathan Fournier", "+33 6 89 01 23 45", "nathan@email.com",   61, 2800.00, minsAgo(120)),
            new Client("c9",  "Lea Girard",      "+33 6 90 12 34 56", "lea@email.com",       3,   75.00, minsAgo(1440)),
            new Client("c10", "Tom Bonnet",      "+33 6 01 23 45 67", "tom@email.com",      22,  710.00, minsAgo(180))
        );
    }

    // ── Bills (7 bills) ──────────────────────────────────────────────────
    public static ObservableList<Bill> bills() {
        return FXCollections.observableArrayList(
            new Bill("b1", "t1", 1, "Alice Martin",    "ord-001", 25.50, PaymentMethod.CARD,    BillStatus.PENDING,   minsAgo(35)),
            new Bill("b2", "t3", 3, "Pierre Dupont",   "ord-002", 49.00, PaymentMethod.CASH,    BillStatus.PENDING,   minsAgo(20)),
            new Bill("b3", "t5", 5, "Sophie Bernard",  "ord-003", 20.00, PaymentMethod.CARD,    BillStatus.PENDING,   minsAgo(45)),
            new Bill("b4", "t2", 2, "Lucas Moreau",    "ord-004", 28.00, PaymentMethod.DIGITAL, BillStatus.PENDING,   minsAgo(10)),
            new Bill("b5", "t7", 7, "Emma Leroy",      "ord-005", 11.50, PaymentMethod.CASH,    BillStatus.PAID,      minsAgo(55)),
            new Bill("b6", "t4", 4, "Hugo Petit",      "ord-006", 21.00, PaymentMethod.CARD,    BillStatus.PENDING,   minsAgo(15)),
            new Bill("b7", "t9", 9, "Camille Roux",    "ord-007",  9.00, PaymentMethod.DIGITAL, BillStatus.PAID,      minsAgo(60))
        );
    }

    // ── Analytics helpers ────────────────────────────────────────────────
    public static double todayRevenue() { return 1247.50; }
    public static double avgPrepTime() { return 14.3; }
    public static int todayCustomers() { return 42; }
    public static double satisfactionPct() { return 94.0; }

    public static List<String[]> topProducts() {
        return List.of(
            new String[]{"Cappuccino",      "45"},
            new String[]{"Pasta Carbonara", "32"},
            new String[]{"Club Sandwich",   "28"},
            new String[]{"Caesar Salad",    "25"},
            new String[]{"Steak Frites",    "22"}
        );
    }

    public static List<String[]> revenueByHour() {
        return List.of(
            new String[]{"08:00",  "85.00"},
            new String[]{"09:00", "142.50"},
            new String[]{"10:00", "198.00"},
            new String[]{"11:00", "234.50"},
            new String[]{"12:00", "312.00"},
            new String[]{"13:00", "275.50"}
        );
    }
}
