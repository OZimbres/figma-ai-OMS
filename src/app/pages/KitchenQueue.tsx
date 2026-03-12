import { useRef } from 'react';
import { DndProvider, useDrag, useDrop } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { useApp } from '../context/AppContext';
import { StatusBadge } from '../components/ui/StatusBadge';
import { OrderTimer } from '../components/ui/OrderTimer';
import { Order, OrderStatus } from '../types';
import { ChefHat, Clock, AlertCircle, GripVertical } from 'lucide-react';

const COLUMNS: { status: OrderStatus; label: string; color: string; bg: string; border: string }[] = [
  { status: 'new',       label: 'New Orders', color: '#6B7280', bg: '#F9FAFB', border: '#E5E7EB' },
  { status: 'preparing', label: 'Preparing',  color: '#2563EB', bg: '#EFF6FF', border: '#BFDBFE' },
  { status: 'ready',     label: 'Ready',      color: '#059669', bg: '#ECFDF5', border: '#6EE7B7' },
];

const DRAG_TYPE = 'KITCHEN_CARD';

interface KitchenCardProps {
  order: Order;
  onMove: (id: string, newStatus: OrderStatus) => void;
}

function KitchenCard({ order, onMove }: KitchenCardProps) {
  const ref = useRef<HTMLDivElement>(null);

  const [{ isDragging }, drag] = useDrag({
    type: DRAG_TYPE,
    item: { id: order.id },
    collect: monitor => ({ isDragging: monitor.isDragging() }),
  });

  drag(ref);

  const elapsed = Math.floor((Date.now() - order.createdAt.getTime()) / 60000);
  const isDelayed = elapsed > order.estimatedPrepTime;
  const isCritical = elapsed > order.estimatedPrepTime * 1.5 || order.priority === 'critical';

  return (
    <div
      ref={ref}
      className="rounded-xl p-4 cursor-grab active:cursor-grabbing transition-all"
      style={{
        background: 'white',
        border: `2px solid ${isCritical ? '#F87171' : order.priority === 'high' ? '#FCD34D' : '#E5E7EB'}`,
        boxShadow: isDragging ? '0 8px 24px rgba(0,0,0,0.15)' : '0 1px 3px rgba(0,0,0,0.04)',
        opacity: isDragging ? 0.6 : 1,
      }}
    >
      {/* Card Header */}
      <div className="flex items-center justify-between mb-3">
        <div className="flex items-center gap-2">
          <GripVertical size={14} color="#D1D5DB" />
          <span style={{ fontSize: 13, fontWeight: 700, color: '#1F2937' }}>{order.id}</span>
          {order.priority !== 'normal' && (
            <StatusBadge type="priority" value={order.priority} size="sm" />
          )}
        </div>
        <OrderTimer createdAt={order.createdAt} estimatedPrepTime={order.estimatedPrepTime} />
      </div>

      {/* Table & Client */}
      <div className="flex items-center gap-2 mb-3">
        <span
          className="rounded-lg px-2 py-0.5"
          style={{ fontSize: 11, fontWeight: 700, background: '#F59E0B', color: 'white' }}
        >
          T{order.tableNumber}
        </span>
        <span style={{ fontSize: 12, color: '#6B7280' }}>{order.clientName}</span>
        <span style={{ fontSize: 11, color: '#9CA3AF' }}>· {order.waiterName.split(' ')[0]}</span>
      </div>

      {/* Items */}
      <div className="flex flex-col gap-1 mb-3">
        {order.items.map(item => (
          <div key={item.id} className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <span
                className="rounded flex items-center justify-center shrink-0"
                style={{ width: 18, height: 18, background: '#F59E0B', fontSize: 10, color: 'white', fontWeight: 700 }}
              >
                {item.quantity}
              </span>
              <span style={{ fontSize: 12, color: '#374151' }}>{item.productName}</span>
            </div>
            <span style={{ fontSize: 10, color: '#9CA3AF' }}>~{item.prepTime}m</span>
          </div>
        ))}
      </div>

      {/* Special Instructions */}
      {order.specialInstructions && (
        <div
          className="flex items-start gap-1.5 rounded-lg px-2.5 py-2 mb-3"
          style={{ background: '#FFFBEB', border: '1px solid #FCD34D' }}
        >
          <AlertCircle size={11} color="#D97706" className="shrink-0 mt-0.5" />
          <span style={{ fontSize: 11, color: '#92400E', lineHeight: 1.4 }}>{order.specialInstructions}</span>
        </div>
      )}

      {/* Delay Warning */}
      {isDelayed && (
        <div
          className="flex items-center gap-1.5 rounded-lg px-2.5 py-1.5 mb-3"
          style={{ background: '#FEF2F2', border: '1px solid #FCA5A5' }}
        >
          <Clock size={11} color="#DC2626" />
          <span style={{ fontSize: 11, color: '#DC2626', fontWeight: 500 }}>Order delayed by {elapsed - order.estimatedPrepTime}min</span>
        </div>
      )}

      {/* Quick Action Buttons */}
      <div className="flex gap-1.5 flex-wrap">
        {COLUMNS.filter(c => c.status !== order.status).map(col => (
          <button
            key={col.status}
            onClick={() => onMove(order.id, col.status)}
            className="rounded-lg px-2.5 py-1 transition-all hover:opacity-80"
            style={{
              fontSize: 11, fontWeight: 500,
              background: col.bg,
              color: col.color,
              border: `1px solid ${col.border}`,
            }}
          >
            → {col.label === 'New Orders' ? 'New' : col.label}
          </button>
        ))}
      </div>
    </div>
  );
}

interface ColumnProps {
  status: OrderStatus;
  label: string;
  color: string;
  bg: string;
  border: string;
  orders: Order[];
  onMove: (id: string, newStatus: OrderStatus) => void;
}

function KitchenColumn({ status, label, color, bg, border, orders, onMove }: ColumnProps) {
  const ref = useRef<HTMLDivElement>(null);

  const [{ isOver }, drop] = useDrop({
    accept: DRAG_TYPE,
    drop: (item: { id: string }) => {
      onMove(item.id, status);
    },
    collect: monitor => ({ isOver: monitor.isOver() }),
  });

  drop(ref);

  const sortedOrders = [...orders].sort((a, b) => {
    const prio = { critical: 0, high: 1, normal: 2 };
    if (prio[a.priority] !== prio[b.priority]) return prio[a.priority] - prio[b.priority];
    return a.createdAt.getTime() - b.createdAt.getTime();
  });

  return (
    <div className="flex flex-col flex-1 min-w-0">
      {/* Column Header */}
      <div
        className="flex items-center justify-between rounded-xl px-4 py-3 mb-3"
        style={{ background: bg, border: `2px solid ${border}` }}
      >
        <div className="flex items-center gap-2">
          <div className="rounded-full" style={{ width: 8, height: 8, background: color }} />
          <span style={{ fontSize: 14, fontWeight: 700, color }}>{label}</span>
        </div>
        <span
          className="rounded-full px-2.5 py-0.5"
          style={{ fontSize: 12, fontWeight: 700, background: color, color: 'white' }}
        >
          {orders.length}
        </span>
      </div>

      {/* Cards Drop Zone */}
      <div
        ref={ref}
        className="flex flex-col gap-3 flex-1 rounded-xl p-2 transition-all overflow-y-auto"
        style={{
          minHeight: 200,
          background: isOver ? bg : 'transparent',
          border: isOver ? `2px dashed ${border}` : '2px dashed transparent',
        }}
      >
        {sortedOrders.map(order => (
          <KitchenCard key={order.id} order={order} onMove={onMove} />
        ))}
        {orders.length === 0 && (
          <div
            className="flex items-center justify-center rounded-xl py-12"
            style={{ border: '2px dashed #E5E7EB', color: '#D1D5DB', fontSize: 13 }}
          >
            {isOver ? 'Drop here' : 'No orders'}
          </div>
        )}
      </div>
    </div>
  );
}

export function KitchenQueue() {
  const { orders, updateOrderStatus } = useApp();

  const handleMove = (orderId: string, newStatus: OrderStatus) => {
    updateOrderStatus(orderId, newStatus);
  };

  const kitchenOrders = orders.filter(o => ['new', 'sent', 'preparing', 'ready'].includes(o.status));

  // Treat 'sent' as 'new' for kitchen view
  const getKitchenStatus = (order: Order): OrderStatus => {
    return order.status === 'sent' ? 'new' : order.status;
  };

  const totalOrders = kitchenOrders.length;
  const criticalCount = kitchenOrders.filter(o => o.priority === 'critical').length;

  return (
    <DndProvider backend={HTML5Backend}>
      <div className="flex flex-col h-full" style={{ background: '#F6F7F9' }}>
        {/* Header */}
        <div
          className="flex items-center justify-between px-8 py-5 shrink-0"
          style={{ background: 'white', borderBottom: '1px solid #E5E7EB' }}
        >
          <div className="flex items-center gap-3">
            <div className="rounded-xl p-2" style={{ background: '#FFFBEB' }}>
              <ChefHat size={20} color="#D97706" />
            </div>
            <div>
              <h1 style={{ fontSize: 22, fontWeight: 700, color: '#1F2937' }}>Kitchen Queue</h1>
              <p style={{ fontSize: 13, color: '#6B7280' }}>
                {totalOrders} active order{totalOrders !== 1 ? 's' : ''} · Drag cards to update status
              </p>
            </div>
          </div>
          <div className="flex items-center gap-3">
            {criticalCount > 0 && (
              <div
                className="flex items-center gap-2 rounded-xl px-4 py-2"
                style={{ background: '#FEF2F2', border: '1px solid #FCA5A5' }}
              >
                <AlertCircle size={15} color="#DC2626" />
                <span style={{ fontSize: 13, color: '#DC2626', fontWeight: 600 }}>
                  {criticalCount} Critical
                </span>
              </div>
            )}
            <div className="flex gap-4">
              {COLUMNS.map(col => (
                <div key={col.status} className="flex items-center gap-1.5">
                  <div className="rounded-full" style={{ width: 8, height: 8, background: col.color }} />
                  <span style={{ fontSize: 12, color: '#6B7280' }}>{col.label}</span>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Kanban Board */}
        <div className="flex gap-5 flex-1 overflow-hidden px-8 py-6">
          {COLUMNS.map(col => (
            <KitchenColumn
              key={col.status}
              {...col}
              orders={kitchenOrders.filter(o => getKitchenStatus(o) === col.status)}
              onMove={handleMove}
            />
          ))}
        </div>
      </div>
    </DndProvider>
  );
}
