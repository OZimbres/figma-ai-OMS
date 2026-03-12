import { useState, useMemo } from 'react';
import { useApp } from '../context/AppContext';
import { StatusBadge } from '../components/ui/StatusBadge';
import { Bill, BillStatus, PaymentMethod } from '../types';
import { Search, Receipt, CreditCard, Banknote, Smartphone, CheckCircle2, XCircle, Clock, X } from 'lucide-react';

function formatTime(date: Date): string {
  const now = new Date();
  const diffMs = now.getTime() - date.getTime();
  const diffMins = Math.floor(diffMs / 60000);
  if (diffMins < 60) return `${diffMins}m ago`;
  return date.toLocaleTimeString('en-GB', { hour: '2-digit', minute: '2-digit' });
}

const paymentIcons: Record<PaymentMethod, JSX.Element> = {
  cash:    <Banknote size={14} color="#10B981" />,
  card:    <CreditCard size={14} color="#3B82F6" />,
  digital: <Smartphone size={14} color="#8B5CF6" />,
};

const paymentColors: Record<PaymentMethod, string> = {
  cash:    '#10B981',
  card:    '#3B82F6',
  digital: '#8B5CF6',
};

export function Bills() {
  const { bills, updateBillStatus } = useApp();
  const [search, setSearch] = useState('');
  const [filterStatus, setFilterStatus] = useState<string>('all');
  const [filterMethod, setFilterMethod] = useState<string>('all');
  const [selectedBill, setSelectedBill] = useState<Bill | null>(null);
  const [showPayModal, setShowPayModal] = useState(false);
  const [payMethod, setPayMethod] = useState<PaymentMethod>('card');

  const filtered = useMemo(() => {
    return bills.filter(b => {
      if (filterStatus !== 'all' && b.status !== filterStatus) return false;
      if (filterMethod !== 'all' && b.paymentMethod !== filterMethod) return false;
      if (search && !b.clientName.toLowerCase().includes(search.toLowerCase()) &&
          !b.id.toLowerCase().includes(search.toLowerCase()) &&
          !String(b.tableNumber).includes(search)) return false;
      return true;
    }).sort((a, b) => b.createdAt.getTime() - a.createdAt.getTime());
  }, [bills, search, filterStatus, filterMethod]);

  const stats = useMemo(() => ({
    totalToday: bills.filter(b => b.status === 'paid').reduce((s, b) => s + b.total, 0),
    pending: bills.filter(b => b.status === 'pending').length,
    paid: bills.filter(b => b.status === 'paid').length,
    byMethod: {
      cash:    bills.filter(b => b.paymentMethod === 'cash' && b.status === 'paid').reduce((s, b) => s + b.total, 0),
      card:    bills.filter(b => b.paymentMethod === 'card' && b.status === 'paid').reduce((s, b) => s + b.total, 0),
      digital: bills.filter(b => b.paymentMethod === 'digital' && b.status === 'paid').reduce((s, b) => s + b.total, 0),
    },
  }), [bills]);

  const handlePay = () => {
    if (!selectedBill) return;
    updateBillStatus(selectedBill.id, { status: 'paid', paymentMethod: payMethod });
    setShowPayModal(false);
    setSelectedBill(null);
  };

  return (
    <div className="flex flex-col h-full" style={{ background: '#F6F7F9' }}>
      {/* Header */}
      <div
        className="px-8 py-5 shrink-0"
        style={{ background: 'white', borderBottom: '1px solid #E5E7EB' }}
      >
        <div className="flex items-start justify-between mb-4">
          <div>
            <h1 style={{ fontSize: 26, fontWeight: 700, color: '#1F2937' }}>Bills & Payments</h1>
            <p style={{ fontSize: 13, color: '#6B7280', marginTop: 2 }}>Financial tracking and payment management</p>
          </div>
          <div
            className="rounded-2xl px-5 py-4 text-center"
            style={{ background: '#1C2333', minWidth: 160 }}
          >
            <div style={{ fontSize: 11, color: '#8892A4', marginBottom: 2 }}>Revenue Today</div>
            <div style={{ fontSize: 26, fontWeight: 700, color: '#F59E0B' }}>€{stats.totalToday.toFixed(2)}</div>
            <div style={{ fontSize: 11, color: '#4A5568', marginTop: 2 }}>{stats.paid} bills processed</div>
          </div>
        </div>

        {/* Payment method stats */}
        <div className="grid grid-cols-3 gap-4">
          {(['cash', 'card', 'digital'] as PaymentMethod[]).map(method => (
            <div
              key={method}
              className="rounded-xl p-3 flex items-center gap-3"
              style={{ background: '#F9FAFB', border: '1px solid #E5E7EB' }}
            >
              <div
                className="rounded-xl p-2"
                style={{ background: `${paymentColors[method]}15` }}
              >
                {paymentIcons[method]}
              </div>
              <div>
                <div style={{ fontSize: 16, fontWeight: 700, color: '#1F2937' }}>
                  €{stats.byMethod[method].toFixed(2)}
                </div>
                <div style={{ fontSize: 11, color: '#9CA3AF', textTransform: 'capitalize' }}>{method}</div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Filters */}
      <div
        className="flex items-center gap-3 px-8 py-3 flex-wrap"
        style={{ background: 'white', borderBottom: '1px solid #F3F4F6' }}
      >
        <div className="relative">
          <Search size={14} className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
          <input
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="Search bills..."
            className="rounded-lg pl-8 pr-3 py-2 outline-none"
            style={{ background: '#F9FAFB', border: '1px solid #E5E7EB', fontSize: 13, width: 200 }}
          />
        </div>
        <div className="flex gap-1.5">
          {['all', 'pending', 'paid', 'cancelled'].map(s => (
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
              {s === 'all' ? 'All' : s}
            </button>
          ))}
        </div>
        <div className="flex gap-1.5 ml-4">
          {['all', 'cash', 'card', 'digital'].map(m => (
            <button
              key={m}
              onClick={() => setFilterMethod(m)}
              className="flex items-center gap-1.5 rounded-full px-3 py-1 capitalize transition-all"
              style={{
                fontSize: 12, fontWeight: 500,
                background: filterMethod === m ? '#1C2333' : '#F3F4F6',
                color: filterMethod === m ? 'white' : '#6B7280',
              }}
            >
              {m !== 'all' && paymentIcons[m as PaymentMethod]}
              {m}
            </button>
          ))}
        </div>
        <div className="ml-auto flex items-center gap-2">
          <span
            className="flex items-center gap-1.5 rounded-full px-3 py-1"
            style={{ fontSize: 12, background: '#FFFBEB', color: '#D97706', fontWeight: 600 }}
          >
            <Clock size={12} /> {stats.pending} pending
          </span>
        </div>
      </div>

      {/* Table */}
      <div className="flex-1 overflow-y-auto px-8 py-6">
        <div className="rounded-2xl overflow-hidden" style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}>
          {/* Table Header */}
          <div
            className="grid items-center px-5 py-3"
            style={{ gridTemplateColumns: '100px 60px 1fr 110px 120px 100px 160px', borderBottom: '1px solid #F3F4F6' }}
          >
            {['Bill ID', 'Table', 'Client', 'Total', 'Payment', 'Status', 'Actions'].map(h => (
              <div key={h} style={{ fontSize: 11, fontWeight: 600, color: '#9CA3AF', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                {h}
              </div>
            ))}
          </div>

          {/* Rows */}
          {filtered.map((bill, idx) => (
            <div
              key={bill.id}
              onClick={() => setSelectedBill(selectedBill?.id === bill.id ? null : bill)}
              className="grid items-center px-5 py-4 cursor-pointer transition-colors hover:bg-gray-50"
              style={{
                gridTemplateColumns: '100px 60px 1fr 110px 120px 100px 160px',
                borderBottom: idx < filtered.length - 1 ? '1px solid #F9FAFB' : 'none',
                background: selectedBill?.id === bill.id ? '#FFFBEB' : undefined,
              }}
            >
              {/* Bill ID */}
              <div style={{ fontSize: 13, fontWeight: 600, color: '#374151' }}>{bill.id}</div>

              {/* Table */}
              <div>
                <span
                  className="rounded-lg px-2 py-0.5"
                  style={{ fontSize: 12, fontWeight: 700, background: '#F59E0B', color: 'white' }}
                >
                  T{bill.tableNumber}
                </span>
              </div>

              {/* Client */}
              <div>
                <div style={{ fontSize: 14, fontWeight: 500, color: '#1F2937' }}>{bill.clientName}</div>
                <div style={{ fontSize: 11, color: '#9CA3AF' }}>{formatTime(bill.createdAt)}</div>
              </div>

              {/* Total */}
              <div style={{ fontSize: 16, fontWeight: 700, color: '#1F2937' }}>
                €{bill.total.toFixed(2)}
              </div>

              {/* Payment method */}
              <div className="flex items-center gap-1.5">
                {paymentIcons[bill.paymentMethod]}
                <span style={{ fontSize: 13, color: '#374151', textTransform: 'capitalize' }}>
                  {bill.paymentMethod}
                </span>
              </div>

              {/* Status */}
              <div>
                <StatusBadge type="bill" value={bill.status} size="sm" />
              </div>

              {/* Actions */}
              <div className="flex items-center gap-2" onClick={e => e.stopPropagation()}>
                {bill.status === 'pending' && (
                  <>
                    <button
                      onClick={() => { setSelectedBill(bill); setShowPayModal(true); }}
                      className="flex items-center gap-1 rounded-lg px-2.5 py-1.5 text-white transition-opacity hover:opacity-90"
                      style={{ background: '#10B981', fontSize: 12, fontWeight: 600 }}
                    >
                      <CheckCircle2 size={12} /> Pay
                    </button>
                    <button
                      onClick={() => updateBillStatus(bill.id, { status: 'cancelled' })}
                      className="flex items-center gap-1 rounded-lg px-2.5 py-1.5 transition-colors hover:bg-red-50"
                      style={{ fontSize: 12, color: '#EF4444', border: '1px solid #FCA5A5' }}
                    >
                      <XCircle size={12} /> Cancel
                    </button>
                  </>
                )}
                {bill.status === 'paid' && (
                  <span style={{ fontSize: 12, color: '#10B981', fontWeight: 500 }}>✓ Processed</span>
                )}
                {bill.status === 'cancelled' && (
                  <span style={{ fontSize: 12, color: '#9CA3AF' }}>Cancelled</span>
                )}
              </div>
            </div>
          ))}

          {filtered.length === 0 && (
            <div className="py-16 text-center" style={{ color: '#9CA3AF', fontSize: 13 }}>
              No bills found
            </div>
          )}
        </div>
      </div>

      {/* Pay Modal */}
      {showPayModal && selectedBill && (
        <div className="fixed inset-0 flex items-center justify-center z-50" style={{ background: 'rgba(0,0,0,0.4)' }}>
          <div className="rounded-2xl p-6 w-full max-w-sm" style={{ background: 'white', boxShadow: '0 20px 60px rgba(0,0,0,0.2)' }}>
            <div className="flex items-center justify-between mb-5">
              <h3 style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>Process Payment</h3>
              <button onClick={() => setShowPayModal(false)} className="rounded-lg p-1.5 hover:bg-gray-100">
                <X size={16} color="#6B7280" />
              </button>
            </div>

            <div className="rounded-xl p-4 mb-5" style={{ background: '#F9FAFB', border: '1px solid #E5E7EB' }}>
              <div className="flex justify-between mb-2">
                <span style={{ fontSize: 13, color: '#6B7280' }}>Bill</span>
                <span style={{ fontSize: 13, fontWeight: 600, color: '#374151' }}>{selectedBill.id}</span>
              </div>
              <div className="flex justify-between mb-2">
                <span style={{ fontSize: 13, color: '#6B7280' }}>Client</span>
                <span style={{ fontSize: 13, color: '#374151' }}>{selectedBill.clientName}</span>
              </div>
              <div className="flex justify-between" style={{ borderTop: '1px solid #E5E7EB', paddingTop: 8, marginTop: 8 }}>
                <span style={{ fontSize: 13, fontWeight: 600, color: '#374151' }}>Total</span>
                <span style={{ fontSize: 20, fontWeight: 700, color: '#F59E0B' }}>€{selectedBill.total.toFixed(2)}</span>
              </div>
            </div>

            <div className="mb-5">
              <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 10 }}>
                Payment Method
              </label>
              <div className="grid grid-cols-3 gap-2">
                {(['cash','card','digital'] as PaymentMethod[]).map(method => (
                  <button
                    key={method}
                    onClick={() => setPayMethod(method)}
                    className="flex flex-col items-center gap-2 rounded-xl p-3 transition-all"
                    style={{
                      border: `2px solid ${payMethod === method ? paymentColors[method] : '#E5E7EB'}`,
                      background: payMethod === method ? `${paymentColors[method]}10` : 'white',
                    }}
                  >
                    {paymentIcons[method]}
                    <span style={{ fontSize: 11, fontWeight: 600, color: payMethod === method ? paymentColors[method] : '#6B7280', textTransform: 'capitalize' }}>
                      {method}
                    </span>
                  </button>
                ))}
              </div>
            </div>

            <button
              onClick={handlePay}
              className="w-full rounded-xl py-3 text-white transition-opacity hover:opacity-90"
              style={{ background: '#10B981', fontSize: 14, fontWeight: 600 }}
            >
              <CheckCircle2 size={16} className="inline mr-2" />
              Confirm Payment
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
