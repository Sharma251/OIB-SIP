<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.libraryManagement.LibraryManagement.entity.Transaction" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>
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
    	<%
		com.libraryManagement.LibraryManagement.entity.User user =
			(com.libraryManagement.LibraryManagement.entity.User) request.getAttribute("user");

		%>
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
                        <th>Issue ID</th>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Book ID</th>
                        <th>Book Title</th>
                        <th>Issue Date</th>
                        <th>Due Date</th>
                        <th>Return Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
					java.util.List<com.libraryManagement.LibraryManagement.entity.Transaction> transactions =
						(java.util.List<com.libraryManagement.LibraryManagement.entity.Transaction>) request.getAttribute("transactions");
					if (transactions != null && !transactions.isEmpty()) {
						for (com.libraryManagement.LibraryManagement.entity.Transaction transaction : transactions) {
							com.libraryManagement.LibraryManagement.entity.Book book = transaction.getBook();

							// Calculate status
							String status = "Issued";
							String statusClass = "status-ontime";
							if (transaction.getReturnDate() != null) {
								status = "Returned";
								statusClass = "status-returned";
							} else if (transaction.getDueDate() != null &&
								transaction.getDueDate().isBefore(java.time.LocalDate.now())) {
								status = "Overdue";
								statusClass = "status-overdue";
							}
					%>
					<tr>
						<td><%= transaction.getId() %></td>
						<td><%= user.getId() %></td>
						<td><%= user.getName() %></td>
						<td><%= book.getId() %></td>
						<td><%= book.getTitle() %></td>
						<td><%= transaction.getIssueDate() != null ? transaction.getIssueDate() : "N/A" %></td>
						<td><%= transaction.getDueDate() != null ? transaction.getDueDate() : "N/A" %></td>
						<td><%= transaction.getReturnDate() != null ? transaction.getReturnDate() : "N/A" %></td>
						<td><span class="<%= statusClass %>"><%= status %></span></td>
						<td>
                            <%
                            if (transaction.getReturnDate() == null) {
                            %>
						<form class="inline" method="get" action="/user/returnBook">
							<input type="hidden" name="transactionId" value="<%= transaction.getId() %>" />
							<button class="btn sm mid" type="submit">Return</button>
						</form>
                            <%
                            } else {
                            %>
                            <span class="status-returned">Returned</span>
                            <%
                            }
                            %>
						</td>
					</tr>
					<%
						}
					} else {
					%>
                        <tr>
                            <td colspan="10" style="text-align:center;">No issued books found</td>
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
