Theme & assets notes — feat/tema-cafecomprosa

What was changed
- Central CSS updated: src/main/resources/css/style.css now holds the Café com Prosa palette.
- New CSS helper classes added for accessibility and contrast (muted-text, table-number, status-badge, status-*, card-border-*, priority-*).
- Inline setStyle(...) usages were replaced across multiple views and mapped to CSS classes (TablesView, OrdersView, KitchenQueueView, ClientsView, ReportsView, BillsView, SettingsView).
- A generated fallback logo: src/main/resources/images/logo-generated.png is used by .sidebar-logo-image.

How to update branding
1. Replace logo: put new image in src/main/resources/images/ (same path or update CSS).
2. Fonts: add font files to resources and use @font-face in CSS, then set -fx-font-family in .root or relevant selectors.
3. Colors: edit palette variables at the top of style.css (search for root and primary colors).

Remaining work
- Manual review: some views may add inline styles dynamically; run a grep for setStyle(" in src/ to catch additions.
- Visual QA: test all flows to confirm contrast and selection states are correct (screenshots saved in build/screenshots).

Branch & commits
- Branch: feat/tema-cafecomprosa
- Commits include theme and refactor changes. Replace assets and rebuild installer as needed.