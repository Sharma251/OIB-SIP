INSERT INTO roles (name) VALUES ('ADMIN'), ('STUDENT');

INSERT INTO users (username, password, full_name, email) VALUES
('admin', 'admin123', 'Administrator', 'admin@example.com'),
('student1', 'student123', 'John Doe', 'student1@example.com'),
('student2', 'student123', 'Jane Smith', 'student2@example.com');

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin
(2, 2), -- student1
(3, 2); -- student2

INSERT INTO exams (title, description, start_time, end_time) VALUES
('Java Basics Exam', 'Test your knowledge of Java fundamentals', '2024-09-15 14:00:00', '2024-09-15 16:00:00'),
('Spring Boot Exam', 'Advanced Spring Boot concepts', '2024-09-16 14:00:00', '2024-09-16 16:00:00');

INSERT INTO questions (text, exam_id) VALUES
('What is Java?', 1),
('What is Spring Boot?', 2);

INSERT INTO options (option_text, question_id, is_correct) VALUES
('A programming language', 1, true),
('A coffee brand', 1, false),
('A car model', 1, false),
('A movie', 1, false),
('A framework for Java', 2, true),
('A type of boot', 2, false),
('A database', 2, false),
('A web server', 2, false);
