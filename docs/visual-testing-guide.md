# Visual Testing Guide for Selection State Text Contrast

## Purpose
This guide provides step-by-step instructions for manually testing the dark text enforcement on selected table/list cells across all views in the RestaurantOS application.

## Pre-requisites
- Application must be running: `./gradlew run`
- Navigate to each view using the sidebar navigation

## Expected Behavior
**GENERAL RULE:** All text in selected rows/cells should be dark (#1F1A17 - nearly black) except for buttons which should maintain white text on their colored backgrounds.

**Selection background color:** #FFF3E6 (light peach/orange)

---

## Test Cases

### 1. ClientsView (Clients Tab)

**Location:** Main navigation → Clients

**Test Steps:**
1. Click on any client row in the table
2. Observe all columns: Name, Email, Phone, Visits, Spending, Last Visit, Actions

**Expected Results:**
- ✅ All text in selected row is dark (#1F1A17)
- ✅ Selected row background is light peach (#FFF3E6)
- ✅ Formatted currency in "Spending" column is dark
- ✅ Text remains readable and high-contrast

**Screenshot Location:** `docs/screenshots/clients-selection-state.png`

---

### 2. ProductsView (Products Tab)

**Location:** Main navigation → Products

**Test Steps:**
1. Click on any product row in the table
2. Pay special attention to:
   - "Available" column (contains emoji ✅ Yes / ❌ No labels)
   - "Edit" button column

**Expected Results:**
- ✅ All text in selected row is dark
- ✅ Emoji labels in "Available" column show dark text ("Yes"/"No")
- ✅ **CRITICAL:** Edit button maintains white text on its colored background (not dark)
- ✅ Formatted price in "Price" column is dark

**Screenshot Location:** `docs/screenshots/products-selection-state.png`

---

### 3. BillsView (Bills Tab)

**Location:** Main navigation → Bills

**Test Steps:**
1. Click on any bill row in the table
2. Observe all columns including the "Total" column with formatted currency

**Expected Results:**
- ✅ All text in selected row is dark
- ✅ Formatted currency in "Total" column is dark and readable
- ✅ Date and payment method text is dark
- ✅ Status text is dark

**Screenshot Location:** `docs/screenshots/bills-selection-state.png`

---

### 4. OrdersView (Orders Tab) - COMPLEX

**Location:** Main navigation → Orders

**Test Steps - Part A: Order List**
1. Click on any order in the LEFT panel order list
2. Observe the multi-line cell format:
   - Top line: Bold order ID (#XXX) and table number (style class: `bold-label`)
   - Bottom line: Client name, status, and total (style class: `muted-text`)

**Expected Results - Part A:**
- ✅ Bold order ID and table number are dark (not lighter)
- ✅ Muted text line is dark (not gray/faded)
- ✅ Both lines maintain readability on light selection background

**Test Steps - Part B: Order Items Table**
1. Select an order from the left panel (if not already selected)
2. Click on any item row in the RIGHT panel items table
3. Pay attention to the "Remove" button column (red X button)

**Expected Results - Part B:**
- ✅ All item text (name, quantity, price) is dark in selected row
- ✅ **CRITICAL:** Remove button (X) maintains white text on red background (not dark)
- ✅ Formatted prices are dark

**Screenshot Locations:** 
- `docs/screenshots/orders-list-selection-state.png`
- `docs/screenshots/orders-items-selection-state.png`

---

### 5. DashboardView (Dashboard Tab)

**Location:** Main navigation → Dashboard (default view)

**Test Steps:**
1. Look at the "Recent Orders" list in the dashboard
2. Click on any order in the list
3. Observe the single-line cell with order information (style class: `order-cell-text`)

**Expected Results:**
- ✅ All order information text is dark (table #, client, status, time, total)
- ✅ Formatted currency is dark
- ✅ Text maintains readability

**Screenshot Location:** `docs/screenshots/dashboard-selection-state.png`

---

### 6. SettingsView (Settings Tab) - CRITICAL

**Location:** Main navigation → Settings

**Test Steps:**
1. Click on any staff member row in the staff table
2. **CRITICAL:** Pay special attention to the "Status" column which uses colored text:
   - "Active" (normally green #10B981)
   - "On Break" (normally orange #F59E0B)
   - Others (normally red #EF4444)

**Expected Results:**
- ✅ **CRITICAL:** Status text is DARK (#1F1A17) when row selected, NOT colored
- ✅ "Active" status does NOT show green text in selected row
- ✅ "On Break" status does NOT show orange text in selected row
- ✅ Other statuses do NOT show red text in selected row
- ✅ All other columns (Name, Role, Contact) show dark text

**Why Critical:** This tests the explicit status class override rules that prevent colored text on light selection backgrounds, which could create poor contrast (especially orange on light orange).

**Screenshot Location:** `docs/screenshots/settings-selection-state.png`

---

## Common Issues to Watch For

### ❌ FAILURE MODES

1. **White-on-white text:**
   - If text appears to disappear or becomes unreadable when row selected
   - Most likely in cells with custom styling or nested components

2. **Colored text on light background:**
   - Especially in SettingsView status column
   - Orange (#F59E0B) on light orange (#FFF3E6) = poor contrast
   - Green or red text should become dark in selected rows

3. **Button text turns dark:**
   - Edit buttons, Remove buttons should keep WHITE text
   - Dark text on colored button backgrounds = poor contrast
   - This indicates button exception rule isn't working

4. **Muted/gray text stays muted:**
   - "Muted" text should become dark when selected
   - Gray (#999) on light background can have insufficient contrast

### ✅ SUCCESS INDICATORS

- All text is clearly readable
- ~15:1 contrast ratio achieved (dark on light)
- No squinting required to read selected rows
- Buttons maintain their original styling
- Status colors don't interfere with readability

---

## Screenshot Checklist

Create screenshots for each view and save to `docs/screenshots/`:

- [ ] `clients-selection-state.png` - ClientsView with row selected
- [ ] `products-selection-state.png` - ProductsView with row selected (show Edit button)
- [ ] `bills-selection-state.png` - BillsView with row selected
- [ ] `orders-list-selection-state.png` - OrdersView list with order selected
- [ ] `orders-items-selection-state.png` - OrdersView items table with item selected (show Remove button)
- [ ] `dashboard-selection-state.png` - DashboardView with order selected
- [ ] `settings-selection-state.png` - SettingsView with staff row selected (CRITICAL: show status column)

---

## Reporting Issues

If any test case fails:

1. **Document the failure:**
   - Which view
   - Which column/element
   - Expected vs actual appearance
   - Screenshot of the issue

2. **Check CSS specificity:**
   - Use browser DevTools or similar to inspect applied styles
   - Look for rules overriding the selection state text color
   - Note the CSS class causing the issue

3. **Verify CSS coverage:**
   - Check if element has a CSS class not covered by current rules
   - Check if `!important` flag is needed
   - Consider if additional explicit selectors required

4. **Test fix:**
   - Add specific rule to `style.css`
   - Rebuild and retest
   - Document the fix in selection-state-audit.md

---

## Automation Considerations

### Future Regression Tests

Consider adding automated visual regression tests:
- Use TestFX for JavaFX UI testing
- Capture screenshots of selected states programmatically
- Compare against baseline images
- Alert on visual differences

### Test Framework Setup
```java
// Example pseudocode for automated test
@Test
public void testProductsViewSelectionTextContrast() {
    // Select first row
    clickOn(".table-view .table-row-cell:nth-child(1)");
    
    // Get text color of selected cell
    Color textColor = lookup(".table-row-cell:selected .label")
        .query()
        .getTextFill();
    
    // Assert dark text
    assertEquals("#1F1A17", toHex(textColor));
    
    // Get button text color
    Color buttonColor = lookup(".table-row-cell:selected .button")
        .query()
        .getTextFill();
    
    // Assert white button text
    assertEquals("#FFFFFF", toHex(buttonColor));
}
```

---

## Sign-off

After completing all test cases:

**Tester Name:** ________________

**Date:** ________________

**Result:** ⬜ PASS ⬜ FAIL (with issues documented)

**Notes:**
