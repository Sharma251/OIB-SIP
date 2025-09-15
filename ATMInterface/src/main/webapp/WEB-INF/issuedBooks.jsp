<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Issued Books</title>
<link rel="stylesheet" href="/reservations.css" />
</head>
<body>
<header class="topbar">
<h1>Issued Books</h1>
<nav>
<a href="adminDashboard">Dashboard</a>
<a href="books">Books</a>
</nav>
</header>
	<main class="container">
		<section class="card">
			<div class="table-wrap">
				<table>
					<thead>
						<tr>
							<th>Issue ID</th>
							<th>Book ID</th>
							<th>Title</th>
							<th>User ID</th>
							<th>Username</th>
							<th>Issue Date</th>
							<th>Due Date</th>
							<th>Fine</th>
						</tr>
					</thead>
					<tbody>
						<%
						java.util.List<com.libraryManagement.LibraryManagement.entity.Transaction> transactions =
							(java.util.List<com.libraryManagement.LibraryManagement.entity.Transaction>) request.getAttribute("transactions");
						if (transactions != null && !transactions.isEmpty()) {
							for (com.libraryManagement.LibraryManagement.entity.Transaction transaction : transactions) {
						%>
						<tr>
							<td><%= transaction.getId() %></td>
							<td><%= transaction.getBook().getId() %></td>
							<td><%= transaction.getBook().getTitle() %></td>
							<td><%= transaction.getUser().getId() %></td>
							<td><%= transaction.getUser().getUsername() %></td>
							<td><%= transaction.getIssueDate() %></td>
							<td><%= transaction.getDueDate() %></td>
							<td><%= transaction.getFine() %></td>
						</tr>
						<%
							}
						} else {
						%>
						<tr>
							<td colspan="8" style="text-align: center;">No issued books found</td>
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
