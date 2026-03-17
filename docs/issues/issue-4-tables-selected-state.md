Issue #4: [Tables] Add explicit selected state for table cards

Status: FIXED (implemented and pushed)

Commit: bc2d658 on branch feat/tema-cafecomprosa

Summary:
- Table cards are clickable and open the side panel. Before the fix, they did not retain a visible selected/highlighted state in the grid.
- The fix adds toggle logic in the `setOnMouseClicked` handler: clicking an unselected card sets `selectedTableId` and applies `.table-card-selected`; clicking the same card again clears `selectedTableId` and restores the placeholder panel.

Current behavior (post-fix):
- Clicking a table card highlights it with `.table-card-selected` (amber border, warm background) and shows its details in the side panel.
- Clicking the already-selected card deselects it (removes the highlight) and restores the "Select a table" placeholder in the side panel.

Evidence — original code (pre-fix):
- The original `setOnMouseClicked` handler only called `showTableDetail(t)` with no selection tracking:
  ```
  card.setOnMouseClicked(e -> showTableDetail(t));
  ```
- There was no add/remove of `.table-card-selected`, so the CSS rule was never applied.

Evidence — fixed code:
- `src/main/java/com/restaurantos/view/TablesView.java`
  ```java
  card.setOnMouseClicked(e -> {
      if (t.getId() != null && t.getId().equals(selectedTableId)) {
          selectedTableId = null;
          refreshGrid();
          restorePlaceholder();
      } else {
          selectedTableId = t.getId();
          refreshGrid();
          showTableDetail(t);
      }
  });
  ```
- During `refreshGrid()`, the card for the selected table receives the class:
  ```java
  if (t.getId() != null && t.getId().equals(selectedTableId)) {
      card.getStyleClass().add("table-card-selected");
  }
  ```

CSS for selected state:
- `src/main/resources/css/style.css`:
  ```css
  .table-card-selected {
      -fx-background-color: #FFF1E0;
      -fx-border-color: #C07A2B !important;
      -fx-border-width: 2 !important;
  }
  ```

Reproduction steps (local):
1. Run start script: build\distributions\restaurant-oms\restaurant-oms\bin\restaurant-oms.bat
2. Open Tables screen.
3. Click a table card — it should show a subtle amber highlight (.table-card-selected) and open details in the side panel.
4. Click the same card again — it should deselect (highlight removed) and restore the "Select a table" placeholder.

Attachments:
- Screenshot: /docs/issues/images/tables_screen.png

Inline preview (if supported by GitHub):

![Tables screen](/docs/issues/images/tables_screen.png)

Notes: The screenshot above was captured after starting the packaged start script in this environment. GUI windows may not render in remote CI; please run locally if the image does not display.
