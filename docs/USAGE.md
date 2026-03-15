RestaurantOS — Usage Guide

Quick start (Windows)
- Installer: run build\jpackage\restaurant-oms-1.0.exe and follow the installer wizard.
- Bundled distribution: run build\distributions\restaurant-oms\restaurant-oms\bin\restaurant-oms.bat
- Running the JAR (developer):
  java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar restaurant-oms.jar

Main areas
- Sidebar: navigate between Orders, Tables, Kitchen, Reports, Settings.
- Tables: click a table card to view details and change status.
- Orders: create, view, change priority and status; select an order to edit items.
- Kitchen Queue: see columns for New / Preparing / Ready and move orders between states.
- Reports: basic KPIs and stats; use for quick analytics.
- Settings: edit restaurant info and app preferences.

Basic workflows
- Create order: + New Order in Orders, add items from the right panel.
- Change table status: in Tables view use the Change Status buttons in the side panel.
- Pay bill: open Bills view and use Pay with ... actions for pending bills.

Troubleshooting
- If UI fails to start: ensure Java + JavaFX are installed and the module-path points to JavaFX libs.
- If packaged installer fails (jpackage): ensure WiX Toolset is installed on Windows for EXE/MSI creation.

Where to find files
- Source: src/main/java and src/main/resources
- Stylesheet: src/main/resources/css/style.css
- Logo asset used: src/main/resources/images/logo-generated.png

Contact
- For asset updates (brand logo, fonts), replace the files in src/main/resources/images and update CSS (logo helper / fonts).