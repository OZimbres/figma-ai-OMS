package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.model.Order;
import com.restaurantos.model.OrderItem;
import com.restaurantos.model.enums.OrderStatus;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.format.DateTimeFormatter;

public class KitchenQueueView extends HBox {

    private final DataStore store = DataStore.getInstance();
    private final VBox newCol = new VBox(10);
    private final VBox prepCol = new VBox(10);
    private final VBox readyCol = new VBox(10);

    public KitchenQueueView() {
        setSpacing(16);
        setPadding(new Insets(24));
        getStyleClass().add("content-area");

        ScrollPane newScroll = wrapColumn("New Orders", newCol, "column-new");
        ScrollPane prepScroll = wrapColumn("Preparing", prepCol, "column-preparing");
        ScrollPane readyScroll = wrapColumn("Ready", readyCol, "column-ready");

        HBox.setHgrow(newScroll, Priority.ALWAYS);
        HBox.setHgrow(prepScroll, Priority.ALWAYS);
        HBox.setHgrow(readyScroll, Priority.ALWAYS);

        getChildren().addAll(newScroll, prepScroll, readyScroll);

        store.getOrders().addListener((ListChangeListener<Order>) c -> refresh());
        refresh();
    }

    private ScrollPane wrapColumn(String title, VBox col, String style) {
        Label header = new Label(title);
        header.getStyleClass().add("column-header");
        col.setPadding(new Insets(8));

        VBox wrapper = new VBox(8, header, col);
        wrapper.getStyleClass().addAll("kitchen-column", style);
        wrapper.setPadding(new Insets(12));

        ScrollPane scroll = new ScrollPane(wrapper);
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scroll;
    }

    private void refresh() {
        newCol.getChildren().clear();
        prepCol.getChildren().clear();
        readyCol.getChildren().clear();

        for (Order o : store.getOrders()) {
            switch (o.getStatus()) {
                case NEW, SENT -> newCol.getChildren().add(orderCard(o, OrderStatus.PREPARING));
                case PREPARING -> prepCol.getChildren().add(orderCard(o, OrderStatus.READY));
                case READY     -> readyCol.getChildren().add(orderCard(o, OrderStatus.SERVED));
                default -> { /* skip served/completed */ }
            }
        }

        if (newCol.getChildren().isEmpty()) newCol.getChildren().add(emptyLabel());
        if (prepCol.getChildren().isEmpty()) prepCol.getChildren().add(emptyLabel());
        if (readyCol.getChildren().isEmpty()) readyCol.getChildren().add(emptyLabel());
    }

    private VBox orderCard(Order o, OrderStatus nextStatus) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        Label header = new Label(String.format("Table %d  -  %s", o.getTableNumber(), o.getId()));
        header.getStyleClass().add("card-title");

        Label client = new Label(String.format("%s  |  %s", o.getClientName(), o.getWaiterName()));
        Label time = new Label("Created: " + o.getCreatedAt().format(fmt));
        time.getStyleClass().add("muted-2");

        Label priority = new Label("Priority: " + o.getPriority().getLabel());
        priority.getStyleClass().add("priority-" + o.getPriority().name().toLowerCase());

        VBox itemsBox = new VBox(2);
        for (OrderItem item : o.getItems()) {
            Label itemLabel = new Label(String.format("  %dx %s [%s]%s",
                    item.getQuantity(), item.getProductName(), item.getStatus().getLabel(),
                    item.getNotes().isEmpty() ? "" : " - " + item.getNotes()));
            itemLabel.setWrapText(true);
            itemsBox.getChildren().add(itemLabel);
        }

        if (!o.getSpecialInstructions().isEmpty()) {
            Label special = new Label("\u26A0 " + o.getSpecialInstructions());
            special.getStyleClass().add("priority-high");
            special.setWrapText(true);
            itemsBox.getChildren().add(special);
        }

        Button moveBtn = new Button("Move to " + nextStatus.getLabel() + " \u2192");
        moveBtn.getStyleClass().add("btn-primary");
        moveBtn.setMaxWidth(Double.MAX_VALUE);
        moveBtn.setOnAction(e -> {
            store.updateOrderStatus(o.getId(), nextStatus);
            refresh();
        });

        Label estTime = new Label(String.format("Est: %d min", o.getEstimatedPrepTime()));
        estTime.getStyleClass().add("muted-2");

        VBox card = new VBox(6, header, client, time, priority, itemsBox, estTime, moveBtn);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(12));
        return card;
    }

    private Label emptyLabel() {
        Label lbl = new Label("No orders");
        lbl.getStyleClass().add("muted-italic");
        return lbl;
    }
}
