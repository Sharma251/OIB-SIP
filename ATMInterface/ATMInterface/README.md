# ATM Interface Web Application

A Spring Boot web application that provides ATM functionalities through JSP pages.

## Features

- **User Authentication**: Login with user ID and PIN
- **Dashboard**: View current balance and access all ATM functions
- **Withdraw**: Withdraw money from account
- **Deposit**: Deposit money to account
- **Transfer**: Transfer money to another user
- **Transaction History**: View all transaction records
- **Logout**: Secure session termination

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL database
- Maven

### Database Setup
1. Create a MySQL database named `atm_db`
2. Update the database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/atm_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Sample Users
The application comes with pre-loaded sample users:
- User ID: `user123`, PIN: `1234`, Balance: $1000.00
- User ID: `user456`, PIN: `5678`, Balance: $500.00
- User ID: `user789`, PIN: `9012`, Balance: $1500.00

### Running the Application
1. Navigate to the project root directory
2. Run: `mvn spring-boot:run`
3. Open browser and go to: `http://localhost:8080`

## Project Structure

```
src/main/java/com/ATMInterface/ATMInterface/
├── model/           # Entity classes
│   ├── User.java
│   └── Transaction.java
├── repository/      # Data access layer
│   ├── UserRepository.java
│   └── TransactionRepository.java
├── service/         # Business logic
│   └── ATMService.java
├── controller/      # Web controllers
│   └── ATMController.java
├── config/          # Configuration
│   └── SecurityConfig.java
└── DataLoader.java  # Sample data

src/main/webapp/WEB-INF/views/
├── login.jsp        # Login page
├── dashboard.jsp    # Main menu
├── withdraw.jsp     # Withdraw money
├── deposit.jsp      # Deposit money
├── transfer.jsp     # Transfer money
└── transactions.jsp # Transaction history
```

## Technologies Used

- **Backend**: Spring Boot, Spring Data JPA, Spring Security
- **Frontend**: JSP, JSTL, CSS
- **Database**: MySQL
- **Build Tool**: Maven

## Security Features

- Spring Security for authentication and authorization
- Session management for user state
- CSRF protection (disabled for demo purposes)
- Secure password handling

## Usage

1. **Login**: Enter user ID and PIN on the login page
2. **Dashboard**: View balance and choose an operation
3. **Withdraw**: Enter amount to withdraw from account
4. **Deposit**: Enter amount to deposit to account
5. **Transfer**: Enter recipient user ID and amount to transfer
6. **Transactions**: View complete transaction history
7. **Logout**: End the session securely
