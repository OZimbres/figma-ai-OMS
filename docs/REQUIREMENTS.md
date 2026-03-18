System requirements

Minimum (developer run)
- OS: Windows 10/11 (tested), macOS/Linux supported for building/running with appropriate jpackage tool differences.
- Java: JDK 21 (or 17+ compatible). Use the full JDK when building with jpackage.
- JavaFX: JavaFX 21 (matching JDK major where possible). When running the JAR directly, provide --module-path to JavaFX SDK libs.
- Gradle: 8.5 (pinned via Gradle wrapper — always use `./gradlew` or `gradlew.bat` instead of a local Gradle install)

Packaging (Windows installer)
- jpackage (part of the JDK used for packaging)
- WiX Toolset (v3.11 recommended) — required to build .exe/.msi installers on Windows

Hardware
- 4 GB RAM minimum; 8 GB recommended for comfortable development/testing
- Screen: 1280x800 recommended for the UI; works on smaller screens but layout may compact

Developer tools
- Git 2.x
- A code editor (IntelliJ IDEA, VS Code, etc.)

Notes
- The built distribution includes JavaFX runtime jars in build/distributions when produced by Gradle.
- For running the jar directly, the command must reference JavaFX SDK libs (module-path) unless using a modular runtime image created by jpackage.
- To produce the installer use the Gradle tasks (or run jpackage manually) after build; WiX must be installed and on PATH for Windows installer creation.