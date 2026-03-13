# figma-ai-OMS

Restaurant Order Management System (OMS) — a Java 21 / Spring Boot REST API built with Gradle.

## Prerequisites

- Java 21 (e.g. Eclipse Temurin 21)

## Build

```bash
./gradlew build
```

## Run

```bash
./gradlew bootRun
```

The server starts on **http://localhost:8080**.

## API Endpoints

### Orders
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/orders` | List all orders |
| GET | `/api/orders/{id}` | Get order by ID |
| POST | `/api/orders` | Create order |
| PUT | `/api/orders/{id}/status` | Update order status |
| PUT | `/api/orders/{id}/priority` | Update order priority |
| POST | `/api/orders/{id}/items` | Add item to order |
| DELETE | `/api/orders/{id}/items/{itemId}` | Remove item from order |

### Tables
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/tables` | List all tables |
| GET | `/api/tables/{id}` | Get table by ID |
| PUT | `/api/tables/{id}` | Update table |

### Products
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Add product |
| PUT | `/api/products/{id}` | Update product |

### Clients
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/clients` | List all clients |
| GET | `/api/clients/{id}` | Get client by ID |

### Bills
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/bills` | List all bills |
| GET | `/api/bills/{id}` | Get bill by ID |
| POST | `/api/bills` | Add bill |
| PUT | `/api/bills/{id}/status` | Update bill status |

### Dashboard
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/dashboard` | Get dashboard statistics |

### Reports
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/reports/revenue` | Revenue data |
| GET | `/api/reports/top-products` | Top selling products |
| GET | `/api/reports/prep-times` | Average prep times by category |