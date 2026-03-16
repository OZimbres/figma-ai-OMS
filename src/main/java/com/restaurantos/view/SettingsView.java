package com.restaurantos.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SettingsView extends VBox {

    public SettingsView() {
        setSpacing(24);
        setPadding(new Insets(24));
        getStyleClass().add("content-area");

        Label title = new Label("Settings");
        title.getStyleClass().add("view-title");

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabs.getTabs().addAll(
            restaurantTab(),
            notificationsTab(),
            staffTab()
        );

        VBox.setVgrow(tabs, Priority.ALWAYS);
        getChildren().addAll(title, tabs);
    }

    private Tab restaurantTab() {
        Tab tab = new Tab("Restaurant Info");

        TextField nameField = new TextField("RestaurantOS Demo");
        nameField.setPromptText("Restaurant name");

        TextField addressField = new TextField("123 Main Street, Paris");
        addressField.setPromptText("Address");

        TextField phoneField = new TextField("+33 1 23 45 67 89");
        phoneField.setPromptText("Phone");

        TextField emailField = new TextField("contact@restaurantos.com");
        emailField.setPromptText("Email");

        TextField currencyField = new TextField("USD ($)");
        currencyField.setPromptText("Currency");

        Spinner<Integer> taxSpinner = new Spinner<>(0, 100, 10);
        taxSpinner.setEditable(true);

        Spinner<Integer> tablesSpinner = new Spinner<>(1, 100, 10);
        tablesSpinner.setEditable(true);

        Button saveBtn = new Button("Save Settings");
        saveBtn.getStyleClass().add("btn-primary");
        saveBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Settings saved!", ButtonType.OK);
            alert.showAndWait();
        });

        VBox content = new VBox(12,
            new Label("Restaurant Name:"), nameField,
            new Label("Address:"), addressField,
            new Label("Phone:"), phoneField,
            new Label("Email:"), emailField,
            new Label("Currency:"), currencyField,
            new Label("Tax Rate (%):"), taxSpinner,
            new Label("Number of Tables:"), tablesSpinner,
            saveBtn
        );
        content.setPadding(new Insets(16));

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        tab.setContent(scroll);
        return tab;
    }

    private Tab notificationsTab() {
        Tab tab = new Tab("Notifications");

        CheckBox newOrder = new CheckBox("New order notifications");
        newOrder.setSelected(true);

        CheckBox readyOrder = new CheckBox("Order ready notifications");
        readyOrder.setSelected(true);

        CheckBox lowStock = new CheckBox("Low stock alerts");
        lowStock.setSelected(false);

        CheckBox vipAlert = new CheckBox("VIP client arrival");
        vipAlert.setSelected(true);

        CheckBox billReminder = new CheckBox("Unpaid bill reminders");
        billReminder.setSelected(true);

        Spinner<Integer> reminderMinutes = new Spinner<>(1, 60, 15);
        reminderMinutes.setEditable(true);

        Button saveBtn = new Button("Save Notifications");
        saveBtn.getStyleClass().add("btn-primary");
        saveBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Notification settings saved!", ButtonType.OK);
            alert.showAndWait();
        });

        VBox content = new VBox(12,
            new Label("Notification Preferences:"),
            newOrder, readyOrder, lowStock, vipAlert, billReminder,
            new Label("Reminder interval (minutes):"), reminderMinutes,
            saveBtn
        );
        content.setPadding(new Insets(16));
        tab.setContent(content);
        return tab;
    }

    private Tab staffTab() {
        Tab tab = new Tab("Staff");

        String[][] staff = {
            {"Jean", "Waiter", "Active"},
            {"Marie", "Waiter", "Active"},
            {"Pierre", "Chef", "Active"},
            {"Sophie", "Chef", "Active"},
            {"Lucas", "Manager", "Active"},
            {"Emma", "Host", "Active"},
            {"Hugo", "Barista", "On Break"},
            {"Camille", "Waiter", "Off Duty"}
        };

        TableView<String[]> table = new TableView<>();

        TableColumn<String[], String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue()[0]));
        nameCol.setPrefWidth(150);

        TableColumn<String[], String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue()[1]));
        roleCol.setPrefWidth(120);

        TableColumn<String[], String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue()[2]));
        statusCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? null : s);
                setStyle(empty || s == null ? "" : null);
                
                // Remove any existing status-* classes
                getStyleClass().removeIf(c -> c.startsWith("status-"));
                
                // Add the appropriate status class if not empty
                if (!empty && s != null) {
                    String cls = switch (s) {
                        case "Active" -> "status-active";
                        case "On Break" -> "status-on-break";
                        case "Off Duty" -> "status-off-duty";
                        default -> "status-default";
                    };
                    // Guard against duplicate class additions
                    if (!getStyleClass().contains(cls)) {
                        getStyleClass().add(cls);
                    }
                }
            }
        });
        statusCol.setPrefWidth(100);

        table.getColumns().addAll(nameCol, roleCol, statusCol);
        table.getItems().addAll(staff);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        VBox content = new VBox(12, new Label("Staff Members:"), table);
        content.setPadding(new Insets(16));
        VBox.setVgrow(table, Priority.ALWAYS);
        tab.setContent(content);
        return tab;
    }
}
