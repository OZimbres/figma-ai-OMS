import { NavLink, Outlet } from 'react-router';
import {
  LayoutDashboard,
  ClipboardList,
  ChefHat,
  LayoutGrid,
  Package,
  Users,
  Receipt,
  BarChart3,
  Settings,
  UtensilsCrossed,
} from 'lucide-react';

const navItems = [
  { section: 'Operations', items: [
    { path: '/',         label: 'Dashboard',     icon: LayoutDashboard },
    { path: '/orders',   label: 'Orders',        icon: ClipboardList },
    { path: '/kitchen',  label: 'Kitchen Queue', icon: ChefHat },
    { path: '/tables',   label: 'Tables',        icon: LayoutGrid },
  ]},
  { section: 'Management', items: [
    { path: '/products', label: 'Products',  icon: Package },
    { path: '/clients',  label: 'Clients',   icon: Users },
    { path: '/bills',    label: 'Bills',     icon: Receipt },
    { path: '/reports',  label: 'Reports',   icon: BarChart3 },
  ]},
  { section: 'Admin', items: [
    { path: '/settings', label: 'Settings', icon: Settings },
  ]},
];

export function Layout() {
  return (
    <div className="flex h-screen w-full overflow-hidden" style={{ background: '#F6F7F9' }}>
      {/* Sidebar */}
      <aside
        className="flex flex-col h-full shrink-0 overflow-y-auto"
        style={{ width: 220, background: '#1C2333', borderRight: '1px solid #252E42' }}
      >
        {/* Logo */}
        <div className="flex items-center gap-3 px-5 py-5" style={{ borderBottom: '1px solid #252E42' }}>
          <div
            className="flex items-center justify-center rounded-xl"
            style={{ width: 36, height: 36, background: '#F59E0B' }}
          >
            <UtensilsCrossed size={18} color="white" />
          </div>
          <div>
            <div style={{ color: 'white', fontSize: 14, fontWeight: 600, lineHeight: 1.2 }}>RestaurantOS</div>
            <div style={{ color: '#8892A4', fontSize: 11 }}>Order Management</div>
          </div>
        </div>

        {/* Navigation */}
        <nav className="flex-1 px-3 py-4 flex flex-col gap-6">
          {navItems.map(group => (
            <div key={group.section}>
              <div
                className="px-2 mb-2 uppercase tracking-widest"
                style={{ color: '#4A5568', fontSize: 10, fontWeight: 600 }}
              >
                {group.section}
              </div>
              <div className="flex flex-col gap-0.5">
                {group.items.map(item => (
                  <NavLink
                    key={item.path}
                    to={item.path}
                    end={item.path === '/'}
                    className={({ isActive }) =>
                      `flex items-center gap-3 px-3 py-2.5 rounded-lg transition-all duration-150 ${
                        isActive
                          ? 'text-white'
                          : 'text-[#8892A4] hover:text-white hover:bg-white/5'
                      }`
                    }
                    style={({ isActive }) => isActive ? { background: '#F59E0B', color: 'white' } : {}}
                  >
                    {({ isActive }) => (
                      <>
                        <item.icon size={16} color={isActive ? 'white' : undefined} />
                        <span style={{ fontSize: 13, fontWeight: 500 }}>{item.label}</span>
                      </>
                    )}
                  </NavLink>
                ))}
              </div>
            </div>
          ))}
        </nav>

        {/* Footer */}
        <div className="px-4 py-4" style={{ borderTop: '1px solid #252E42' }}>
          <div className="flex items-center gap-3">
            <div
              className="flex items-center justify-center rounded-full shrink-0"
              style={{ width: 32, height: 32, background: '#F59E0B', fontSize: 13, color: 'white', fontWeight: 600 }}
            >
              M
            </div>
            <div>
              <div style={{ color: 'white', fontSize: 12, fontWeight: 500 }}>Manager</div>
              <div style={{ color: '#8892A4', fontSize: 11 }}>Admin Access</div>
            </div>
          </div>
        </div>
      </aside>

      {/* Main content */}
      <main className="flex-1 h-full overflow-y-auto">
        <Outlet />
      </main>
    </div>
  );
}
