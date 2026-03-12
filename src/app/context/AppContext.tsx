import React, { createContext, useContext, useState, useCallback } from 'react';
import { Order, Table, Product, Client, Bill, OrderStatus, Priority, OrderItem } from '../types';
import {
  initialOrders,
  initialTables,
  initialProducts,
  initialClients,
  initialBills,
} from '../data/mockData';

interface AppContextType {
  orders: Order[];
  tables: Table[];
  products: Product[];
  clients: Client[];
  bills: Bill[];
  selectedOrderId: string | null;
  setSelectedOrderId: (id: string | null) => void;
  updateOrderStatus: (orderId: string, status: OrderStatus) => void;
  updateOrderPriority: (orderId: string, priority: Priority) => void;
  addItemToOrder: (orderId: string, item: OrderItem) => void;
  removeItemFromOrder: (orderId: string, itemId: string) => void;
  createOrder: (tableId: string, tableNumber: number, clientName: string, waiterName: string) => string;
  updateTableStatus: (tableId: string, updates: Partial<Table>) => void;
  updateProduct: (productId: string, updates: Partial<Product>) => void;
  addProduct: (product: Product) => void;
  updateBillStatus: (billId: string, updates: Partial<Bill>) => void;
  addBill: (bill: Bill) => void;
}

const AppContext = createContext<AppContextType | null>(null);

export function AppProvider({ children }: { children: React.ReactNode }) {
  const [orders, setOrders] = useState<Order[]>(initialOrders);
  const [tables, setTables] = useState<Table[]>(initialTables);
  const [products, setProducts] = useState<Product[]>(initialProducts);
  const [clients] = useState<Client[]>(initialClients);
  const [bills, setBills] = useState<Bill[]>(initialBills);
  const [selectedOrderId, setSelectedOrderId] = useState<string | null>('#1002');

  const updateOrderStatus = useCallback((orderId: string, status: OrderStatus) => {
    setOrders(prev =>
      prev.map(o => {
        if (o.id !== orderId) return o;
        const updated = { ...o, status };
        // Update item statuses based on order status
        if (status === 'preparing') {
          updated.items = o.items.map(item => ({ ...item, status: 'preparing' as const }));
        } else if (status === 'ready' || status === 'served') {
          updated.items = o.items.map(item => ({ ...item, status: 'ready' as const }));
        }
        return updated;
      })
    );
    // Sync table status
    setTables(prev =>
      prev.map(t => {
        if (t.orderId !== orderId) return t;
        const tableStatus =
          status === 'new' ? 'ordering' :
          status === 'sent' || status === 'preparing' ? 'waiting' :
          status === 'ready' ? 'waiting' :
          status === 'served' ? 'pay' :
          status === 'completed' ? 'free' : t.status;
        return { ...t, status: tableStatus };
      })
    );
  }, []);

  const updateOrderPriority = useCallback((orderId: string, priority: Priority) => {
    setOrders(prev => prev.map(o => o.id === orderId ? { ...o, priority } : o));
  }, []);

  const addItemToOrder = useCallback((orderId: string, item: OrderItem) => {
    setOrders(prev =>
      prev.map(o => {
        if (o.id !== orderId) return o;
        const existing = o.items.find(i => i.productId === item.productId);
        if (existing) {
          return { ...o, items: o.items.map(i => i.productId === item.productId ? { ...i, quantity: i.quantity + 1 } : i) };
        }
        return { ...o, items: [...o.items, item] };
      })
    );
  }, []);

  const removeItemFromOrder = useCallback((orderId: string, itemId: string) => {
    setOrders(prev =>
      prev.map(o => o.id === orderId ? { ...o, items: o.items.filter(i => i.id !== itemId) } : o)
    );
  }, []);

  const createOrder = useCallback((tableId: string, tableNumber: number, clientName: string, waiterName: string) => {
    const id = `#${1100 + Math.floor(Math.random() * 900)}`;
    const newOrder: Order = {
      id,
      tableId,
      tableNumber,
      clientName,
      waiterName,
      createdAt: new Date(),
      status: 'new',
      priority: 'normal',
      items: [],
      estimatedPrepTime: 0,
    };
    setOrders(prev => [newOrder, ...prev]);
    setTables(prev => prev.map(t => t.id === tableId ? { ...t, status: 'ordering', orderId: id, occupiedSince: new Date() } : t));
    return id;
  }, []);

  const updateTableStatus = useCallback((tableId: string, updates: Partial<Table>) => {
    setTables(prev => prev.map(t => t.id === tableId ? { ...t, ...updates } : t));
  }, []);

  const updateProduct = useCallback((productId: string, updates: Partial<Product>) => {
    setProducts(prev => prev.map(p => p.id === productId ? { ...p, ...updates } : p));
  }, []);

  const addProduct = useCallback((product: Product) => {
    setProducts(prev => [...prev, product]);
  }, []);

  const updateBillStatus = useCallback((billId: string, updates: Partial<Bill>) => {
    setBills(prev => prev.map(b => b.id === billId ? { ...b, ...updates } : b));
  }, []);

  const addBill = useCallback((bill: Bill) => {
    setBills(prev => [bill, ...prev]);
  }, []);

  return (
    <AppContext.Provider value={{
      orders, tables, products, clients, bills,
      selectedOrderId, setSelectedOrderId,
      updateOrderStatus, updateOrderPriority,
      addItemToOrder, removeItemFromOrder,
      createOrder, updateTableStatus,
      updateProduct, addProduct,
      updateBillStatus, addBill,
    }}>
      {children}
    </AppContext.Provider>
  );
}

export function useApp() {
  const ctx = useContext(AppContext);
  if (!ctx) throw new Error('useApp must be used within AppProvider');
  return ctx;
}
