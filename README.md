# Online Food Delivery System

A Java-based enterprise solution for managing online food delivery operations, built with Oracle Database integration. This system provides comprehensive customer management, order processing, and database operations specifically optimized for Oracle environments.

## Features

### Database Integration
- Oracle Database implementation (tested with Oracle XE)
- JDBC-based connectivity
- Prepared statement usage for secure queries
- Connection pooling optimization

### Authentication System
- Dual authentication paths:
  - Customer login via email/password
  - Administrative access control
- Secure password handling
- Session management

### User Interface
- Swing-based graphical interface
- Responsive panel switching
- Custom-styled components
- Background image support

### User Interface Images

#### Main Interface
![Main Interface](Userinterfaceimg\entry.png)
- Landing page with customer and admin login options
- Themed with food delivery artwork
- Pizza-themed background with vibrant colors

#### Login Interfaces
![Customer Login](Userinterfaceimg\customer.png)
- Customer login interface with email and password fields
- Clean, simple design for easy access

![Admin Login](Userinterfaceimg\admin.png)
- Administrative login portal
- Secure access point for system management

#### Customer Management
![Customer List](Userinterfaceimg\customers.png)
- Customer database interface
- Display of customer information and management options

![Customer Form](Userinterfaceimg\customerEdit.png)
- Customer information editing interface
- Profile management with multiple fields

#### Employee Data Management
![Employee Details](Userinterfaceimg\employee.png)
- Comprehensive employee data interface

#### Order Management
![Menu Interface](Userinterfaceimg\orderselection.png)
- Menu selection and order placement interface
- Real-time pricing and quantity management
- Order confirmation system

![Order History](Userinterfaceimg\order.png)
- Detailed order tracking and history
- Transaction records with timestamps

![Order Now](Userinterfaceimg\ordernow.png)
- Quick order placement interface
- Delivery personnel visualization
- Brand integration with delivery service


## Technical Requirements

### System Prerequisites
- Java Development Kit (JDK) 8 or higher
- Oracle Database (XE 11g/18c/21c supported)
- Oracle JDBC Driver (ojdbc8.jar)
- Minimum 4GB RAM
- 500MB available storage space

### Oracle Database Configuration
- Default service name: xe
- Default port: 1521
- Required privileges: CREATE SESSION, CREATE TABLE, CREATE SEQUENCE

## System Architecture

```
OnlineFoodDelivery/
├── src/
│   ├── f1.java               # Authentication system
│   ├── f4.java               # Customer interface
│   ├── f6.java               # Admin interface
│   ├── CUSTOMER.java         # Customer entity
│   └── CustomerForm.java     # Registration form
├── database/
│   ├── schema/
│   │   ├── tables.sql        # Table definitions
│   │   └── sequences.sql     # Sequence definitions
│   └── queries/
│       ├── customer.sql      # Customer-related queries
│       └── admin.sql         # Administrative queries
└── lib/
    └── ojdbc8.jar           # Oracle JDBC driver
```

## Installation Instructions

1. Database Setup:
```sql
-- Connect as SYSTEM user
sqlplus SYSTEM/your_password

-- Create required tables
@database/schema/tables.sql

-- Grant necessary permissions
GRANT CREATE SESSION TO food_delivery_user;
GRANT CREATE TABLE TO food_delivery_user;
```

2. Application Configuration:
```bash
# Compile the application
javac -cp "lib/ojdbc8.jar" src/*.java

# Run the application
java -cp "lib/ojdbc8.jar:src" f1
```

3. Default Credentials:
   - Admin Login:
     - Username: admin1
     - Password: cvb
   - Customer Login: Use registered email and password

## Database Schema

### Customer Table
```sql
CREATE TABLE customer (
    customer_id NUMBER PRIMARY KEY,
    emailid VARCHAR2(100) UNIQUE,
    pwd VARCHAR2(50) NOT NULL
    -- Additional fields as per requirements
);
```

## Security Considerations

1. Database Security:
   - Uses prepared statements to prevent SQL injection
   - Implements connection pooling for resource optimization
   - Secures database credentials

2. Authentication Security:
   - Password encryption in transit
   - Session timeout implementation
   - Input validation and sanitization

## Development Guidelines

1. Oracle-Specific Best Practices:
   - Use Oracle data types (VARCHAR2 instead of VARCHAR)
   - Implement proper exception handling for Oracle SQLExceptions
   - Follow Oracle naming conventions

2. Code Contribution:
   - Maintain consistent error handling
   - Document Oracle-specific functionality
   - Test against target Oracle version

## Troubleshooting

Common Issues:
1. ORA-12541: No listener
   - Verify Oracle service is running
   - Check tnsnames.ora configuration

2. ORA-01017: Invalid username/password
   - Verify database credentials
   - Check user permissions

## Support

Technical Assistance:
- Database Issues: dba@organization.com
- Application Support: support@organization.com
- Documentation: Project Wiki

## License

This project is licensed under the MIT License. See LICENSE.md for details.

## Acknowledgments

- Oracle Database architecture implementation
- UI/UX design patterns
- Security implementation guidelines
