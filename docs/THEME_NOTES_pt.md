# Notas de Tema e Ativos — Café com Prosa

## O que foi alterado

- CSS central atualizado: `src\main\resources\css\style.css` contém a paleta Café com Prosa.
- Novas classes helper no CSS para acessibilidade e contraste: `muted-text`, `table-number`, `status-badge`, `status-*`, `card-border-*`, `priority-*`.
- Usos de `setStyle(...)` foram substituídos por classes CSS em várias views (TablesView, OrdersView, KitchenQueueView, ClientsView, ReportsView, BillsView, SettingsView).
- Logo fallback gerado: `src\main\resources\images\logo-generated.png` usado por `.sidebar-logo-image`.
- Nome da marca na barra lateral: **Café com Prosa**.

## Como atualizar a identidade visual

1. **Substitua o logo:** coloque a nova imagem em `src\main\resources\images` (mesmo caminho ou atualize o seletor CSS `.sidebar-logo-image`).
2. **Fontes:** adicione arquivos de fontes em resources e use `@font-face` no CSS, depois defina `-fx-font-family` em `.root` ou seletores relevantes.
3. **Cores:** edite as cores diretamente em `style.css`. A paleta base está no seletor `.root` no início do arquivo. Busque por `.root` e pelos valores hex primários (ex.: `#C07A2B`, `#FBF9F7`) para localizar as cores a serem alteradas.
4. **Nome da marca:** atualize a linha `Label logo = new Label("Café com Prosa")` em `src\main\java\com\restaurantos\App.java`.

## Observações

- Execute `grep -r 'setStyle("'` em `src/` para localizar eventuais estilos inline adicionados dinamicamente.
- Teste todos os fluxos visualmente para confirmar contraste e estados de seleção.
