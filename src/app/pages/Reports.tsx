import { useState } from 'react';
import {
  LineChart, Line, BarChart, Bar, PieChart, Pie, Cell,
  XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer,
  Legend, RadarChart, Radar, PolarGrid, PolarAngleAxis,
} from 'recharts';
import { revenueData, topProducts, prepTimeData } from '../data/mockData';
import { TrendingUp, ShoppingBag, Clock, Users, BarChart3, Calendar } from 'lucide-react';

const staffPerformance = [
  { staff: 'Lucas F.',   orders: 48, revenue: 876, avgTime: 12, satisfaction: 96 },
  { staff: 'Ana R.',     orders: 42, revenue: 743, avgTime: 14, satisfaction: 94 },
  { staff: 'Pedro M.',   orders: 35, revenue: 612, avgTime: 16, satisfaction: 91 },
  { staff: 'Sofia B.',   orders: 28, revenue: 489, avgTime: 13, satisfaction: 98 },
];

const weeklyData = [
  { day: 'Mon', revenue: 1240, orders: 48 },
  { day: 'Tue', revenue: 1580, orders: 62 },
  { day: 'Wed', revenue: 1350, orders: 54 },
  { day: 'Thu', revenue: 1890, orders: 74 },
  { day: 'Fri', revenue: 2340, orders: 91 },
  { day: 'Sat', revenue: 2780, orders: 108 },
  { day: 'Sun', revenue: 1960, orders: 78 },
];

const COLORS = ['#F59E0B','#3B82F6','#10B981','#8B5CF6','#EF4444','#14B8A6'];

export function Reports() {
  const [period, setPeriod] = useState<'today' | 'week' | 'month'>('today');

  const summaryStats = [
    { label: 'Total Revenue',    value: '€2,515.50', change: '+18.4%', positive: true,  icon: TrendingUp, color: '#F59E0B' },
    { label: 'Orders Processed', value: '127',        change: '+12.1%', positive: true,  icon: ShoppingBag, color: '#3B82F6' },
    { label: 'Avg. Prep Time',   value: '14.2 min',  change: '-2.3 min', positive: true, icon: Clock,      color: '#10B981' },
    { label: 'Active Clients',   value: '38',         change: '+5',      positive: true,  icon: Users,      color: '#8B5CF6' },
  ];

  return (
    <div className="flex flex-col h-full" style={{ background: '#F6F7F9' }}>
      {/* Header */}
      <div
        className="flex items-center justify-between px-8 py-5 shrink-0"
        style={{ background: 'white', borderBottom: '1px solid #E5E7EB' }}
      >
        <div className="flex items-center gap-3">
          <div className="rounded-xl p-2" style={{ background: '#FFFBEB' }}>
            <BarChart3 size={20} color="#D97706" />
          </div>
          <div>
            <h1 style={{ fontSize: 26, fontWeight: 700, color: '#1F2937' }}>Reports</h1>
            <p style={{ fontSize: 13, color: '#6B7280', marginTop: 2 }}>Analytics & performance dashboard</p>
          </div>
        </div>

        {/* Period Filter */}
        <div className="flex items-center gap-1.5">
          <Calendar size={14} color="#9CA3AF" />
          {(['today', 'week', 'month'] as const).map(p => (
            <button
              key={p}
              onClick={() => setPeriod(p)}
              className="rounded-full px-3 py-1.5 capitalize transition-all"
              style={{
                fontSize: 13, fontWeight: 500,
                background: period === p ? '#F59E0B' : '#F3F4F6',
                color: period === p ? 'white' : '#6B7280',
              }}
            >
              {p === 'today' ? 'Today' : p === 'week' ? 'This Week' : 'This Month'}
            </button>
          ))}
        </div>
      </div>

      {/* Content */}
      <div className="flex-1 overflow-y-auto px-8 py-6 flex flex-col gap-6">
        {/* Summary KPIs */}
        <div className="grid grid-cols-4 gap-4">
          {summaryStats.map(stat => (
            <div
              key={stat.label}
              className="rounded-2xl p-5"
              style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
            >
              <div className="flex items-start justify-between mb-3">
                <div className="rounded-xl p-2" style={{ background: `${stat.color}15` }}>
                  <stat.icon size={18} color={stat.color} />
                </div>
                <span
                  className="rounded-full px-2 py-0.5"
                  style={{
                    fontSize: 11, fontWeight: 600,
                    background: stat.positive ? '#ECFDF5' : '#FEF2F2',
                    color: stat.positive ? '#059669' : '#DC2626',
                  }}
                >
                  {stat.change}
                </span>
              </div>
              <div style={{ fontSize: 24, fontWeight: 700, color: '#1F2937' }}>{stat.value}</div>
              <div style={{ fontSize: 12, color: '#9CA3AF', marginTop: 4 }}>{stat.label}</div>
            </div>
          ))}
        </div>

        {/* Charts Row 1 */}
        <div className="grid grid-cols-3 gap-5">
          {/* Revenue/Orders Chart */}
          <div
            className="col-span-2 rounded-2xl p-5"
            style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
          >
            <h2 style={{ fontSize: 16, fontWeight: 700, color: '#1F2937', marginBottom: 16 }}>
              {period === 'today' ? 'Orders Per Hour' : 'Weekly Revenue & Orders'}
            </h2>
            <ResponsiveContainer width="100%" height={220}>
              <BarChart data={period === 'today' ? revenueData : weeklyData}>
                <CartesianGrid strokeDasharray="3 3" stroke="#F3F4F6" />
                <XAxis
                  dataKey={period === 'today' ? 'hour' : 'day'}
                  tick={{ fontSize: 11, fill: '#9CA3AF' }}
                  tickLine={false}
                  axisLine={false}
                />
                <YAxis tick={{ fontSize: 11, fill: '#9CA3AF' }} tickLine={false} axisLine={false} />
                <Tooltip />
                <Bar dataKey="revenue" fill="#F59E0B" radius={[4, 4, 0, 0]} name="Revenue (€)" />
                <Bar dataKey="orders" fill="#3B82F6" radius={[4, 4, 0, 0]} name="Orders" />
              </BarChart>
            </ResponsiveContainer>
          </div>

          {/* Top Products Pie */}
          <div
            className="rounded-2xl p-5"
            style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
          >
            <h2 style={{ fontSize: 16, fontWeight: 700, color: '#1F2937', marginBottom: 16 }}>Top Products</h2>
            <ResponsiveContainer width="100%" height={140}>
              <PieChart>
                <Pie
                  data={topProducts}
                  dataKey="sales"
                  nameKey="name"
                  cx="50%"
                  cy="50%"
                  innerRadius={40}
                  outerRadius={65}
                >
                  {topProducts.map((_, index) => (
                    <Cell key={index} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip formatter={(v, n) => [v, n]} />
              </PieChart>
            </ResponsiveContainer>
            <div className="flex flex-col gap-1 mt-2">
              {topProducts.slice(0, 4).map((p, i) => (
                <div key={p.name} className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <div className="rounded-full" style={{ width: 8, height: 8, background: COLORS[i] }} />
                    <span style={{ fontSize: 11, color: '#6B7280' }}>{p.name}</span>
                  </div>
                  <span style={{ fontSize: 11, fontWeight: 600, color: '#374151' }}>{p.sales}</span>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Charts Row 2 */}
        <div className="grid grid-cols-2 gap-5">
          {/* Preparation Time by Category */}
          <div
            className="rounded-2xl p-5"
            style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
          >
            <h2 style={{ fontSize: 16, fontWeight: 700, color: '#1F2937', marginBottom: 16 }}>Avg. Prep Time by Category</h2>
            <ResponsiveContainer width="100%" height={200}>
              <BarChart data={prepTimeData} layout="vertical">
                <CartesianGrid strokeDasharray="3 3" stroke="#F3F4F6" horizontal={false} />
                <XAxis type="number" tick={{ fontSize: 11, fill: '#9CA3AF' }} tickLine={false} axisLine={false} tickFormatter={v => `${v}m`} />
                <YAxis type="category" dataKey="name" tick={{ fontSize: 12, fill: '#6B7280' }} tickLine={false} axisLine={false} width={80} />
                <Tooltip formatter={(v: number) => [`${v} min`, 'Avg Prep Time']} />
                <Bar dataKey="avg" fill="#F59E0B" radius={[0, 4, 4, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>

          {/* Staff Performance */}
          <div
            className="rounded-2xl p-5"
            style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
          >
            <h2 style={{ fontSize: 16, fontWeight: 700, color: '#1F2937', marginBottom: 16 }}>Staff Performance</h2>
            <div className="flex flex-col gap-3">
              {staffPerformance.map((staff, idx) => (
                <div key={staff.staff} className="flex items-center gap-4">
                  <div
                    className="flex items-center justify-center rounded-full shrink-0"
                    style={{ width: 32, height: 32, background: COLORS[idx], color: 'white', fontSize: 12, fontWeight: 700 }}
                  >
                    {staff.staff[0]}
                  </div>
                  <div className="flex-1">
                    <div className="flex items-center justify-between mb-1">
                      <span style={{ fontSize: 13, fontWeight: 600, color: '#1F2937' }}>{staff.staff}</span>
                      <div className="flex items-center gap-3">
                        <span style={{ fontSize: 12, color: '#F59E0B', fontWeight: 600 }}>€{staff.revenue}</span>
                        <span style={{ fontSize: 12, color: '#6B7280' }}>{staff.orders} orders</span>
                      </div>
                    </div>
                    <div className="flex items-center gap-2">
                      <div className="flex-1 rounded-full overflow-hidden" style={{ height: 4, background: '#F3F4F6' }}>
                        <div
                          className="h-full rounded-full"
                          style={{ width: `${staff.satisfaction}%`, background: COLORS[idx] }}
                        />
                      </div>
                      <span style={{ fontSize: 11, color: '#9CA3AF', whiteSpace: 'nowrap' }}>
                        {staff.satisfaction}% satisfaction
                      </span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Revenue Line Chart */}
        <div
          className="rounded-2xl p-5"
          style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
        >
          <div className="flex items-center justify-between mb-4">
            <h2 style={{ fontSize: 16, fontWeight: 700, color: '#1F2937' }}>Revenue Trend</h2>
            <div className="flex items-center gap-3">
              <div className="flex items-center gap-1.5">
                <div className="rounded-full" style={{ width: 8, height: 8, background: '#F59E0B' }} />
                <span style={{ fontSize: 11, color: '#6B7280' }}>Revenue</span>
              </div>
              <div className="flex items-center gap-1.5">
                <div className="rounded-full" style={{ width: 8, height: 8, background: '#3B82F6' }} />
                <span style={{ fontSize: 11, color: '#6B7280' }}>Orders</span>
              </div>
            </div>
          </div>
          <ResponsiveContainer width="100%" height={160}>
            <LineChart data={revenueData}>
              <CartesianGrid strokeDasharray="3 3" stroke="#F3F4F6" />
              <XAxis dataKey="hour" tick={{ fontSize: 11, fill: '#9CA3AF' }} tickLine={false} axisLine={false} />
              <YAxis yAxisId="left" tick={{ fontSize: 11, fill: '#9CA3AF' }} tickLine={false} axisLine={false} tickFormatter={v => `€${v}`} />
              <YAxis yAxisId="right" orientation="right" tick={{ fontSize: 11, fill: '#9CA3AF' }} tickLine={false} axisLine={false} />
              <Tooltip />
              <Line yAxisId="left" type="monotone" dataKey="revenue" stroke="#F59E0B" strokeWidth={2.5} dot={false} />
              <Line yAxisId="right" type="monotone" dataKey="orders" stroke="#3B82F6" strokeWidth={2} dot={false} strokeDasharray="4 4" />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
}
