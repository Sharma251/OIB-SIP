<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.libraryManagement.LibraryManagement.entity.Transaction" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>History</title>
<link rel="stylesheet" href="/reservations.css" />
</head>
<body>
<header class="topbar">
    <h1>History</h1>
    	<%
		com.libraryManagement.LibraryManagement.entity.User adminUser =
			(com.libraryManagement.LibraryManagement.entity.User) request.getAttribute("user");

		%>
    <nav>
        <a href="/admin/adminDashboard">Dashboard</a>
        <a href="/books">Books</a>
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
                        <th>Title</th>
                        <th>Issue Date</th>
                        <th>Due Date</th>
                        <th>Return Date</th>
                        <th>Fine</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
					java.util.List<com.libraryManagement.LibraryManagement.entity.Transaction> transactions =
						(java.util.List<com.libraryManagement.LibraryManagement.entity.Transaction>) request.getAttribute("transactions");
					if (transactions != null && !transactions.isEmpty()) {
						for (com.libraryManagement.LibraryManagement.entity.Transaction transaction : transactions) {
							com.libraryManagement.LibraryManagement.entity.Book book = transaction.getBook();
							com.libraryManagement.LibraryManagement.entity.User transactionUser = transaction.getUser();

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
						<td><%= transactionUser.getId() %></td>
						<td><%= transactionUser.getName() %></td>
						<td><%= book.getId() %></td>
						<td><%= book.getTitle() %></td>
						<td><%= transaction.getIssueDate() != null ? transaction.getIssueDate() : "N/A" %></td>
						<td><%= transaction.getDueDate() != null ? transaction.getDueDate() : "N/A" %></td>
						<td><%= transaction.getReturnDate() != null ? transaction.getReturnDate() : "N/A" %></td>
						<td><%= transaction.getFine() %></td>
						<td><span class="<%= statusClass %>"><%= status %></span></td>
					</tr>
					<%
						}
					} else {
					%>
                        <tr>
                            <td colspan="10" style="text-align:center;">No transactions found</td>
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
