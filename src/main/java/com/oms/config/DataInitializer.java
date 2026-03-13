package com.oms.config;

import com.oms.dto.PrepTimeData;
import com.oms.dto.RevenueDataPoint;
import com.oms.dto.TopProductData;
import com.oms.model.*;
import com.oms.model.enums.*;
import com.oms.repository.*;
import com.oms.service.ReportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;
    private final ClientRepository clientRepository;
    private final BillRepository billRepository;
    private final ReportService reportService;

    public DataInitializer(ProductRepository productRepository, OrderRepository orderRepository,
                           TableRepository tableRepository, ClientRepository clientRepository,
                           BillRepository billRepository, ReportService reportService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.clientRepository = clientRepository;
        this.billRepository = billRepository;
        this.reportService = reportService;
    }

    @Override
    public void run(String... args) {
        initProducts();
        initTables();
        initOrders();
        initClients();
        initBills();
        initReportData();
    }

    private void initProducts() {
        List<Product> products = List.of(
            new Product("p1", "Espresso", ProductCategory.DRINKS, 2.50, 2, true, "\u2615"),
            new Product("p2", "Cappuccino", ProductCategory.DRINKS, 3.50, 3, true, "\u2615"),
            new Product("p3", "Orange Juice", ProductCategory.DRINKS, 4.00, 2, true, "\uD83C\uDF4A"),
            new Product("p4", "Lemon Tea", ProductCategory.DRINKS, 3.00, 3, true, "\uD83C\uDF75"),
            new Product("p5", "Sparkling Water", ProductCategory.DRINKS, 2.00, 1, true, "\uD83D\uDCA7"),
            new Product("p6", "Croissant", ProductCategory.PASTRIES, 3.50, 5, true, "\uD83E\uDD50"),
            new Product("p7", "Blueberry Muffin", ProductCategory.PASTRIES, 3.00, 4, true, "\uD83E\uDED0"),
            new Product("p8", "Cinnamon Danish", ProductCategory.PASTRIES, 4.00, 5, true, "\uD83C\uDF5E"),
            new Product("p9", "Cinnamon Roll", ProductCategory.PASTRIES, 4.50, 6, false, "\uD83C\uDF00"),
            new Product("p10", "Club Sandwich", ProductCategory.SANDWICHES, 9.50, 8, true, "\uD83E\uDD6A"),
            new Product("p11", "BLT Sandwich", ProductCategory.SANDWICHES, 8.50, 7, true, "\uD83E\uDD53"),
            new Product("p12", "Veggie Wrap", ProductCategory.SANDWICHES, 8.00, 7, true, "\uD83C\uDF2F"),
            new Product("p13", "Tuna Melt", ProductCategory.SANDWICHES, 9.00, 9, true, "\uD83D\uDC1F"),
            new Product("p14", "Grilled Chicken", ProductCategory.MEALS, 14.50, 18, true, "\uD83C\uDF57"),
            new Product("p15", "Pasta Carbonara", ProductCategory.MEALS, 13.00, 15, true, "\uD83C\uDF5D"),
            new Product("p16", "Caesar Salad", ProductCategory.MEALS, 11.50, 8, true, "\uD83E\uDD57"),
            new Product("p17", "Beef Burger", ProductCategory.MEALS, 15.00, 20, true, "\uD83C\uDF54"),
            new Product("p18", "Margherita Pizza", ProductCategory.MEALS, 12.00, 22, true, "\uD83C\uDF55"),
            new Product("p19", "Cheesecake", ProductCategory.DESSERTS, 6.50, 3, true, "\uD83C\uDF70"),
            new Product("p20", "Tiramisu", ProductCategory.DESSERTS, 7.00, 3, true, "\uD83C\uDF6E"),
            new Product("p21", "Chocolate Brownie", ProductCategory.DESSERTS, 5.50, 4, true, "\uD83C\uDF6B"),
            new Product("p22", "Vanilla Ice Cream", ProductCategory.DESSERTS, 5.00, 2, true, "\uD83C\uDF66")
        );
        products.forEach(productRepository::save);
    }

    private void initTables() {
        LocalDateTime now = LocalDateTime.now();
        List<RestaurantTable> tables = List.of(
            new RestaurantTable("t1", 1, 4, 3, TableStatus.OCCUPIED, "#1001", now.minusMinutes(45)),
            new RestaurantTable("t2", 2, 2, 2, TableStatus.WAITING, "#1002", now.minusMinutes(30)),
            new RestaurantTable("t3", 3, 6, 5, TableStatus.ORDERING, "#1003", now.minusMinutes(15)),
            new RestaurantTable("t4", 4, 4, 0, TableStatus.FREE, null, null),
            new RestaurantTable("t5", 5, 8, 6, TableStatus.OCCUPIED, "#1004", now.minusMinutes(60)),
            new RestaurantTable("t6", 6, 2, 0, TableStatus.FREE, null, null),
            new RestaurantTable("t7", 7, 4, 4, TableStatus.PAY, "#1005", now.minusMinutes(90)),
            new RestaurantTable("t8", 8, 6, 0, TableStatus.FREE, null, null),
            new RestaurantTable("t9", 9, 4, 2, TableStatus.OCCUPIED, "#1006", now.minusMinutes(20)),
            new RestaurantTable("t10", 10, 8, 0, TableStatus.FREE, null, null)
        );
        tables.forEach(tableRepository::save);
    }

    private void initOrders() {
        LocalDateTime now = LocalDateTime.now();

        // Order 1 - preparing
        Order o1 = new Order("#1001", "t1", 1, "Maria Silva", "Carlos", now.minusMinutes(45),
                OrderStatus.PREPARING, Priority.NORMAL, new ArrayList<>(List.of(
                new OrderItem("i1", "p2", "Cappuccino", 2, 3.50, OrderItemStatus.PREPARING, null, 3),
                new OrderItem("i2", "p6", "Croissant", 1, 3.50, OrderItemStatus.PREPARING, null, 5),
                new OrderItem("i3", "p15", "Pasta Carbonara", 1, 13.00, OrderItemStatus.PREPARING, null, 15)
        )), null, 15);
        orderRepository.save(o1);

        // Order 2 - sent
        Order o2 = new Order("#1002", "t2", 2, "Joao Santos", "Ana", now.minusMinutes(30),
                OrderStatus.SENT, Priority.HIGH, new ArrayList<>(List.of(
                new OrderItem("i4", "p1", "Espresso", 1, 2.50, OrderItemStatus.PENDING, null, 2),
                new OrderItem("i5", "p10", "Club Sandwich", 2, 9.50, OrderItemStatus.PENDING, null, 8)
        )), "No onions please", 8);
        orderRepository.save(o2);

        // Order 3 - new
        Order o3 = new Order("#1003", "t3", 3, "Ana Oliveira", "Pedro", now.minusMinutes(15),
                OrderStatus.NEW, Priority.NORMAL, new ArrayList<>(List.of(
                new OrderItem("i6", "p3", "Orange Juice", 3, 4.00, OrderItemStatus.PENDING, null, 2),
                new OrderItem("i7", "p17", "Beef Burger", 2, 15.00, OrderItemStatus.PENDING, null, 20),
                new OrderItem("i8", "p16", "Caesar Salad", 1, 11.50, OrderItemStatus.PENDING, null, 8)
        )), null, 20);
        orderRepository.save(o3);

        // Order 4 - ready
        Order o4 = new Order("#1004", "t5", 5, "Pedro Costa", "Carlos", now.minusMinutes(60),
                OrderStatus.READY, Priority.CRITICAL, new ArrayList<>(List.of(
                new OrderItem("i9", "p14", "Grilled Chicken", 2, 14.50, OrderItemStatus.READY, null, 18),
                new OrderItem("i10", "p4", "Lemon Tea", 2, 3.00, OrderItemStatus.READY, null, 3),
                new OrderItem("i11", "p19", "Cheesecake", 2, 6.50, OrderItemStatus.READY, null, 3)
        )), "Extra sauce on the side", 18);
        orderRepository.save(o4);

        // Order 5 - completed
        Order o5 = new Order("#1005", "t7", 7, "Sofia Rodrigues", "Ana", now.minusMinutes(90),
                OrderStatus.COMPLETED, Priority.NORMAL, new ArrayList<>(List.of(
                new OrderItem("i12", "p18", "Margherita Pizza", 1, 12.00, OrderItemStatus.READY, null, 22),
                new OrderItem("i13", "p5", "Sparkling Water", 2, 2.00, OrderItemStatus.READY, null, 1)
        )), null, 22);
        orderRepository.save(o5);

        // Order 6 - preparing
        Order o6 = new Order("#1006", "t9", 9, "Ricardo Ferreira", "Pedro", now.minusMinutes(20),
                OrderStatus.PREPARING, Priority.HIGH, new ArrayList<>(List.of(
                new OrderItem("i14", "p11", "BLT Sandwich", 1, 8.50, OrderItemStatus.PREPARING, null, 7),
                new OrderItem("i15", "p1", "Espresso", 2, 2.50, OrderItemStatus.PREPARING, null, 2),
                new OrderItem("i16", "p21", "Chocolate Brownie", 1, 5.50, OrderItemStatus.PREPARING, null, 4)
        )), null, 7);
        orderRepository.save(o6);

        // Order 7 - served
        Order o7 = new Order("#1007", "t5", 5, "Mariana Almeida", "Carlos", now.minusMinutes(50),
                OrderStatus.SERVED, Priority.NORMAL, new ArrayList<>(List.of(
                new OrderItem("i17", "p12", "Veggie Wrap", 1, 8.00, OrderItemStatus.READY, null, 7),
                new OrderItem("i18", "p3", "Orange Juice", 1, 4.00, OrderItemStatus.READY, null, 2),
                new OrderItem("i19", "p20", "Tiramisu", 1, 7.00, OrderItemStatus.READY, null, 3)
        )), null, 7);
        orderRepository.save(o7);
    }

    private void initClients() {
        LocalDateTime now = LocalDateTime.now();
        List<Client> clients = List.of(
            new Client("c1", "Maria Silva", "+351 912 345 678", "maria.silva@email.com", 15, 342.50, now.minusDays(1)),
            new Client("c2", "Joao Santos", "+351 923 456 789", "joao.santos@email.com", 8, 189.00, now.minusDays(3)),
            new Client("c3", "Ana Oliveira", "+351 934 567 890", "ana.oliveira@email.com", 22, 567.80, now),
            new Client("c4", "Pedro Costa", "+351 945 678 901", "pedro.costa@email.com", 5, 125.00, now.minusDays(7)),
            new Client("c5", "Sofia Rodrigues", "+351 956 789 012", "sofia.rodrigues@email.com", 12, 298.50, now.minusDays(2)),
            new Client("c6", "Ricardo Ferreira", "+351 967 890 123", "ricardo.ferreira@email.com", 3, 78.00, now.minusDays(14)),
            new Client("c7", "Mariana Almeida", "+351 978 901 234", "mariana.almeida@email.com", 18, 445.00, now),
            new Client("c8", "Bruno Martins", "+351 989 012 345", "bruno.martins@email.com", 7, 167.50, now.minusDays(5)),
            new Client("c9", "Carolina Pereira", "+351 910 123 456", "carolina.pereira@email.com", 10, 234.00, now.minusDays(4)),
            new Client("c10", "Tiago Sousa", "+351 921 234 567", "tiago.sousa@email.com", 1, 45.00, now.minusDays(10))
        );
        clients.forEach(clientRepository::save);
    }

    private void initBills() {
        LocalDateTime now = LocalDateTime.now();
        List<Bill> bills = List.of(
            new Bill("b1", "t1", 1, "Maria Silva", "#1001", 23.50, PaymentMethod.CARD, BillStatus.PENDING, now.minusMinutes(45)),
            new Bill("b2", "t2", 2, "Joao Santos", "#1002", 21.50, PaymentMethod.CASH, BillStatus.PENDING, now.minusMinutes(30)),
            new Bill("b3", "t5", 5, "Pedro Costa", "#1004", 48.00, PaymentMethod.DIGITAL, BillStatus.PENDING, now.minusMinutes(60)),
            new Bill("b4", "t7", 7, "Sofia Rodrigues", "#1005", 16.00, PaymentMethod.CARD, BillStatus.PAID, now.minusMinutes(90)),
            new Bill("b5", "t9", 9, "Ricardo Ferreira", "#1006", 19.00, PaymentMethod.CASH, BillStatus.PENDING, now.minusMinutes(20)),
            new Bill("b6", "t3", 3, "Ana Oliveira", "#1003", 53.50, PaymentMethod.CARD, BillStatus.PENDING, now.minusMinutes(15)),
            new Bill("b7", "t5", 5, "Mariana Almeida", "#1007", 19.00, PaymentMethod.DIGITAL, BillStatus.CANCELLED, now.minusMinutes(50))
        );
        bills.forEach(billRepository::save);
    }

    private void initReportData() {
        reportService.setRevenueData(List.of(
            new RevenueDataPoint("08:00", 5, 67.50),
            new RevenueDataPoint("09:00", 12, 156.00),
            new RevenueDataPoint("10:00", 8, 98.50),
            new RevenueDataPoint("11:00", 15, 234.00),
            new RevenueDataPoint("12:00", 28, 445.50),
            new RevenueDataPoint("13:00", 32, 512.00),
            new RevenueDataPoint("14:00", 18, 267.00),
            new RevenueDataPoint("15:00", 10, 134.50),
            new RevenueDataPoint("16:00", 14, 189.00),
            new RevenueDataPoint("17:00", 22, 345.00)
        ));

        reportService.setTopProducts(List.of(
            new TopProductData("Cappuccino", 45, 157.50),
            new TopProductData("Club Sandwich", 38, 361.00),
            new TopProductData("Beef Burger", 32, 480.00),
            new TopProductData("Caesar Salad", 28, 322.00),
            new TopProductData("Espresso", 52, 130.00)
        ));

        reportService.setPrepTimeData(List.of(
            new PrepTimeData("Drinks", 2.5),
            new PrepTimeData("Pastries", 5.0),
            new PrepTimeData("Sandwiches", 7.8),
            new PrepTimeData("Meals", 16.6),
            new PrepTimeData("Desserts", 3.0)
        ));
    }
}
