# Theme & Assets Notes — Café com Prosa

## What Was Changed

- Central CSS updated: `src/main/resources/css/style.css` holds the Café com Prosa palette.
- CSS helper classes added for accessibility and contrast: `muted-text`, `table-number`, `status-badge`, `status-*`, `card-border-*`, `priority-*`.
- Inline `setStyle(...)` usages were replaced across multiple views with CSS classes (TablesView, OrdersView, KitchenQueueView, ClientsView, ReportsView, BillsView, SettingsView).
- Generated fallback logo: `src/main/resources/images/logo-generated.png` used by `.sidebar-logo-image`.
- Application brand name in the sidebar: **Café com Prosa**.

## How to Update Branding

1. **Replace logo:** put new image in `src/main/resources/images/` (same path or update CSS selector `.sidebar-logo-image`).
2. **Fonts:** add font files to resources and use `@font-face` in CSS, then set `-fx-font-family` in `.root` or relevant selectors.
3. **Colors:** edit palette colors directly in `style.css`. The base palette is set in the `.root` selector at the top of the file. Search for `.root` and the primary hex values (e.g., `#C07A2B`, `#FBF9F7`) to locate the colors to change.
4. **Brand name:** update the `Label logo = new Label("Café com Prosa")` line in `src/main/java/com/restaurantos/App.java`.

## Notes

- Run `grep -r 'setStyle("'` in `src/` to catch any remaining inline styles added dynamically.
- Test all flows visually to confirm contrast and selection states are correct.
