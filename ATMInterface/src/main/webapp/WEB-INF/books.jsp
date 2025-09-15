<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Books | LMS</title>
	<link rel="stylesheet" href="/books.css" />
</head>
<body>
<header class="topbar">
	<h1>Books</h1>
		<nav>
		<a href="/admin/adminDashboard">Dashboard</a>
		<a href="/books/add">Add Book</a>
		<a href="/books/deletedBooks">Deleted Books</a> 
		<form method="get" action="/logout" class="inline">
			<button type="submit">Logout</button>
		</form>
	</nav>
</header>
<main class="container">
	<section class="card">
		<form class="filters" method="get" action="/books">
			<input type="text" name="q" placeholder="Search by title, author, ISBN" />
			<select name="category">
				<option value="">All Categories</option>
				<%
					java.util.List<String> categories = (java.util.List<String>) request.getAttribute("categories");
					if (categories != null) {
						for (String cat : categories) {
				%>
				<option value="<%= cat %>" <%= cat.equals(request.getParameter("category")) ? "selected" : "" %>><%= cat %></option>
				<%
						}
					}
				%>
			</select>
			<button type="submit" class="btn">Search</button>
		</form>
		<div class="table-wrap">
			<table>
				<thead>
					<tr>
						<th>S.No</th>
						<th>Book Id</th>
						<th>Title</th>
						<th>Author</th>
						<th>Quantity</th>
						<th>Status</th>
						<th></th>
						<th>Actions</th>
					</tr>
				</thead>
			<tbody>
				<%
				java.util.List<com.libraryManagement.LibraryManagement.entity.Book> books =
					(java.util.List<com.libraryManagement.LibraryManagement.entity.Book>) request.getAttribute("books");
				if (books != null && !books.isEmpty()) {
					int serialNumber = 1;
					for (com.libraryManagement.LibraryManagement.entity.Book book : books) {
				%>
				<tr>
					<td><%= serialNumber++ %></td>
					<td><%= book.getId() %></td>
					<td><%= book.getTitle() %></td>
					<td><%= book.getAuthor() != null ? book.getAuthor() : "N/A" %></td>
					<td><%= book.getQuantity() %></td>
					<td><%= book.getStatus() %></td>
					<td></td>
					<td>
					<div class="table-actions">
						<a class="btn sm cold" href="/books/edit/<%= book.getId() %>">Edit</a>
						<form class="inline" method="post" action="/books/delete/<%= book.getId() %>">
							<button class="btn sm warn" type="submit">Delete</button>
						</form>
					</div>
					</td>
				</tr>
				<%
					}
				} else {
				%>
				<tr>
					<td colspan="8" style="text-align: center;">No books found</td>
				</tr>
				<%
				}
				%>
			</tbody>
			</table>
		</div>
	</section>
</main>
</body>
</html>