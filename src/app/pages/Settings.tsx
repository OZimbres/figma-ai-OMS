import {
  Settings as SettingsIcon, Store, Bell, Users, Shield, Printer,
  Wifi, Database, ChevronRight, Save,
} from 'lucide-react';

const sections = [
  { id: 'restaurant',    label: 'Restaurant Info',   icon: Store },
  { id: 'notifications', label: 'Notifications',     icon: Bell },
  { id: 'staff',         label: 'Staff & Roles',     icon: Users },
  { id: 'permissions',   label: 'Permissions',       icon: Shield },
  { id: 'printing',      label: 'Printing',          icon: Printer },
  { id: 'integrations',  label: 'Integrations',      icon: Wifi },
  { id: 'database',      label: 'Data & Backup',     icon: Database },
];

interface ToggleRowProps {
  label: string;
  description?: string;
  defaultOn?: boolean;
}

function ToggleRow({ label, description, defaultOn = false }: ToggleRowProps) {
  const [on, setOn] = useState(defaultOn);
  return (
    <div className="flex items-center justify-between py-3.5" style={{ borderBottom: '1px solid #F9FAFB' }}>
      <div>
        <div style={{ fontSize: 14, fontWeight: 500, color: '#1F2937' }}>{label}</div>
        {description && <div style={{ fontSize: 12, color: '#9CA3AF', marginTop: 2 }}>{description}</div>}
      </div>
      <button
        onClick={() => setOn(!on)}
        className="rounded-full transition-all"
        style={{
          width: 44, height: 24, background: on ? '#F59E0B' : '#D1D5DB',
          position: 'relative', flexShrink: 0,
        }}
      >
        <span
          className="rounded-full absolute top-1 transition-all"
          style={{
            width: 16, height: 16, background: 'white',
            left: on ? 24 : 4,
            boxShadow: '0 1px 3px rgba(0,0,0,0.2)',
          }}
        />
      </button>
    </div>
  );
}

export function Settings() {
  const [activeSection, setActiveSection] = useState('restaurant');
  const [restaurantForm, setRestaurantForm] = useState({
    name: 'Café Lisboa',
    address: 'Rua Augusta 45, Lisboa',
    phone: '+351 21 342 1100',
    email: 'info@cafelisboa.pt',
    currency: 'EUR',
    timezone: 'Europe/Lisbon',
    tables: '10',
    taxRate: '23',
  });

  const staff = [
    { name: 'Lucas Ferreira', role: 'Waiter',  status: 'active' },
    { name: 'Ana Rodrigues',  role: 'Waiter',  status: 'active' },
    { name: 'Pedro Martins',  role: 'Waiter',  status: 'active' },
    { name: 'Chef António',   role: 'Chef',    status: 'active' },
    { name: 'Manager Silva',  role: 'Manager', status: 'active' },
    { name: 'Sofia Branco',   role: 'Cashier', status: 'inactive' },
  ];

  const roleColors: Record<string, { bg: string; text: string }> = {
    Waiter:  { bg: '#EFF6FF', text: '#2563EB' },
    Chef:    { bg: '#FFF7ED', text: '#EA580C' },
    Manager: { bg: '#F5F3FF', text: '#7C3AED' },
    Cashier: { bg: '#F0FDF4', text: '#16A34A' },
  };

  return (
    <div className="flex h-full" style={{ background: '#F6F7F9' }}>
      {/* Sidebar */}
      <div
        className="flex flex-col shrink-0 overflow-y-auto"
        style={{ width: 240, background: 'white', borderRight: '1px solid #E5E7EB' }}
      >
        <div className="p-5" style={{ borderBottom: '1px solid #E5E7EB' }}>
          <div className="flex items-center gap-2">
            <SettingsIcon size={18} color="#6B7280" />
            <h1 style={{ fontSize: 18, fontWeight: 700, color: '#1F2937' }}>Settings</h1>
          </div>
        </div>
        <nav className="p-3 flex flex-col gap-0.5">
          {sections.map(section => (
            <button
              key={section.id}
              onClick={() => setActiveSection(section.id)}
              className="flex items-center justify-between rounded-xl px-3 py-2.5 w-full text-left transition-all"
              style={{
                background: activeSection === section.id ? '#FFFBEB' : 'transparent',
                color: activeSection === section.id ? '#D97706' : '#6B7280',
              }}
            >
              <div className="flex items-center gap-2.5">
                <section.icon size={15} />
                <span style={{ fontSize: 13, fontWeight: activeSection === section.id ? 600 : 400 }}>
                  {section.label}
                </span>
              </div>
              <ChevronRight size={13} />
            </button>
          ))}
        </nav>
      </div>

      {/* Content */}
      <div className="flex-1 overflow-y-auto p-8">
        {activeSection === 'restaurant' && (
          <div>
            <h2 style={{ fontSize: 20, fontWeight: 700, color: '#1F2937', marginBottom: 6 }}>Restaurant Info</h2>
            <p style={{ fontSize: 13, color: '#6B7280', marginBottom: 24 }}>Basic restaurant configuration and details</p>
            <div
              className="rounded-2xl p-6"
              style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
            >
              <div className="grid grid-cols-2 gap-5">
                {[
                  { key: 'name',     label: 'Restaurant Name' },
                  { key: 'address',  label: 'Address' },
                  { key: 'phone',    label: 'Phone Number' },
                  { key: 'email',    label: 'Email Address' },
                  { key: 'currency', label: 'Currency' },
                  { key: 'timezone', label: 'Timezone' },
                  { key: 'tables',   label: 'Number of Tables' },
                  { key: 'taxRate',  label: 'Tax Rate (%)' },
                ].map(field => (
                  <div key={field.key}>
                    <label style={{ fontSize: 12, fontWeight: 600, color: '#374151', display: 'block', marginBottom: 6 }}>
                      {field.label}
                    </label>
                    <input
                      value={restaurantForm[field.key as keyof typeof restaurantForm]}
                      onChange={e => setRestaurantForm(f => ({ ...f, [field.key]: e.target.value }))}
                      className="w-full rounded-xl px-4 py-2.5 outline-none transition-all focus:ring-2"
                      style={{
                        border: '1px solid #E5E7EB',
                        fontSize: 14,
                        background: '#FAFAFA',
                      }}
                    />
                  </div>
                ))}
              </div>
              <button
                className="mt-6 flex items-center gap-2 rounded-xl px-5 py-2.5 text-white transition-opacity hover:opacity-90"
                style={{ background: '#F59E0B', fontSize: 14, fontWeight: 600 }}
              >
                <Save size={15} /> Save Changes
              </button>
            </div>
          </div>
        )}

        {activeSection === 'notifications' && (
          <div>
            <h2 style={{ fontSize: 20, fontWeight: 700, color: '#1F2937', marginBottom: 6 }}>Notifications</h2>
            <p style={{ fontSize: 13, color: '#6B7280', marginBottom: 24 }}>Configure when and how you receive alerts</p>
            <div
              className="rounded-2xl px-6 py-2"
              style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
            >
              <ToggleRow label="New Order Alert"         description="Sound alert when a new order is placed"         defaultOn={true} />
              <ToggleRow label="Kitchen Delay Warning"   description="Alert when orders exceed estimated prep time"    defaultOn={true} />
              <ToggleRow label="Order Ready Notification" description="Notify waiter when food is ready to serve"     defaultOn={true} />
              <ToggleRow label="Low Stock Warning"       description="Alert when a product is running low"            defaultOn={false} />
              <ToggleRow label="Bill Payment Reminder"   description="Remind staff of pending bills after 30 minutes" defaultOn={true} />
              <ToggleRow label="Critical Order Alert"    description="Immediate alert for critical priority orders"   defaultOn={true} />
              <ToggleRow label="Desktop Notifications"   description="Browser notifications when window is inactive"  defaultOn={false} />
              <ToggleRow label="Sound Alerts"            description="Enable audio alerts for all notifications"      defaultOn={true} />
            </div>
          </div>
        )}

        {activeSection === 'staff' && (
          <div>
            <h2 style={{ fontSize: 20, fontWeight: 700, color: '#1F2937', marginBottom: 6 }}>Staff & Roles</h2>
            <p style={{ fontSize: 13, color: '#6B7280', marginBottom: 24 }}>Manage staff accounts and role assignments</p>
            <div
              className="rounded-2xl overflow-hidden"
              style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
            >
              {staff.map((member, idx) => {
                const rc = roleColors[member.role];
                return (
                  <div
                    key={member.name}
                    className="flex items-center justify-between px-5 py-4 transition-colors hover:bg-gray-50"
                    style={{ borderBottom: idx < staff.length - 1 ? '1px solid #F9FAFB' : 'none' }}
                  >
                    <div className="flex items-center gap-3">
                      <div
                        className="flex items-center justify-center rounded-full"
                        style={{ width: 36, height: 36, background: '#F59E0B', color: 'white', fontSize: 12, fontWeight: 700 }}
                      >
                        {member.name.split(' ').map(n => n[0]).join('').slice(0, 2)}
                      </div>
                      <div>
                        <div style={{ fontSize: 14, fontWeight: 600, color: '#1F2937' }}>{member.name}</div>
                        <span
                          className="rounded-full px-2 py-0.5 inline-block mt-0.5"
                          style={{ fontSize: 11, fontWeight: 500, background: rc.bg, color: rc.text }}
                        >
                          {member.role}
                        </span>
                      </div>
                    </div>
                    <div className="flex items-center gap-3">
                      <span
                        className="rounded-full px-2.5 py-1"
                        style={{
                          fontSize: 11, fontWeight: 500,
                          background: member.status === 'active' ? '#ECFDF5' : '#F9FAFB',
                          color: member.status === 'active' ? '#059669' : '#9CA3AF',
                        }}
                      >
                        {member.status}
                      </span>
                      <button
                        className="rounded-lg px-3 py-1.5 transition-colors hover:bg-gray-100"
                        style={{ fontSize: 12, color: '#6B7280', border: '1px solid #E5E7EB' }}
                      >
                        Edit
                      </button>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        )}

        {activeSection === 'permissions' && (
          <div>
            <h2 style={{ fontSize: 20, fontWeight: 700, color: '#1F2937', marginBottom: 6 }}>Permissions</h2>
            <p style={{ fontSize: 13, color: '#6B7280', marginBottom: 24 }}>Control what each role can access and do</p>
            <div
              className="rounded-2xl overflow-hidden"
              style={{ background: 'white', border: '1px solid #E5E7EB', boxShadow: '0 1px 3px rgba(0,0,0,0.04)' }}
            >
              <div className="grid px-5 py-3" style={{ gridTemplateColumns: '200px 1fr 1fr 1fr 1fr', borderBottom: '1px solid #F3F4F6' }}>
                <div style={{ fontSize: 11, fontWeight: 600, color: '#9CA3AF' }}>MODULE</div>
                {['Waiter', 'Chef', 'Cashier', 'Manager'].map(r => (
                  <div key={r} style={{ fontSize: 11, fontWeight: 600, color: '#9CA3AF', textAlign: 'center' }}>{r.toUpperCase()}</div>
                ))}
              </div>
              {[
                { module: 'Dashboard',       waiter: true,  chef: true,  cashier: true,  manager: true  },
                { module: 'Orders',          waiter: true,  chef: false, cashier: false, manager: true  },
                { module: 'Kitchen Queue',   waiter: false, chef: true,  cashier: false, manager: true  },
                { module: 'Tables',          waiter: true,  chef: false, cashier: true,  manager: true  },
                { module: 'Products',        waiter: false, chef: false, cashier: false, manager: true  },
                { module: 'Clients',         waiter: false, chef: false, cashier: true,  manager: true  },
                { module: 'Bills',           waiter: false, chef: false, cashier: true,  manager: true  },
                { module: 'Reports',         waiter: false, chef: false, cashier: false, manager: true  },
                { module: 'Settings',        waiter: false, chef: false, cashier: false, manager: true  },
              ].map((row, idx) => (
                <div
                  key={row.module}
                  className="grid items-center px-5 py-3.5 hover:bg-gray-50"
                  style={{
                    gridTemplateColumns: '200px 1fr 1fr 1fr 1fr',
                    borderBottom: idx < 8 ? '1px solid #F9FAFB' : 'none',
                  }}
                >
                  <span style={{ fontSize: 13, fontWeight: 500, color: '#374151' }}>{row.module}</span>
                  {[row.waiter, row.chef, row.cashier, row.manager].map((allowed, i) => (
                    <div key={i} className="flex justify-center">
                      <span style={{ fontSize: 16 }}>{allowed ? '✅' : '⛔'}</span>
                    </div>
                  ))}
                </div>
              ))}
            </div>
          </div>
        )}

        {(activeSection === 'printing' || activeSection === 'integrations' || activeSection === 'database') && (
          <div>
            <h2 style={{ fontSize: 20, fontWeight: 700, color: '#1F2937', marginBottom: 6 }}>
              {sections.find(s => s.id === activeSection)?.label}
            </h2>
            <p style={{ fontSize: 13, color: '#6B7280', marginBottom: 24 }}>Configuration options for {activeSection}</p>
            <div
              className="rounded-2xl p-6 flex flex-col items-center justify-center"
              style={{ background: 'white', border: '1px solid #E5E7EB', minHeight: 200 }}
            >
              <div className="rounded-2xl p-5 mb-4" style={{ background: '#F9FAFB' }}>
                {activeSection === 'printing' && <Printer size={32} color="#D1D5DB" />}
                {activeSection === 'integrations' && <Wifi size={32} color="#D1D5DB" />}
                {activeSection === 'database' && <Database size={32} color="#D1D5DB" />}
              </div>
              <p style={{ fontSize: 14, fontWeight: 500, color: '#6B7280' }}>
                {activeSection === 'printing' ? 'Printer configuration coming soon' :
                 activeSection === 'integrations' ? 'Third-party integrations coming soon' :
                 'Data management tools coming soon'}
              </p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}