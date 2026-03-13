package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.data.MockData;
import com.restaurantos.model.Order;
import com.restaurantos.model.enums.OrderStatus;
import com.restaurantos.model.enums.TableStatus;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import java.time.format.DateTimeFormatter;

public class DashboardView extends VBox {

    private final DataStore store = DataStore.getInstance();
    private final Label activeOrdersVal = new Label();
    private final Label preparingVal = new Label();
    private final Label readyVal = new Label();
    private final Label completedVal = new Label();
    private final Label occupiedVal = new Label();
    private final Label freeVal = new Label();
    private final ListView<Order> recentList = new ListView<>();

    public DashboardView() {
        setSpacing(24);
        setPadding(new Insets(24));
        getStyleClass().add("content-area");

        Label title = new Label("Dashboard");
        title.getStyleClass().add("view-title");

        // KPI cards
        HBox kpiRow = new HBox(16,
            kpiCard("Active Orders", activeOrdersVal, "kpi-blue"),
            kpiCard("Preparing",     preparingVal,    "kpi-amber"),
            kpiCard("Ready",         readyVal,        "kpi-green"),
            kpiCard("Completed",     completedVal,    "kpi-gray")
        );

        // Analytics row
        HBox analyticsRow = new HBox(16,
            statCard("Today Revenue", String.format("$%.2f", MockData.todayRevenue())),
            statCard("Avg Prep Time", String.format("%.1f min", MockData.avgPrepTime())),
            statCard("Customers",     String.valueOf(MockData.todayCustomers())),
            statCard("Satisfaction",  String.format("%.0f%%", MockData.satisfactionPct()))
        );

        // Table occupancy
        HBox tableRow = new HBox(16,
            statCard("Occupied Tables", occupiedVal),
            statCard("Free Tables",     freeVal)
        );

        // Recent orders
        Label recentLabel = new Label("Recent Orders");
        recentLabel.getStyleClass().add("section-title");
        recentList.setPrefHeight(250);
        recentList.setCellFactory(lv -> new OrderCell());

        getChildren().addAll(title, kpiRow, analyticsRow, tableRow, recentLabel, recentList);

        // Bind data
        store.getOrders().addListener((ListChangeListener<Order>) c -> refresh());
        store.getTables().addListener((ListChangeListener<? super com.restaurantos.model.RestaurantTable>) c -> refresh());
        refresh();
    }

    private void refresh() {
        long active   = store.getOrders().stream().filter(o -> o.getStatus() != OrderStatus.COMPLETED).count();
        long prep     = store.getOrders().stream().filter(o -> o.getStatus() == OrderStatus.PREPARING).count();
        long ready    = store.getOrders().stream().filter(o -> o.getStatus() == OrderStatus.READY).count();
        long complete = store.getOrders().stream().filter(o -> o.getStatus() == OrderStatus.COMPLETED).count();
        activeOrdersVal.setText(String.valueOf(active));
        preparingVal.setText(String.valueOf(prep));
        readyVal.setText(String.valueOf(ready));
        completedVal.setText(String.valueOf(complete));

        long occ  = store.getTables().stream().filter(t -> t.getStatus() != TableStatus.FREE).count();
        long free = store.getTables().stream().filter(t -> t.getStatus() == TableStatus.FREE).count();
        occupiedVal.setText(String.valueOf(occ));
        freeVal.setText(String.valueOf(free));

        recentList.getItems().setAll(store.getOrders().sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt())));
    }

    private VBox kpiCard(String title, Label valLabel, String styleClass) {
        Label lbl = new Label(title);
        lbl.getStyleClass().add("kpi-label");
        valLabel.getStyleClass().addAll("kpi-value", styleClass);
        VBox card = new VBox(4, lbl, valLabel);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        return card;
    }

    private VBox statCard(String title, String value) {
        Label valLabel = new Label(value);
        return statCard(title, valLabel);
    }

    private VBox statCard(String title, Label valLabel) {
        Label lbl = new Label(title);
        lbl.getStyleClass().add("kpi-label");
        valLabel.getStyleClass().add("kpi-value");
        VBox card = new VBox(4, lbl, valLabel);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        return card;
    }

    private static class OrderCell extends ListCell<Order> {
        private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm");
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);
            if (empty || order == null) { setText(null); setGraphic(null); return; }
            Label info = new Label(String.format("Table %d  |  %s  |  %s  |  %s  |  $%.2f",
                    order.getTableNumber(), order.getClientName(),
                    order.getStatus().getLabel(), order.getCreatedAt().format(FMT),
                    order.getTotal()));
            info.getStyleClass().add("order-cell-text");
            setGraphic(info);
        }
    }
}
