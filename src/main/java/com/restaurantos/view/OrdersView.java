package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.model.*;
import com.restaurantos.model.enums.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class OrdersView extends HBox {

    private final DataStore store = DataStore.getInstance();
    private final ListView<Order> orderList = new ListView<>();
    private final VBox detailPane = new VBox(12);
    private final VBox productPane = new VBox(8);
    private final TextField searchField = new TextField();
    private final ComboBox<String> statusFilter = new ComboBox<>();
    private Order selectedOrder;

    public OrdersView() {
        setSpacing(0);
        getStyleClass().add("content-area");
        setPadding(new Insets(0));

        // Left panel - order list
        VBox leftPanel = buildLeftPanel();
        leftPanel.setPrefWidth(320);
        leftPanel.setMinWidth(280);

        // Center panel - order details
        ScrollPane detailScroll = new ScrollPane(detailPane);
        detailScroll.setFitToWidth(true);
        detailScroll.getStyleClass().add("detail-scroll");
        detailPane.setPadding(new Insets(16));
        detailPane.getStyleClass().add("detail-pane");

        VBox centerPanel = new VBox(detailScroll);
        VBox.setVgrow(detailScroll, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(centerPanel, javafx.scene.layout.Priority.ALWAYS);

        // Right panel - add products
        ScrollPane productScroll = new ScrollPane(productPane);
        productScroll.setFitToWidth(true);
        productPane.setPadding(new Insets(12));
        VBox rightPanel = new VBox(productScroll);
        rightPanel.setPrefWidth(260);
        rightPanel.setMinWidth(220);
        VBox.setVgrow(productScroll, javafx.scene.layout.Priority.ALWAYS);

        buildProductPanel();

        getChildren().addAll(leftPanel, centerPanel, rightPanel);

        store.getOrders().addListener((ListChangeListener<Order>) c -> refreshOrderList());
        refreshOrderList();
    }

    private VBox buildLeftPanel() {
        Label title = new Label("Orders");
        title.getStyleClass().add("section-title");

        searchField.setPromptText("Search orders...");
        searchField.textProperty().addListener((o, ov, nv) -> refreshOrderList());

        statusFilter.setItems(FXCollections.observableArrayList("All", "New", "Preparing", "Ready", "Served", "Completed"));
        statusFilter.setValue("All");
        statusFilter.setMaxWidth(Double.MAX_VALUE);
        statusFilter.valueProperty().addListener((o, ov, nv) -> refreshOrderList());

        Button createBtn = new Button("+ New Order");
        createBtn.getStyleClass().add("btn-primary");
        createBtn.setMaxWidth(Double.MAX_VALUE);
        createBtn.setOnAction(e -> showCreateOrderDialog());

        orderList.setCellFactory(lv -> new OrderListCell());
        orderList.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            selectedOrder = nv;
            if (nv != null) store.setSelectedOrderId(nv.getId());
            refreshDetail();
        });

        VBox panel = new VBox(8, title, searchField, statusFilter, createBtn, orderList);
        panel.setPadding(new Insets(12));
        panel.getStyleClass().add("left-panel");
        VBox.setVgrow(orderList, javafx.scene.layout.Priority.ALWAYS);
        return panel;
    }

    private void refreshOrderList() {
        String search = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        String statusVal = statusFilter.getValue();

        ObservableList<Order> filtered = store.getOrders().filtered(order -> {
            boolean matchSearch = search.isEmpty()
                    || order.getClientName().toLowerCase().contains(search)
                    || order.getId().toLowerCase().contains(search)
                    || String.valueOf(order.getTableNumber()).contains(search);
            boolean matchStatus = "All".equals(statusVal)
                    || order.getStatus().getLabel().equals(statusVal);
            return matchSearch && matchStatus;
        });

        orderList.setItems(FXCollections.observableArrayList(filtered));
        if (selectedOrder != null) {
            orderList.getSelectionModel().select(selectedOrder);
        }
    }

    private void refreshDetail() {
        detailPane.getChildren().clear();
        if (selectedOrder == null) {
            detailPane.getChildren().add(new Label("Select an order to view details"));
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm dd/MM");
        Order o = selectedOrder;

        Label header = new Label("Order " + o.getId());
        header.getStyleClass().add("section-title");

        Label info = new Label(String.format(
                "Table: %d  |  Client: %s  |  Waiter: %s\nStatus: %s  |  Priority: %s\nCreated: %s  |  Est. Prep: %d min\nSpecial: %s",
                o.getTableNumber(), o.getClientName(), o.getWaiterName(),
                o.getStatus().getLabel(), o.getPriority().getLabel(),
                o.getCreatedAt().format(fmt), o.getEstimatedPrepTime(),
                o.getSpecialInstructions().isEmpty() ? "None" : o.getSpecialInstructions()));
        info.setWrapText(true);

        // Status actions
        HBox actions = new HBox(8);
        for (OrderStatus st : OrderStatus.values()) {
            if (st != o.getStatus()) {
                Button btn = new Button(st.getLabel());
                btn.getStyleClass().add("btn-small");
                btn.setOnAction(e -> {
                    store.updateOrderStatus(o.getId(), st);
                    refreshDetail();
                });
                actions.getChildren().add(btn);
            }
        }

        // Priority
        HBox priorityBox = new HBox(8);
        priorityBox.setAlignment(Pos.CENTER_LEFT);
        priorityBox.getChildren().add(new Label("Priority:"));
        for (com.restaurantos.model.enums.Priority p : com.restaurantos.model.enums.Priority.values()) {
            Button btn = new Button(p.getLabel());
            btn.getStyleClass().add(p == o.getPriority() ? "btn-active" : "btn-small");
            btn.setOnAction(e -> {
                store.updateOrderPriority(o.getId(), p);
                refreshDetail();
            });
            priorityBox.getChildren().add(btn);
        }

        // Items table
        Label itemsLabel = new Label("Items");
        itemsLabel.getStyleClass().add("section-title");

        TableView<OrderItem> itemsTable = new TableView<>();
        itemsTable.setPrefHeight(200);

        TableColumn<OrderItem, String> nameCol = new TableColumn<>("Product");
        nameCol.setCellValueFactory(cd -> cd.getValue().productNameProperty());
        nameCol.setPrefWidth(150);

        TableColumn<OrderItem, Number> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(cd -> cd.getValue().quantityProperty());
        qtyCol.setPrefWidth(50);

        TableColumn<OrderItem, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cd -> cd.getValue().priceProperty());
        priceCol.setPrefWidth(70);

        TableColumn<OrderItem, ItemStatus> stCol = new TableColumn<>("Status");
        stCol.setCellValueFactory(cd -> cd.getValue().statusProperty());
        stCol.setPrefWidth(90);

        TableColumn<OrderItem, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(cd -> cd.getValue().notesProperty());
        notesCol.setPrefWidth(120);

        TableColumn<OrderItem, Void> removeCol = new TableColumn<>("");
        removeCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("X");
            { btn.getStyleClass().add("btn-danger-small"); }
            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty) { setGraphic(null); } else {
                    btn.setOnAction(e -> {
                        OrderItem item = getTableView().getItems().get(getIndex());
                        store.removeItemFromOrder(o.getId(), item.getId());
                        refreshDetail();
                    });
                    setGraphic(btn);
                }
            }
        });
        removeCol.setPrefWidth(40);

        itemsTable.getColumns().addAll(nameCol, qtyCol, priceCol, stCol, notesCol, removeCol);
        itemsTable.setItems(o.getItems());

        Label totalLabel = new Label(String.format("Total: $%.2f", o.getTotal()));
        totalLabel.getStyleClass().add("total-label");

        detailPane.getChildren().addAll(header, info, new Label("Change Status:"), actions, priorityBox,
                itemsLabel, itemsTable, totalLabel);
    }

    private void buildProductPanel() {
        Label title = new Label("Add Items");
        title.getStyleClass().add("section-title");
        productPane.getChildren().add(title);

        for (Product p : store.getProducts()) {
            if (!p.isAvailable()) continue;
            Button btn = new Button(String.format("%s %s - $%.2f", p.getEmoji(), p.getName(), p.getPrice()));
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("product-btn");
            btn.setOnAction(e -> {
                if (selectedOrder != null) {
                    store.addItemToOrder(selectedOrder.getId(), p, 1, "");
                    refreshDetail();
                }
            });
            productPane.getChildren().add(btn);
        }
    }

    private void showCreateOrderDialog() {
        Dialog<Order> dialog = new Dialog<>();
        dialog.setTitle("New Order");
        dialog.setHeaderText("Create a new order");

        ButtonType createType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createType, ButtonType.CANCEL);

        TextField tableField = new TextField();
        tableField.setPromptText("Table number");
        TextField clientField = new TextField();
        clientField.setPromptText("Client name");
        TextField waiterField = new TextField();
        waiterField.setPromptText("Waiter name");
        TextField specialField = new TextField();
        specialField.setPromptText("Special instructions");

        VBox form = new VBox(8, new Label("Table #:"), tableField, new Label("Client:"), clientField,
                new Label("Waiter:"), waiterField, new Label("Special:"), specialField);
        form.setPadding(new Insets(16));
        dialog.getDialogPane().setContent(form);

        dialog.setResultConverter(bt -> {
            if (bt == createType) {
                try {
                    int tableNum = Integer.parseInt(tableField.getText().trim());
                    return store.createOrder(tableNum, clientField.getText().trim(),
                            waiterField.getText().trim(), specialField.getText().trim());
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });

        Optional<Order> result = dialog.showAndWait();
        result.ifPresent(o -> {
            refreshOrderList();
            orderList.getSelectionModel().select(o);
        });
    }

    private static class OrderListCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);
            if (empty || order == null) { setText(null); setGraphic(null); return; }
            VBox box = new VBox(2);
            Label top = new Label(String.format("#%s  Table %d", order.getId(), order.getTableNumber()));
            top.setStyle("-fx-font-weight: bold;");
            Label bottom = new Label(String.format("%s  |  %s  |  $%.2f",
                    order.getClientName(), order.getStatus().getLabel(), order.getTotal()));
            bottom.setStyle("-fx-text-fill: #666;");
            box.getChildren().addAll(top, bottom);
            setGraphic(box);
        }
    }
}
