# Guia de Gravação de Vídeos — RestaurantOS

Este documento descreve o que cada vídeo demonstrativo deve cobrir e como gravá-los.

---

## Ferramentas Recomendadas

| Ferramenta | Plataforma | Uso |
|------------|-----------|-----|
| **OBS Studio** | Windows / macOS / Linux | Gravação gratuita e robusta |
| **Loom** | Windows / macOS | Gravação com narração fácil |
| **ShareX** | Windows | Gravação + GIF animado |
| **macOS Screen Recording** | macOS | Gravação nativa (Cmd+Shift+5) |

---

## Configurações Recomendadas

- **Resolução:** 1280×800 ou 1920×1080
- **Frame rate:** 30 fps
- **Formato de saída:** MP4 (H.264)
- **Duração máxima por vídeo:** 3-5 minutos
- **Narração:** Sim (explique cada ação enquanto realiza)

---

## Scripts por Vídeo

### 🎥 Vídeo 01 — Primeiros Passos (Getting Started)
**Arquivo:** `01-getting-started.mp4`  
**Duração estimada:** 2-3 min

Roteiro:
1. Abrir o terminal
2. Executar `./gradlew run`
3. Mostrar a janela do RestaurantOS abrindo
4. Apresentar a sidebar e seus grupos (Operações, Gestão, Admin)
5. Navegar por cada tela rapidamente (overview de 5 segundos em cada)
6. Encerrar mostrando o atalho para fechar o app

---

### 🎥 Vídeo 02 — Dashboard
**Arquivo:** `02-dashboard.mp4`  
**Duração estimada:** 2 min

Roteiro:
1. Clicar em "Dashboard" no menu
2. Explicar os 4 cartões KPI de pedidos (Active, Preparing, Ready, Completed)
3. Explicar os cartões analíticos (Revenue, Avg Prep Time, Customers, Satisfaction)
4. Mostrar os cartões de mesas (Occupied/Free)
5. Rolar para a lista de pedidos recentes e mostrar os campos
6. Ir à tela de Pedidos, criar um novo pedido, voltar ao Dashboard e mostrar que os números atualizaram

---

### 🎥 Vídeo 03 — Pedidos (Orders)
**Arquivo:** `03-orders.mp4`  
**Duração estimada:** 4-5 min

Roteiro:
1. Clicar em "Pedidos" no menu
2. Mostrar a lista de pedidos e o campo de busca
3. Demonstrar o filtro por status (All → Preparing)
4. Clicar em **+ New Order**, preencher (mesa: 7, cliente: "Lucas Ramos", garçom: "Jean", instrução: "sem cebola")
5. Selecionar o pedido criado na lista
6. No painel central, adicionar 2 produtos clicando no painel direito
7. Alterar a prioridade para "High"
8. Mover o status para "Enviado", depois "Em Preparo"
9. Remover um item (clicar no X)
10. Mostrar o total atualizado

---

### 🎥 Vídeo 04 — Fila da Cozinha (Kitchen Queue)
**Arquivo:** `04-kitchen-queue.mp4`  
**Duração estimada:** 2-3 min

Roteiro:
1. Clicar em "Cozinha" no menu
2. Mostrar as 3 colunas Kanban (New Orders, Preparing, Ready)
3. Ler um cartão de pedido: mesa, cliente, itens, instrução especial
4. Clicar em "Mover para Preparing" em um pedido da coluna New
5. Mostrar o cartão aparecendo na coluna Preparing
6. Clicar em "Mover para Ready"
7. Mostrar o cartão na coluna Ready
8. Abrir a tela de Pedidos em outra aba e mostrar que o status também mudou

---

### 🎥 Vídeo 05 — Mesas (Tables)
**Arquivo:** `05-tables.mp4`  
**Duração estimada:** 2-3 min

Roteiro:
1. Clicar em "Mesas" no menu
2. Mostrar a grade com os cartões coloridos
3. Explicar a legenda de cores (verde=Livre, azul=Ocupada, etc.)
4. Clicar em uma mesa
5. Mostrar o painel lateral com detalhes (status, assentos, pedido vinculado)
6. Alterar o status de uma mesa clicando nos botões
7. Mostrar a grade se atualizando com a nova cor

---

### 🎥 Vídeo 06 — Produtos (Products)
**Arquivo:** `06-products.mp4`  
**Duração estimada:** 2-3 min

Roteiro:
1. Clicar em "Produtos" no menu
2. Mostrar a tabela com os produtos existentes
3. Clicar em **+ Add Product**
4. Preencher o formulário (nome: "Cheesecake", categoria: Desserts, preço: 12.00, preparo: 5, emoji: 🍰)
5. Clicar em Save e mostrar o novo produto na tabela
6. Clicar em Edit em um produto existente, alterar o preço
7. Mostrar como desmarcar "Available" e o que muda na prática (produto não aparece ao adicionar itens)

---

### 🎥 Vídeo 07 — Clientes (Clients)
**Arquivo:** `07-clients.mp4`  
**Duração estimada:** 2 min

Roteiro:
1. Clicar em "Clientes" no menu
2. Mostrar a tabela com colunas Name, Phone, Email, Visits, Tier, VIP, Spending
3. Usar o campo de busca para filtrar por nome
4. Clicar em um cliente VIP (Gold)
5. Mostrar o painel lateral: badge VIP, tier, contato, visitas, total gasto, último pedido
6. Mostrar a barra de progresso de gastos
7. Clicar em outro cliente e comparar as barras

---

### 🎥 Vídeo 08 — Cobranças (Bills)
**Arquivo:** `08-bills.mp4`  
**Duração estimada:** 3 min

Roteiro:
1. Clicar em "Cobranças" no menu
2. Mostrar os cartões de resumo (Total Revenue, Pending, Paid)
3. Usar o filtro para mostrar apenas Pending
4. Clicar em uma cobrança pendente
5. Mostrar os detalhes no painel lateral
6. Clicar em "Pay with Credit Card"
7. Mostrar que o status mudou para Paid e o Total Revenue aumentou
8. Demonstrar o cancelamento de outra cobrança

---

### 🎥 Vídeo 09 — Relatórios (Reports)
**Arquivo:** `09-reports.mp4`  
**Duração estimada:** 2-3 min

Roteiro:
1. Clicar em "Relatórios" no menu
2. Mostrar os KPIs do dia e explicar cada um
3. Rolar até Order Statistics e explicar os dados
4. Rolar até Table Statistics e destacar a barra de ocupação
5. Mostrar o gráfico de Receita por Hora
6. Mostrar o ranking de Top Products
7. Rolar até Billing Summary e comentar

---

### 🎥 Vídeo 10 — Configurações (Settings)
**Arquivo:** `10-settings.mp4`  
**Duração estimada:** 2 min

Roteiro:
1. Clicar em "Configurações" no menu
2. Na aba "Restaurant Info": explicar os campos e alterar o nome do restaurante
3. Clicar em "Save Settings" e mostrar o dialog de confirmação
4. Ir para a aba "Notifications": mostrar as opções e alterar o intervalo
5. Ir para a aba "Staff": mostrar a tabela com funcionários e status coloridos

---

## Como Publicar os Vídeos

Após gravar, você pode:
1. **Hospedar no YouTube** (não-listado) e adicionar links neste README
2. **Hospedar no GitHub Releases** — faça upload dos arquivos MP4 em uma release
3. **Embutir como GIFs** no próprio markdown usando ferramentas como [LICEcap](https://www.cockos.com/licecap/) para capturas curtas

Para referenciar o vídeo em um documento de markdown:
```markdown
[![Assista ao vídeo](thumbnail.png)](https://link-do-video)
```

---

*Guia de gravação de vídeos — RestaurantOS.*
