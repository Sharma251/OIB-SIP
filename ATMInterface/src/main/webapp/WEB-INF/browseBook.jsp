<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Reservation" %>
<%@ page import="com.libraryManagement.LibraryManagement.service.ReservationService" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Browse Books</title>
	<link rel="stylesheet" href="/books.css" />
</head>
<body>
<header class="topbar">
	<h1>Books</h1>
	<nav>
		<a href="/user/userDashboard">Dashboard</a>
		<a href="/user/reservations">My Reservations</a>
		<a href="/user/myIssues">My Issues</a>
		<form method="get" action="/logout" class="inline">
			<button type="submit">Logout</button>
		</form>
	</nav>
</header>
<main class="container">
	<section class="card">
		<form class="filters" method="get" action="browseBook">
			<input type="text" name="q" placeholder="Search by title or author" value="<%= request.getParameter("q") != null ? request.getParameter("q") : "" %>" />
			<select name="category">
				<option value="">All Categories</option>
				<%
					java.util.List<String> categories = (java.util.List<String>) request.getAttribute("categories");
					String selectedCategory = request.getParameter("category");
					if (categories != null) {
						for (String cat : categories) {
				%>
				<option value="<%= cat %>" <%= cat.equals(selectedCategory) ? "selected" : "" %>><%= cat %></option>
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
						<th>Book Id</th>
						<th>Title</th>
						<th>Author</th>
						<th>Status</th>
						<th></th>
						<th>Actions</th>
					</tr>
				</thead>
			<tbody>
				<%
				List<Book> books =
						(List<Book>) request.getAttribute("books");
				User currentUser = (User) session.getAttribute("user");



				if (books != null && !books.isEmpty()) {
					for (Book book : books) {
						// Get user's reservations to check if they have reserved this book
						List<Reservation> userReservations = new java.util.ArrayList<>();
						boolean hasReservation = false;
						try {
							WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
							if (context != null) {
								ReservationService reservationService = context.getBean(ReservationService.class);
								if (reservationService != null) {
									userReservations = reservationService.getUserPendingReservations(currentUser);
									hasReservation = userReservations.stream().anyMatch(r -> r.getBook().getId().equals(book.getId()));
								}
							}
						} catch (Exception e) {
							// Log error but continue
							System.out.println("Error accessing ReservationService: " + e.getMessage());
						}
						// Determine availability based on quantity and user issued/reserved status
						boolean isAvailable = book.getQuantity() > 0;
						boolean userHasIssued = false;
						boolean userHasReserved = false;

						try {
							WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
							if (context != null) {
								com.libraryManagement.LibraryManagement.service.TransactionService transactionService = context.getBean(com.libraryManagement.LibraryManagement.service.TransactionService.class);
								if (transactionService != null) {
									List<com.libraryManagement.LibraryManagement.entity.Transaction> userTransactions = transactionService.getUserTransactions(currentUser);
									userHasIssued = userTransactions.stream()
										.anyMatch(t -> t.getBook().getId().equals(book.getId()) &&
											t.getReturnDate() == null &&
											t.getIssueDate() != null);
								}
							}
						} catch (Exception e) {
							System.out.println("Error accessing TransactionService: " + e.getMessage());
						}

						try {
							WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
							if (context != null) {
								ReservationService reservationService = context.getBean(ReservationService.class);
								if (reservationService != null) {
									userHasReserved = reservationService.hasPendingReservation(currentUser, book);
								}
							}
						} catch (Exception e) {
							System.out.println("Error accessing ReservationService: " + e.getMessage());
						}
				%>
				<tr>
					<td><%= book.getId() %></td>
					<td><%= book.getTitle() %></td>
					<td><%= book.getAuthor() != null ? book.getAuthor() : "N/A" %></td>
					<td>
						<%
						String statusText = "Unavailable";
						if (book.getQuantity() > 0) {
							if (userHasIssued) {
								statusText = "Issued";
							} else if (userHasReserved) {
								statusText = "Reserved";
							} else {
								statusText = "Available";
							}
						}
						%>
						<%= statusText %>
					</td>
					<td></td>
					<td>
					<div class="table-actions">
						<%

						if (isAvailable) {
							if (userHasIssued) {
						%>
							<!-- Issue Button Disabled -->
							<button class="btn sm cold" disabled style="opacity: 0.5; cursor: not-allowed;">Issue</button>
						<%
							} else {
						%>
							<!-- Issue Button -->
							<form class="inline" method="get" action="/user/issueBook">
								<input type="hidden" name="bookId" value="<%= book.getId() %>" />
								<button type="submit" class="btn sm cold">Issue</button>
							</form>
						<%
							}
							if (userHasReserved) {
						%>
							<!-- Reserve Button Disabled -->
							<button class="btn sm mid" disabled style="opacity: 0.5; cursor: not-allowed;">Reserve</button>
						<%
							} else {
						%>
							<!-- Reserve Button -->
							<form class="inline" method="post" action="/user/reserveBook">
								<input type="hidden" name="bookId" value="<%= book.getId() %>" />
								<button class="btn sm mid" type="submit">Reserve</button>
							</form>
						<%
							}
						} else {
							if (userHasReserved) {
						%>
							<!-- Reserve Button Disabled -->
							<button class="btn sm mid" disabled style="opacity: 0.5; cursor: not-allowed;">Reserve</button>
						<%
							} else {
						%>
							<!-- Reserve Button Disabled (Unavailable) -->
							<button class="btn sm mid" disabled style="opacity: 0.5; cursor: not-allowed;">Reserve</button>
						<%
							}
						}
						if (hasReservation) {
						%>
							<form class="inline" method="post" action="/user/cancelReservation">
								<input type="hidden" name="reservationId" value="<%= userReservations.stream().filter(r -> r.getBook().getId().equals(book.getId())).findFirst().get().getId() %>" />
								<button class="btn sm danger" type="submit">Cancel Reservation</button>
							</form>
						<%
						} else if ("RESERVED".equals(book.getStatus().name())) {
						%>
							<span class="status-text">Reserved by another user</span>
						<%
						} else if ("ISSUED".equals(book.getStatus().name())) {
						%>
							<span class="status-text">Currently Issued</span>
						<%
						} else if (book.getQuantity() <= 0) {
						%>
							<span class="status-text">Unavailable</span>
						<%
						} else {
						%>
							<span class="status-text">Unknown Status: <%= book.getStatus() %></span>
						<%
						}
						%>
					</div>
					</td>
				</tr>
				<%
					}
				} else {
				%>
				<tr>
					<td colspan="6" style="text-align: center;">No books found</td>
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