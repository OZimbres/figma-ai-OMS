import { useState, useMemo } from 'react';
import { useApp } from '../context/AppContext';
import { Search, Users, TrendingUp, Star, Phone, Mail, Calendar } from 'lucide-react';

function formatDate(date: Date): string {
  const now = new Date();
  const diffMs = now.getTime() - date.getTime();
  const diffMins = Math.floor(diffMs / 60000);
  const diffDays = Math.floor(diffMs / 86400000);
  if (diffMins < 60) return `${diffMins}m ago`;
  if (diffDays === 0) return 'Today';
  if (diffDays === 1) return 'Yesterday';
  return date.toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' });
}

function getInitials(name: string): string {
  return name.split(' ').map(n => n[0]).join('').slice(0, 2).toUpperCase();
}

const avatarColors = [
  '#F59E0B','#3B82F6','#10B981','#8B5CF6','#EC4899',
  '#EF4444','#14B8A6','#F97316','#6366F1','#84CC16',
];

export function Clients() {
  const { clients } = useApp();
  const [search, setSearch] = useState('');
  const [sortBy, setSortBy] = useState<'visits' | 'spending' | 'name' | 'recent'>('visits');
  const [selectedClient, setSelectedClient] = useState<typeof clients[0] | null>(null);

  const filtered = useMemo(() => {
    let list = [...clients];
    if (search) {
      list = list.filter(c =>
        c.name.toLowerCase().includes(search.toLowerCase()) ||
        c.email.toLowerCase().includes(search.toLowerCase()) ||
        c.phone.includes(search)
      );
    }
    list.sort((a, b) => {
      if (sortBy === 'visits') return b.visits - a.visits;
      if (sortBy === 'spending') return b.totalSpending - a.totalSpending;
      if (sortBy === 'name') return a.name.localeCompare(b.name);
      if (sortBy === 'recent') return b.lastOrderDate.getTime() - a.lastOrderDate.getTime();
      return 0;
    });
    return list;
  }, [clients, search, sortBy]);

  const stats = useMemo(() => ({
    total: clients.length,
    totalRevenue: clients.reduce((s, c) => s + c.totalSpending, 0),
    avgSpending: clients.reduce((s, c) => s + c.totalSpending, 0) / clients.length,
    loyalCustomers: clients.filter(c => c.visits >= 20).length,
  }), [clients]);

  return (
    <div className="flex flex-col h-full" style={{ background: '#F6F7F9' }}>
      {/* Header */}
      <div
        className="flex items-center justify-between px-8 py-5 shrink-0"
        style={{ background: 'white', borderBottom: '1px solid #E5E7EB' }}
      >
        <div>
          <h1 style={{ fontSize: 26, fontWeight: 700, color: '#1F2937' }}>Clients</h1>
          <p style={{ fontSize: 13, color: '#6B7280', marginTop: 2 }}>Customer database & loyalty tracking</p>
        </div>

        {/* Stats */}
        <div className="flex gap-6">
          {[
            { label: 'Total Clients', value: stats.total, icon: Users, color: '#3B82F6' },
            { label: 'Total Revenue', value: `€${stats.totalRevenue.toFixed(0)}`, icon: TrendingUp, color: '#F59E0B' },
            { label: 'Loyal (20+ visits)', value: stats.loyalCustomers, icon: Star, color: '#10B981' },
          ].map(s => (
            <div key={s.label} className="flex items-center gap-2">
              <div className="rounded-xl p-2" style={{ background: `${s.color}15` }}>
                <s.icon size={16} color={s.color} />
              </div>
              <div>
                <div style={{ fontSize: 16, fontWeight: 700, color: '#1F2937', lineHeight: 1 }}>{s.value}</div>
                <div style={{ fontSize: 11, color: '#9CA3AF', marginTop: 2 }}>{s.label}</div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Filters */}
      <div
        className="flex items-center gap-3 px-8 py-3"
        style={{ background: 'white', borderBottom: '1px solid #F3F4F6' }}
      >
        <div className="relative">
          <Search size={14} className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
          <input
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="Search clients..."
            className="rounded-lg pl-8 pr-3 py-2 outline-none"
            style={{ background: '#F9FAFB', border: '1px solid #E5E7EB', fontSize: 13, width: 240 }}
          />
        </div>
        <div className="flex items-center gap-2 ml-4">
          <span style={{ fontSize: 12, color: '#6B7280' }}>Sort by:</span>
          {[
            { key: 'visits',   label: 'Most Visits' },
            { key: 'spending', label: 'Top Spenders' },
            { key: 'recent',   label: 'Recent' },
            { key: 'name',     label: 'Name' },
          ].map(s => (
            <button
              key={s.key}
              onClick={() => setSortBy(s.key as typeof sortBy)}
              className="rounded-full px-3 py-1 transition-all"
              style={{
                fontSize: 12, fontWeight: 500,
                background: sortBy === s.key ? '#F59E0B' : '#F3F4F6',
                color: sortBy === s.key ? 'white' : '#6B7280',
              }}
            >
              {s.label}
            </button>
          ))}
        </div>
      </div>

      <div className="flex flex-1 overflow-hidden">
        {/* Table */}
        <div className="flex-1 overflow-y-auto px-8 py-6">
          <div className="rounded-2xl overflow-hidden" style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}>
            {/* Header Row */}
            <div
              className="grid items-center px-5 py-3"
              style={{
                gridTemplateColumns: '48px 1fr 180px 100px 140px 160px',
                borderBottom: '1px solid #F3F4F6',
              }}
            >
              {['', 'Client', 'Contact', 'Visits', 'Total Spent', 'Last Order'].map(h => (
                <div key={h} style={{ fontSize: 11, fontWeight: 600, color: '#9CA3AF', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                  {h}
                </div>
              ))}
            </div>

            {/* Rows */}
            {filtered.map((client, idx) => {
              const color = avatarColors[idx % avatarColors.length];
              const isVip = client.visits >= 30;
              return (
                <div
                  key={client.id}
                  onClick={() => setSelectedClient(selectedClient?.id === client.id ? null : client)}
                  className="grid items-center px-5 py-4 cursor-pointer transition-colors hover:bg-gray-50"
                  style={{
                    gridTemplateColumns: '48px 1fr 180px 100px 140px 160px',
                    borderBottom: idx < filtered.length - 1 ? '1px solid #F9FAFB' : 'none',
                    background: selectedClient?.id === client.id ? '#FFFBEB' : undefined,
                  }}
                >
                  {/* Avatar */}
                  <div
                    className="flex items-center justify-center rounded-full"
                    style={{ width: 36, height: 36, background: color, color: 'white', fontSize: 12, fontWeight: 700 }}
                  >
                    {getInitials(client.name)}
                  </div>

                  {/* Name */}
                  <div>
                    <div className="flex items-center gap-2">
                      <span style={{ fontSize: 14, fontWeight: 600, color: '#1F2937' }}>{client.name}</span>
                      {isVip && (
                        <span
                          className="inline-flex items-center gap-1 rounded-full px-2 py-0.5"
                          style={{ fontSize: 10, background: '#FFFBEB', color: '#D97706', border: '1px solid #FCD34D', fontWeight: 600 }}
                        >
                          <Star size={8} fill="#D97706" /> VIP
                        </span>
                      )}
                    </div>
                    <div style={{ fontSize: 11, color: '#9CA3AF' }}>{client.email}</div>
                  </div>

                  {/* Contact */}
                  <div style={{ fontSize: 12, color: '#6B7280' }}>{client.phone}</div>

                  {/* Visits */}
                  <div>
                    <span style={{ fontSize: 14, fontWeight: 700, color: '#1F2937' }}>{client.visits}</span>
                    <span style={{ fontSize: 11, color: '#9CA3AF', marginLeft: 4 }}>visits</span>
                  </div>

                  {/* Spending */}
                  <div style={{ fontSize: 14, fontWeight: 700, color: '#F59E0B' }}>
                    €{client.totalSpending.toFixed(2)}
                  </div>

                  {/* Last Order */}
                  <div style={{ fontSize: 12, color: '#6B7280' }}>{formatDate(client.lastOrderDate)}</div>
                </div>
              );
            })}

            {filtered.length === 0 && (
              <div className="py-16 text-center" style={{ color: '#9CA3AF', fontSize: 13 }}>
                No clients found
              </div>
            )}
          </div>
        </div>

        {/* Client Detail Panel */}
        {selectedClient && (
          <div
            className="flex flex-col shrink-0 overflow-y-auto"
            style={{ width: 280, background: 'white', borderLeft: '1px solid #E5E7EB' }}
          >
            <div className="p-6 flex flex-col items-center" style={{ borderBottom: '1px solid #E5E7EB' }}>
              <div
                className="flex items-center justify-center rounded-full mb-3"
                style={{ width: 64, height: 64, background: '#F59E0B', color: 'white', fontSize: 22, fontWeight: 700 }}
              >
                {getInitials(selectedClient.name)}
              </div>
              <div style={{ fontSize: 16, fontWeight: 700, color: '#1F2937', textAlign: 'center' }}>{selectedClient.name}</div>
              {selectedClient.visits >= 30 && (
                <span
                  className="inline-flex items-center gap-1 rounded-full px-2.5 py-1 mt-2"
                  style={{ fontSize: 11, background: '#FFFBEB', color: '#D97706', border: '1px solid #FCD34D', fontWeight: 600 }}
                >
                  <Star size={10} fill="#D97706" /> VIP Customer
                </span>
              )}
            </div>

            <div className="p-5 flex flex-col gap-4">
              <div className="flex flex-col gap-2">
                <div className="flex items-center gap-2">
                  <Phone size={13} color="#9CA3AF" />
                  <span style={{ fontSize: 13, color: '#374151' }}>{selectedClient.phone}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Mail size={13} color="#9CA3AF" />
                  <span style={{ fontSize: 12, color: '#374151' }}>{selectedClient.email}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Calendar size={13} color="#9CA3AF" />
                  <span style={{ fontSize: 12, color: '#374151' }}>Last: {formatDate(selectedClient.lastOrderDate)}</span>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div className="rounded-xl p-3 text-center" style={{ background: '#F9FAFB' }}>
                  <div style={{ fontSize: 22, fontWeight: 700, color: '#F59E0B' }}>{selectedClient.visits}</div>
                  <div style={{ fontSize: 11, color: '#9CA3AF' }}>Total Visits</div>
                </div>
                <div className="rounded-xl p-3 text-center" style={{ background: '#F9FAFB' }}>
                  <div style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>€{(selectedClient.totalSpending / selectedClient.visits).toFixed(0)}</div>
                  <div style={{ fontSize: 11, color: '#9CA3AF' }}>Avg/Visit</div>
                </div>
              </div>

              <div className="rounded-xl p-4" style={{ background: '#FFFBEB', border: '1px solid #FCD34D' }}>
                <div style={{ fontSize: 11, color: '#92400E', marginBottom: 2 }}>Total Lifetime Value</div>
                <div style={{ fontSize: 24, fontWeight: 700, color: '#D97706' }}>€{selectedClient.totalSpending.toFixed(2)}</div>
              </div>

              {/* Loyalty Progress */}
              <div>
                <div className="flex justify-between mb-2">
                  <span style={{ fontSize: 12, fontWeight: 600, color: '#374151' }}>Loyalty Level</span>
                  <span style={{ fontSize: 11, color: '#6B7280' }}>
                    {selectedClient.visits >= 50 ? 'Platinum' :
                     selectedClient.visits >= 30 ? 'Gold' :
                     selectedClient.visits >= 10 ? 'Silver' : 'Bronze'}
                  </span>
                </div>
                <div className="rounded-full overflow-hidden" style={{ height: 6, background: '#F3F4F6' }}>
                  <div
                    className="h-full rounded-full"
                    style={{
                      width: `${Math.min((selectedClient.visits / 50) * 100, 100)}%`,
                      background: '#F59E0B',
                    }}
                  />
                </div>
                <div style={{ fontSize: 11, color: '#9CA3AF', marginTop: 4 }}>
                  {Math.max(0, 50 - selectedClient.visits)} visits to Platinum
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
