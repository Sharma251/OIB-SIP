<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="forgotPasswordStyle.css">
</head>
<body>
<div class="auth-container">
    <h2 class="auth-title">Reset Password</h2>
    <form action="forgotPassword" method="post" class="auth-form">
        <div class="input-group">
            <label>Username</label>
            <input type="text" name="username" required placeholder="Enter your username">
        </div>
        <div class="input-group">
            <label>New Password</label>
            <input type="password" name="newPassword" required placeholder="Enter new password">
        </div>
        <button type="submit" class="auth-btn">Update Password</button>
    </form>
    <div class="auth-options">
        <a href="/login">Back to Login</a>
    </div>
</div>
</body>
</html>
