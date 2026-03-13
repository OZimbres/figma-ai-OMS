package com.oms.model;

import java.time.LocalDateTime;

public class Client {
    private String id;
    private String name;
    private String phone;
    private String email;
    private int visits;
    private double totalSpending;
    private LocalDateTime lastOrderDate;

    public Client() {}

    public Client(String id, String name, String phone, String email,
                  int visits, double totalSpending, LocalDateTime lastOrderDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.visits = visits;
        this.totalSpending = totalSpending;
        this.lastOrderDate = lastOrderDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getVisits() { return visits; }
    public void setVisits(int visits) { this.visits = visits; }

    public double getTotalSpending() { return totalSpending; }
    public void setTotalSpending(double totalSpending) { this.totalSpending = totalSpending; }

    public LocalDateTime getLastOrderDate() { return lastOrderDate; }
    public void setLastOrderDate(LocalDateTime lastOrderDate) { this.lastOrderDate = lastOrderDate; }
}
