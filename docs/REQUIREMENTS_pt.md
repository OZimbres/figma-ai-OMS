Requisitos do Sistema

Mínimo (execução do desenvolvedor)
- SO: Windows 10/11 (testado). macOS/Linux suportados para build/execução com diferenças no jpackage.
- Java: JDK 21 (ou 17+ compatível). Use JDK completo para empacotar com jpackage.
- JavaFX: JavaFX 21 (combine com a versão do JDK quando possível). Ao executar o JAR diretamente, forneça --module-path para as libs do JavaFX.
- Gradle: 8.5 (fixado via Gradle wrapper — sempre use `./gradlew` ou `gradlew.bat` em vez de uma instalação local do Gradle)

Empacotamento (instalador Windows)
- jpackage (incluso no JDK usado para empacotar)
- WiX Toolset (recomendado v3.11) — necessário para gerar .exe/.msi no Windows

Hardware
- RAM: mínimo 4 GB; recomendado 8 GB para desenvolvimento/testes confortáveis
- Tela: 1280x720 recomendado para a UI; funciona em telas menores, mas o layout pode ficar mais compacto

Ferramentas de desenvolvimento
- Git 2.x
- Editor de código (IntelliJ IDEA, VS Code, etc.)

Observações
- A distribuição gerada inclui as jars do runtime do JavaFX em build/distributions quando produzida pelo Gradle.
- Para rodar o jar diretamente, use o caminho do JavaFX via --module-path, a menos que tenha sido criada uma imagem runtime modular pelo jpackage.
- Para produzir o instalador, use as tasks do Gradle (ou jpackage manualmente); o WiX deve estar instalado e no PATH para criar instaladores no Windows.