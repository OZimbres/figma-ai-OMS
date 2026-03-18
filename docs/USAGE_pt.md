RestaurantOS — Guia de Uso

Início rápido (Windows)

1) Executar o instalador (recomendado)
- Caminho do instalador (criado com jpackage):
  .\build\jpackage\restaurant-oms-1.0.exe
- Execute o EXE e siga o assistente. Após a instalação o app fica disponível no menu Iniciar ou na pasta de instalação.

2) Executar a distribuição empacotada (sem instalar)
- Script de inicialização (Windows):
  .\build\distributions\restaurant-oms\restaurant-oms\bin\restaurant-oms.bat
- Este script usa as bibliotecas JavaFX incluídas na distribuição.

3) Executar o JAR (desenvolvedor)
- Build (recomendado):
  .\gradlew.bat clean build
- Executar o JAR com module-path apontando para as libs do JavaFX (exemplo usando as libs geradas na pasta de distribuição):
  java --module-path ".\build\distributions\restaurant-oms\restaurant-oms\lib" --add-modules javafx.controls,javafx.fxml -jar .\build\libs\restaurant-oms.jar
- Se preferir usar um JavaFX SDK separado, ajuste o module-path para a pasta lib do SDK.

4) Comando jpackage (exemplo)
- Exemplo de jpackage no Windows (requer WiX):
  jpackage --type exe --name restaurant-oms --input .\build\libs --main-jar restaurant-oms.jar --main-class com.restaurantos.App --dest .\build\jpackage --module-path .\build\distributions\restaurant-oms\restaurant-oms\lib --add-modules javafx.controls,javafx.fxml --win-shortcut --win-menu --verbose

Principais áreas
- Barra lateral: navegue por Dashboard, Pedidos, Cozinha, Mesas (OPERATIONS); Produtos, Clientes, Cobranças (MANAGEMENT); Relatórios, Configurações (ADMIN).
- Mesas: clique em um card para ver detalhes e alterar status.
- Pedidos: criar, visualizar, ajustar prioridade e status; selecione um pedido para editar itens.
- Fila da Cozinha: colunas Novos / Preparando / Prontos.
- Relatórios: KPIs e estatísticas.
- Configurações: editar informações do restaurante e preferências.

Fluxos básicos
- Criar pedido: clique em + Novo Pedido em Pedidos e adicione itens.
- Alterar status da mesa: em Mesas clique na mesa e use Alterar Status no painel lateral.
- Pagar conta: abra Contas e use as ações de pagamento para contas pendentes.

Resolução de problemas (quando a interface não aparece)
- Execute o comando java a partir de um terminal PowerShell para ver erros e stacktraces.
- Erro "Module javafx.controls not found": module-path está incorreto ou faltam as libs do JavaFX. Aponte module-path para a pasta com os jars do JavaFX (ex.: a pasta lib da distribuição acima ou a pasta lib do SDK JavaFX).
- Erro: processo roda mas janela não aparece: execute o comando java no terminal para capturar logs; verifique se seu JDK suporta JavaFX.
- Para empacotar o instalador, instale o WiX Toolset e verifique se está no PATH.

Onde encontrar arquivos
- Código-fonte: src\main\java e src\main\resources
- Folha de estilos: src\main\resources\css\style.css
- Logo gerado: src\main\resources\images\logo-generated.png

Se ainda não funcionar, execute o comando java no PowerShell e cole a saída do console aqui para que eu analise.