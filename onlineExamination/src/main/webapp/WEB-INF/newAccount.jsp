<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Sign Up</title>
    <link rel="stylesheet" href="forgotPasswordStyle.css">
</head>
<body>
<div class="auth-container">
    <h2 class="auth-title">Student Sign Up</h2>
    <form action="registerStudent" method="post" class="auth-form">
        <div class="input-group">
            <label>Full Name</label>
            <input type="text" name="fullName" required>
        </div>
        <div class="input-group">
            <label>Email</label>
            <input type="email" name="email" required>
        </div>
        <div class="input-group">
            <label>Username</label>
            <input type="text" name="username" required>
        </div>
        <div class="input-group">
            <label>Password</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit" class="auth-btn">Sign Up</button>
    </form>
    <div class="auth-options">
        <a href="/login">Already have an account? Login</a>
    </div>
</div>
</body>
</html>
