postgresql://postgres.tbkmynwpovebpgwmdsil:[YOUR-PASSWORD]@aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres# Point of Sale (POS) System Implementation

This plan outlines the creation of a new Point of Sale (POS) application using Java, mirroring the secure, multi-scene architecture of your existing `test-student-management` project.

## User Review Required

> [!IMPORTANT]  
> Please review the project location and database schema before we begin coding. I plan to create a new folder for this project so we do not overwrite your existing Student Management System.

## Open Questions

> [!WARNING]  
> 1. **Project Location:** Should I create this new POS system in a new directory `pos-system` beside `test-student-management`? 
> 2. **Database:** Should we create a new `pos_system.sql` script for you to run on your Supabase instance, containing tables for `products`, `orders`, `order_items`, and `users`?
> 3. **Design:** Would you prefer the same simple JavaFX UI, or should we incorporate a more modern stylesheet (CSS)?

## Proposed Changes

We will create a new Maven project mimicking your current structure.

### `pom.xml` Setup
We will set up a new `pom.xml` with dependencies for:
*   JavaFX (Controls, FXML)
*   PostgreSQL JDBC Driver
*   Dotenv-java (for secure `.env` credentials)

### Database Schema (`pos_system.sql`)
We will create a foundational schema:
*   `users`: For login authentication (Admin/Cashier).
*   `products`: Storing item names, prices, and stock.
*   `orders`: Tracking overall transactions.
*   `order_items`: Tracking individual items within an order.

### Project Structure (MVC)
*   **`app/`**: `PosApplication.java` - Main entry point.
*   **`util/`**: `Database.java` (using `.env`) and `PasswordUtil.java`.
*   **`model/`**: `User`, `Product`, `Order`, `OrderItem`.
*   **`repository/`**: `UserRepository`, `ProductRepository`, `OrderRepository`.
*   **`controller/`**: `LoginController`, `PosDashboardController`, `InventoryController`.
*   **`factory/`**: `WindowFactory` for handling scene transitions.

### Views (src/main/resources/...)
*   **`login-view.fxml`**: Secure authentication screen.
*   **`pos-dashboard.fxml`**: The main POS checkout screen (adding items, calculating totals).
*   **`inventory-view.fxml`**: Screen to manage products.

## Verification Plan

### Automated Tests
*   Compile the project using Maven to ensure all dependencies are correct.
*   Check if JavaFX scenes can load without `NullPointerException`s on the FXML files.

### Manual Verification
*   We will require you to provide a `.env` file (or reuse your existing one with new tables) to test the database connection locally.
*   Run the application to test the transition from the Login screen to the Main POS screen.
