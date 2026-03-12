Professional Restaurant OMS
Product Specification + UX Architecture Guide (For Figma AI)

Design a professional desktop Order Management System (OMS) for bakeries, cafés, and restaurants.

The system replaces paper-based order management, reduces confusion between staff, and improves operational flow between waiters, kitchen staff, and managers.

The system must feel like a commercial SaaS product used in real restaurants, not a basic internal tool.

Target platform:
Desktop Application (Java-based)

Primary resolution:
1920×1080

Product Vision

The OMS must function as a Restaurant Control Center where staff can instantly understand:

• What orders exist
• What needs to be cooked
• What has priority
• Which tables are occupied
• Which orders are ready
• Which bills are pending

The system should remove communication errors between front-of-house and kitchen staff.

Core Design Principles
Operational Clarity

Every screen must communicate restaurant state instantly.

Speed

Common actions must take less than 3 seconds.

Low Cognitive Load

The interface must avoid overwhelming staff during busy service hours.

Eye Comfort

The UI must support 8–12 hour shifts.

Visual Hierarchy

Orders and kitchen activity must always dominate the interface.

Real Restaurant Workflow Model

The system must visually represent the full order lifecycle.

Customer arrives
→ Table assigned
→ Order created
→ Order sent to kitchen
→ Kitchen preparing
→ Order ready
→ Order served
→ Bill generated
→ Payment processed
→ Table cleared

The interface must reflect this lifecycle clearly.

System Architecture

The system should be divided into three main operational layers.

1 — Real-Time Operations Layer

Used continuously by waiters and kitchen staff.

Modules:

Dashboard
Orders
Kitchen Queue
Tables

These modules must be fast to access and visually dominant.

2 — Business Management Layer

Used mainly by managers.

Modules:

Products
Clients
Bills
Reports

3 — System Administration Layer

Modules:

Settings
User management
Permissions

Navigation Layout

Left vertical sidebar navigation.

Sections in order:

Dashboard
Orders
Kitchen Queue
Tables
Products
Clients
Bills
Reports
Settings

Operational modules should appear at the top of navigation.

Dashboard Screen

Purpose:
Provide a live operational overview.

Widgets:

Active Orders
Orders Being Prepared
Orders Ready to Serve
Completed Orders Today

Operational panels:

Kitchen Queue Preview
Table Occupancy Map
Revenue Today
Average Preparation Time

Orders Screen (Primary Screen)

This is the central interface of the system.

Layout:

Three-panel workspace.

Panel 1
Orders Queue

Panel 2
Selected Order Details

Panel 3
Product Selection

Orders Queue

Displays all active orders.

Each order card shows:

Order ID
Table number
Client name
Number of items
Elapsed time
Order status
Priority indicator

Statuses:

New
Sent to kitchen
Preparing
Ready
Served
Completed

Orders must automatically sort by priority and waiting time.

Smart Priority System

To make the OMS feel like a professional product, implement dynamic priority indicators.

Priority is influenced by:

Order waiting time
Preparation time
Manual priority flag
Special instructions
Order size

Priority levels:

Normal
High Priority
Critical

Visual indicators:

Priority badge
Highlighted border
Background tint

Critical orders appear at the top of the queue.

Order Details Panel

Shows full order information.

Information:

Order ID
Table number
Client name
Waiter name
Time created
Estimated preparation time
Special instructions

Item list:

Product name
Quantity
Preparation status
Notes

Controls:

Add product
Remove product
Send to kitchen
Mark as preparing
Mark as ready
Mark as served
Complete order

Fast Product Selection Panel

Right panel allows very fast order creation.

Products organized by:

Category

Examples:

Drinks
Pastries
Sandwiches
Meals
Desserts

Features:

Search bar
Category filter
Clickable product cards

Clicking product adds it instantly to the order.

Kitchen Queue Screen

Dedicated interface for kitchen staff.

Layout inspired by Kanban boards.

Columns:

New Orders
Preparing
Ready

Each card represents an order or group of items.

Card shows:

Order ID
Table number
Items list
Special notes
Elapsed time
Priority indicator

High priority orders appear at the top of the column.

Kitchen staff can drag cards between columns.

Preparation Time Intelligence

Each product includes estimated preparation time.

Example:

Espresso
2 minutes

Croissant
5 minutes

Sandwich
8 minutes

The system calculates estimated completion time for each order.

Orders nearing delay should be highlighted.

Tables Screen

Visual map or grid of restaurant tables.

Each table card displays:

Table number
Status
Number of guests
Active order indicator

Table statuses:

Free
Occupied
Ordering
Waiting for food
Ready to pay

Colors represent each status.

Clicking a table opens its order.

Bills & Payments

Billing screen for financial tracking.

Table columns:

Bill ID
Table
Client
Total
Payment method
Status

Statuses:

Pending
Paid
Cancelled

Payment options:

Cash
Card
Digital

Products Management

Product catalog management.

Columns:

Product name
Category
Price
Preparation time
Availability

Managers can:

Add product
Edit product
Disable product

Optional product image.

Clients Management

Customer database.

Columns:

Client name
Phone
Email
Total visits
Total spending
Last order date

Useful for loyalty tracking.

Reports

Analytics dashboard.

Charts showing:

Daily revenue
Orders per hour
Top products
Preparation times
Staff performance

UX Intelligence (Professional Features)

To feel like a commercial restaurant system, implement:

Smart Order Timer

Each order displays:

Time since creation

Visual timer indicator:

Green → Normal
Orange → Delayed
Red → Critical

Kitchen Delay Alerts

Orders exceeding estimated prep time should be highlighted.

Table Turnover Tracking

Track how long each table has been occupied.

Helps staff improve table rotation.

Quick Reorder

Ability to reorder previous orders for repeat customers.

Component System

Reusable UI components:

Order cards
Table cards
Status badges
Product tiles
Data tables
Dropdown menus
Search bars
Modals
Confirmation dialogs

Button System

Primary Button
Accent color background

Secondary Button
Outline style

Danger Button
Red tone

Disabled Button
Reduced opacity

Status Badge System

Badges for:

New
Preparing
Ready
Served
Completed
Priority

Rounded pill style.

Color System

The interface must follow calm color psychology.

Background
Soft neutral gray
#F6F7F9

Cards
White

Primary accent
Warm amber
#F59E0B

Status colors:

Preparing
Soft blue

Ready
Soft green

Priority
Soft red

Warning
Soft amber

Avoid neon or overly saturated colors.

Typography

Use a modern sans-serif.

Recommended:

Inter
Roboto
SF Pro

Hierarchy:

Page titles
26px bold

Section headers
18px

Body text
14px

Labels
12px

Spacing System

Spacing scale:

4px
8px
12px
16px
24px
32px

Cards should feel spacious and readable.

Interaction Design

Hover states on cards and rows.

Smooth transitions.

Minimal animations.

Immediate visual feedback for actions.

Accessibility

Minimum contrast ratio 4.5:1.

Large clickable areas.

Readable typography.

Clear icons.

Visual Feel

The interface should feel like a modern restaurant command center.

Professional
Calm
Efficient
Organized
Warm

Avoid:

Corporate coldness
Excessive decoration
Visual clutter

Deliverables for Figma AI

Generate:

Full desktop UI layout
Navigation system
Dashboard
Orders workflow
Kitchen queue board
Tables management
Products management
Clients database
Billing system
Reports dashboard

Also include a reusable design system and component library.