# RestaurantOS - Order Management System

A desktop application for restaurant order management built with **Java 21** (LTS), **JavaFX**, and **Gradle**.

📚 **[Documentação completa →](docs/index.md)**

## Requirements

- **Java 21** (LTS) or later
- **Gradle 8.5+** (wrapper included)

## Getting Started

### Build
```bash
./gradlew build
```

### Run
```bash
./gradlew run
```

## Features

- **Dashboard** — KPI cards, table occupancy, and recent orders overview
- **Orders** — Create, manage, and track orders with search/filter capabilities
- **Kitchen Queue** — Kanban-style board (New → Preparing → Ready) for kitchen staff
- **Tables** — Visual grid of restaurant tables with status tracking
- **Products** — Full product/menu management with add/edit dialogs
- **Clients** — Customer database with loyalty tiers and VIP tracking
- **Bills** — Payment processing with revenue summaries
- **Reports** — Analytics dashboard with order, table, and revenue statistics
- **Settings** — Restaurant configuration, notifications, and staff management

## Tech Stack

| Component       | Technology          |
|----------------|---------------------|
| Language        | Java 21 (LTS)       |
| UI Framework    | JavaFX 21           |
| Build Tool      | Gradle 8.5          |
| Architecture    | Desktop Application  |

## Project Structure

```
src/main/java/com/restaurantos/
├── App.java                    # Main application entry point
├── data/
│   ├── DataStore.java          # Centralized state management (singleton)
│   └── MockData.java           # Demo data provider
├── model/
│   ├── Bill.java
│   ├── Client.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Product.java
│   ├── RestaurantTable.java
│   └── enums/                  # Status and type enumerations
│       ├── BillStatus.java
│       ├── ItemStatus.java
│       ├── OrderStatus.java
│       ├── PaymentMethod.java
│       ├── Priority.java
│       ├── ProductCategory.java
│       └── TableStatus.java
└── view/
    ├── BillsView.java
    ├── ClientsView.java
    ├── DashboardView.java
    ├── KitchenQueueView.java
    ├── OrdersView.java
    ├── ProductsView.java
    ├── ReportsView.java
    ├── SettingsView.java
    └── TablesView.java
```