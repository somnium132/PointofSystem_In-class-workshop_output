# JavaFX Point of Sale (POS) System

A complete Point of Sale desktop application built with JavaFX and PostgreSQL. This system provides a robust architecture for managing products, tracking inventory, and securely processing checkout transactions.

## Features
- **Secure Authentication**: User login and registration with hashed passwords.
- **Inventory Management**: View products, prices, and available stock.
- **Dynamic Shopping Cart**: Add products to the cart, adjust quantities, and dynamically calculate subtotals and totals.
- **Transaction Handling**: Secure checkout process utilizing SQL Transactions to atomically update inventory stock and create order records.
- **Modern Database**: Remote PostgreSQL connection utilizing Supabase and PgBouncer.

## Technology Stack
- **Language**: Java 21
- **UI Framework**: JavaFX
- **Build Tool**: Maven
- **Database**: PostgreSQL (Supabase)
- **Security**: Environment variables via `dotenv-java` and SHA-256 password hashing.

## Prerequisites
- Java Development Kit (JDK) 21 or higher.
- Maven (optional, as the Maven Wrapper `mvnw` is included).
- A PostgreSQL database (e.g., Supabase).

## Setup & Installation

### 1. Database Setup
Execute the provided `pos_system.sql` script in your PostgreSQL database to create the necessary tables (`users`, `products`, `orders`, `order_items`) and insert some dummy products.

### 2. Configure Environment Variables
Create a `.env` file in the root of the project directory with your database credentials:
```env
DB_URL=jdbc:postgresql://[YOUR-DB-HOST]:[PORT]/[DB-NAME]?prepareThreshold=0
DB_USER=your_db_username
DB_PASSWORD=your_db_password
```
*(Note: `?prepareThreshold=0` is required if you are connecting through a connection pooler like PgBouncer in transaction mode).*

### 3. Compile and Run
You can run the application directly using the Maven Wrapper. Open your terminal in the project directory and run:

**On Windows:**
```powershell
.\mvnw.cmd clean javafx:run
```

**On Mac/Linux:**
```bash
./mvnw clean javafx:run
```

## Project Structure
The application follows a clean MVC-style architecture:
- `model/`: Data structures representing Database entities (`UserAccount`, `Product`, `Order`, `OrderItem`).
- `repository/`: Data Access Objects containing SQL queries and JDBC logic.
- `controller/`: JavaFX controllers managing UI interactions and business logic.
- `factory/`: Window factories for handling smooth scene transitions.
- `util/`: Helper classes for database connections and password hashing.
