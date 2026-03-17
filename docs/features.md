# Funcionalidades do RestaurantOS

Catálogo completo de todas as funcionalidades disponíveis no sistema.

---

## 📊 1. Dashboard

| Funcionalidade | Descrição |
|----------------|-----------|
| KPIs de pedidos | Cartões com contadores de pedidos Ativos, Em Preparo, Prontos e Concluídos em tempo real |
| Métricas analíticas | Receita do dia, Tempo médio de preparo, Clientes atendidos e Índice de satisfação |
| Ocupação de mesas | Contadores de mesas Ocupadas e Livres atualizados automaticamente |
| Lista de pedidos recentes | Exibe os últimos pedidos ordenados por horário (tabela, cliente, status, valor) |
| Atualização em tempo real | Todos os dados são atualizados automaticamente quando há mudanças no sistema |

---

## 📋 2. Pedidos

| Funcionalidade | Descrição |
|----------------|-----------|
| Listagem de pedidos | Exibe todos os pedidos com ID, mesa, cliente, status e valor total |
| Busca | Filtragem por nome do cliente, ID do pedido ou número da mesa |
| Filtro por status | Filtrar por: Todos, Novo, Em Preparo, Pronto, Servido, Concluído |
| Novo pedido | Dialog para criar pedido informando mesa, cliente, garçom e instruções especiais |
| Detalhes do pedido | Painel central com todas as informações: mesa, cliente, garçom, status, prioridade, horário, instruções especiais e itens |
| Mudança de status | Botões de ação para mover o pedido para qualquer status: Novo → Enviado → Em Preparo → Pronto → Servido → Concluído |
| Prioridade | Definir prioridade do pedido: Normal, Alta, Crítica |
| Itens do pedido | Tabela com produto, quantidade, preço, status do item e notas |
| Adicionar itens | Painel lateral direito com todos os produtos disponíveis; clique adiciona ao pedido selecionado |
| Remover itens | Botão "X" em cada item da tabela de itens do pedido |
| Total do pedido | Exibição do valor total calculado automaticamente |

---

## 👨‍🍳 3. Fila da Cozinha (Kitchen Queue)

| Funcionalidade | Descrição |
|----------------|-----------|
| Kanban de 3 colunas | Visualização em colunas: Novos Pedidos, Em Preparo e Prontos |
| Cartão de pedido | Cada cartão exibe: mesa, ID, cliente, garçom, horário, prioridade, lista de itens e tempo estimado |
| Indicador de prioridade | Cor diferenciada: vermelho = Crítica, amarelo = Alta |
| Instruções especiais | Alerta visual (⚠️) quando há instruções especiais no pedido |
| Mover pedido | Botão "Mover para →" avança o pedido para a próxima etapa do kanban |
| Atualização automática | Colunas se atualizam quando qualquer status de pedido muda |

---

## 🪑 4. Mesas

| Funcionalidade | Descrição |
|----------------|-----------|
| Grade visual | Exibição em grade de todas as mesas com cartões coloridos |
| Status colorido | Verde=Livre, Azul=Ocupada, Amarelo=Pedindo, Roxo=Aguardando, Vermelho=Pagamento |
| Informações rápidas | Cada cartão mostra número da mesa, status e ocupação (convidados/lugares) |
| Detalhes no painel lateral | Ao clicar: status, assentos, há quanto tempo está ocupada e pedido vinculado |
| Pedido vinculado | Exibe ID do pedido, cliente e total quando há pedido ativo na mesa |
| Mudança de status | Botões para alterar status: Livre, Ocupada, Pedindo, Aguardando, Pagamento |

---

## 🍽️ 5. Produtos

| Funcionalidade | Descrição |
|----------------|-----------|
| Listagem de produtos | Tabela com emoji, nome, categoria, preço, tempo de preparo e disponibilidade |
| Adicionar produto | Dialog com campos: nome, categoria, preço, tempo de preparo, emoji e disponibilidade |
| Editar produto | Mesmo dialog pré-preenchido para atualizar dados de produto existente |
| Disponibilidade | Indicador visual ✅ Sim / ❌ Não para controle de disponibilidade no cardápio |
| Categorias | Categorias disponíveis: Drinks, Pastries, Sandwiches, Meals, Desserts |
| Tempo de preparo | Campo em minutos usado para estimativa na cozinha |

---

## 👥 6. Clientes

| Funcionalidade | Descrição |
|----------------|-----------|
| Listagem de clientes | Tabela com nome, telefone, e-mail, visitas, tier de fidelidade, VIP e total gasto |
| Busca | Filtragem por nome, e-mail ou telefone |
| Perfil no painel lateral | Ao selecionar: informações detalhadas de contato, histórico e gastos |
| Indicador VIP | ⭐ VIP para clientes especiais |
| Tiers de fidelidade | Bronze, Silver, Gold, Platinum conforme número de visitas (15/30/50+) |
| Barra de gastos | Barra de progresso visual comparando o total gasto com o maior cliente |
| Último pedido | Data e hora do último pedido registrado |

---

## 💰 7. Cobranças (Bills)

| Funcionalidade | Descrição |
|----------------|-----------|
| Resumo financeiro | Cartões com Receita Total, Cobranças Pendentes e Cobranças Pagas |
| Listagem de cobranças | Tabela com ID, mesa, cliente, total, método de pagamento, status e data |
| Filtro por status | Todos, Pendente, Pago, Cancelado |
| Detalhes no painel lateral | ID, mesa, cliente, pedido vinculado, total, método, status e data |
| Processar pagamento | Botões para pagar com cada método disponível: Dinheiro (Cash), Cartão (Card), Digital |
| Cancelar cobrança | Botão para cancelar uma cobrança pendente |
| Atualização automática | Resumo e tabela atualizam em tempo real |

---

## 📈 8. Relatórios

| Funcionalidade | Descrição |
|----------------|-----------|
| KPIs do dia | Receita, tempo médio de preparo, clientes e satisfação |
| Estatísticas de pedidos | Total, ativos, concluídos e valor médio por pedido |
| Estatísticas de mesas | Total, ocupadas e taxa de ocupação com barra de progresso |
| Receita por hora | Barras horizontais mostrando a receita em cada faixa horária do dia |
| Produtos mais vendidos | Ranking visual dos produtos mais pedidos |
| Resumo de cobranças | Total faturado, valor pago e cobranças pendentes |

---

## ⚙️ 9. Configurações

| Funcionalidade | Descrição |
|----------------|-----------|
| **Aba Restaurante** | Nome, endereço, telefone, e-mail, moeda, taxa de imposto e número de mesas |
| **Aba Notificações** | Ativar/desativar alertas: novo pedido, pedido pronto, estoque baixo, cliente VIP, lembrete de conta não paga; intervalo de lembretes |
| **Aba Equipe** | Tabela de funcionários com nome, função e status (Ativo, Em Pausa, Fora de Serviço) |
| Salvar configurações | Botão de salvamento com confirmação por dialog |

---

## 🔄 Fluxo Operacional Completo

```
[Cliente chega] → Mesa atualizada (Ocupada)
      ↓
[Garçom cria pedido] → Pedido criado (Status: Novo)
      ↓
[Pedido enviado à cozinha] → Cozinha vê na coluna "Novos"
      ↓
[Cozinha inicia preparo] → Status: Em Preparo → Coluna "Preparando"
      ↓
[Prato pronto] → Status: Pronto → Coluna "Prontos"
      ↓
[Garçom serve] → Status: Servido
      ↓
[Pedido concluído] → Status: Concluído → Cobrança gerada (Pendente)
      ↓
[Pagamento processado] → Cobrança: Paga → Receita computada
      ↓
[Mesa liberada] → Status da mesa: Livre
```

---

## 📊 Status de Pedidos

| Status | Descrição | Quem atualiza |
|--------|-----------|---------------|
| `Novo` | Pedido criado, aguardando envio | Garçom |
| `Enviado` | Enviado para a cozinha | Garçom |
| `Em Preparo` | Cozinha iniciou o preparo | Cozinheiro (Kitchen Queue) |
| `Pronto` | Prato pronto para servir | Cozinheiro (Kitchen Queue) |
| `Servido` | Prato entregue ao cliente | Garçom / Cozinheiro |
| `Concluído` | Pedido finalizado | Garçom |

## 🎨 Status de Mesas

| Status | Cor | Descrição |
|--------|-----|-----------|
| Livre | 🟢 Verde | Mesa disponível |
| Ocupada | 🔵 Azul | Mesa com clientes |
| Pedindo | 🟡 Amarelo | Clientes escolhendo |
| Aguardando | 🟣 Roxo | Aguardando comida |
| Pagamento | 🔴 Vermelho | Aguardando cobrança |

## 💳 Métodos de Pagamento

- 💵 Cash (Dinheiro)
- 💳 Card (Cartão)
- 📱 Digital (PIX, transferência, etc.)

## ⭐ Tiers de Fidelidade de Clientes

| Tier | Critério |
|------|----------|
| Bronze | Menos de 15 visitas |
| Silver | 15 ou mais visitas |
| Gold | 30 ou mais visitas (também marcado como VIP ⭐) |
| Platinum | 50 ou mais visitas (também marcado como VIP ⭐) |

---

*Documentação de funcionalidades do RestaurantOS.*
