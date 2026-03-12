import { createBrowserRouter } from 'react-router';
import { Layout } from './components/Layout';
import { Dashboard } from './pages/Dashboard';
import { Orders } from './pages/Orders';
import { KitchenQueue } from './pages/KitchenQueue';
import { Tables } from './pages/Tables';
import { Products } from './pages/Products';
import { Clients } from './pages/Clients';
import { Bills } from './pages/Bills';
import { Reports } from './pages/Reports';
import { Settings } from './pages/Settings';

export const router = createBrowserRouter([
  {
    path: '/',
    Component: Layout,
    children: [
      { index: true,         Component: Dashboard },
      { path: 'orders',      Component: Orders },
      { path: 'kitchen',     Component: KitchenQueue },
      { path: 'tables',      Component: Tables },
      { path: 'products',    Component: Products },
      { path: 'clients',     Component: Clients },
      { path: 'bills',       Component: Bills },
      { path: 'reports',     Component: Reports },
      { path: 'settings',    Component: Settings },
    ],
  },
]);
