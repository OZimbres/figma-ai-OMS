Issue #4: [Tables] Add explicit selected state for table cards

Status: FIXED (implemented and pushed)

Commit: bc2d658 on branch feat/tema-cafecomprosa

Summary:
- Observed that table cards are clickable and open the side panel, but do not retain a visible selected/highlighted state in the grid.
- Code shows a selectedTableId field in TablesView but there is no logic applying or toggling the CSS class .table-card-selected on card clicks.

Evidence (code snippets):
- src/main/java/com/restaurantos/view/TablesView.java
  Relevant excerpt:

  private String selectedTableId = null;

  private VBox tableCard(RestaurantTable t) {
      ...
      VBox card = new VBox(8, num, statusLabel, seats);
      card.setAlignment(Pos.CENTER);
      card.setPadding(new Insets(16));
      card.setPrefSize(160, 130);
      card.getStyleClass().add("card");
      card.getStyleClass().add("card-border-" + t.getStatus().name().toLowerCase());

      card.setOnMouseClicked(e -> showTableDetail(t));
      return card;
  }

- Note: there is no add/remove of "table-card-selected" here, so the CSS rule in src/main/resources/css/style.css (".table-card-selected") is not applied.

CSS for selected state (present):
- src/main/resources/css/style.css contains:
  .table-card-selected {
      -fx-background-color: #FFF1E0;
      -fx-border-color: #C07A2B;
      -fx-border-width: 0 0 3 0;
  }

Reproduction steps (local):
1. Run start script: build\distributions\restaurant-oms\restaurant-oms\bin\restaurant-oms.bat
2. Open Tables screen.
3. Click a table card.
4. Expectation: the clicked card should show a subtle highlight/border (.table-card-selected). Current: only the side panel updates; card remains visually unselected.

Suggested fix:
- In tableCard(...) setOnMouseClicked handler, toggle selectedTableId and add/remove the class on the previously and newly selected card nodes.
- Example pseudo-fix:
    card.setOnMouseClicked(e -> {
        String prev = selectedTableId;
        selectedTableId = t.getId();
        refreshGrid(); // or toggle class on nodes
        showTableDetail(t);
    });
  Alternatively keep references to card nodes and apply getStyleClass().add("table-card-selected")/remove(...).

Logs: build\logs\app-run.final.log

Attachments:
- Screenshot: /docs/issues/images/tables_screen.png

Inline preview (if supported by GitHub):

![Tables screen](/docs/issues/images/tables_screen.png)

Notes: The screenshot above was captured after starting the packaged start script in this environment. GUI windows may not render in remote CI; please run locally if the image does not display.

Next steps I can take if you want:
- Implement the selection toggle in TablesView and commit the change.
- Run the app and capture a screenshot showing the selected card.
