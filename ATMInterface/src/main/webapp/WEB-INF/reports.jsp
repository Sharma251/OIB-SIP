<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Query" %>
<%
    User admin = (User) session.getAttribute("user");
    if (admin == null || !admin.getRole().equals(User.Role.ADMIN)) {
        response.sendRedirect("/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Query Reports | Admin LMS</title>
<link rel="stylesheet" href="/reports.css" />
<style>
   
</style>
</head>
<body>
<header class="topbar">
    <h1>Query Reports</h1>
    <nav>
        <a href="/admin/adminDashboard">Dashboard</a>
        <a href="/admin/books">Books</a>
        <a href="/admin/issuedBooks">Issued Books</a>
        <a href="/admin/reports">Reports</a>
        <form method="get" action="/logout" class="inline">
            <button type="submit">Logout</button>
        </form>
    </nav>
</header>

<main class="container">
    <section class="card">
        <h2>All User Queries</h2>

        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th>Query ID</th>
                        <th>User Name</th>
                        <th>Subject</th>
                        <th>Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<Query> queries = (List<Query>) request.getAttribute("queries");
                    if (queries != null && !queries.isEmpty()) {
                        for (Query query : queries) {
                    %>
                    <tr class="query-row" data-query-id="<%= query.getId() %>"
                        data-user-name="<%= query.getUser().getName() %>"
                        data-user-email="<%= query.getUser().getEmail() %>"
                        data-subject="<%= query.getSubject().replaceAll("\"", "") %>"
                        data-message="<%= query.getMessage().replaceAll("\"", "").replaceAll("\n", "&#10;").replaceAll("\r", "") %>"
                        data-date="<%= query.getDate().toLocalDate() %>"
                        data-status="<%= query.getStatus() %>">
                        <td><%= query.getId() %></td>
                        <td><%= query.getUser().getName() %></td>
                        <td><a href="#" class="subject-link"><%= query.getSubject() %></a></td>
                        <td><%= query.getDate().toLocalDate() %></td>
                        <td>
                            <span class="status <%= query.getStatus().toString().toLowerCase() %>">
                                <%= query.getStatus() %>
                            </span>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5" style="text-align: center;">No queries found</td>
                    </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        </div>
    </section>
</main>

<!-- Modal for Query Details -->
<div id="queryModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Query Details</h2>
        <div class="query-details">
            <div><label>Query ID:</label> <span id="modalQueryId"></span></div>
            <div><label>User Name:</label> <span id="modalUserName"></span></div>
            <div><label>User Email:</label> <span id="modalUserEmail"></span></div>
            <div><label>Subject:</label> <span id="modalSubject"></span></div>
            <div><label>Message:</label></div>
            <div style="margin-left: 120px; margin-top: 5px;"><span id="modalMessage"></span></div>
            <div><label>Date:</label> <span id="modalDate"></span></div>
            <div><label>Status:</label> <span id="modalStatus"></span></div>
            <div id="statusUpdateForm" style="margin-top: 20px;">
                <form method="post" action="/admin/updateQueryStatus">
                    <input type="hidden" name="queryId" id="formQueryId" />
                    <input type="hidden" name="status" value="RESOLVED" />
                    <button type="submit" class="btn">Mark as Resolved</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Add click event listeners to all query rows
    document.addEventListener('DOMContentLoaded', function() {
        var queryRows = document.querySelectorAll('.query-row');
        queryRows.forEach(function(row) {
            row.addEventListener('click', function() {
                var queryId = this.getAttribute('data-query-id');
                var userName = this.getAttribute('data-user-name');
                var userEmail = this.getAttribute('data-user-email');
                var subject = this.getAttribute('data-subject');
                var message = this.getAttribute('data-message');
                var date = this.getAttribute('data-date');
                var status = this.getAttribute('data-status');

                document.getElementById('modalQueryId').textContent = queryId;
                document.getElementById('modalUserName').textContent = userName;
                document.getElementById('modalUserEmail').textContent = userEmail;
                document.getElementById('modalSubject').textContent = subject;
                document.getElementById('modalMessage').textContent = message.replace(/&#10;/g, '\n');
                document.getElementById('modalDate').textContent = date;
                document.getElementById('modalStatus').textContent = status;

                document.getElementById('formQueryId').value = queryId;

                // Hide the update form if already resolved
                if (status === 'RESOLVED') {
                    document.getElementById('statusUpdateForm').style.display = 'none';
                } else {
                    document.getElementById('statusUpdateForm').style.display = 'block';
                }

                document.getElementById('queryModal').style.display = 'block';
            });
        });
    });

    function closeModal() {
        document.getElementById('queryModal').style.display = 'none';
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        var modal = document.getElementById('queryModal');
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }
</script>
</body>
</html>
