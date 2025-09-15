<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reservations | LMS</title>
<link rel="stylesheet" href="/reservations.css" />
</head>
<body>
<header class="topbar">
<h1>Your Reserved Books</h1>
<nav>
<a href="userDashboard">Dashboard</a>
<a href="/books/browseBook">Books</a>
</nav>
</header>
	<main class="container">
		<section class="card">
			<div class="table-wrap">
				<table>
					<thead>
						<tr>
					
	                        <th>Book ID</th>
	                        <th>Book Title</th>
	                        <th>Status</th>
	                        <th>Action</th>
						</tr>
					</thead>
					<tbody>
						<%
						java.util.List<com.libraryManagement.LibraryManagement.entity.Reservation> reservations =
							(java.util.List<com.libraryManagement.LibraryManagement.entity.Reservation>) request.getAttribute("reservations");
						if (reservations != null && !reservations.isEmpty()) {
							for (com.libraryManagement.LibraryManagement.entity.Reservation reservation : reservations) {
								com.libraryManagement.LibraryManagement.entity.Book book = reservation.getBook();
						%>
						<tr>
							<td><%= book.getId() %></td>
							<td><%= book.getTitle() %></td>
							<td>Pending</td>
							<td>
								<form class="inline" method="get" action="/user/issueBook" style="display: inline;">
									<input type="hidden" name="reservationId" value="<%= reservation.getId() %>" />
									<button class="btn sm" type="submit">Issue</button>
								</form>
								<form class="inline" method="post" action="/user/cancelReservation" style="display: inline;">
									<input type="hidden" name="reservationId" value="<%= reservation.getId() %>" />
									<button class="btn sm warn" type="submit" onclick="return confirm('Are you sure you want to cancel this reservation?')">Remove</button>
								</form>
							</td>
						</tr>
						<%
							}
						} else {
						%>
						<tr>
							<td colspan="4" style="text-align: center;">No reservations found</td>
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