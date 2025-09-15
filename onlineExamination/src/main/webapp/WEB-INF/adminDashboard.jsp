<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.User" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Exam" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Attempt" %>

<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="adminDashboardStyle.css">
</head>
<body>
<div class="dashboard-container">
    <h2>Welcome, Admin!</h2>
    <div class="actions">
        <a href="/logout" class="btn btn-danger">Logout</a>
        <a href="/admin/manageQuestions" class="btn btn-primary">Manage Questions</a>
        <a href="/admin/viewResults" class="btn btn-info">View Results</a>
    </div>

    <%
        List<User> users = (List<User>) request.getAttribute("users");
        List<Exam> exams = (List<Exam>) request.getAttribute("exams");
        List<Attempt> attempts = (List<Attempt>) request.getAttribute("attempts");
    %>

    <h3>Users</h3>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Full Name</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (users != null) {
                    for (User user : users) {
            %>
                <tr>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getFullName() %></td>
                    <td><%= user.getEmail() %></td>
                    <!-- <td><a href="/profile" class="btn btn-primary">Edit</a></td>      
                    <td><a href="/logout" class="btn btn-danger">Delete</a></td> -->
                </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <h3>Exams</h3>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Duration</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (exams != null) {
                    for (Exam exam : exams) {
            %>
                <tr>
                    <td><%= exam.getTitle() %></td>
                    <td><%= exam.getDescription() %></td>
                    <td><%= exam.getDurationMinutes() %> minutes</td>
                </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <h3>Attempts</h3>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>User</th>
                    <th>Exam</th>
                    <th>Score</th>
                    <th>Completed</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (attempts != null) {
                    for (Attempt attempt : attempts) {
            %>
                <tr>
                    <td><%= attempt.getUser().getUsername() %></td>
                    <td><%= attempt.getExam().getTitle() %></td>
                    <td><%= attempt.getScore() %></td>
<%--                    <td><%= attempt.isCompleted() ? "Yes" : "No" %></td> --%>               
				 </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
