# RestaurantOS — Documentação Oficial

Bem-vindo à documentação completa do **RestaurantOS**, um sistema desktop de gerenciamento de pedidos (Order Management System) desenvolvido em **Java 21 + JavaFX**.

---

## 📚 Índice de Documentos

### Manual do Usuário

| # | Documento | Descrição |
|---|-----------|-----------|
| 1 | [Primeiros Passos](user-manual/01-getting-started.md) | Requisitos, instalação e primeiro acesso |
| 2 | [Dashboard](user-manual/02-dashboard.md) | Visão geral e KPIs em tempo real |
| 3 | [Pedidos](user-manual/03-orders.md) | Criação, gestão e acompanhamento de pedidos |
| 4 | [Fila da Cozinha](user-manual/04-kitchen-queue.md) | Kanban de preparo para a equipe de cozinha |
| 5 | [Mesas](user-manual/05-tables.md) | Mapa visual e controle de ocupação de mesas |
| 6 | [Produtos](user-manual/06-products.md) | Gerenciamento do cardápio e produtos |
| 7 | [Clientes](user-manual/07-clients.md) | Cadastro e histórico de clientes |
| 8 | [Cobranças](user-manual/08-bills.md) | Pagamentos e fechamento de contas |
| 9 | [Relatórios](user-manual/09-reports.md) | Analytics e indicadores de desempenho |
| 10 | [Configurações](user-manual/10-settings.md) | Parâmetros do restaurante e equipe |

### Referência de Funcionalidades

| Documento | Descrição |
|-----------|-----------|
| [Funcionalidades](features.md) | Catálogo completo de todas as features do sistema |

### Mídia

| Tipo | Local | Descrição |
|------|-------|-----------|
| 🖼️ Screenshots | [docs/media/screenshots/](media/screenshots/) | Capturas de tela de cada tela do sistema |
| 🎥 Vídeos | [docs/media/videos/](media/videos/) | Guias em vídeo de cada funcionalidade |

---

## 🚀 Início Rápido

```bash
# 1. Clone o repositório
git clone https://github.com/OZimbres/figma-ai-OMS.git
cd figma-ai-OMS

# 2. Compile e execute
./gradlew run
```

Requisitos: **Java 21 LTS** ou superior.

---

## 🗺️ Visão Geral da Aplicação

```
┌─────────────────────────────────────────────────────────────┐
│  Café com Prosa                                              │
├──────────────┬──────────────────────────────────────────────┤
│  OPERATIONS  │                                              │
│  📊 Dashboard │         Área de Conteúdo Principal           │
│  📋 Orders   │                                              │
│  👨‍🍳 Kitchen  │   (cada item do menu lateral carrega       │
│  🪑 Tables   │    sua respectiva tela aqui)                 │
├──────────────┤                                              │
│  MANAGEMENT  │                                              │
│  🍽 Products │                                              │
│  👥 Clients  │                                              │
│  💰 Bills    │                                              │
├──────────────┤                                              │
│  ADMIN       │                                              │
│  📈 Reports  │                                              │
│  ⚙️ Settings │                                              │
├──────────────┴──────────────────────────────────────────────┤
│  👤 Admin User                                              │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔧 Stack Tecnológica

| Componente | Tecnologia |
|------------|-----------|
| Linguagem | Java 21 (LTS) |
| Interface | JavaFX 21 |
| Build | Gradle 8.5 |
| Tipo | Aplicação Desktop |
| Estado | Em memória (ConcurrentHashMap) |

---

*Documentação gerada para o projeto RestaurantOS — OMS.*
