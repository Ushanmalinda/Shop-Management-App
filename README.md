# Shop Management App | GitHub

Built a comprehensive console-based shop management system using Java with object-oriented programming principles.
Implemented real-time inventory management, point-of-sale (POS) functionality with shopping cart system, dynamic bill generation with itemized receipts, and automatic stock tracking.
Developed modular architecture with separate classes for Storage, Product, Cashier, and Bill management, supporting multiple product operations and order processing workflows.

**Tools:** Java, OOP Design Patterns, ArrayList Collections, Scanner I/O

## Features
- **Inventory Management:** Add, remove, and view products with real-time stock tracking
- **Dynamic Pricing:** Update product prices on-the-fly
- **Stock Control:** Modify and monitor inventory levels with out-of-stock validation
- **POS System:** Interactive order placement with shopping cart functionality
- **Bill Generation:** Automated itemized bill printing with total calculations
- **Data Persistence:** In-memory data storage using ArrayList collections

## How to Run
```bash
javac ShopManagementApp.java
java ShopManagementApp
```

## Requirements
- Java JDK 8 or higher

## System Architecture
- **Storage Class:** Manages product inventory and stock operations
- **Product Class:** Encapsulates product attributes (ID, name, price, stock)
- **Cashier Class:** Handles order processing and cart management
- **Bill Class:** Extends Cashier for receipt generation and cost calculations
