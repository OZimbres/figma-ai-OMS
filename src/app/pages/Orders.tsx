import { useState, useMemo } from 'react';
import { useApp } from '../context/AppContext';
import { StatusBadge } from '../components/ui/StatusBadge';
import { OrderTimer } from '../components/ui/OrderTimer';
import { Order, OrderStatus, ProductCategory } from '../types';
import {
  Search, Plus, Send, ChefHat, CheckCircle2, Utensils, X, AlertCircle,
  Trash2, ClipboardList,
} from 'lucide-react';

const STATUS_FLOW: OrderStatus[] = ['new', 'sent', 'preparing', 'ready', 'served', 'completed'];

const priorityBorder: Record<string, string> = {
  normal:   'border-transparent',
  high:     'border-amber-400',
  critical: 'border-red-400',
};

const priorityBg: Record<string, string> = {
  normal:   'bg-white',
  high:     'bg-amber-50/40',
  critical: 'bg-red-50/40',
};

const categories: ProductCategory[] = ['Drinks', 'Pastries', 'Sandwiches', 'Meals', 'Desserts'];

export function Orders() {
  const { orders, products, selectedOrderId, setSelectedOrderId,
    updateOrderStatus, updateOrderPriority, addItemToOrder, removeItemFromOrder, createOrder } = useApp();
  const [search, setSearch] = useState('');
  const [filterStatus, setFilterStatus] = useState<string>('all');
  const [productSearch, setProductSearch] = useState('');
  const [productCategory, setProductCategory] = useState<string>('all');
  const [showNewOrderModal, setShowNewOrderModal] = useState(false);
  const [newOrderForm, setNewOrderForm] = useState({ tableNumber: '', clientName: '', waiterName: '' });

  const selectedOrder = useMemo(
    () => orders.find(o => o.id === selectedOrderId) ?? null,
    [orders, selectedOrderId]
  );

  const filteredOrders = useMemo(() => {
    return orders
      .filter(o => {
        if (filterStatus !== 'all' && o.status !== filterStatus) return false;
        if (search && !o.id.toLowerCase().includes(search.toLowerCase()) &&
            !o.clientName.toLowerCase().includes(search.toLowerCase()) &&
            !String(o.tableNumber).includes(search)) return false;
        return true;
      })
      .sort((a, b) => {
        const prio = { critical: 0, high: 1, normal: 2 };
        if (prio[a.priority] !== prio[b.priority]) return prio[a.priority] - prio[b.priority];
        return a.createdAt.getTime() - b.createdAt.getTime();
      });
  }, [orders, search, filterStatus]);

  const filteredProducts = useMemo(() => {
    return products.filter(p => {
      if (!p.available) return false;
      if (productCategory !== 'all' && p.category !== productCategory) return false;
      if (productSearch && !p.name.toLowerCase().includes(productSearch.toLowerCase())) return false;
      return true;
    });
  }, [products, productSearch, productCategory]);

  const nextStatus = (order: Order): OrderStatus | null => {
    const idx = STATUS_FLOW.indexOf(order.status);
    return idx < STATUS_FLOW.length - 1 ? STATUS_FLOW[idx + 1] : null;
  };

  const nextStatusLabel: Record<OrderStatus, string> = {
    new:       'Send to Kitchen',
    sent:      'Mark Preparing',
    preparing: 'Mark Ready',
    ready:     'Mark Served',
    served:    'Complete Order',
    completed: '',
  };

  const nextStatusIcon: Record<string, JSX.Element> = {
    new:       <Send size={14} />,
    sent:      <ChefHat size={14} />,
    preparing: <CheckCircle2 size={14} />,
    ready:     <Utensils size={14} />,
    served:    <CheckCircle2 size={14} />,
  };

  const handleAddProduct = (productId: string) => {
    if (!selectedOrderId) return;
    const product = products.find(p => p.id === productId);
    if (!product) return;
    addItemToOrder(selectedOrderId, {
      id: `item-${Date.now()}`,
      productId: product.id,
      productName: product.name,
      quantity: 1,
      price: product.price,
      status: 'pending',
      prepTime: product.prepTime,
    });
  };

  const handleCreateOrder = () => {
    if (!newOrderForm.tableNumber || !newOrderForm.clientName) return;
    const tableId = `t${newOrderForm.tableNumber}`;
    const id = createOrder(tableId, parseInt(newOrderForm.tableNumber), newOrderForm.clientName, newOrderForm.waiterName || 'Unassigned');
    setSelectedOrderId(id);
    setShowNewOrderModal(false);
    setNewOrderForm({ tableNumber: '', clientName: '', waiterName: '' });
  };

  const totalAmount = selectedOrder
    ? selectedOrder.items.reduce((s, i) => s + i.price * i.quantity, 0)
    : 0;

  return (
    <div className="flex h-full" style={{ minHeight: '100vh' }}>
      {/* Panel 1: Orders Queue */}
      <div
        className="flex flex-col shrink-0 overflow-hidden"
        style={{ width: 300, borderRight: '1px solid #E5E7EB', background: 'white' }}
      >
        {/* Header */}
        <div className="p-4" style={{ borderBottom: '1px solid #F3F4F6' }}>
          <div className="flex items-center justify-between mb-3">
            <h1 style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>Orders</h1>
            <button
              onClick={() => setShowNewOrderModal(true)}
              className="flex items-center gap-1 rounded-lg px-3 py-1.5 text-white transition-opacity hover:opacity-90"
              style={{ background: '#F59E0B', fontSize: 12, fontWeight: 600 }}
            >
              <Plus size={14} /> New
            </button>
          </div>
          {/* Search */}
          <div className="relative mb-2">
            <Search size={14} className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
            <input
              value={search}
              onChange={e => setSearch(e.target.value)}
              placeholder="Search orders..."
              className="w-full rounded-lg pl-8 pr-3 py-2 outline-none"
              style={{ background: '#F9FAFB', border: '1px solid #E5E7EB', fontSize: 13 }}
            />
          </div>
          {/* Filter */}
          <div className="flex gap-1.5 flex-wrap">
            {['all', 'new', 'sent', 'preparing', 'ready', 'served'].map(s => (
              <button
                key={s}
                onClick={() => setFilterStatus(s)}
                className="rounded-full px-2.5 py-0.5 capitalize transition-all"
                style={{
                  fontSize: 11,
                  fontWeight: 500,
                  background: filterStatus === s ? '#F59E0B' : '#F3F4F6',
                  color: filterStatus === s ? 'white' : '#6B7280',
                }}
              >
                {s}
              </button>
            ))}
          </div>
        </div>

        {/* Orders List */}
        <div className="flex-1 overflow-y-auto p-3 flex flex-col gap-2">
          {filteredOrders.map(order => (
            <button
              key={order.id}
              onClick={() => setSelectedOrderId(order.id)}
              className={`w-full text-left rounded-xl p-3.5 border-2 transition-all ${priorityBg[order.priority]} ${priorityBorder[order.priority]} ${
                selectedOrderId === order.id ? 'ring-2 ring-amber-400' : 'hover:shadow-sm'
              }`}
              style={{ border: `2px solid ${order.priority === 'critical' ? '#F87171' : order.priority === 'high' ? '#FCD34D' : '#E5E7EB'}` }}
            >
              <div className="flex items-start justify-between mb-2">
                <div className="flex items-center gap-1.5">
                  <span style={{ fontSize: 13, fontWeight: 700, color: '#1F2937' }}>{order.id}</span>
                  {order.priority !== 'normal' && (
                    <StatusBadge type="priority" value={order.priority} size="sm" />
                  )}
                </div>
                <OrderTimer createdAt={order.createdAt} estimatedPrepTime={order.estimatedPrepTime} />
              </div>
              <div className="flex items-center gap-1 mb-2">
                <span
                  className="rounded px-1.5 py-0.5"
                  style={{ fontSize: 11, background: '#F3F4F6', color: '#374151', fontWeight: 600 }}
                >
                  T{order.tableNumber}
                </span>
                <span style={{ fontSize: 12, color: '#374151' }}>{order.clientName}</span>
              </div>
              <div className="flex items-center justify-between">
                <span style={{ fontSize: 11, color: '#9CA3AF' }}>
                  {order.items.reduce((s, i) => s + i.quantity, 0)} items · {order.waiterName.split(' ')[0]}
                </span>
                <StatusBadge type="order" value={order.status} size="sm" />
              </div>
            </button>
          ))}
          {filteredOrders.length === 0 && (
            <div className="text-center py-12" style={{ color: '#9CA3AF', fontSize: 13 }}>
              No orders found
            </div>
          )}
        </div>
      </div>

      {/* Panel 2: Order Details */}
      <div
        className="flex flex-col flex-1 overflow-hidden"
        style={{ borderRight: '1px solid #E5E7EB', background: '#FAFAFA' }}
      >
        {selectedOrder ? (
          <>
            {/* Order Header */}
            <div
              className="p-5"
              style={{ background: 'white', borderBottom: '1px solid #E5E7EB' }}
            >
              <div className="flex items-start justify-between mb-3">
                <div>
                  <div className="flex items-center gap-2 mb-1">
                    <h2 style={{ fontSize: 20, fontWeight: 700, color: '#1F2937' }}>{selectedOrder.id}</h2>
                    <StatusBadge type="order" value={selectedOrder.status} />
                    {selectedOrder.priority !== 'normal' && (
                      <StatusBadge type="priority" value={selectedOrder.priority} />
                    )}
                  </div>
                  <div className="flex items-center gap-3" style={{ fontSize: 13, color: '#6B7280' }}>
                    <span>Table {selectedOrder.tableNumber}</span>
                    <span>·</span>
                    <span>{selectedOrder.clientName}</span>
                    <span>·</span>
                    <span>Waiter: {selectedOrder.waiterName}</span>
                  </div>
                </div>
                <div className="text-right">
                  <OrderTimer createdAt={selectedOrder.createdAt} estimatedPrepTime={selectedOrder.estimatedPrepTime} />
                  <div style={{ fontSize: 12, color: '#9CA3AF', marginTop: 4 }}>
                    Est. {selectedOrder.estimatedPrepTime}min prep
                  </div>
                </div>
              </div>

              {selectedOrder.specialInstructions && (
                <div
                  className="flex items-start gap-2 rounded-lg px-3 py-2 mt-2"
                  style={{ background: '#FFFBEB', border: '1px solid #FCD34D' }}
                >
                  <AlertCircle size={14} color="#D97706" className="mt-0.5 shrink-0" />
                  <span style={{ fontSize: 12, color: '#92400E' }}>{selectedOrder.specialInstructions}</span>
                </div>
              )}
            </div>

            {/* Items List */}
            <div className="flex-1 overflow-y-auto p-5">
              <div className="flex items-center justify-between mb-3">
                <h3 style={{ fontSize: 14, fontWeight: 600, color: '#374151' }}>
                  Order Items ({selectedOrder.items.reduce((s, i) => s + i.quantity, 0)})
                </h3>
              </div>
              <div className="flex flex-col gap-2 mb-4">
                {selectedOrder.items.map(item => (
                  <div
                    key={item.id}
                    className="flex items-center justify-between rounded-xl px-4 py-3"
                    style={{ background: 'white', border: '1px solid #E5E7EB' }}
                  >
                    <div className="flex items-center gap-3">
                      <span
                        className="rounded-lg flex items-center justify-center"
                        style={{ width: 28, height: 28, background: '#F59E0B', fontSize: 13, color: 'white', fontWeight: 700 }}
                      >
                        {item.quantity}
                      </span>
                      <div>
                        <div style={{ fontSize: 13, fontWeight: 500, color: '#1F2937' }}>{item.productName}</div>
                        <div style={{ fontSize: 11, color: '#9CA3AF' }}>~{item.prepTime}min · {item.notes || 'No notes'}</div>
                      </div>
                    </div>
                    <div className="flex items-center gap-3">
                      <div>
                        <div style={{ fontSize: 13, fontWeight: 600, color: '#1F2937', textAlign: 'right' }}>
                          €{(item.price * item.quantity).toFixed(2)}
                        </div>
                        <div style={{ fontSize: 11, color: '#9CA3AF', textAlign: 'right' }}>€{item.price.toFixed(2)} ea</div>
                      </div>
                      <div>
                        <span
                          className={`inline-block rounded-full px-2 py-0.5 text-[10px] font-medium ${
                            item.status === 'ready' ? 'bg-green-100 text-green-700' :
                            item.status === 'preparing' ? 'bg-blue-100 text-blue-700' : 'bg-gray-100 text-gray-600'
                          }`}
                        >
                          {item.status}
                        </span>
                      </div>
                      {['new','sent','preparing'].includes(selectedOrder.status) && (
                        <button
                          onClick={() => removeItemFromOrder(selectedOrder.id, item.id)}
                          className="rounded-lg p-1.5 transition-colors hover:bg-red-50"
                        >
                          <Trash2 size={13} color="#EF4444" />
                        </button>
                      )}
                    </div>
                  </div>
                ))}

                {selectedOrder.items.length === 0 && (
                  <div
                    className="text-center py-8 rounded-xl"
                    style={{ border: '2px dashed #E5E7EB', color: '#9CA3AF', fontSize: 13 }}
                  >
                    No items yet. Select products from the right panel.
                  </div>
                )}
              </div>

              {/* Total */}
              <div
                className="rounded-xl px-4 py-3 flex items-center justify-between"
                style={{ background: '#1C2333' }}
              >
                <span style={{ fontSize: 14, color: '#9CA3AF' }}>Total Amount</span>
                <span style={{ fontSize: 20, fontWeight: 700, color: '#F59E0B' }}>
                  €{totalAmount.toFixed(2)}
                </span>
              </div>
            </div>

            {/* Action Buttons */}
            <div
              className="p-4 flex flex-col gap-2"
              style={{ background: 'white', borderTop: '1px solid #E5E7EB' }}
            >
              {/* Priority */}
              <div className="flex gap-2 mb-1">
                <span style={{ fontSize: 12, color: '#6B7280', alignSelf: 'center' }}>Priority:</span>
                {(['normal','high','critical'] as const).map(p => (
                  <button
                    key={p}
                    onClick={() => updateOrderPriority(selectedOrder.id, p)}
                    className="rounded-full px-2.5 py-0.5 capitalize transition-all"
                    style={{
                      fontSize: 11, fontWeight: 500,
                      background: selectedOrder.priority === p
                        ? p === 'critical' ? '#FEE2E2' : p === 'high' ? '#FEF3C7' : '#F3F4F6'
                        : '#F9FAFB',
                      color: selectedOrder.priority === p
                        ? p === 'critical' ? '#DC2626' : p === 'high' ? '#D97706' : '#374151'
                        : '#9CA3AF',
                      border: `1px solid ${selectedOrder.priority === p
                        ? p === 'critical' ? '#FCA5A5' : p === 'high' ? '#FCD34D' : '#D1D5DB'
                        : '#E5E7EB'}`,
                    }}
                  >
                    {p}
                  </button>
                ))}
              </div>
              {/* Next status button */}
              {nextStatus(selectedOrder) && (
                <button
                  onClick={() => updateOrderStatus(selectedOrder.id, nextStatus(selectedOrder)!)}
                  className="flex items-center justify-center gap-2 rounded-xl py-3 text-white transition-opacity hover:opacity-90"
                  style={{ background: '#F59E0B', fontSize: 14, fontWeight: 600 }}
                >
                  {nextStatusIcon[selectedOrder.status]}
                  {nextStatusLabel[selectedOrder.status]}
                </button>
              )}
              {selectedOrder.status === 'served' && (
                <button
                  onClick={() => updateOrderStatus(selectedOrder.id, 'completed')}
                  className="flex items-center justify-center gap-2 rounded-xl py-3 transition-all"
                  style={{ background: '#F3F4F6', fontSize: 14, fontWeight: 600, color: '#374151' }}
                >
                  <CheckCircle2 size={14} /> Complete Order
                </button>
              )}
            </div>
          </>
        ) : (
          <div className="flex-1 flex items-center justify-center flex-col gap-3">
            <div className="rounded-full p-6" style={{ background: '#F3F4F6' }}>
              <ClipboardList size={32} color="#D1D5DB" />
            </div>
            <p style={{ fontSize: 14, color: '#9CA3AF' }}>Select an order to view details</p>
          </div>
        )}
      </div>

      {/* Panel 3: Product Selection */}
      <div
        className="flex flex-col shrink-0 overflow-hidden"
        style={{ width: 320, background: 'white' }}
      >
        <div className="p-4" style={{ borderBottom: '1px solid #F3F4F6' }}>
          <h3 style={{ fontSize: 16, fontWeight: 700, color: '#1F2937', marginBottom: 12 }}>Add Products</h3>
          {/* Product Search */}
          <div className="relative mb-3">
            <Search size={14} className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
            <input
              value={productSearch}
              onChange={e => setProductSearch(e.target.value)}
              placeholder="Search products..."
              className="w-full rounded-lg pl-8 pr-3 py-2 outline-none"
              style={{ background: '#F9FAFB', border: '1px solid #E5E7EB', fontSize: 13 }}
            />
          </div>
          {/* Category Filter */}
          <div className="flex flex-wrap gap-1.5">
            {['all', ...categories].map(cat => (
              <button
                key={cat}
                onClick={() => setProductCategory(cat)}
                className="rounded-full px-2.5 py-0.5 capitalize transition-all"
                style={{
                  fontSize: 11,
                  fontWeight: 500,
                  background: productCategory === cat ? '#F59E0B' : '#F3F4F6',
                  color: productCategory === cat ? 'white' : '#6B7280',
                }}
              >
                {cat}
              </button>
            ))}
          </div>
        </div>

        {/* Product Cards */}
        <div className="flex-1 overflow-y-auto p-3">
          {categories
            .filter(cat => productCategory === 'all' || cat === productCategory)
            .map(cat => {
              const catProducts = filteredProducts.filter(p => p.category === cat);
              if (catProducts.length === 0) return null;
              return (
                <div key={cat} className="mb-4">
                  <div
                    className="px-1 mb-2"
                    style={{ fontSize: 11, fontWeight: 600, color: '#9CA3AF', textTransform: 'uppercase', letterSpacing: '0.05em' }}
                  >
                    {cat}
                  </div>
                  <div className="grid grid-cols-2 gap-2">
                    {catProducts.map(product => (
                      <button
                        key={product.id}
                        onClick={() => handleAddProduct(product.id)}
                        disabled={!selectedOrderId || !['new','sent','preparing'].includes(selectedOrder?.status ?? '')}
                        className="rounded-xl p-3 text-left transition-all hover:shadow-md active:scale-95 disabled:opacity-40 disabled:cursor-not-allowed"
                        style={{ background: '#F9FAFB', border: '1px solid #E5E7EB' }}
                      >
                        <div style={{ fontSize: 22, marginBottom: 4 }}>{product.emoji}</div>
                        <div style={{ fontSize: 12, fontWeight: 600, color: '#1F2937', lineHeight: 1.3 }}>{product.name}</div>
                        <div style={{ fontSize: 11, color: '#F59E0B', fontWeight: 700, marginTop: 4 }}>
                          €{product.price.toFixed(2)}
                        </div>
                        <div style={{ fontSize: 10, color: '#9CA3AF', marginTop: 2 }}>~{product.prepTime}min</div>
                      </button>
                    ))}
                  </div>
                </div>
              );
            })}
        </div>
      </div>

      {/* New Order Modal */}
      {showNewOrderModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50" style={{ background: 'rgba(0,0,0,0.4)' }}>
          <div className="rounded-2xl p-6 w-full max-w-sm" style={{ background: 'white', boxShadow: '0 20px 60px rgba(0,0,0,0.2)' }}>
            <div className="flex items-center justify-between mb-5">
              <h3 style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>New Order</h3>
              <button onClick={() => setShowNewOrderModal(false)} className="rounded-lg p-1.5 hover:bg-gray-100">
                <X size={16} color="#6B7280" />
              </button>
            </div>
            <div className="flex flex-col gap-3">
              <div>
                <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 4 }}>
                  Table Number *
                </label>
                <input
                  type="number"
                  value={newOrderForm.tableNumber}
                  onChange={e => setNewOrderForm(f => ({ ...f, tableNumber: e.target.value }))}
                  placeholder="e.g. 4"
                  className="w-full rounded-lg px-3 py-2.5 outline-none"
                  style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                />
              </div>
              <div>
                <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 4 }}>
                  Client Name *
                </label>
                <input
                  value={newOrderForm.clientName}
                  onChange={e => setNewOrderForm(f => ({ ...f, clientName: e.target.value }))}
                  placeholder="e.g. Maria Santos"
                  className="w-full rounded-lg px-3 py-2.5 outline-none"
                  style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                />
              </div>
              <div>
                <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 4 }}>
                  Waiter Name
                </label>
                <input
                  value={newOrderForm.waiterName}
                  onChange={e => setNewOrderForm(f => ({ ...f, waiterName: e.target.value }))}
                  placeholder="e.g. Lucas Ferreira"
                  className="w-full rounded-lg px-3 py-2.5 outline-none"
                  style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                />
              </div>
              <button
                onClick={handleCreateOrder}
                disabled={!newOrderForm.tableNumber || !newOrderForm.clientName}
                className="w-full rounded-xl py-3 text-white transition-opacity hover:opacity-90 disabled:opacity-40 mt-2"
                style={{ background: '#F59E0B', fontSize: 14, fontWeight: 600 }}
              >
                Create Order
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}