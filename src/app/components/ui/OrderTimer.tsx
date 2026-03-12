import { useState, useEffect } from 'react';
import { Clock } from 'lucide-react';

interface OrderTimerProps {
  createdAt: Date;
  estimatedPrepTime?: number; // minutes
  showIcon?: boolean;
}

function getElapsedMinutes(createdAt: Date): number {
  return Math.floor((Date.now() - createdAt.getTime()) / 60000);
}

export function OrderTimer({ createdAt, estimatedPrepTime, showIcon = true }: OrderTimerProps) {
  const [elapsed, setElapsed] = useState(getElapsedMinutes(createdAt));

  useEffect(() => {
    const interval = setInterval(() => {
      setElapsed(getElapsedMinutes(createdAt));
    }, 30000);
    return () => clearInterval(interval);
  }, [createdAt]);

  const isDelayed = estimatedPrepTime ? elapsed > estimatedPrepTime : elapsed > 20;
  const isCritical = estimatedPrepTime ? elapsed > estimatedPrepTime * 1.5 : elapsed > 35;

  const color = isCritical ? 'text-red-600' : isDelayed ? 'text-amber-600' : 'text-green-600';
  const bgColor = isCritical ? 'bg-red-50' : isDelayed ? 'bg-amber-50' : 'bg-green-50';

  const display = elapsed < 60
    ? `${elapsed}m`
    : `${Math.floor(elapsed / 60)}h ${elapsed % 60}m`;

  return (
    <span className={`inline-flex items-center gap-1 rounded-full px-2 py-0.5 text-[11px] font-medium ${color} ${bgColor}`}>
      {showIcon && <Clock size={10} />}
      {display}
    </span>
  );
}
