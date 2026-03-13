package com.restaurantos.view;

import com.restaurantos.data.DataStore;
import com.restaurantos.model.Product;
import com.restaurantos.model.enums.ProductCategory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Optional;
import java.util.UUID;

public class ProductsView extends VBox {

    private final DataStore store = DataStore.getInstance();
    private final TableView<Product> table = new TableView<>();

    public ProductsView() {
        setSpacing(16);
        setPadding(new Insets(24));
        getStyleClass().add("content-area");

        Label title = new Label("Products");
        title.getStyleClass().add("view-title");

        Button addBtn = new Button("+ Add Product");
        addBtn.getStyleClass().add("btn-primary");
        addBtn.setOnAction(e -> showProductDialog(null));

        HBox header = new HBox(16, title, addBtn);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        buildTable();

        VBox.setVgrow(table, Priority.ALWAYS);
        getChildren().addAll(header, table);

        store.getProducts().addListener((ListChangeListener<Product>) c -> table.setItems(store.getProducts()));
        table.setItems(store.getProducts());
    }

    private void buildTable() {
        TableColumn<Product, String> emojiCol = new TableColumn<>("");
        emojiCol.setCellValueFactory(cd -> cd.getValue().emojiProperty());
        emojiCol.setPrefWidth(40);

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cd -> cd.getValue().nameProperty());
        nameCol.setPrefWidth(180);

        TableColumn<Product, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getCategory().getLabel()));
        catCol.setPrefWidth(120);

        TableColumn<Product, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cd -> cd.getValue().priceProperty());
        priceCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Number n, boolean empty) {
                super.updateItem(n, empty);
                setText(empty || n == null ? null : String.format("$%.2f", n.doubleValue()));
            }
        });
        priceCol.setPrefWidth(80);

        TableColumn<Product, Number> prepCol = new TableColumn<>("Prep (min)");
        prepCol.setCellValueFactory(cd -> cd.getValue().prepTimeProperty());
        prepCol.setPrefWidth(80);

        TableColumn<Product, Boolean> availCol = new TableColumn<>("Available");
        availCol.setCellValueFactory(cd -> cd.getValue().availableProperty().asObject());
        availCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Boolean b, boolean empty) {
                super.updateItem(b, empty);
                if (empty || b == null) { setText(null); setGraphic(null); } else {
                    Label lbl = new Label(b ? "\u2705 Yes" : "\u274C No");
                    setGraphic(lbl);
                }
            }
        });
        availCol.setPrefWidth(80);

        TableColumn<Product, Void> editCol = new TableColumn<>("");
        editCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Edit");
            { btn.getStyleClass().add("btn-small"); }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty) { setGraphic(null); } else {
                    btn.setOnAction(e -> showProductDialog(getTableView().getItems().get(getIndex())));
                    setGraphic(btn);
                }
            }
        });
        editCol.setPrefWidth(70);

        table.getColumns().addAll(emojiCol, nameCol, catCol, priceCol, prepCol, availCol, editCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void showProductDialog(Product existing) {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle(existing == null ? "Add Product" : "Edit Product");

        ButtonType saveType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveType, ButtonType.CANCEL);

        TextField nameField = new TextField(existing != null ? existing.getName() : "");
        nameField.setPromptText("Product name");

        ComboBox<ProductCategory> catBox = new ComboBox<>();
        catBox.getItems().addAll(ProductCategory.values());
        catBox.setValue(existing != null ? existing.getCategory() : ProductCategory.DRINKS);

        TextField priceField = new TextField(existing != null ? String.valueOf(existing.getPrice()) : "");
        priceField.setPromptText("Price");

        TextField prepField = new TextField(existing != null ? String.valueOf(existing.getPrepTime()) : "");
        prepField.setPromptText("Prep time (min)");

        TextField emojiField = new TextField(existing != null ? existing.getEmoji() : "");
        emojiField.setPromptText("Emoji");

        CheckBox availCheck = new CheckBox("Available");
        availCheck.setSelected(existing == null || existing.isAvailable());

        VBox form = new VBox(8,
            new Label("Name:"), nameField,
            new Label("Category:"), catBox,
            new Label("Price:"), priceField,
            new Label("Prep Time:"), prepField,
            new Label("Emoji:"), emojiField,
            availCheck
        );
        form.setPadding(new Insets(16));
        dialog.getDialogPane().setContent(form);

        dialog.setResultConverter(bt -> {
            if (bt == saveType) {
                try {
                    String id = existing != null ? existing.getId() : "p-" + UUID.randomUUID().toString().substring(0, 6);
                    return new Product(id, nameField.getText().trim(), catBox.getValue(),
                            Double.parseDouble(priceField.getText().trim()),
                            Integer.parseInt(prepField.getText().trim()),
                            availCheck.isSelected(), emojiField.getText().trim());
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });

        Optional<Product> result = dialog.showAndWait();
        result.ifPresent(p -> {
            if (existing != null) { store.updateProduct(p); }
            else { store.addProduct(p); }
        });
    }
}
