<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Transaction" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Return Book</title>
<link rel="stylesheet" href="/returnBook.css" />
</head>
<body>
<header class="topbar"><h1>Return Book</h1><nav><a href="/user/userDashboard">Dashboard</a></nav></header>
<main class="container">
<section class="card">
<%
    Transaction transaction = (Transaction) request.getAttribute("transaction");
    User user = (User) request.getAttribute("user");
    Book book = transaction != null ? transaction.getBook() : null;

    // Calculate fine: ₹50 per day late
    long fine = 0;
    java.time.LocalDate today = java.time.LocalDate.now();
    if (transaction != null && transaction.getDueDate() != null && today.isAfter(transaction.getDueDate())) {
        long daysLate = today.toEpochDay() - transaction.getDueDate().toEpochDay();
        fine = daysLate * 50; // ₹50 per day
    }
%>
<form class="grid" method="post" action="/user/processReturn">
    <label>Issue ID<input type="text" name="issueId" value="<%= transaction != null ? transaction.getId() : "" %>" readonly /></label>
    <label>Book ID<input type="text" name="bookId" value="<%= book != null ? book.getId() : "" %>" readonly /></label>
    <label>Book Title<input type="text" name="bookTitle" value="<%= book != null ? book.getTitle() : "" %>" readonly /></label>
    <label>User ID<input type="text" name="userId" value="<%= user != null ? user.getId() : "" %>" readonly /></label>
    <label>Username<input type="text" name="username" value="<%= user != null ? user.getName() : "" %>" readonly /></label>
    <label>Issue Date<input type="text" name="issueDate" value="<%= transaction != null && transaction.getIssueDate() != null ? transaction.getIssueDate() : "" %>" readonly /></label>
    <label>Due Date<input type="text" name="dueDate" value="<%= transaction != null && transaction.getDueDate() != null ? transaction.getDueDate() : "" %>" readonly /></label>
    <label>Return Date<input type="date" name="returnDate" value="<%= today %>" required /></label>
    <label>Condition<select name="condition"><option>Good</option><option>Damaged</option></select></label>
    <label>Fine (₹)<input type="number" name="fine" value="<%= fine %>" min="0" step="50" readonly /></label>
    <button class="btn primary full" type="submit">Process Return</button>
</form>
</section>
</main>

</body>
</html>
