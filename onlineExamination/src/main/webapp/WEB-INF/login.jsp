<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Login</title>
    <link rel="stylesheet" href="loginStyle.css">
</head>
<body>
<div class="login-container">
    <h2 class="login-title">Student Login</h2>
    <form action="login" method="post" class="login-form">
        <div class="input-group">
            <label>Username</label>
            <input type="text" name="username" required>
        </div>
        <div class="input-group">
            <label>Password</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit" class="login-btn">Login</button>
    </form>

    <!-- Extra links -->
    <div class="login-options">
        <a href="/forgotPassword" class="forgot-link">Forgot Password?</a>
        <span> | </span>
        <a href="/newAccount" class="signup-link">New Student? Sign Up</a>
    </div>

    <% 
        String error = (String) session.getAttribute("error");
        if (error != null) { 
    %>
        <p class="error"><%= error %></p>
    <%
            session.removeAttribute("error"); 
        }
    %>
</div>
</body>
</html>
