import { Order, Table, Product, Client, Bill } from '../types';

const now = new Date();
const minsAgo = (m: number) => new Date(now.getTime() - m * 60 * 1000);

export const initialProducts: Product[] = [
  // Drinks
  { id: 'p1',  name: 'Espresso',          category: 'Drinks',     price: 2.50,  prepTime: 2,  available: true,  emoji: '☕' },
  { id: 'p2',  name: 'Cappuccino',         category: 'Drinks',     price: 3.50,  prepTime: 3,  available: true,  emoji: '☕' },
  { id: 'p3',  name: 'Orange Juice',       category: 'Drinks',     price: 4.00,  prepTime: 2,  available: true,  emoji: '🍊' },
  { id: 'p4',  name: 'Lemon Tea',          category: 'Drinks',     price: 3.00,  prepTime: 3,  available: true,  emoji: '🍵' },
  { id: 'p5',  name: 'Sparkling Water',    category: 'Drinks',     price: 2.00,  prepTime: 1,  available: true,  emoji: '💧' },
  // Pastries
  { id: 'p6',  name: 'Croissant',          category: 'Pastries',   price: 3.50,  prepTime: 5,  available: true,  emoji: '🥐' },
  { id: 'p7',  name: 'Blueberry Muffin',   category: 'Pastries',   price: 3.00,  prepTime: 4,  available: true,  emoji: '🫐' },
  { id: 'p8',  name: 'Cinnamon Danish',    category: 'Pastries',   price: 4.00,  prepTime: 5,  available: true,  emoji: '🍞' },
  { id: 'p9',  name: 'Cinnamon Roll',      category: 'Pastries',   price: 4.50,  prepTime: 6,  available: false, emoji: '🌀' },
  // Sandwiches
  { id: 'p10', name: 'Club Sandwich',      category: 'Sandwiches', price: 9.50,  prepTime: 8,  available: true,  emoji: '🥪' },
  { id: 'p11', name: 'BLT Sandwich',       category: 'Sandwiches', price: 8.50,  prepTime: 7,  available: true,  emoji: '🥓' },
  { id: 'p12', name: 'Veggie Wrap',        category: 'Sandwiches', price: 8.00,  prepTime: 7,  available: true,  emoji: '🌯' },
  { id: 'p13', name: 'Tuna Melt',          category: 'Sandwiches', price: 9.00,  prepTime: 9,  available: true,  emoji: '🐟' },
  // Meals
  { id: 'p14', name: 'Grilled Chicken',    category: 'Meals',      price: 14.50, prepTime: 18, available: true,  emoji: '🍗' },
  { id: 'p15', name: 'Pasta Carbonara',    category: 'Meals',      price: 13.00, prepTime: 15, available: true,  emoji: '🍝' },
  { id: 'p16', name: 'Caesar Salad',       category: 'Meals',      price: 11.50, prepTime: 8,  available: true,  emoji: '🥗' },
  { id: 'p17', name: 'Beef Burger',        category: 'Meals',      price: 15.00, prepTime: 20, available: true,  emoji: '🍔' },
  { id: 'p18', name: 'Margherita Pizza',   category: 'Meals',      price: 12.00, prepTime: 22, available: true,  emoji: '🍕' },
  // Desserts
  { id: 'p19', name: 'Cheesecake',         category: 'Desserts',   price: 6.50,  prepTime: 3,  available: true,  emoji: '🍰' },
  { id: 'p20', name: 'Tiramisu',           category: 'Desserts',   price: 7.00,  prepTime: 3,  available: true,  emoji: '🍮' },
  { id: 'p21', name: 'Chocolate Brownie',  category: 'Desserts',   price: 5.50,  prepTime: 4,  available: true,  emoji: '🍫' },
  { id: 'p22', name: 'Vanilla Ice Cream',  category: 'Desserts',   price: 5.00,  prepTime: 2,  available: true,  emoji: '🍦' },
];

export const initialOrders: Order[] = [
  {
    id: '#1001',
    tableId: 't3',
    tableNumber: 3,
    clientName: 'Maria Santos',
    waiterName: 'Lucas Ferreira',
    createdAt: minsAgo(5),
    status: 'new',
    priority: 'normal',
    estimatedPrepTime: 10,
    items: [
      { id: 'i1', productId: 'p2', productName: 'Cappuccino',       quantity: 2, price: 3.50, status: 'pending', prepTime: 3 },
      { id: 'i2', productId: 'p6', productName: 'Croissant',        quantity: 2, price: 3.50, status: 'pending', prepTime: 5 },
    ],
  },
  {
    id: '#1002',
    tableId: 't5',
    tableNumber: 5,
    clientName: 'Carlos Lima',
    waiterName: 'Ana Rodrigues',
    createdAt: minsAgo(18),
    status: 'preparing',
    priority: 'high',
    estimatedPrepTime: 22,
    specialInstructions: 'No onions on the burger. Allergy to shellfish.',
    items: [
      { id: 'i3', productId: 'p17', productName: 'Beef Burger',     quantity: 1, price: 15.00, status: 'preparing', prepTime: 20 },
      { id: 'i4', productId: 'p3',  productName: 'Orange Juice',    quantity: 2, price: 4.00,  status: 'ready',     prepTime: 2 },
      { id: 'i5', productId: 'p19', productName: 'Cheesecake',      quantity: 1, price: 6.50,  status: 'pending',   prepTime: 3 },
    ],
  },
  {
    id: '#1003',
    tableId: 't1',
    tableNumber: 1,
    clientName: 'Ana Costa',
    waiterName: 'Lucas Ferreira',
    createdAt: minsAgo(32),
    status: 'ready',
    priority: 'critical',
    estimatedPrepTime: 25,
    items: [
      { id: 'i6',  productId: 'p14', productName: 'Grilled Chicken',  quantity: 2, price: 14.50, status: 'ready', prepTime: 18 },
      { id: 'i7',  productId: 'p16', productName: 'Caesar Salad',     quantity: 1, price: 11.50, status: 'ready', prepTime: 8 },
      { id: 'i8',  productId: 'p1',  productName: 'Espresso',         quantity: 2, price: 2.50,  status: 'ready', prepTime: 2 },
      { id: 'i9',  productId: 'p20', productName: 'Tiramisu',         quantity: 2, price: 7.00,  status: 'ready', prepTime: 3 },
    ],
  },
  {
    id: '#1004',
    tableId: 't7',
    tableNumber: 7,
    clientName: 'João Pereira',
    waiterName: 'Ana Rodrigues',
    createdAt: minsAgo(9),
    status: 'sent',
    priority: 'normal',
    estimatedPrepTime: 15,
    items: [
      { id: 'i10', productId: 'p15', productName: 'Pasta Carbonara', quantity: 1, price: 13.00, status: 'pending', prepTime: 15 },
      { id: 'i11', productId: 'p4',  productName: 'Lemon Tea',       quantity: 1, price: 3.00,  status: 'pending', prepTime: 3 },
    ],
  },
  {
    id: '#1005',
    tableId: 't2',
    tableNumber: 2,
    clientName: 'Sofia Alves',
    waiterName: 'Lucas Ferreira',
    createdAt: minsAgo(45),
    status: 'served',
    priority: 'normal',
    estimatedPrepTime: 20,
    items: [
      { id: 'i12', productId: 'p18', productName: 'Margherita Pizza', quantity: 1, price: 12.00, status: 'ready', prepTime: 22 },
      { id: 'i13', productId: 'p2',  productName: 'Cappuccino',       quantity: 2, price: 3.50,  status: 'ready', prepTime: 3 },
      { id: 'i14', productId: 'p21', productName: 'Chocolate Brownie',quantity: 1, price: 5.50,  status: 'ready', prepTime: 4 },
    ],
  },
  {
    id: '#1006',
    tableId: 't6',
    tableNumber: 6,
    clientName: 'Ricardo Nunes',
    waiterName: 'Ana Rodrigues',
    createdAt: minsAgo(3),
    status: 'new',
    priority: 'high',
    estimatedPrepTime: 9,
    specialInstructions: 'VIP client — fast service please.',
    items: [
      { id: 'i15', productId: 'p10', productName: 'Club Sandwich',    quantity: 2, price: 9.50, status: 'pending', prepTime: 8 },
      { id: 'i16', productId: 'p5',  productName: 'Sparkling Water',  quantity: 2, price: 2.00, status: 'pending', prepTime: 1 },
      { id: 'i17', productId: 'p22', productName: 'Vanilla Ice Cream',quantity: 1, price: 5.00, status: 'pending', prepTime: 2 },
    ],
  },
  {
    id: '#1007',
    tableId: 't4',
    tableNumber: 4,
    clientName: 'Beatriz Moura',
    waiterName: 'Lucas Ferreira',
    createdAt: minsAgo(55),
    status: 'completed',
    priority: 'normal',
    estimatedPrepTime: 12,
    items: [
      { id: 'i18', productId: 'p11', productName: 'BLT Sandwich', quantity: 2, price: 8.50, status: 'ready', prepTime: 7 },
      { id: 'i19', productId: 'p3',  productName: 'Orange Juice', quantity: 2, price: 4.00, status: 'ready', prepTime: 2 },
    ],
  },
];

export const initialTables: Table[] = [
  { id: 't1', number: 1, seats: 4, guests: 4, status: 'waiting',  orderId: '#1003', occupiedSince: minsAgo(35) },
  { id: 't2', number: 2, seats: 2, guests: 2, status: 'pay',      orderId: '#1005', occupiedSince: minsAgo(50) },
  { id: 't3', number: 3, seats: 4, guests: 3, status: 'ordering', orderId: '#1001', occupiedSince: minsAgo(10) },
  { id: 't4', number: 4, seats: 6, guests: 0, status: 'free' },
  { id: 't5', number: 5, seats: 4, guests: 4, status: 'waiting',  orderId: '#1002', occupiedSince: minsAgo(22) },
  { id: 't6', number: 6, seats: 2, guests: 2, status: 'occupied', orderId: '#1006', occupiedSince: minsAgo(8) },
  { id: 't7', number: 7, seats: 4, guests: 2, status: 'occupied', orderId: '#1004', occupiedSince: minsAgo(12) },
  { id: 't8', number: 8, seats: 8, guests: 0, status: 'free' },
  { id: 't9', number: 9, seats: 2, guests: 0, status: 'free' },
  { id: 't10', number: 10, seats: 6, guests: 5, status: 'pay', occupiedSince: minsAgo(70) },
];

export const initialClients: Client[] = [
  { id: 'c1', name: 'Maria Santos',    phone: '+351 912 345 678', email: 'maria.santos@email.com',    visits: 24, totalSpending: 487.50, lastOrderDate: minsAgo(5) },
  { id: 'c2', name: 'Carlos Lima',     phone: '+351 913 456 789', email: 'carlos.lima@email.com',     visits: 8,  totalSpending: 156.00, lastOrderDate: minsAgo(18) },
  { id: 'c3', name: 'Ana Costa',       phone: '+351 914 567 890', email: 'ana.costa@email.com',       visits: 41, totalSpending: 892.00, lastOrderDate: minsAgo(32) },
  { id: 'c4', name: 'João Pereira',    phone: '+351 915 678 901', email: 'joao.pereira@email.com',    visits: 15, totalSpending: 302.50, lastOrderDate: minsAgo(9) },
  { id: 'c5', name: 'Sofia Alves',     phone: '+351 916 789 012', email: 'sofia.alves@email.com',     visits: 33, totalSpending: 678.00, lastOrderDate: minsAgo(45) },
  { id: 'c6', name: 'Ricardo Nunes',   phone: '+351 917 890 123', email: 'ricardo.nunes@email.com',   visits: 62, totalSpending: 1340.00,lastOrderDate: minsAgo(3) },
  { id: 'c7', name: 'Beatriz Moura',   phone: '+351 918 901 234', email: 'beatriz.moura@email.com',   visits: 19, totalSpending: 398.00, lastOrderDate: minsAgo(55) },
  { id: 'c8', name: 'Tiago Ferreira',  phone: '+351 919 012 345', email: 'tiago.ferreira@email.com',  visits: 5,  totalSpending: 89.50,  lastOrderDate: new Date('2026-03-05') },
  { id: 'c9', name: 'Inês Rodrigues',  phone: '+351 920 123 456', email: 'ines.rodrigues@email.com',  visits: 28, totalSpending: 563.00, lastOrderDate: new Date('2026-03-10') },
  { id: 'c10', name: 'Diogo Carvalho', phone: '+351 921 234 567', email: 'diogo.carvalho@email.com',  visits: 11, totalSpending: 224.50, lastOrderDate: new Date('2026-03-08') },
];

export const initialBills: Bill[] = [
  { id: 'b1', tableId: 't2',  tableNumber: 2,  clientName: 'Sofia Alves',    orderId: '#1005', total: 26.50, paymentMethod: 'card',    status: 'pending', createdAt: minsAgo(5) },
  { id: 'b2', tableId: 't10', tableNumber: 10, clientName: 'Diogo Carvalho', orderId: '#998',  total: 89.00, paymentMethod: 'cash',    status: 'pending', createdAt: minsAgo(15) },
  { id: 'b3', tableId: 't4',  tableNumber: 4,  clientName: 'Beatriz Moura',  orderId: '#1007', total: 42.00, paymentMethod: 'digital', status: 'paid',    createdAt: minsAgo(55) },
  { id: 'b4', tableId: 't6',  tableNumber: 6,  clientName: 'Inês Rodrigues', orderId: '#990',  total: 31.50, paymentMethod: 'card',    status: 'paid',    createdAt: new Date('2026-03-12T10:30:00') },
  { id: 'b5', tableId: 't9',  tableNumber: 9,  clientName: 'Tiago Ferreira', orderId: '#985',  total: 15.00, paymentMethod: 'cash',    status: 'paid',    createdAt: new Date('2026-03-12T09:45:00') },
  { id: 'b6', tableId: 't3',  tableNumber: 3,  clientName: 'Carlos Lima',    orderId: '#980',  total: 22.00, paymentMethod: 'card',    status: 'cancelled', createdAt: new Date('2026-03-12T09:00:00') },
  { id: 'b7', tableId: 't8',  tableNumber: 8,  clientName: 'Ana Costa',      orderId: '#975',  total: 67.50, paymentMethod: 'digital', status: 'paid',    createdAt: new Date('2026-03-12T08:30:00') },
];

export const revenueData = [
  { hour: '08:00', orders: 4,  revenue: 48.50 },
  { hour: '09:00', orders: 8,  revenue: 112.00 },
  { hour: '10:00', orders: 12, revenue: 187.50 },
  { hour: '11:00', orders: 15, revenue: 234.00 },
  { hour: '12:00', orders: 28, revenue: 456.00 },
  { hour: '13:00', orders: 32, revenue: 521.50 },
  { hour: '14:00', orders: 22, revenue: 348.00 },
  { hour: '15:00', orders: 14, revenue: 198.50 },
  { hour: '16:00', orders: 10, revenue: 142.00 },
  { hour: '17:00', orders: 18, revenue: 267.50 },
];

export const topProducts = [
  { name: 'Cappuccino',      sales: 48, revenue: 168 },
  { name: 'Club Sandwich',   sales: 32, revenue: 304 },
  { name: 'Beef Burger',     sales: 29, revenue: 435 },
  { name: 'Caesar Salad',    sales: 25, revenue: 287.50 },
  { name: 'Cheesecake',      sales: 22, revenue: 143 },
  { name: 'Pasta Carbonara', sales: 20, revenue: 260 },
];

export const prepTimeData = [
  { name: 'Drinks',     avg: 2.5 },
  { name: 'Pastries',   avg: 5.0 },
  { name: 'Sandwiches', avg: 7.8 },
  { name: 'Meals',      avg: 16.6 },
  { name: 'Desserts',   avg: 3.1 },
];
