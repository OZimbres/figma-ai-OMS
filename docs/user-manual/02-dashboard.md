# 02 — Dashboard

O Dashboard é a tela inicial do RestaurantOS. Ele apresenta uma visão geral em tempo real das operações do restaurante.

---

## Visão Geral

![Dashboard — Visão Geral](../media/screenshots/02-dashboard.svg)

---

## Componentes da Tela

### 1. Cartões KPI de Pedidos

Quatro cartões exibem contadores de pedidos atualizados em tempo real:

| Cartão | Cor | O que mostra |
|--------|-----|-------------|
| **Active Orders** | 🔵 Azul | Total de pedidos que ainda não foram concluídos |
| **Preparing** | 🟡 Âmbar | Pedidos com status "Em Preparo" |
| **Ready** | 🟢 Verde | Pedidos com status "Pronto" (aguardando serviço) |
| **Completed** | ⬜ Cinza | Pedidos já concluídos |

```
┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ Active Orders│ │  Preparing   │ │    Ready     │ │  Completed   │
│      5       │ │      2       │ │      1       │ │      3       │
└──────────────┘ └──────────────┘ └──────────────┘ └──────────────┘
```

### 2. Métricas Analíticas

Segunda linha de cartões com indicadores do dia:

| Cartão | Descrição |
|--------|-----------|
| **Today Revenue** | Receita total do dia (ex: $1.245,00) |
| **Avg Prep Time** | Tempo médio de preparo de pedidos (em minutos) |
| **Customers** | Número de clientes atendidos hoje |
| **Satisfaction** | Índice de satisfação dos clientes (em %) |

### 3. Ocupação de Mesas

| Cartão | Descrição |
|--------|-----------|
| **Occupied Tables** | Quantidade de mesas em uso (não livres) |
| **Free Tables** | Quantidade de mesas disponíveis |

### 4. Lista de Pedidos Recentes

Exibe os pedidos mais recentes ordenados do mais novo para o mais antigo.

Cada linha mostra:
```
Table 3  |  Maria Silva  |  Preparing  |  14:32  |  $47.50
```

- **Table** — Número da mesa
- **Client** — Nome do cliente
- **Status** — Status atual do pedido
- **Time** — Horário de criação
- **Total** — Valor do pedido

---

## Comportamento em Tempo Real

Todos os dados do Dashboard são **atualizados automaticamente** sempre que:

- Um pedido é criado, atualizado ou concluído (em qualquer tela)
- O status de uma mesa é alterado
- Um novo item é adicionado a um pedido

Não é necessário recarregar ou clicar em nada — a atualização é instantânea.

---

## Casos de Uso

### Monitoramento matinal (abertura)
1. Abra o RestaurantOS
2. O Dashboard carrega automaticamente
3. Verifique se não há pedidos pendentes de dias anteriores

### Acompanhamento durante o serviço
1. Mantenha o Dashboard aberto em um monitor secundário
2. Acompanhe os contadores de "Preparing" e "Ready" para saber quando pedidos precisam de atenção
3. Monitore a ocupação de mesas para planejar reservas

### Encerramento do dia
1. Verifique o "Today Revenue" para conferir o faturamento
2. Certifique-se de que não há pedidos com status "Ready" ou "Preparing" sem atendimento
3. Confirme que todas as cobranças foram processadas (acesse a tela [Bills](08-bills.md))

---

## 🎥 Vídeo Demonstrativo

📹 [Assista: Dashboard em ação](../media/videos/02-dashboard.md)

---

*[← Primeiros Passos](01-getting-started.md) | [Pedidos →](03-orders.md)*  
*[← Voltar ao Índice](../index.md)*
