<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Edit Book | LMS</title>
	<link rel="stylesheet" href="/editBook.css" />
</head>
<body>
<header class="topbar">
	<h1>Edit Book</h1>
	<nav>
		<a href="/admin/adminDashboard">Dashboard</a>
	</nav>
</header>
<main class="container">
	<section class="card">
	<%
	Book book =
		(Book) request.getAttribute("book");
	if (book != null) {
	%>
	<form class="grid" method="post" action="/books/edit">
		<input type="hidden" name="id" value="<%= book.getId() %>" />
		<label>Book ID
			<input type="text" name="id" value="<%= book.getId() %>" readonly />
		</label>
		<label>Title
			<input type="text" name="title" value="<%= book.getTitle() != null ? book.getTitle() : "" %>" required />
		</label>
		<label>Author
			<input type="text" name="author" value="<%= book.getAuthor() != null ? book.getAuthor() : "" %>" required />
		</label>
		<label>Category
			<input type="text" name="category" value="<%= book.getCategory() != null ? book.getCategory() : "" %>" />
		</label>
		<label>Status
			<select name="status">
				<option value="AVAILABLE" <%= book.getStatus() == Book.Status.AVAILABLE ? "selected" : "" %>>Available</option>
				<option value="ISSUED" <%= book.getStatus() == Book.Status.ISSUED ? "selected" : "" %>>Issued</option>
				<option value="RESERVED" <%= book.getStatus() == Book.Status.RESERVED ? "selected" : "" %>>Reserved</option>
			</select>
		</label>
		<label>Quantity
			<input type="number" name="quantity" min="1" value="<%= book.getQuantity() != null ? book.getQuantity() : 1 %>" required />
		</label>
		<button class="btn primary full" type="submit">Update Book</button>
	</form>
	<%
	} else {
	%>
	<p>Book not found.</p>
	<%
	}
	%>
	</section>
</main>
</body>
</html>