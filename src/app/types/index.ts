export type OrderStatus = 'new' | 'sent' | 'preparing' | 'ready' | 'served' | 'completed';
export type Priority = 'normal' | 'high' | 'critical';
export type TableStatus = 'free' | 'occupied' | 'ordering' | 'waiting' | 'pay';
export type BillStatus = 'pending' | 'paid' | 'cancelled';
export type PaymentMethod = 'cash' | 'card' | 'digital';
export type ProductCategory = 'Drinks' | 'Pastries' | 'Sandwiches' | 'Meals' | 'Desserts';

export interface OrderItem {
  id: string;
  productId: string;
  productName: string;
  quantity: number;
  price: number;
  status: 'pending' | 'preparing' | 'ready';
  notes?: string;
  prepTime: number;
}

export interface Order {
  id: string;
  tableId: string;
  tableNumber: number;
  clientName: string;
  waiterName: string;
  createdAt: Date;
  status: OrderStatus;
  priority: Priority;
  items: OrderItem[];
  specialInstructions?: string;
  estimatedPrepTime: number;
}

export interface Table {
  id: string;
  number: number;
  seats: number;
  guests: number;
  status: TableStatus;
  orderId?: string;
  occupiedSince?: Date;
}

export interface Product {
  id: string;
  name: string;
  category: ProductCategory;
  price: number;
  prepTime: number;
  available: boolean;
  emoji: string;
}

export interface Client {
  id: string;
  name: string;
  phone: string;
  email: string;
  visits: number;
  totalSpending: number;
  lastOrderDate: Date;
}

export interface Bill {
  id: string;
  tableId: string;
  tableNumber: number;
  clientName: string;
  orderId: string;
  total: number;
  paymentMethod: PaymentMethod;
  status: BillStatus;
  createdAt: Date;
}
