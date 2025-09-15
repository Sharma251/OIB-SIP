<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Exam Result</title>
    <link rel="stylesheet" href="examStyle.css">
</head>
<body>
<div class="container">
    <h2>Exam Result</h2>
    <p>Your Score: <%= request.getAttribute("score") %> / <%= request.getAttribute("total") %></p>
    <a href="studentDashboard" class="btn primary">Back to Dashboard</a>
</div>
</body>
</html>
