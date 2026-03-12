import { useState } from 'react';
import { useApp } from '../context/AppContext';
import { Product, ProductCategory } from '../types';
import { Search, Plus, Edit2, ToggleLeft, ToggleRight, X, Clock, Tag } from 'lucide-react';

const categories: ProductCategory[] = ['Drinks', 'Pastries', 'Sandwiches', 'Meals', 'Desserts'];

const categoryColors: Record<ProductCategory, { bg: string; text: string }> = {
  Drinks:     { bg: '#EFF6FF', text: '#2563EB' },
  Pastries:   { bg: '#FFFBEB', text: '#D97706' },
  Sandwiches: { bg: '#F0FDF4', text: '#16A34A' },
  Meals:      { bg: '#FDF4FF', text: '#9333EA' },
  Desserts:   { bg: '#FFF1F2', text: '#E11D48' },
};

const emojis = ['☕','🍵','🧃','💧','🥤','🍞','🥐','🧁','🍩','🥪','🌯','🥗','🍝','🍔','🍕','🍗','🍰','🍮','🍫','🍦','🍧','🍷','🫖','🥓'];

export function Products() {
  const { products, updateProduct, addProduct } = useApp();
  const [search, setSearch] = useState('');
  const [filterCategory, setFilterCategory] = useState<string>('all');
  const [filterAvail, setFilterAvail] = useState<string>('all');
  const [showModal, setShowModal] = useState(false);
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);
  const [form, setForm] = useState({
    name: '', category: 'Drinks' as ProductCategory,
    price: '', prepTime: '', emoji: '☕', available: true,
  });

  const filtered = products.filter(p => {
    if (filterCategory !== 'all' && p.category !== filterCategory) return false;
    if (filterAvail === 'available' && !p.available) return false;
    if (filterAvail === 'unavailable' && p.available) return false;
    if (search && !p.name.toLowerCase().includes(search.toLowerCase())) return false;
    return true;
  });

  const openNew = () => {
    setEditingProduct(null);
    setForm({ name: '', category: 'Drinks', price: '', prepTime: '', emoji: '☕', available: true });
    setShowModal(true);
  };

  const openEdit = (p: Product) => {
    setEditingProduct(p);
    setForm({ name: p.name, category: p.category, price: String(p.price), prepTime: String(p.prepTime), emoji: p.emoji, available: p.available });
    setShowModal(true);
  };

  const handleSave = () => {
    if (!form.name || !form.price || !form.prepTime) return;
    if (editingProduct) {
      updateProduct(editingProduct.id, {
        name: form.name,
        category: form.category,
        price: parseFloat(form.price),
        prepTime: parseInt(form.prepTime),
        emoji: form.emoji,
        available: form.available,
      });
    } else {
      addProduct({
        id: `p${Date.now()}`,
        name: form.name,
        category: form.category,
        price: parseFloat(form.price),
        prepTime: parseInt(form.prepTime),
        emoji: form.emoji,
        available: form.available,
      });
    }
    setShowModal(false);
  };

  const totalAvail = products.filter(p => p.available).length;

  return (
    <div className="flex flex-col h-full" style={{ background: '#F6F7F9' }}>
      {/* Header */}
      <div
        className="flex items-center justify-between px-8 py-5 shrink-0"
        style={{ background: 'white', borderBottom: '1px solid #E5E7EB' }}
      >
        <div>
          <h1 style={{ fontSize: 26, fontWeight: 700, color: '#1F2937' }}>Products</h1>
          <p style={{ fontSize: 13, color: '#6B7280', marginTop: 2 }}>
            {products.length} total · {totalAvail} available · {products.length - totalAvail} unavailable
          </p>
        </div>
        <button
          onClick={openNew}
          className="flex items-center gap-2 rounded-xl px-4 py-2.5 text-white transition-opacity hover:opacity-90"
          style={{ background: '#F59E0B', fontSize: 13, fontWeight: 600 }}
        >
          <Plus size={16} /> Add Product
        </button>
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
            placeholder="Search products..."
            className="rounded-lg pl-8 pr-3 py-2 outline-none"
            style={{ background: '#F9FAFB', border: '1px solid #E5E7EB', fontSize: 13, width: 220 }}
          />
        </div>
        <div className="flex gap-1.5">
          {['all', ...categories].map(c => (
            <button
              key={c}
              onClick={() => setFilterCategory(c)}
              className="rounded-full px-3 py-1 transition-all"
              style={{
                fontSize: 12, fontWeight: 500,
                background: filterCategory === c ? '#F59E0B' : '#F3F4F6',
                color: filterCategory === c ? 'white' : '#6B7280',
              }}
            >
              {c}
            </button>
          ))}
        </div>
        <div className="flex gap-1.5 ml-auto">
          {['all', 'available', 'unavailable'].map(a => (
            <button
              key={a}
              onClick={() => setFilterAvail(a)}
              className="rounded-full px-3 py-1 capitalize transition-all"
              style={{
                fontSize: 12, fontWeight: 500,
                background: filterAvail === a ? '#1C2333' : '#F3F4F6',
                color: filterAvail === a ? 'white' : '#6B7280',
              }}
            >
              {a}
            </button>
          ))}
        </div>
      </div>

      {/* Table */}
      <div className="flex-1 overflow-y-auto px-8 py-6">
        <div className="rounded-2xl overflow-hidden" style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}>
          {/* Table Header */}
          <div
            className="grid items-center px-5 py-3"
            style={{ gridTemplateColumns: '60px 1fr 140px 90px 110px 90px 100px', borderBottom: '1px solid #F3F4F6' }}
          >
            {['', 'Product', 'Category', 'Price', 'Prep Time', 'Status', 'Actions'].map(h => (
              <div key={h} style={{ fontSize: 11, fontWeight: 600, color: '#9CA3AF', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                {h}
              </div>
            ))}
          </div>

          {/* Rows */}
          {filtered.map((product, idx) => {
            const catColor = categoryColors[product.category];
            return (
              <div
                key={product.id}
                className="grid items-center px-5 py-3.5 transition-colors hover:bg-gray-50"
                style={{
                  gridTemplateColumns: '60px 1fr 140px 90px 110px 90px 100px',
                  borderBottom: idx < filtered.length - 1 ? '1px solid #F9FAFB' : 'none',
                }}
              >
                {/* Emoji */}
                <div
                  className="flex items-center justify-center rounded-xl"
                  style={{ width: 40, height: 40, background: catColor.bg, fontSize: 20 }}
                >
                  {product.emoji}
                </div>

                {/* Name */}
                <div>
                  <div style={{ fontSize: 14, fontWeight: 600, color: '#1F2937' }}>{product.name}</div>
                  <div style={{ fontSize: 11, color: '#9CA3AF' }}>ID: {product.id}</div>
                </div>

                {/* Category */}
                <div>
                  <span
                    className="rounded-full px-2.5 py-1 inline-block"
                    style={{ fontSize: 12, fontWeight: 500, background: catColor.bg, color: catColor.text }}
                  >
                    {product.category}
                  </span>
                </div>

                {/* Price */}
                <div style={{ fontSize: 14, fontWeight: 700, color: '#F59E0B' }}>
                  €{product.price.toFixed(2)}
                </div>

                {/* Prep Time */}
                <div className="flex items-center gap-1.5">
                  <Clock size={13} color="#9CA3AF" />
                  <span style={{ fontSize: 13, color: '#6B7280' }}>{product.prepTime} min</span>
                </div>

                {/* Available */}
                <div>
                  <span
                    className="rounded-full px-2.5 py-1 inline-block"
                    style={{
                      fontSize: 12, fontWeight: 500,
                      background: product.available ? '#ECFDF5' : '#F9FAFB',
                      color: product.available ? '#059669' : '#9CA3AF',
                    }}
                  >
                    {product.available ? 'Available' : 'Off Menu'}
                  </span>
                </div>

                {/* Actions */}
                <div className="flex items-center gap-1">
                  <button
                    onClick={() => openEdit(product)}
                    className="rounded-lg p-2 transition-colors hover:bg-gray-100"
                    title="Edit"
                  >
                    <Edit2 size={14} color="#6B7280" />
                  </button>
                  <button
                    onClick={() => updateProduct(product.id, { available: !product.available })}
                    className="rounded-lg p-2 transition-colors hover:bg-gray-100"
                    title={product.available ? 'Disable' : 'Enable'}
                  >
                    {product.available
                      ? <ToggleRight size={18} color="#F59E0B" />
                      : <ToggleLeft size={18} color="#9CA3AF" />
                    }
                  </button>
                </div>
              </div>
            );
          })}

          {filtered.length === 0 && (
            <div className="py-16 text-center" style={{ color: '#9CA3AF', fontSize: 13 }}>
              No products found
            </div>
          )}
        </div>
      </div>

      {/* Add/Edit Modal */}
      {showModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50" style={{ background: 'rgba(0,0,0,0.4)' }}>
          <div className="rounded-2xl p-6 w-full max-w-md" style={{ background: 'white', boxShadow: '0 20px 60px rgba(0,0,0,0.2)' }}>
            <div className="flex items-center justify-between mb-5">
              <h3 style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>
                {editingProduct ? 'Edit Product' : 'New Product'}
              </h3>
              <button onClick={() => setShowModal(false)} className="rounded-lg p-1.5 hover:bg-gray-100">
                <X size={16} color="#6B7280" />
              </button>
            </div>
            <div className="flex flex-col gap-4">
              {/* Emoji Picker */}
              <div>
                <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 6 }}>Icon</label>
                <div className="flex flex-wrap gap-2">
                  {emojis.map(e => (
                    <button
                      key={e}
                      onClick={() => setForm(f => ({ ...f, emoji: e }))}
                      className="rounded-lg p-1.5 transition-all"
                      style={{
                        fontSize: 18, background: form.emoji === e ? '#FFFBEB' : '#F9FAFB',
                        border: `2px solid ${form.emoji === e ? '#F59E0B' : '#E5E7EB'}`,
                      }}
                    >
                      {e}
                    </button>
                  ))}
                </div>
              </div>
              <div className="grid grid-cols-2 gap-3">
                <div className="col-span-2">
                  <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 4 }}>Name *</label>
                  <input
                    value={form.name}
                    onChange={e => setForm(f => ({ ...f, name: e.target.value }))}
                    placeholder="Product name"
                    className="w-full rounded-lg px-3 py-2.5 outline-none"
                    style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                  />
                </div>
                <div>
                  <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 4 }}>Category</label>
                  <select
                    value={form.category}
                    onChange={e => setForm(f => ({ ...f, category: e.target.value as ProductCategory }))}
                    className="w-full rounded-lg px-3 py-2.5 outline-none"
                    style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                  >
                    {categories.map(c => <option key={c} value={c}>{c}</option>)}
                  </select>
                </div>
                <div>
                  <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 4 }}>Price (€) *</label>
                  <input
                    type="number" step="0.5" min="0"
                    value={form.price}
                    onChange={e => setForm(f => ({ ...f, price: e.target.value }))}
                    placeholder="0.00"
                    className="w-full rounded-lg px-3 py-2.5 outline-none"
                    style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                  />
                </div>
                <div>
                  <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 4 }}>Prep Time (min) *</label>
                  <input
                    type="number" min="1"
                    value={form.prepTime}
                    onChange={e => setForm(f => ({ ...f, prepTime: e.target.value }))}
                    placeholder="5"
                    className="w-full rounded-lg px-3 py-2.5 outline-none"
                    style={{ border: '1px solid #E5E7EB', fontSize: 14 }}
                  />
                </div>
                <div className="flex items-center gap-3">
                  <button
                    onClick={() => setForm(f => ({ ...f, available: !f.available }))}
                    className="flex items-center gap-2"
                  >
                    {form.available
                      ? <ToggleRight size={24} color="#F59E0B" />
                      : <ToggleLeft size={24} color="#9CA3AF" />
                    }
                    <span style={{ fontSize: 13, color: '#374151' }}>Available on menu</span>
                  </button>
                </div>
              </div>
              <button
                onClick={handleSave}
                disabled={!form.name || !form.price || !form.prepTime}
                className="w-full rounded-xl py-3 text-white transition-opacity hover:opacity-90 disabled:opacity-40"
                style={{ background: '#F59E0B', fontSize: 14, fontWeight: 600, marginTop: 4 }}
              >
                {editingProduct ? 'Save Changes' : 'Add Product'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
