Notas de Tema e Ativos — feat/tema-cafecomprosa

O que foi alterado
- CSS central atualizado: src\main\resources\css\style.css agora contém a paleta Café com Prosa.
- Novas classes helper no CSS para acessibilidade e contraste (muted-text, table-number, status-badge, status-*, card-border-*, priority-*).
- Usos de setStyle(...) foram substituídos por classes CSS em várias views (TablesView, OrdersView, KitchenQueueView, ClientsView, ReportsView, BillsView, SettingsView).
- Logo fallback gerado: src\main\resources\images\logo-generated.png usado por .sidebar-logo-image.

Como atualizar a identidade visual
1. Substitua o logo: coloque a nova imagem em src\main\resources\images (mesmo caminho ou atualize o CSS).
2. Fontes: adicione arquivos de fontes em resources e use @font-face no CSS, depois defina -fx-font-family em .root ou seletores relevantes.
3. Cores: edite as variáveis de paleta no topo de style.css (procure por :root e cores primárias).

Trabalho restante
- Revisão manual: algumas views podem aplicar estilos inline dinamicamente; rode um grep por setStyle(" em src/ para localizar.
- QA visual: teste todos os fluxos para confirmar contraste e estados de seleção (prints em build/screenshots).

Branch e commits
- Branch: feat/tema-cafecomprosa
- Commits incluem alterações de tema e refatoração. Substitua ativos e reconstrua o instalador conforme necessário.