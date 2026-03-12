import { useState } from 'react';
import { useApp } from '../context/AppContext';
import { StatusBadge } from '../components/ui/StatusBadge';
import { Table, TableStatus } from '../types';
import { Users, Clock, X, Edit2 } from 'lucide-react';
import { useNavigate } from 'react-router';

const TABLE_COLORS: Record<TableStatus, { bg: string; border: string; text: string; dot: string }> = {
  free:     { bg: '#ECFDF5', border: '#6EE7B7', text: '#059669', dot: '#10B981' },
  occupied: { bg: '#EFF6FF', border: '#93C5FD', text: '#2563EB', dot: '#3B82F6' },
  ordering: { bg: '#FFFBEB', border: '#FCD34D', text: '#D97706', dot: '#F59E0B' },
  waiting:  { bg: '#FFF7ED', border: '#FB923C', text: '#EA580C', dot: '#F97316' },
  pay:      { bg: '#F5F3FF', border: '#C4B5FD', text: '#7C3AED', dot: '#8B5CF6' },
};

function getElapsedMins(date: Date): number {
  return Math.floor((Date.now() - date.getTime()) / 60000);
}

interface TableCardProps {
  table: Table;
  onSelect: (t: Table) => void;
  isSelected: boolean;
}

function TableCard({ table, onSelect, isSelected }: TableCardProps) {
  const colors = TABLE_COLORS[table.status];
  const elapsed = table.occupiedSince ? getElapsedMins(table.occupiedSince) : null;

  return (
    <button
      onClick={() => onSelect(table)}
      className="relative rounded-2xl p-4 text-left transition-all hover:shadow-md"
      style={{
        background: colors.bg,
        border: `2px solid ${isSelected ? colors.dot : colors.border}`,
        boxShadow: isSelected ? `0 0 0 3px ${colors.dot}30` : '0 1px 3px rgba(0,0,0,0.06)',
        minHeight: 140,
      }}
    >
      {/* Table number */}
      <div className="flex items-start justify-between mb-3">
        <div
          className="flex items-center justify-center rounded-xl"
          style={{ width: 44, height: 44, background: colors.dot, color: 'white', fontSize: 18, fontWeight: 700 }}
        >
          {table.number}
        </div>
        <div className="rounded-full" style={{ width: 8, height: 8, background: colors.dot, marginTop: 4 }} />
      </div>

      {/* Guests */}
      <div className="flex items-center gap-1.5 mb-2">
        <Users size={13} color={colors.text} />
        <span style={{ fontSize: 13, color: colors.text, fontWeight: 600 }}>
          {table.guests > 0 ? `${table.guests} / ${table.seats}` : `0 / ${table.seats}`}
        </span>
      </div>

      {/* Elapsed Time */}
      {elapsed !== null && (
        <div className="flex items-center gap-1.5 mb-2">
          <Clock size={12} color={colors.text} />
          <span style={{ fontSize: 12, color: colors.text }}>
            {elapsed < 60 ? `${elapsed}m` : `${Math.floor(elapsed / 60)}h ${elapsed % 60}m`}
          </span>
        </div>
      )}

      {/* Status Badge */}
      <StatusBadge type="table" value={table.status} size="sm" />

      {/* Order indicator */}
      {table.orderId && (
        <div
          className="absolute top-2 right-2 rounded-full px-1.5 py-0.5"
          style={{ fontSize: 9, background: colors.dot, color: 'white', fontWeight: 700 }}
        >
          {table.orderId}
        </div>
      )}
    </button>
  );
}

export function Tables() {
  const { tables, orders, updateTableStatus } = useApp();
  const [selectedTable, setSelectedTable] = useState<Table | null>(null);
  const [filterStatus, setFilterStatus] = useState<string>('all');
  const [showEditModal, setShowEditModal] = useState(false);
  const [editGuests, setEditGuests] = useState('');
  const [editStatus, setEditStatus] = useState<TableStatus>('free');
  const navigate = useNavigate();

  const filteredTables = tables.filter(t =>
    filterStatus === 'all' || t.status === filterStatus
  );

  const stats = {
    free:     tables.filter(t => t.status === 'free').length,
    occupied: tables.filter(t => t.status !== 'free').length,
    paying:   tables.filter(t => t.status === 'pay').length,
  };

  const selectedOrder = selectedTable?.orderId
    ? orders.find(o => o.id === selectedTable.orderId)
    : null;

  const handleEdit = () => {
    if (!selectedTable) return;
    setEditGuests(String(selectedTable.guests));
    setEditStatus(selectedTable.status);
    setShowEditModal(true);
  };

  const handleSaveEdit = () => {
    if (!selectedTable) return;
    updateTableStatus(selectedTable.id, {
      guests: parseInt(editGuests) || 0,
      status: editStatus,
    });
    setSelectedTable(prev => prev ? { ...prev, guests: parseInt(editGuests) || 0, status: editStatus } : null);
    setShowEditModal(false);
  };

  return (
    <div className="flex h-full" style={{ background: '#F6F7F9' }}>
      {/* Main Content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        {/* Header */}
        <div
          className="flex items-center justify-between px-8 py-5 shrink-0"
          style={{ background: 'white', borderBottom: '1px solid #E5E7EB' }}
        >
          <div>
            <h1 style={{ fontSize: 26, fontWeight: 700, color: '#1F2937' }}>Tables</h1>
            <p style={{ fontSize: 13, color: '#6B7280', marginTop: 2 }}>
              {stats.occupied} occupied · {stats.free} free · {stats.paying} waiting to pay
            </p>
          </div>

          {/* Stats */}
          <div className="flex gap-4">
            {[
              { label: 'Free',     value: stats.free,     color: '#10B981' },
              { label: 'Occupied', value: stats.occupied,  color: '#3B82F6' },
              { label: 'Pay',      value: stats.paying,   color: '#8B5CF6' },
            ].map(s => (
              <div key={s.label} className="text-center">
                <div style={{ fontSize: 22, fontWeight: 700, color: s.color }}>{s.value}</div>
                <div style={{ fontSize: 11, color: '#9CA3AF' }}>{s.label}</div>
              </div>
            ))}
          </div>
        </div>

        {/* Filters */}
        <div className="px-8 py-3 flex gap-2" style={{ background: 'white', borderBottom: '1px solid #F3F4F6' }}>
          {['all', 'free', 'occupied', 'ordering', 'waiting', 'pay'].map(s => (
            <button
              key={s}
              onClick={() => setFilterStatus(s)}
              className="rounded-full px-3 py-1 capitalize transition-all"
              style={{
                fontSize: 12, fontWeight: 500,
                background: filterStatus === s ? '#F59E0B' : '#F3F4F6',
                color: filterStatus === s ? 'white' : '#6B7280',
              }}
            >
              {s === 'all' ? 'All Tables' : s === 'pay' ? 'Ready to Pay' : s === 'waiting' ? 'Waiting Food' : s.charAt(0).toUpperCase() + s.slice(1)}
            </button>
          ))}
        </div>

        {/* Table Grid */}
        <div className="flex-1 overflow-y-auto p-8">
          <div className="grid gap-4" style={{ gridTemplateColumns: 'repeat(auto-fill, minmax(160px, 1fr))' }}>
            {filteredTables.map(table => (
              <TableCard
                key={table.id}
                table={table}
                onSelect={setSelectedTable}
                isSelected={selectedTable?.id === table.id}
              />
            ))}
          </div>

          {/* Legend */}
          <div className="flex flex-wrap gap-4 mt-8 pt-6" style={{ borderTop: '1px solid #E5E7EB' }}>
            {Object.entries(TABLE_COLORS).map(([status, colors]) => (
              <div key={status} className="flex items-center gap-2">
                <div className="rounded-full" style={{ width: 10, height: 10, background: colors.dot }} />
                <span style={{ fontSize: 12, color: '#6B7280', textTransform: 'capitalize' }}>
                  {status === 'pay' ? 'Ready to Pay' : status === 'waiting' ? 'Waiting Food' : status}
                </span>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Side Panel */}
      {selectedTable && (
        <div
          className="flex flex-col shrink-0 overflow-y-auto"
          style={{ width: 300, background: 'white', borderLeft: '1px solid #E5E7EB' }}
        >
          <div className="p-5" style={{ borderBottom: '1px solid #E5E7EB' }}>
            <div className="flex items-center justify-between mb-3">
              <h2 style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>Table {selectedTable.number}</h2>
              <div className="flex items-center gap-1">
                <button
                  onClick={handleEdit}
                  className="rounded-lg p-1.5 hover:bg-gray-100 transition-colors"
                >
                  <Edit2 size={14} color="#6B7280" />
                </button>
                <button
                  onClick={() => setSelectedTable(null)}
                  className="rounded-lg p-1.5 hover:bg-gray-100 transition-colors"
                >
                  <X size={14} color="#6B7280" />
                </button>
              </div>
            </div>
            <StatusBadge type="table" value={selectedTable.status} />
          </div>

          <div className="p-5 flex flex-col gap-4">
            <div className="grid grid-cols-2 gap-3">
              <div className="rounded-xl p-3" style={{ background: '#F9FAFB' }}>
                <div style={{ fontSize: 11, color: '#9CA3AF', marginBottom: 2 }}>Guests</div>
                <div style={{ fontSize: 20, fontWeight: 700, color: '#1F2937' }}>
                  {selectedTable.guests}
                  <span style={{ fontSize: 14, color: '#9CA3AF', fontWeight: 400 }}>/{selectedTable.seats}</span>
                </div>
              </div>
              <div className="rounded-xl p-3" style={{ background: '#F9FAFB' }}>
                <div style={{ fontSize: 11, color: '#9CA3AF', marginBottom: 2 }}>Occupied</div>
                <div style={{ fontSize: 14, fontWeight: 600, color: '#1F2937' }}>
                  {selectedTable.occupiedSince
                    ? `${getElapsedMins(selectedTable.occupiedSince)}m ago`
                    : '—'
                  }
                </div>
              </div>
            </div>

            {selectedOrder ? (
              <div>
                <div style={{ fontSize: 12, fontWeight: 600, color: '#374151', marginBottom: 8 }}>Active Order</div>
                <div className="rounded-xl p-4" style={{ background: '#F9FAFB', border: '1px solid #E5E7EB' }}>
                  <div className="flex items-center justify-between mb-3">
                    <span style={{ fontSize: 14, fontWeight: 700, color: '#1F2937' }}>{selectedOrder.id}</span>
                    <StatusBadge type="order" value={selectedOrder.status} size="sm" />
                  </div>
                  <div style={{ fontSize: 12, color: '#6B7280', marginBottom: 8 }}>{selectedOrder.clientName}</div>
                  <div className="flex flex-col gap-1 mb-3">
                    {selectedOrder.items.map(item => (
                      <div key={item.id} style={{ fontSize: 12, color: '#374151' }}>
                        {item.quantity}× {item.productName}
                      </div>
                    ))}
                  </div>
                  <div className="flex items-center justify-between" style={{ borderTop: '1px solid #E5E7EB', paddingTop: 8 }}>
                    <span style={{ fontSize: 12, color: '#6B7280' }}>Total</span>
                    <span style={{ fontSize: 14, fontWeight: 700, color: '#F59E0B' }}>
                      €{selectedOrder.items.reduce((s, i) => s + i.price * i.quantity, 0).toFixed(2)}
                    </span>
                  </div>
                </div>
                <button
                  onClick={() => navigate('/orders')}
                  className="w-full mt-3 rounded-xl py-2.5 text-white transition-opacity hover:opacity-90"
                  style={{ background: '#F59E0B', fontSize: 13, fontWeight: 600 }}
                >
                  Open Order
                </button>
              </div>
            ) : (
              <div
                className="rounded-xl p-4 text-center"
                style={{ border: '2px dashed #E5E7EB' }}
              >
                <p style={{ fontSize: 13, color: '#9CA3AF' }}>No active order</p>
                {selectedTable.status === 'free' && (
                  <button
                    onClick={() => navigate('/orders')}
                    className="mt-2 rounded-lg px-3 py-1.5 text-white transition-opacity hover:opacity-90"
                    style={{ background: '#F59E0B', fontSize: 12, fontWeight: 600 }}
                  >
                    New Order
                  </button>
                )}
              </div>
            )}
          </div>
        </div>
      )}

      {/* Edit Modal */}
      {showEditModal && selectedTable && (
        <div className="fixed inset-0 flex items-center justify-center z-50" style={{ background: 'rgba(0,0,0,0.4)' }}>
          <div className="rounded-2xl p-6 w-full max-w-sm" style={{ background: 'white', boxShadow: '0 20px 60px rgba(0,0,0,0.2)' }}>
            <div className="flex items-center justify-between mb-5">
              <h3 style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>Edit Table {selectedTable.number}</h3>
              <button onClick={() => setShowEditModal(false)} className="rounded-lg p-1.5 hover:bg-gray-100">
                <X size={16} color="#6B7280" />
              </button>
            </div>
            <div className="flex flex-col gap-4">
              <div>
                <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 6 }}>Guests</label>
                <input
                  type="number"
                  value={editGuests}
                  onChange={e => setEditGuests(e.target.value)}
                  min="0"
                  max={selectedTable.seats}
                  className="w-full rounded-lg px-3 py-2.5 outline-none"
                  style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                />
              </div>
              <div>
                <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 6 }}>Status</label>
                <select
                  value={editStatus}
                  onChange={e => setEditStatus(e.target.value as TableStatus)}
                  className="w-full rounded-lg px-3 py-2.5 outline-none"
                  style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                >
                  <option value="free">Free</option>
                  <option value="occupied">Occupied</option>
                  <option value="ordering">Ordering</option>
                  <option value="waiting">Waiting for Food</option>
                  <option value="pay">Ready to Pay</option>
                </select>
              </div>
              <button
                onClick={handleSaveEdit}
                className="w-full rounded-xl py-3 text-white transition-opacity hover:opacity-90"
                style={{ background: '#F59E0B', fontSize: 14, fontWeight: 600 }}
              >
                Save Changes
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
