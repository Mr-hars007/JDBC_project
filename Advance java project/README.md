# Campus Resource Exchange - Advance Java Project (Swing)

This project is a Swing-based GUI application for the Campus Resource Exchange system with **Hybrid Database Support**.

## Features
- **Auto-Detection**: The app automatically detects if a MySQL server is running. If not, it falls back to **Mock Data Mode**.
- **Login & Registration**: Unified login screen with a registration dialog.
- **Resource Management**: Add and view available resources.
- **Smart Borrowing**: Borrow resources without manually entering User ID (it uses your logged-in session).
- **Borrowed Tracking**: View all items currently borrowed by you in a dedicated list.

## Database Setup (Optional)
If you want to use a real MySQL database:
1. Ensure MySQL is installed and running on `localhost:3306`.
2. Create a database named `practice`.
3. Run the following SQL script:

```sql
CREATE DATABASE practice;
USE practice;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE resources (
    resource_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    owner_id INT,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE borrow_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    resource_id INT,
    borrower_id INT,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP NULL,
    FOREIGN KEY (resource_id) REFERENCES resources(resource_id),
    FOREIGN KEY (borrower_id) REFERENCES users(id)
);
```

4. Add the `mysql-connector-j.jar` to your project classpath.
5. Update credentials in `crex.db.DBConnection` if necessary.

## How to Run
1. Navigate to the project folder.
2. Compile and run `crex.gui.LoginFrame`.
3. If no database is found, just use any username/password to login (Mock Mode).
