# TODO for Online Examination System

## 1. Configuration
- [x] Update application.properties with MySQL database configuration
- [x] Verify database connection settings

## 2. Entities
- [x] Create User entity
- [x] Create Role entity
- [x] Create Exam entity
- [x] Create Question entity
- [x] Create Option entity
- [x] Create Attempt entity

## 3. Repositories
- [x] Create UserRepository
- [x] Create RoleRepository
- [x] Create ExamRepository
- [x] Create QuestionRepository
- [x] Create OptionRepository
- [x] Create AttemptRepository

## 4. Services
- [x] Create AuthService for login/logout
- [ ] Complete ExamService implementation
- [ ] Complete AdminService implementation

## 5. Controllers
- [x] Create AuthController for login/register
- [ ] Complete StudentController implementation
- [ ] Complete AdminController implementation
- [ ] Complete ExamController implementation
- [ ] Fix role assignment in registration

## 6. JSP Pages
- [x] Create login.jsp
- [x] Create register.jsp
- [x] Create studentDashboard.jsp
- [x] Create adminDashboard.jsp
- [x] Create profile.jsp
- [x] Create exam.jsp with timer
- [x] Create results.jsp
- [x] Create addQuestion.jsp
- [x] Create manageExams.jsp

## 7. Database
- [x] Create schema.sql with tables
- [x] Create data.sql with sample data
- [x] Test database connectivity and schema creation

## 8. Static Resources
- [ ] Add Bootstrap CSS/JS
- [ ] Add custom CSS
- [ ] Add JavaScript for timer
- [ ] Add session validation and role-based access control

## 9. Testing
- [ ] Run the application
- [ ] Test login, exam, admin features
- [ ] Test user registration with proper role assignment
- [ ] Test exam timer functionality
- [ ] Generate unit tests for services and controllers
- [ ] Run tests

## 10. Final
- [ ] Step-by-step setup guide
- [ ] Add README with instructions
- [ ] Ensure all features work end-to-end
