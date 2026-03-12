import { OrderStatus, Priority, TableStatus, BillStatus } from '../../types';

const orderStatusConfig: Record<OrderStatus, { label: string; className: string }> = {
  new:       { label: 'New',             className: 'bg-gray-100 text-gray-700' },
  sent:      { label: 'Sent to Kitchen', className: 'bg-indigo-100 text-indigo-700' },
  preparing: { label: 'Preparing',       className: 'bg-blue-100 text-blue-700' },
  ready:     { label: 'Ready',           className: 'bg-green-100 text-green-700' },
  served:    { label: 'Served',          className: 'bg-purple-100 text-purple-700' },
  completed: { label: 'Completed',       className: 'bg-gray-200 text-gray-500' },
};

const priorityConfig: Record<Priority, { label: string; className: string }> = {
  normal:   { label: 'Normal',   className: 'bg-gray-100 text-gray-500' },
  high:     { label: 'High',     className: 'bg-amber-100 text-amber-700' },
  critical: { label: 'Critical', className: 'bg-red-100 text-red-700' },
};

const tableStatusConfig: Record<TableStatus, { label: string; className: string }> = {
  free:     { label: 'Free',             className: 'bg-green-100 text-green-700' },
  occupied: { label: 'Occupied',         className: 'bg-blue-100 text-blue-700' },
  ordering: { label: 'Ordering',         className: 'bg-amber-100 text-amber-700' },
  waiting:  { label: 'Waiting for Food', className: 'bg-orange-100 text-orange-700' },
  pay:      { label: 'Ready to Pay',     className: 'bg-purple-100 text-purple-700' },
};

const billStatusConfig: Record<BillStatus, { label: string; className: string }> = {
  pending:   { label: 'Pending',   className: 'bg-amber-100 text-amber-700' },
  paid:      { label: 'Paid',      className: 'bg-green-100 text-green-700' },
  cancelled: { label: 'Cancelled', className: 'bg-red-100 text-red-600' },
};

interface Props {
  type: 'order' | 'priority' | 'table' | 'bill';
  value: string;
  size?: 'sm' | 'md';
}

export function StatusBadge({ type, value, size = 'md' }: Props) {
  let config: { label: string; className: string } | undefined;

  if (type === 'order') config = orderStatusConfig[value as OrderStatus];
  else if (type === 'priority') config = priorityConfig[value as Priority];
  else if (type === 'table') config = tableStatusConfig[value as TableStatus];
  else if (type === 'bill') config = billStatusConfig[value as BillStatus];

  if (!config) return null;

  return (
    <span
      className={`inline-flex items-center rounded-full font-medium ${config.className} ${
        size === 'sm' ? 'px-2 py-0.5 text-[11px]' : 'px-2.5 py-1 text-xs'
      }`}
    >
      {config.label}
    </span>
  );
}
