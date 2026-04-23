# Campus Resource Exchange (CREX)

A Java-based console application for managing campus resource sharing and borrowing using JDBC and MySQL database.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Class Documentation](#class-documentation)

## 🎯 Overview

Campus Resource Exchange (CREX) is a console-based application that enables students to share and borrow resources within a campus community. The system implements a complete CRUD (Create, Read, Update, Delete) functionality using JDBC for database operations with proper separation of concerns through DAO (Data Access Object) pattern.

## ✨ Features

- **User Management**: Register new users with name and email
- **Resource Management**: Add resources with owner information
- **Resource Browsing**: View all available resources in the system
- **Borrowing System**: Borrow available resources
- **Return System**: Return borrowed resources and update availability
- **SQL Injection Protection**: Uses PreparedStatement for all database operations
- **Duplicate Entry Handling**: Graceful error handling for duplicate registrations

## 🛠️ Technology Stack

- **Language**: Java
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J (com.mysql.cj.jdbc.Driver)
- **Design Pattern**: DAO (Data Access Object)
- **Architecture**: Layered Architecture (Presentation → Business → Data Access)

## 🗄️ Database Schema

### Database Name: `practice`

### Tables:

#### 1. users
```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);
```

#### 2. resources
```sql
CREATE TABLE resources (
    resource_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    owner_id INT NOT NULL,
    is_available BOOLEAN DEFAULT true,
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);
```

#### 3. borrow_records
```sql
CREATE TABLE borrow_records (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    resource_id INT NOT NULL,
    borrower_id INT NOT NULL,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP NULL,
    FOREIGN KEY (resource_id) REFERENCES resources(resource_id),
    FOREIGN KEY (borrower_id) REFERENCES users(user_id)
);
```

## 📁 Project Structure

```
JDBC_project/
├── src/
│   └── crex/
│       ├── MainApp.java          # Main entry point with menu-driven interface
│       ├── DBConnection.java     # Database connection utility
│       ├── UserDAO.java           # User data access operations
│       ├── ResourceDAO.java       # Resource data access operations
│       └── BorrowDAO.java         # Borrow/Return data access operations
├── bin/                           # Compiled class files
├── .vscode/                       # VS Code configuration
├── Documentation.txt              # Additional documentation
└── README.md                      # This file
```

## 🚀 Installation

### Prerequisites
1. Java Development Kit (JDK) 8 or higher
2. MySQL Server 8.0 or higher
3. MySQL Connector/J JAR file

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Mr-hars007/JDBC_project.git
   cd JDBC_project
   ```

2. **Set up the MySQL database**
   ```sql
   CREATE DATABASE practice;
   USE practice;
   
   -- Create tables (use schema provided above)
   ```

3. **Update database credentials**
   
   Edit `src/crex/DBConnection.java` and update:
   ```java
   String url = "jdbc:mysql://localhost:3306/practice";
   String user = "your_username";
   String password = "your_password";
   ```

4. **Add MySQL Connector JAR to classpath**
   - Download MySQL Connector/J from [MySQL website](https://dev.mysql.com/downloads/connector/j/)
   - Add the JAR file to your project's build path

5. **Compile the project**
   ```bash
   javac -d bin src/crex/*.java
   ```

6. **Run the application**
   ```bash
   java -cp bin crex.MainApp
   ```

## 💻 Usage

### Main Menu Options

```
===== CAMPUS RESOURCE EXCHANGE =====
1. Register User
2. Add Resource
3. View Available Resources
4. Borrow Resource
5. Return Resource
6. Exit
```

### Example Workflow

1. **Register a User**
   ```
   Enter choice: 1
   Enter name: John Doe
   Enter email: john@example.com
   User registered successfully!
   ```

2. **Add a Resource**
   ```
   Enter choice: 2
   Enter resource title: Data Structures Textbook
   Enter owner user ID: 1
   Resource added successfully!
   ```

3. **View Available Resources**
   ```
   Enter choice: 3
   
   Available Resources:
   1 - Data Structures Textbook
   2 - Scientific Calculator
   ```

4. **Borrow a Resource**
   ```
   Enter choice: 4
   Enter resource ID: 1
   Enter your user ID: 2
   Resource borrowed successfully!
   ```

5. **Return a Resource**
   ```
   Enter choice: 5
   Enter resource ID: 1
   Resource returned successfully!
   ```

## 📚 Class Documentation

### MainApp.java
- **Purpose**: Entry point of the application
- **Responsibilities**: 
  - Display menu-driven interface
  - Route user choices to appropriate DAO methods
  - Handle user input via Scanner
- **Key Method**: `main(String[] args)`

### DBConnection.java
- **Purpose**: Centralized database connection management
- **Responsibilities**:
  - Load MySQL JDBC driver
  - Provide connection objects to DAO classes
  - Manage database credentials
- **Key Method**: `getConnection()` - Returns a Connection object
- **Design Benefit**: Single point of configuration for database settings

### UserDAO.java
- **Purpose**: Handle all user-related database operations
- **Methods**:
  - `registerUser(Scanner sc)` - Register new users into the system
- **Security Features**:
  - PreparedStatement to prevent SQL injection
  - Duplicate email detection via exception handling
- **Database Table**: `users`

### ResourceDAO.java
- **Purpose**: Handle all resource-related database operations
- **Methods**:
  - `addResource(Scanner sc)` - Add new resources to the system
  - `viewAvailable()` - Display all available resources
- **Security Features**:
  - PreparedStatement for parameterized queries
- **Database Table**: `resources`

### BorrowDAO.java
- **Purpose**: Handle borrowing and returning of resources
- **Methods**:
  - `borrowResource(Scanner sc)` - Process resource borrowing
    - Validates resource availability
    - Creates borrow record
    - Updates resource availability status
  - `returnResource(Scanner sc)` - Process resource returns
    - Updates borrow record with return timestamp
    - Marks resource as available
- **Database Tables**: `borrow_records`, `resources`
- **Transaction Logic**: Multiple SQL statements executed for consistency

## 🔒 Security Features

- **SQL Injection Prevention**: All user inputs are handled via PreparedStatement
- **Exception Handling**: Graceful error handling for database operations
- **Input Validation**: Checks for resource availability before borrowing
- **Unique Constraints**: Email uniqueness enforced at database level

## 🎯 Design Patterns Used

1. **DAO Pattern**: Separates business logic from data access logic
2. **Singleton-like Connection**: Centralized connection management
3. **Layered Architecture**: Clear separation between presentation and data layers

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is available for educational purposes.

## 👤 Author

**Mr-hars007**
- GitHub: [@Mr-hars007](https://github.com/Mr-hars007)

## 📧 Contact

For questions or suggestions, please open an issue on GitHub.

---

**Note**: Remember to update database credentials in `DBConnection.java` before running the application.
