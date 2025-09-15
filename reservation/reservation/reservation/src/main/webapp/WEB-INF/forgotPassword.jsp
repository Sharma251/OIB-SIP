<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="forgotpwdStyle.css">
</head>
<body>
    <div class="forgot-password-container">
        <h2>🔐 Forgot Password</h2>
        
        <div class="info">
            Enter your username and create a new password to reset your account.
        </div>

        <%
            String success = (String) request.getAttribute("success");
            if (success != null && !success.isEmpty()) {
        %>
            <div class="message success"><%= success %></div>
        <%
            }
        %>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null && !error.isEmpty()) {
        %>
            <div class="message error"><%= error %></div>
        <%
            }
        %>

        <form action="${pageContext.request.contextPath}/forgotPassword" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" 
                       value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" 
                       placeholder="Enter your username" required>
            </div>
            
            <div class="form-group">
                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword" 
                       placeholder="Enter new password (min 6 characters)" required>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" 
                       placeholder="Confirm new password" required>
            </div>
            
            <button type="submit" class="reset-btn">Reset Password</button>
        </form>
        
        <div class="back-to-login">
            <a href="${pageContext.request.contextPath}/login">← Back to Login</a>
        </div>
    </div>
</body>
</html>
