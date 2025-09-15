<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>

<%
    // Get current date for issue date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentDate = sdf.format(new Date());

    Book book = (Book) request.getAttribute("book");
    User user = (User) request.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Issue Book</title>
<link rel="stylesheet" href="/issueBook.css" />
</head>
<body>
<header class="topbar">
    <h1>Issue Book</h1>
    <nav>
        <a href="/user/userDashboard">Dashboard</a>
        <a href="/books/browseBook">Browse Books</a>
        <form method="get" action="/logout" class="inline">
            <button type="submit">Logout</button>
        </form>
    </nav>
</header>

<main class="container">
    <section class="card">
        <h2>Issue Book Form</h2>

        <!-- Whole form -->
        <form method="post" action="/user/confirmIssue" class="issue-form">

            <p>
                <label><strong>Issue ID:</strong></label>
                <input type="text" name="issueId" value="Auto-generated" readonly />
            </p>

            <p>
                <label><strong>User ID:</strong></label>
                <input type="text" name="userId" value="<%= user != null ? user.getId() : "" %>" readonly />
            </p>

            <p>
                <label><strong>Username:</strong></label>
                <input type="text" name="username" value="<%= user != null ? user.getName() : "" %>" readonly />
            </p>

            <p>
                <label><strong>Book ID:</strong></label>
                <input type="text" name="bookId" value="<%= book != null ? book.getId() : "" %>" readonly />
            </p>

            <p>
                <label><strong>Book Title:</strong></label>
                <input type="text" name="bookTitle" value="<%= book != null ? book.getTitle() : "" %>" readonly />
            </p>

            <p>
                <label><strong>Category:</strong></label>
                <input type="text" name="category" value="<%= book != null ? book.getCategory() : "" %>" readonly />
            </p>

            <p>
                <label><strong>Author:</strong></label>
                <input type="text" name="author" value="<%= book != null ? book.getAuthor() : "" %>" readonly />
            </p>

            <p>
                <label for="issueDate"><strong>Issue Date:</strong></label>
                <input type="date" id="issueDate" name="issueDate" value="<%= currentDate %>" readonly />
            </p>

            <p>
                <label for="dueDate"><strong>Due Date:</strong></label>
                <input type="date" id="dueDate" name="dueDate" required />
            </p>

            <p>
                <label for="status"><strong>Status:</strong></label>
                <select id="status" name="status" required>
                    <option value="Issued">Issue</option>
                </select>
            </p>

            <button type="submit" class="btn">Confirm Issue</button>
        </form>
    </section>
</main>
</body>
</html>
