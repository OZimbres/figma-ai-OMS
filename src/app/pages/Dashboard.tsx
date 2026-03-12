import { useMemo } from 'react';
import { useNavigate } from 'react-router';
import { useApp } from '../context/AppContext';
import { StatusBadge } from '../components/ui/StatusBadge';
import { OrderTimer } from '../components/ui/OrderTimer';
import {
  ClipboardList,
  ChefHat,
  CheckCircle2,
  TrendingUp,
  Users,
  Clock,
  ArrowRight,
  AlertTriangle,
} from 'lucide-react';
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, BarChart, Bar,
} from 'recharts';
import { revenueData } from '../data/mockData';

export function Dashboard() {
  const { orders, tables } = useApp();
  const navigate = useNavigate();

  const stats = useMemo(() => {
    const active    = orders.filter(o => ['new','sent','preparing'].includes(o.status));
    const preparing = orders.filter(o => o.status === 'preparing');
    const ready     = orders.filter(o => o.status === 'ready');
    const completed = orders.filter(o => o.status === 'completed');
    const todayRevenue = revenueData.reduce((s, d) => s + d.revenue, 0);
    const avgPrepTime = 14.2;
    return { active, preparing, ready, completed, todayRevenue, avgPrepTime };
  }, [orders]);

  const occupiedTables = tables.filter(t => t.status !== 'free');
  const criticalOrders = orders.filter(o => o.priority === 'critical' && o.status !== 'completed');

  return (
    <div className="p-8" style={{ minHeight: '100%' }}>
      {/* Header */}
      <div className="mb-8">
        <h1 style={{ fontSize: 26, fontWeight: 700, color: '#1F2937' }}>Dashboard</h1>
        <p style={{ fontSize: 14, color: '#6B7280', marginTop: 4 }}>
          Live operational overview · Thursday, March 12, 2026
        </p>
      </div>

      {/* KPI Widgets */}
      <div className="grid grid-cols-4 gap-5 mb-8">
        {[
          {
            label: 'Active Orders',
            value: stats.active.length,
            icon: ClipboardList,
            color: '#3B82F6',
            bg: '#EFF6FF',
            sub: `${stats.active.filter(o => o.priority !== 'normal').length} need attention`,
          },
          {
            label: 'Being Prepared',
            value: stats.preparing.length,
            icon: ChefHat,
            color: '#8B5CF6',
            bg: '#F5F3FF',
            sub: 'In kitchen now',
          },
          {
            label: 'Ready to Serve',
            value: stats.ready.length,
            icon: CheckCircle2,
            color: '#10B981',
            bg: '#ECFDF5',
            sub: 'Waiting for waiter',
          },
          {
            label: 'Completed Today',
            value: stats.completed.length + 18,
            icon: TrendingUp,
            color: '#F59E0B',
            bg: '#FFFBEB',
            sub: '+12% from yesterday',
          },
        ].map(stat => (
          <div
            key={stat.label}
            className="rounded-xl p-5"
            style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
          >
            <div className="flex items-start justify-between mb-4">
              <div>
                <p style={{ fontSize: 13, color: '#6B7280', fontWeight: 500 }}>{stat.label}</p>
                <p style={{ fontSize: 32, fontWeight: 700, color: '#1F2937', lineHeight: 1.2, marginTop: 4 }}>{stat.value}</p>
              </div>
              <div className="rounded-xl p-2.5" style={{ background: stat.bg }}>
                <stat.icon size={20} color={stat.color} />
              </div>
            </div>
            <p style={{ fontSize: 12, color: '#9CA3AF' }}>{stat.sub}</p>
          </div>
        ))}
      </div>

      {/* Revenue + Stats row */}
      <div className="grid grid-cols-3 gap-5 mb-8">
        {/* Revenue Chart */}
        <div
          className="col-span-2 rounded-xl p-5"
          style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
        >
          <div className="flex items-center justify-between mb-5">
            <h2 style={{ fontSize: 16, fontWeight: 600, color: '#1F2937' }}>Revenue Today</h2>
            <span style={{ fontSize: 22, fontWeight: 700, color: '#F59E0B' }}>
              €{stats.todayRevenue.toLocaleString('pt-PT', { minimumFractionDigits: 2 })}
            </span>
          </div>
          <ResponsiveContainer width="100%" height={180}>
            <LineChart data={revenueData}>
              <CartesianGrid strokeDasharray="3 3" stroke="#F3F4F6" />
              <XAxis dataKey="hour" tick={{ fontSize: 11, fill: '#9CA3AF' }} tickLine={false} axisLine={false} />
              <YAxis tick={{ fontSize: 11, fill: '#9CA3AF' }} tickLine={false} axisLine={false} tickFormatter={v => `€${v}`} />
              <Tooltip formatter={(v: number) => [`€${v.toFixed(2)}`, 'Revenue']} />
              <Line type="monotone" dataKey="revenue" stroke="#F59E0B" strokeWidth={2.5} dot={false} />
            </LineChart>
          </ResponsiveContainer>
        </div>

        {/* Quick Stats */}
        <div className="flex flex-col gap-4">
          <div
            className="rounded-xl p-5 flex-1"
            style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
          >
            <div className="flex items-center gap-2 mb-3">
              <Users size={16} color="#6B7280" />
              <span style={{ fontSize: 13, color: '#6B7280', fontWeight: 500 }}>Table Occupancy</span>
            </div>
            <div style={{ fontSize: 28, fontWeight: 700, color: '#1F2937' }}>
              {occupiedTables.length}/{tables.length}
            </div>
            <div className="mt-3 rounded-full overflow-hidden" style={{ height: 6, background: '#F3F4F6' }}>
              <div
                className="h-full rounded-full"
                style={{ width: `${(occupiedTables.length / tables.length) * 100}%`, background: '#F59E0B' }}
              />
            </div>
            <p style={{ fontSize: 12, color: '#9CA3AF', marginTop: 6 }}>
              {Math.round((occupiedTables.length / tables.length) * 100)}% occupied
            </p>
          </div>

          <div
            className="rounded-xl p-5 flex-1"
            style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
          >
            <div className="flex items-center gap-2 mb-3">
              <Clock size={16} color="#6B7280" />
              <span style={{ fontSize: 13, color: '#6B7280', fontWeight: 500 }}>Avg. Prep Time</span>
            </div>
            <div style={{ fontSize: 28, fontWeight: 700, color: '#1F2937' }}>
              {stats.avgPrepTime} <span style={{ fontSize: 16, color: '#6B7280', fontWeight: 400 }}>min</span>
            </div>
            <p style={{ fontSize: 12, color: '#10B981', marginTop: 6 }}>↓ 2.1 min from last week</p>
          </div>
        </div>
      </div>

      {/* Bottom row */}
      <div className="grid grid-cols-2 gap-5">
        {/* Kitchen Queue Preview */}
        <div
          className="rounded-xl p-5"
          style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
        >
          <div className="flex items-center justify-between mb-4">
            <h2 style={{ fontSize: 16, fontWeight: 600, color: '#1F2937' }}>Kitchen Queue</h2>
            <button
              onClick={() => navigate('/kitchen')}
              className="flex items-center gap-1 transition-colors hover:text-amber-600"
              style={{ fontSize: 12, color: '#6B7280' }}
            >
              View All <ArrowRight size={12} />
            </button>
          </div>
          <div className="flex gap-4 mb-4">
            {[
              { label: 'New',       count: orders.filter(o => o.status === 'new').length,       color: '#6B7280' },
              { label: 'Preparing', count: orders.filter(o => o.status === 'preparing').length, color: '#3B82F6' },
              { label: 'Ready',     count: orders.filter(o => o.status === 'ready').length,     color: '#10B981' },
            ].map(col => (
              <div key={col.label} className="flex-1 rounded-lg p-3 text-center" style={{ background: '#F9FAFB' }}>
                <div style={{ fontSize: 22, fontWeight: 700, color: col.color }}>{col.count}</div>
                <div style={{ fontSize: 11, color: '#9CA3AF', fontWeight: 500 }}>{col.label}</div>
              </div>
            ))}
          </div>
          <div className="flex flex-col gap-2">
            {orders
              .filter(o => ['new','sent','preparing','ready'].includes(o.status))
              .slice(0, 4)
              .map(order => (
                <div
                  key={order.id}
                  className="flex items-center justify-between rounded-lg px-3 py-2"
                  style={{ background: '#F9FAFB' }}
                >
                  <div className="flex items-center gap-2">
                    <span style={{ fontSize: 12, fontWeight: 600, color: '#374151' }}>{order.id}</span>
                    <span style={{ fontSize: 11, color: '#9CA3AF' }}>T{order.tableNumber}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <StatusBadge type="order" value={order.status} size="sm" />
                    <OrderTimer createdAt={order.createdAt} estimatedPrepTime={order.estimatedPrepTime} showIcon={false} />
                  </div>
                </div>
              ))}
          </div>
        </div>

        {/* Table Occupancy Map */}
        <div
          className="rounded-xl p-5"
          style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
        >
          <div className="flex items-center justify-between mb-4">
            <h2 style={{ fontSize: 16, fontWeight: 600, color: '#1F2937' }}>Table Overview</h2>
            <button
              onClick={() => navigate('/tables')}
              className="flex items-center gap-1 transition-colors hover:text-amber-600"
              style={{ fontSize: 12, color: '#6B7280' }}
            >
              View All <ArrowRight size={12} />
            </button>
          </div>

          {criticalOrders.length > 0 && (
            <div
              className="flex items-center gap-2 rounded-lg px-3 py-2 mb-4"
              style={{ background: '#FEF2F2', border: '1px solid #FCA5A5' }}
            >
              <AlertTriangle size={14} color="#DC2626" />
              <span style={{ fontSize: 12, color: '#DC2626', fontWeight: 500 }}>
                {criticalOrders.length} critical order{criticalOrders.length > 1 ? 's' : ''} need immediate attention
              </span>
            </div>
          )}

          <div className="grid grid-cols-5 gap-2">
            {tables.map(table => {
              const bgColors: Record<string, string> = {
                free: '#ECFDF5', occupied: '#EFF6FF', ordering: '#FFFBEB',
                waiting: '#FFF7ED', pay: '#F5F3FF',
              };
              const borderColors: Record<string, string> = {
                free: '#6EE7B7', occupied: '#93C5FD', ordering: '#FCD34D',
                waiting: '#FB923C', pay: '#C4B5FD',
              };
              const textColors: Record<string, string> = {
                free: '#059669', occupied: '#2563EB', ordering: '#D97706',
                waiting: '#EA580C', pay: '#7C3AED',
              };
              return (
                <button
                  key={table.id}
                  onClick={() => navigate('/tables')}
                  className="rounded-lg p-2 transition-all hover:scale-105 cursor-pointer"
                  style={{
                    background: bgColors[table.status],
                    border: `2px solid ${borderColors[table.status]}`,
                    textAlign: 'center',
                  }}
                >
                  <div style={{ fontSize: 14, fontWeight: 700, color: textColors[table.status] }}>
                    {table.number}
                  </div>
                  <div style={{ fontSize: 10, color: textColors[table.status], opacity: 0.8 }}>
                    {table.guests > 0 ? `${table.guests}p` : '—'}
                  </div>
                </button>
              );
            })}
          </div>

          <div className="flex flex-wrap gap-3 mt-4">
            {[
              { label: 'Free', color: '#6EE7B7', text: '#059669' },
              { label: 'Occupied', color: '#93C5FD', text: '#2563EB' },
              { label: 'Ordering', color: '#FCD34D', text: '#D97706' },
              { label: 'Waiting', color: '#FB923C', text: '#EA580C' },
              { label: 'Pay', color: '#C4B5FD', text: '#7C3AED' },
            ].map(item => (
              <div key={item.label} className="flex items-center gap-1.5">
                <div className="rounded-sm" style={{ width: 10, height: 10, background: item.color }} />
                <span style={{ fontSize: 11, color: '#6B7280' }}>{item.label}</span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
