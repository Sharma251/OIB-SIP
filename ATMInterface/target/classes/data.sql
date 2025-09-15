-- Insert sample users if not present
INSERT INTO users (name, username, email, password, role)
SELECT 'Admin User', 'admin', 'admin@example.com', 'admin123', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (name, username, email, password, role)
SELECT 'John Doe', 'johndoe', 'john@example.com', 'password123', 'USER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'johndoe');

INSERT INTO users (name, username, email, password, role)
SELECT 'Jane Smith', 'janesmith', 'jane@example.com', 'password123', 'USER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'janesmith');

-- Insert sample books if not present
INSERT INTO books (title, author, category, status, quantity)
SELECT 'Clean Code', 'Robert C. Martin', 'Computer Science', 'AVAILABLE', 5
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Clean Code');

INSERT INTO books (title, author, category, status, quantity)
SELECT 'Effective Java', 'Joshua Bloch', 'Computer Science', 'AVAILABLE', 3
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Effective Java');

INSERT INTO books (title, author, category, status, quantity)
SELECT 'The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', 'AVAILABLE', 4
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'The Great Gatsby');

INSERT INTO books (title, author, category, status, quantity)
SELECT 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', 'AVAILABLE', 2
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'To Kill a Mockingbird');

INSERT INTO books (title, author, category, status, quantity)
SELECT '1984', 'George Orwell', 'Fiction', 'AVAILABLE', 6
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = '1984');
