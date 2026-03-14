package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.data.MockData;
import com.restaurantos.model.enums.BillStatus;
import com.restaurantos.model.enums.OrderStatus;
import com.restaurantos.model.enums.TableStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;

public class ReportsView extends VBox {

    private final DataStore store = DataStore.getInstance();

    public ReportsView() {
        setSpacing(24);
        setPadding(new Insets(24));
        getStyleClass().add("content-area");

        Label title = new Label("Reports & Analytics");
        title.getStyleClass().add("view-title");

        // KPI row
        HBox kpis = new HBox(16,
            kpi("Today Revenue", String.format("$%.2f", MockData.todayRevenue())),
            kpi("Avg Prep Time", String.format("%.1f min", MockData.avgPrepTime())),
            kpi("Customers Today", String.valueOf(MockData.todayCustomers())),
            kpi("Satisfaction", String.format("%.0f%%", MockData.satisfactionPct()))
        );

        // Order stats
        Label orderSection = new Label("Order Statistics");
        orderSection.getStyleClass().add("section-title");

        long totalOrders = store.getOrders().size();
        long active = store.getOrders().stream().filter(o -> o.getStatus() != OrderStatus.COMPLETED).count();
        long completed = store.getOrders().stream().filter(o -> o.getStatus() == OrderStatus.COMPLETED).count();
        double avgTotal = store.getOrders().stream().mapToDouble(o -> o.getTotal()).average().orElse(0);

        VBox orderStats = new VBox(8,
            statRow("Total Orders", String.valueOf(totalOrders)),
            statRow("Active Orders", String.valueOf(active)),
            statRow("Completed Orders", String.valueOf(completed)),
            statRow("Average Order Value", String.format("$%.2f", avgTotal))
        );

        // Table stats
        Label tableSection = new Label("Table Statistics");
        tableSection.getStyleClass().add("section-title");

        long totalTables = store.getTables().size();
        long occupied = store.getTables().stream().filter(t -> t.getStatus() != TableStatus.FREE).count();
        double occupancyRate = totalTables > 0 ? (double) occupied / totalTables * 100 : 0;

        VBox tableStats = new VBox(8,
            statRow("Total Tables", String.valueOf(totalTables)),
            statRow("Occupied", String.valueOf(occupied)),
            statRow("Occupancy Rate", String.format("%.0f%%", occupancyRate)),
            progressRow("Occupancy", occupancyRate / 100.0)
        );

        // Revenue stats
        Label revenueSection = new Label("Revenue by Hour");
        revenueSection.getStyleClass().add("section-title");

        VBox revenueRows = new VBox(6);
        double maxRev = MockData.revenueByHour().stream()
                .mapToDouble(r -> Double.parseDouble(r[1])).max().orElse(1);
        for (String[] row : MockData.revenueByHour()) {
            double val = Double.parseDouble(row[1]);
            revenueRows.getChildren().add(barRow(row[0], String.format("$%.2f", val), val / maxRev));
        }

        // Top products
        Label topSection = new Label("Top Products");
        topSection.getStyleClass().add("section-title");

        VBox topRows = new VBox(6);
        int maxSold = MockData.topProducts().stream()
                .mapToInt(r -> Integer.parseInt(r[1])).max().orElse(1);
        for (String[] row : MockData.topProducts()) {
            int sold = Integer.parseInt(row[1]);
            topRows.getChildren().add(barRow(row[0], row[1] + " sold", (double) sold / maxSold));
        }

        // Bill stats
        Label billSection = new Label("Billing Summary");
        billSection.getStyleClass().add("section-title");

        double totalBilled = store.getBills().stream().mapToDouble(b -> b.getTotal()).sum();
        double paidTotal = store.getBills().stream()
                .filter(b -> b.getStatus() == BillStatus.PAID).mapToDouble(b -> b.getTotal()).sum();
        long pendingBills = store.getBills().stream().filter(b -> b.getStatus() == BillStatus.PENDING).count();

        VBox billStats = new VBox(8,
            statRow("Total Billed", String.format("$%.2f", totalBilled)),
            statRow("Paid", String.format("$%.2f", paidTotal)),
            statRow("Pending Bills", String.valueOf(pendingBills))
        );

        getChildren().addAll(title, kpis,
                orderSection, orderStats,
                tableSection, tableStats,
                revenueSection, revenueRows,
                topSection, topRows,
                billSection, billStats);
    }

    private VBox kpi(String label, String value) {
        Label lbl = new Label(label);
        lbl.getStyleClass().add("kpi-label");
        Label val = new Label(value);
        val.getStyleClass().add("kpi-value");
        VBox card = new VBox(4, lbl, val);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        return card;
    }

    private HBox statRow(String label, String value) {
        Label lbl = new Label(label + ":");
        lbl.setPrefWidth(180);
        lbl.setStyle("-fx-text-fill: #666;");
        Label val = new Label(value);
        val.setStyle("-fx-font-weight: bold;");
        return new HBox(12, lbl, val);
    }

    private HBox barRow(String label, String value, double fraction) {
        Label lbl = new Label(label);
        lbl.setPrefWidth(140);
        ProgressBar bar = new ProgressBar(fraction);
        bar.setPrefWidth(200);
        bar.getStyleClass().add("chart-bar");
        Label val = new Label(value);
        val.setPrefWidth(80);
        val.setStyle("-fx-text-fill: #666;");
        return new HBox(12, lbl, bar, val);
    }

    private HBox progressRow(String label, double fraction) {
        Label lbl = new Label(label + ":");
        lbl.setPrefWidth(180);
        lbl.setStyle("-fx-text-fill: #666;");
        ProgressBar bar = new ProgressBar(fraction);
        bar.setPrefWidth(200);
        Label pct = new Label(String.format("%.0f%%", fraction * 100));
        pct.setStyle("-fx-font-weight: bold;");
        return new HBox(12, lbl, bar, pct);
    }
}
