<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.libraryManagement.LibraryManagement.entity.Transaction" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>User Dashboard | LMS</title>
	<link rel="stylesheet" href="/userDashboard.css" />
</head>
<body>
	<header class="topbar">
		<h1>Welcome, <span>
		<%
		  User user = (User) request.getAttribute("user");
		if (user != null) {
			out.print(user.getName());
		}
		%>
		</span></h1>
		<nav>
			<a href="/books/browseBook">Books</a>
			<a href="/user/reservations">Reserved Book</a>
			<a href="/user/myIssues">My Issues</a>
			<a href="/user/query">Contact Admin</a>
			<form method="get" action="/logout" class="inline">
				<button type="submit">Logout</button>
			</form>
		</nav>
	</header>
	<main class="container">
		<!-- Quick Stats -->
		<section class="stats">
			<div class="card stat">
				<h3>Books Issued</h3>
				<p class="big">
				<%
					List transactions = (List) request.getAttribute("transactions");
				if (transactions != null) {
					out.print(transactions.size());
				} else {
					out.print("0");
				}
				%>
				</p>
			</div>
			<div class="card stat">
				<h3>Reservations</h3>
				<p class="big">
				<%
					List reservations = (List) request.getAttribute("reservations");
				if (reservations != null) {
					out.print(reservations.size());
				} else {
					out.print("0");
				}
				%>
				</p>
			</div>
			<div class="card stat">
				<h3>Overdue</h3>
				<p class="big">
				<%
				Object overdueCount = request.getAttribute("overdueCount");
				if (overdueCount != null) {
					out.print(overdueCount);
				} else {
					out.print("0");
				}
				%>
				</p>
			</div>
		</section>

		<!-- Currently Issued Books -->
		<section class="card">
			<h2>Currently Issued Books</h2>
			<div class="table-wrap">
				<table>
					<thead>
					<tr>
						<th>Issue ID</th>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Book Id</th>                        
                        <th>Book Title</th>
                        <th>Category</th>
                        <th>Author</th>
                        <th>Issue Date</th>
                        <th>Due Date</th>
                        <th>Status</th>
						<th>Fine</th>
					</tr>
					</thead>
					<tbody>
					<%
					if (transactions != null && !transactions.isEmpty()) {
						for (Object obj : transactions) {
								Transaction transaction = (Transaction) obj;
								Book book = transaction.getBook();

							// Calculate status
							String status = "Issue";
							String statusClass = "status-ontime";
							if (transaction.getDueDate() != null &&
								transaction.getDueDate().isBefore(java.time.LocalDate.now())) {
								status = "Overdue";
								statusClass = "status-overdue";
							}
					%>
					<tr>
						<td></td>
						<td><%= user.getId() %></td>
						<td><%= user != null ? user.getName() : "" %></td>
						<td><%= book != null ? book.getId() : "" %> </td>
						<td><%= book.getTitle() %></td>
						<td><%= book.getCategory() %> </td>
						<td><%= book.getAuthor() != null ? book.getAuthor() : "N/A" %></td>
						<td><%= transaction.getIssueDate() != null ? transaction.getIssueDate() : "N/A" %></td>
						<td><%= transaction.getDueDate() != null ? transaction.getDueDate() : "N/A" %></td>
						<td><span class="<%= statusClass %>"><%= status %></span></td>
						<td>$<%= transaction.getFine() %></td>
					</tr>
					<%
						}
					} else {
					%>
					<tr>
						<td colspan="7" style="text-align: center;">No books currently issued</td>
					</tr>
					<%
					}
					%>
					</tbody>
				</table>
			</div>
		</section>

		<!-- My Reservations -->
		<section class="card">
			<h2>My Reservations</h2>
			<div class="table-wrap">
				<table>
					<thead>
					<tr>
						<th>Book Title</th>
						<th>Author</th>
						<th>Reservation Date</th>
						<th>Status</th>
					</tr>
					</thead>
					<tbody>
					<%
					if (reservations != null && !reservations.isEmpty()) {
						for (Object obj : reservations) {
							com.libraryManagement.LibraryManagement.entity.Reservation reservation =
								(com.libraryManagement.LibraryManagement.entity.Reservation) obj;
							com.libraryManagement.LibraryManagement.entity.Book book = reservation.getBook();
					%>
					<tr>
						<td><%= book.getTitle() %></td>
						<td><%= book.getAuthor() != null ? book.getAuthor() : "N/A" %></td>
						<td><%= reservation.getCreatedAt() != null ? reservation.getCreatedAt() : "Pending" %></td>
						<td><span class="status-reserved">Reserved</span></td>
					</tr>
					<%
						}
					} else {
					%>
					<tr>
						<td colspan="4" style="text-align: center;">No active reservations</td>
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
