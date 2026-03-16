package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.model.Order;
import com.restaurantos.model.RestaurantTable;
import com.restaurantos.model.enums.TableStatus;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TablesView extends HBox {

    private final DataStore store = DataStore.getInstance();
    private final FlowPane grid = new FlowPane();
    private final VBox sidePanel = new VBox(12);
    private String selectedTableId = null;

    public TablesView() {
        setSpacing(0);
        getStyleClass().add("content-area");

        // Grid area
        grid.setHgap(16);
        grid.setVgap(16);
        grid.setPadding(new Insets(24));
        ScrollPane gridScroll = new ScrollPane(grid);
        gridScroll.setFitToWidth(true);
        HBox.setHgrow(gridScroll, Priority.ALWAYS);

        // Side panel
        sidePanel.setPadding(new Insets(16));
        sidePanel.setPrefWidth(300);
        sidePanel.getStyleClass().add("side-panel");

        Label sidePlaceholder = new Label("Select a table");
        sidePlaceholder.getStyleClass().add("muted-3");
        sidePanel.getChildren().add(sidePlaceholder);

        getChildren().addAll(gridScroll, sidePanel);

        store.getTables().addListener((ListChangeListener<RestaurantTable>) c -> refreshGrid());
        refreshGrid();
    }

    private void refreshGrid() {
        grid.getChildren().clear();
        for (RestaurantTable t : store.getTables()) {
            grid.getChildren().add(tableCard(t));
        }
    }

    private VBox tableCard(RestaurantTable t) {
        String color = switch (t.getStatus()) {
            case FREE     -> "#10B981";
            case OCCUPIED -> "#3B82F6";
            case ORDERING -> "#F59E0B";
            case WAITING  -> "#8B5CF6";
            case PAY      -> "#EF4444";
        };

        Label num = new Label("Table " + t.getNumber());
        num.getStyleClass().add("table-number");

        Label statusLabel = new Label(t.getStatus().getLabel());
        statusLabel.getStyleClass().addAll("status-badge", "status-" + t.getStatus().name().toLowerCase());

        Label seats = new Label(String.format("%d/%d seats", t.getGuests(), t.getSeats()));
        seats.getStyleClass().add("muted-text");

        VBox card = new VBox(8, num, statusLabel, seats);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(16));
        card.setPrefSize(160, 130);
        card.getStyleClass().add("card");
        card.getStyleClass().add("card-border-" + t.getStatus().name().toLowerCase());

        // Apply selected class if this table is currently selected
        if (t.getId() != null && t.getId().equals(selectedTableId)) {
            if (!card.getStyleClass().contains("table-card-selected")) {
                card.getStyleClass().add("table-card-selected");
            }
        }

        card.setOnMouseClicked(e -> {
            // Select this table and clear any previous selection
            // Note: If table has null ID, we clear selection but still show details (graceful degradation)
            if (t.getId() != null) {
                selectedTableId = t.getId();
            } else {
                selectedTableId = null;
            }
            refreshGrid();
            showTableDetail(t);
        });
        return card;
    }

    private void showTableDetail(RestaurantTable t) {
        sidePanel.getChildren().clear();

        Label title = new Label("Table " + t.getNumber());
        title.getStyleClass().add("section-title");

        Label status = new Label("Status: " + t.getStatus().getLabel());
        Label seatsLbl = new Label(String.format("Seats: %d  |  Guests: %d", t.getSeats(), t.getGuests()));

        sidePanel.getChildren().addAll(title, status, seatsLbl);

        if (t.getOccupiedSince() != null) {
            long mins = Duration.between(t.getOccupiedSince(), LocalDateTime.now()).toMinutes();
            Label occupied = new Label(String.format("Occupied: %d min ago", mins));
            sidePanel.getChildren().add(occupied);
        }

        if (t.getOrderId() != null) {
            Order order = store.findOrder(t.getOrderId());
            if (order != null) {
                Label orderLabel = new Label(String.format("Order: %s\nClient: %s\nTotal: $%.2f",
                        order.getId(), order.getClientName(), order.getTotal()));
                orderLabel.setWrapText(true);
                sidePanel.getChildren().add(orderLabel);
            }
        }

        // Status change buttons
        Label changeLabel = new Label("Change Status:");
        changeLabel.getStyleClass().addAll("bold-label", "pad-top-8");
        sidePanel.getChildren().add(changeLabel);

        HBox buttons = new HBox(6);
        for (TableStatus ts : TableStatus.values()) {
            Button btn = new Button(ts.getLabel());
            btn.getStyleClass().add(ts == t.getStatus() ? "btn-active" : "btn-small");
            btn.setOnAction(e -> {
                store.updateTableStatus(t.getId(), ts);
                refreshGrid();
                showTableDetail(t);
            });
            buttons.getChildren().add(btn);
        }
        FlowPane btnFlow = new FlowPane(6, 6);
        btnFlow.getChildren().addAll(buttons.getChildren());
        sidePanel.getChildren().add(btnFlow);
    }
}
