RestaurantOS — Guia de Uso

Início rápido (Windows)
- Instalador: execute build\jpackage\restaurant-oms-1.0.exe e siga o assistente.
- Distribuição empacotada: execute build\distributions\restaurant-oms\restaurant-oms\bin\restaurant-oms.bat
- Executando o JAR (desenvolvedor):
  java --module-path /caminho/para/javafx/lib --add-modules javafx.controls,javafx.fxml -jar restaurant-oms.jar

Principais áreas
- Barra lateral: navegue por Pedidos, Mesas, Cozinha, Relatórios, Configurações.
- Mesas: clique em um card para ver detalhes e alterar status.
- Pedidos: criar, visualizar, ajustar prioridade e status; selecione um pedido para editar itens.
- Fila da Cozinha: colunas Novos / Preparando / Prontos; mova pedidos entre estados.
- Relatórios: KPIs e estatísticas básicas.
- Configurações: edite informações do restaurante e preferências do app.

Fluxos básicos
- Criar pedido: + Novo Pedido em Pedidos, adicione itens pelo painel direito.
- Alterar status da mesa: em Mesas use os botões de Alterar Status no painel lateral.
- Pagar conta: abra Contas e use as ações de Pagamento para contas pendentes.

Resolução de problemas
- Se a interface não iniciar: verifique Java + JavaFX e que --module-path aponte para as libs do JavaFX.
- Se o empacotamento falhar (jpackage): instale o WiX Toolset no Windows para criar EXE/MSI.

Onde encontrar arquivos
- Código-fonte: src\main\java e src\main\resources
- Folha de estilos: src\main\resources\css\style.css
- Logo gerado: src\main\resources\images\logo-generated.png

Contato
- Para atualizar ativos (logo, fontes), substitua em src\main\resources\images e ajuste o CSS (helper de logo/fontes).