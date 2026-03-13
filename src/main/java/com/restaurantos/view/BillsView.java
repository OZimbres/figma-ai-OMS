package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.model.Bill;
import com.restaurantos.model.enums.BillStatus;
import com.restaurantos.model.enums.PaymentMethod;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.format.DateTimeFormatter;

public class BillsView extends HBox {

    private final DataStore store = DataStore.getInstance();
    private final TableView<Bill> table = new TableView<>();
    private final VBox sidePanel = new VBox(12);
    private final ComboBox<String> statusFilter = new ComboBox<>();
    private final Label totalRevenue = new Label();
    private final Label pendingCount = new Label();
    private final Label paidCount = new Label();

    public BillsView() {
        setSpacing(0);
        getStyleClass().add("content-area");

        // Main area
        VBox main = new VBox(16);
        main.setPadding(new Insets(24));
        HBox.setHgrow(main, Priority.ALWAYS);

        Label title = new Label("Bills");
        title.getStyleClass().add("view-title");

        // Summary row
        HBox summary = new HBox(16,
            summaryCard("Total Revenue", totalRevenue),
            summaryCard("Pending", pendingCount),
            summaryCard("Paid", paidCount)
        );

        // Filter
        statusFilter.setItems(FXCollections.observableArrayList("All", "Pending", "Paid", "Cancelled"));
        statusFilter.setValue("All");
        statusFilter.valueProperty().addListener((o, ov, nv) -> refreshTable());

        buildTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        main.getChildren().addAll(title, summary, statusFilter, table);

        // Side panel
        sidePanel.setPadding(new Insets(16));
        sidePanel.setPrefWidth(300);
        sidePanel.getStyleClass().add("side-panel");
        sidePanel.getChildren().add(new Label("Select a bill"));

        getChildren().addAll(main, sidePanel);

        store.getBills().addListener((ListChangeListener<Bill>) c -> { refreshTable(); refreshSummary(); });
        refreshTable();
        refreshSummary();
    }

    private VBox summaryCard(String label, Label valueLabel) {
        Label lbl = new Label(label);
        lbl.getStyleClass().add("kpi-label");
        valueLabel.getStyleClass().add("kpi-value");
        VBox card = new VBox(4, lbl, valueLabel);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(180);
        return card;
    }

    private void refreshSummary() {
        double total = store.getBills().stream().filter(b -> b.getStatus() == BillStatus.PAID).mapToDouble(Bill::getTotal).sum();
        long pending = store.getBills().stream().filter(b -> b.getStatus() == BillStatus.PENDING).count();
        long paid = store.getBills().stream().filter(b -> b.getStatus() == BillStatus.PAID).count();
        totalRevenue.setText(String.format("$%.2f", total));
        pendingCount.setText(String.valueOf(pending));
        paidCount.setText(String.valueOf(paid));
    }

    private void buildTable() {
        TableColumn<Bill, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cd -> cd.getValue().idProperty());
        idCol.setPrefWidth(70);

        TableColumn<Bill, Number> tableCol = new TableColumn<>("Table");
        tableCol.setCellValueFactory(cd -> cd.getValue().tableNumberProperty());
        tableCol.setPrefWidth(60);

        TableColumn<Bill, String> clientCol = new TableColumn<>("Client");
        clientCol.setCellValueFactory(cd -> cd.getValue().clientNameProperty());
        clientCol.setPrefWidth(140);

        TableColumn<Bill, Number> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(cd -> cd.getValue().totalProperty());
        totalCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Number n, boolean empty) {
                super.updateItem(n, empty);
                setText(empty || n == null ? null : String.format("$%.2f", n.doubleValue()));
            }
        });
        totalCol.setPrefWidth(80);

        TableColumn<Bill, String> methodCol = new TableColumn<>("Payment");
        methodCol.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getPaymentMethod() != null ? cd.getValue().getPaymentMethod().getLabel() : ""));
        methodCol.setPrefWidth(80);

        TableColumn<Bill, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getStatus().getLabel()));
        statusCol.setPrefWidth(80);

        TableColumn<Bill, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm dd/MM"))));
        dateCol.setPrefWidth(110);

        table.getColumns().addAll(idCol, tableCol, clientCol, totalCol, methodCol, statusCol, dateCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        table.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            if (nv != null) showBillDetail(nv);
        });
    }

    private void refreshTable() {
        String filter = statusFilter.getValue();
        table.setItems(FXCollections.observableArrayList(
            store.getBills().filtered(b ->
                "All".equals(filter) || b.getStatus().getLabel().equals(filter)
            )
        ));
    }

    private void showBillDetail(Bill b) {
        sidePanel.getChildren().clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Label title = new Label("Bill " + b.getId());
        title.getStyleClass().add("section-title");

        Label info = new Label(String.format(
                "Table: %d\nClient: %s\nOrder: %s\nTotal: $%.2f\nPayment: %s\nStatus: %s\nDate: %s",
                b.getTableNumber(), b.getClientName(), b.getOrderId(),
                b.getTotal(), b.getPaymentMethod() != null ? b.getPaymentMethod().getLabel() : "N/A",
                b.getStatus().getLabel(), b.getCreatedAt().format(fmt)));
        info.setWrapText(true);

        sidePanel.getChildren().addAll(title, info);

        // Actions
        if (b.getStatus() == BillStatus.PENDING) {
            Label actLabel = new Label("Actions:");
            actLabel.setStyle("-fx-font-weight: bold; -fx-padding: 8 0 0 0;");
            sidePanel.getChildren().add(actLabel);

            for (PaymentMethod pm : PaymentMethod.values()) {
                Button btn = new Button("Pay with " + pm.getLabel());
                btn.getStyleClass().add("btn-primary");
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setOnAction(e -> {
                    b.setPaymentMethod(pm);
                    store.updateBillStatus(b.getId(), BillStatus.PAID);
                    refreshTable();
                    refreshSummary();
                    showBillDetail(b);
                });
                sidePanel.getChildren().add(btn);
            }

            Button cancelBtn = new Button("Cancel Bill");
            cancelBtn.getStyleClass().add("btn-danger");
            cancelBtn.setMaxWidth(Double.MAX_VALUE);
            cancelBtn.setOnAction(e -> {
                store.updateBillStatus(b.getId(), BillStatus.CANCELLED);
                refreshTable();
                refreshSummary();
                showBillDetail(b);
            });
            sidePanel.getChildren().add(cancelBtn);
        }
    }
}
