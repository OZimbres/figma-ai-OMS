package com.restaurantos;

import com.restaurantos.view.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    private final BorderPane root = new BorderPane();
    private final StackPane contentArea = new StackPane();
    private Button activeNavButton;

    @Override
    public void start(Stage stage) {
        VBox sidebar = buildSidebar();
        sidebar.setPrefWidth(220);
        sidebar.setMinWidth(220);

        ScrollPane contentScroll = new ScrollPane(contentArea);
        contentScroll.setFitToWidth(true);
        contentScroll.setFitToHeight(true);
        contentScroll.getStyleClass().add("content-scroll");

        root.setLeft(sidebar);
        root.setCenter(contentScroll);

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("RestaurantOS - Order Management System");
        stage.setScene(scene);
        stage.show();

        // Show dashboard by default
        switchContent(new DashboardView());
    }

    private VBox buildSidebar() {
        // Logo
        Label logo = new Label("Café com Prosa");
        logo.getStyleClass().addAll("sidebar-logo","sidebar-logo-image");

        // Navigation sections
        Label opsLabel = sectionLabel("OPERATIONS");
        Button dashBtn    = navButton("\uD83D\uDCCA  Dashboard",     () -> switchContent(new DashboardView()));
        Button ordersBtn  = navButton("\uD83D\uDCCB  Orders",        () -> switchContent(new OrdersView()));
        Button kitchenBtn = navButton("\uD83D\uDC68\u200D\uD83C\uDF73  Kitchen",       () -> switchContent(new KitchenQueueView()));
        Button tablesBtn  = navButton("\uD83E\uDE91  Tables",        () -> switchContent(new TablesView()));

        Label mgmtLabel = sectionLabel("MANAGEMENT");
        Button productsBtn = navButton("\uD83C\uDF7D  Products",     () -> switchContent(new ProductsView()));
        Button clientsBtn  = navButton("\uD83D\uDC65  Clients",      () -> switchContent(new ClientsView()));
        Button billsBtn    = navButton("\uD83D\uDCB0  Bills",        () -> switchContent(new BillsView()));

        Label adminLabel = sectionLabel("ADMIN");
        Button reportsBtn  = navButton("\uD83D\uDCC8  Reports",      () -> switchContent(new ReportsView()));
        Button settingsBtn = navButton("\u2699\uFE0F  Settings",     () -> switchContent(new SettingsView()));

        // Set dashboard as active initially
        dashBtn.getStyleClass().add("nav-btn-active");
        activeNavButton = dashBtn;

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // User info
        Label userLabel = new Label("\uD83D\uDC64  Admin User");
        userLabel.getStyleClass().add("sidebar-user");
        userLabel.setPadding(new Insets(12, 16, 16, 16));

        VBox sidebar = new VBox(2,
            logo,
            new Separator(),
            opsLabel, dashBtn, ordersBtn, kitchenBtn, tablesBtn,
            new Separator(),
            mgmtLabel, productsBtn, clientsBtn, billsBtn,
            new Separator(),
            adminLabel, reportsBtn, settingsBtn,
            spacer,
            new Separator(),
            userLabel
        );
        sidebar.getStyleClass().add("sidebar");
        return sidebar;
    }

    private Label sectionLabel(String text) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("sidebar-section");
        lbl.setPadding(new Insets(12, 16, 4, 16));
        return lbl;
    }

    private Button navButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.getStyleClass().add("nav-btn");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnAction(e -> {
            if (activeNavButton != null) activeNavButton.getStyleClass().remove("nav-btn-active");
            btn.getStyleClass().add("nav-btn-active");
            activeNavButton = btn;
            action.run();
        });
        return btn;
    }

    private void switchContent(Node view) {
        contentArea.getChildren().setAll(view);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
