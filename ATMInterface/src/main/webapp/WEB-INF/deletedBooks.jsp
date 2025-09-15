<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deleted Books | LMS</title>
<link rel="stylesheet" href="/books.css" />
</head>
<body>
<header class="topbar">
	<h1>Deleted Books</h1>
	<nav>
		<a href="/admin/adminDashboard">Dashboard</a>
		<a href="/books">Back to Books</a>
		<form method="get" action="/logout" class="inline">
			<button type="submit">Logout</button>
		</form>
	</nav>
</header>
<main class="container">
	<section class="card">
		<form class="filters" method="get" action="deletedBooks">
			<input type="text" name="q" placeholder="Search by title or author" value="<%= request.getParameter("q") != null ? request.getParameter("q") : "" %>" />
			<select name="category">
				<option value="">All Categories</option>
				<%
				String selectedCategory = request.getParameter("category");
				java.util.List<String> categories = (java.util.List<String>) request.getAttribute("categories");
				if (categories != null) {
					for (String cat : categories) {
				%>
				<option value="<%= cat %>" <%= cat.equals(selectedCategory) ? "selected" : "" %>><%= cat %></option>
				<%
					}
				}
				%>
			</select>
			<input type="text" name="author" placeholder="Filter by author" value="<%= request.getParameter("author") != null ? request.getParameter("author") : "" %>" />
			<button type="submit" class="btn">Search</button>
		</form>
		<div class="table-wrap">
			<table>
				<thead>
					<tr>
						<th>S.No</th>
						<th>Original Book ID</th>
						<th>Title</th>
						<th>Author</th>
						<th>Category</th>
						<th>Status</th>
						<th>Deleted At</th>
						<th>Actions</th>
					</tr>
				</thead>
			<tbody>
				<%
				java.util.List<com.libraryManagement.LibraryManagement.entity.DeletedBook> deletedBooks =
					(java.util.List<com.libraryManagement.LibraryManagement.entity.DeletedBook>) request.getAttribute("deletedBooks");
				if (deletedBooks != null && !deletedBooks.isEmpty()) {
					int serialNumber = 1;
					for (com.libraryManagement.LibraryManagement.entity.DeletedBook book : deletedBooks) {
				%>
				<tr>
					<td><%= serialNumber++ %></td>
					<td><%= book.getOriginalBookId() %></td>
					<td><%= book.getTitle() %></td>
					<td><%= book.getAuthor() != null ? book.getAuthor() : "N/A" %></td>
					<td><%= book.getCategory() != null ? book.getCategory() : "N/A" %></td>
					<td><%= book.getStatus() %></td>
					<td><%= book.getDeletedAt() %></td>
					<td>
						<form class="inline" method="post" action="/books/restore/<%= book.getId() %>">
							<button class="btn sm" type="submit" onclick="return confirm('Are you sure you want to restore this book?')">Restore</button>
						</form>
					</td>
				</tr>
				<%
					}
				} else {
				%>
				<tr>
					<td colspan="8" style="text-align: center;">No deleted books found</td>
				</tr>
				<%
				}
				%>
			</tbody>
			</table>
		</div>
		<!-- Simple Pagination -->
		<%
		Integer currentPage = (Integer) request.getAttribute("currentPage");
		Integer totalPages = (Integer) request.getAttribute("totalPages");
		if (currentPage != null && totalPages != null && totalPages > 1) {
		%>
		<div class="pagination">
			<%
			if (currentPage > 0) {
			%>
			<a href="?page=<%= currentPage - 1 %>&q=<%= request.getParameter("q") != null ? request.getParameter("q") : "" %>&category=<%= request.getParameter("category") != null ? request.getParameter("category") : "" %>&author=<%= request.getParameter("author") != null ? request.getParameter("author") : "" %>" class="btn sm">Previous</a>
			<%
			}
			for (int i = 0; i < totalPages; i++) {
				if (i == currentPage) {
			%>
			<span class="btn sm disabled"><%= i + 1 %></span>
			<%
				} else {
			%>
			<a href="?page=<%= i %>&q=<%= request.getParameter("q") != null ? request.getParameter("q") : "" %>&category=<%= request.getParameter("category") != null ? request.getParameter("category") : "" %>&author=<%= request.getParameter("author") != null ? request.getParameter("author") : "" %>" class="btn sm"><%= i + 1 %></a>
			<%
				}
			}
			if (currentPage < totalPages - 1) {
			%>
			<a href="?page=<%= currentPage + 1 %>&q=<%= request.getParameter("q") != null ? request.getParameter("q") : "" %>&category=<%= request.getParameter("category") != null ? request.getParameter("category") : "" %>&author=<%= request.getParameter("author") != null ? request.getParameter("author") : "" %>" class="btn sm">Next</a>
			<%
			}
			%>
		</div>
		<%
		}
		%>
	</section>
</main>
</body>
</html>
