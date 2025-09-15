# ATM Interface Testing Plan

## Steps to Test and Run the Application

1. [ ] Check MySQL database setup and connection
2. [ ] Build the application using Maven
3. [ ] Run the Spring Boot application
4. [ ] Test the web interface functionality
   - [ ] Login with test credentials
   - [ ] Test dashboard access
   - [ ] Test withdrawal functionality
   - [ ] Test deposit functionality
   - [ ] Test transfer functionality
   - [ ] Test transaction history
5. [ ] Run existing unit tests
6. [ ] Create additional test scenarios if needed

## Test Users (from DataLoader)
- user123 / 1234 - $1000.0
- user456 / 5678 - $500.0  
- user789 / 9012 - $1500.0

## Database Configuration
- URL: jdbc:mysql://localhost:3306/atm_db
- Username: atm_user
- Password: secure_password

## Application Details
- Port: 8081
- Build Tool: Maven
- Java Version: 17
- Spring Boot Version: 3.5.5
