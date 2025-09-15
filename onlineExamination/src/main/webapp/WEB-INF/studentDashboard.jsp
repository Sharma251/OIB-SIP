
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.User" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Exam" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Attempt" %>
<%
    // Check if user is logged in
    User user = (User)session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
    String username = user.getUsername();
%>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="studentDashboardStyle.css"/>
</head>
<body>

    <header class="dashboard-header">
        <h2>Welcome, <%= username %></h2>
        <nav>
            <a href="profile" class="btn warning">Profile</a>
            <a href="logout" class="btn danger">Logout</a>
        </nav>
    </header>

    <main class="dashboard-container">

        <!-- Available Exams -->
        <section>
            <h3>Available Exams</h3>
            <table>
                <thead>
                    <tr>
                        <th>Exam Title</th>
                        <th>Description</th>
                        <th>Duration</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<Exam> exams = (List<Exam>) request.getAttribute("exams");

                    if (exams != null && !exams.isEmpty()) {
                        for (Exam e : exams) {
                %>
                    <tr>
                        <td><%= e.getTitle() %></td>
                        <td><%= e.getDescription() %></td>
                        <td><%= e.getDurationMinutes() %> minutes</td>
                        <td><%= e.getStartTime() %></td>
                        <td><%= e.getEndTime() %></td>
                        <td>
                             <form action="startExam/<%= e.getId() %>" method="get">
                                 <button class="btn primary" type="submit">Start Exam</button>
                             </form>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr><td colspan="6">No exams available</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </section>

        <!-- Attempts -->
        <section>
            <h3>Your Attempts</h3>
            <table>
                <thead>
                    <tr>
                        <th>Exam Title</th>
                        <th>Score</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<Attempt> attempts = (List<Attempt>) request.getAttribute("attempts");

                    if (attempts != null && !attempts.isEmpty()) {
                        for (Attempt a : attempts) {
                %>
                    <tr>
                        <td><%= a.getExam().getTitle() %></td>
                        <td><%= a.getScore() %></td>
                        <td><%= a.getAttemptTime() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr><td colspan="3">No attempts yet</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </section>
    </main>
</body>
</html>






































