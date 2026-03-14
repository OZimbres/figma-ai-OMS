package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.model.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.format.DateTimeFormatter;

public class ClientsView extends HBox {

    private final DataStore store = DataStore.getInstance();
    private final TableView<Client> table = new TableView<>();
    private final VBox sidePanel = new VBox(12);
    private final TextField searchField = new TextField();

    public ClientsView() {
        setSpacing(0);
        getStyleClass().add("content-area");

        // Main area
        VBox main = new VBox(16);
        main.setPadding(new Insets(24));
        HBox.setHgrow(main, Priority.ALWAYS);

        Label title = new Label("Clients");
        title.getStyleClass().add("view-title");

        searchField.setPromptText("Search clients...");
        searchField.textProperty().addListener((o, ov, nv) -> refreshTable());

        buildTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        main.getChildren().addAll(title, searchField, table);

        // Side panel
        sidePanel.setPadding(new Insets(16));
        sidePanel.setPrefWidth(300);
        sidePanel.getStyleClass().add("side-panel");
        sidePanel.getChildren().add(new Label("Select a client"));

        getChildren().addAll(main, sidePanel);

        store.getClients().addListener((ListChangeListener<Client>) c -> refreshTable());
        refreshTable();
    }

    private void buildTable() {
        TableColumn<Client, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cd -> cd.getValue().nameProperty());
        nameCol.setPrefWidth(160);

        TableColumn<Client, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(cd -> cd.getValue().phoneProperty());
        phoneCol.setPrefWidth(160);

        TableColumn<Client, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cd -> cd.getValue().emailProperty());
        emailCol.setPrefWidth(180);

        TableColumn<Client, Number> visitsCol = new TableColumn<>("Visits");
        visitsCol.setCellValueFactory(cd -> cd.getValue().visitsProperty());
        visitsCol.setPrefWidth(60);

        TableColumn<Client, String> tierCol = new TableColumn<>("Tier");
        tierCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getLoyaltyTier()));
        tierCol.setPrefWidth(80);

        TableColumn<Client, String> vipCol = new TableColumn<>("VIP");
        vipCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().isVip() ? "\u2B50 VIP" : ""));
        vipCol.setPrefWidth(60);

        TableColumn<Client, Number> spendCol = new TableColumn<>("Spending");
        spendCol.setCellValueFactory(cd -> cd.getValue().totalSpendingProperty());
        spendCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Number n, boolean empty) {
                super.updateItem(n, empty);
                setText(empty || n == null ? null : String.format("$%.2f", n.doubleValue()));
            }
        });
        spendCol.setPrefWidth(100);

        table.getColumns().addAll(nameCol, phoneCol, emailCol, visitsCol, tierCol, vipCol, spendCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        table.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            if (nv != null) showClientDetail(nv);
        });
    }

    private void refreshTable() {
        String search = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        table.setItems(FXCollections.observableArrayList(
            store.getClients().filtered(c ->
                search.isEmpty()
                || c.getName().toLowerCase().contains(search)
                || c.getEmail().toLowerCase().contains(search)
                || c.getPhone().contains(search)
            )
        ));
    }

    private void showClientDetail(Client c) {
        sidePanel.getChildren().clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Label name = new Label(c.getName());
        name.getStyleClass().add("section-title");

        if (c.isVip()) {
            Label vip = new Label("\u2B50 VIP Client");
            vip.setStyle("-fx-text-fill: #F59E0B; -fx-font-weight: bold;");
            sidePanel.getChildren().add(vip);
        }

        Label tier = new Label("Loyalty: " + c.getLoyaltyTier());
        tier.setStyle("-fx-font-weight: bold;");

        Label phone = new Label("\uD83D\uDCDE " + c.getPhone());
        Label email = new Label("\u2709 " + c.getEmail());
        Label visits = new Label("Visits: " + c.getVisits());
        Label spending = new Label(String.format("Total Spending: $%.2f", c.getTotalSpending()));

        String lastOrder = c.getLastOrderDate() != null ? c.getLastOrderDate().format(fmt) : "N/A";
        Label last = new Label("Last Order: " + lastOrder);

        // Spending bar
        double maxSpend = store.getClients().stream().mapToDouble(Client::getTotalSpending).max().orElse(1);
        ProgressBar bar = new ProgressBar(c.getTotalSpending() / maxSpend);
        bar.setMaxWidth(Double.MAX_VALUE);
        bar.getStyleClass().add("spending-bar");

        sidePanel.getChildren().addAll(name, tier, phone, email, visits, spending, last, new Label("Spending:"), bar);
    }
}
