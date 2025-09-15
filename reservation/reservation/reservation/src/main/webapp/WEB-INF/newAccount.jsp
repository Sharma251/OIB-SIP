<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Account</title>
     <link rel="stylesheet" href="newAccountStyle.css">
    
</head>
<body>
    <div class="new-account-container">
        <h2>👤 Create New Account</h2>
        
        <div class="info">
            Fill in the form below to create your new account. All fields marked with <span class="required">*</span> are required.
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

        <form action="${pageContext.request.contextPath}/newAccount" method="post">
            <div class="form-group">
                <label for="username">Username <span class="required">*</span>:</label>
                <input type="text" id="username" name="username" 
                       value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" 
                       placeholder="Choose a unique username" required>
            </div>
            
            <div class="form-group">
                <label for="fullName">Full Name <span class="required">*</span>:</label>
                <input type="text" id="fullName" name="fullName" 
                       value="<%= request.getAttribute("fullName") != null ? request.getAttribute("fullName") : "" %>" 
                       placeholder="Enter your full name" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email <span class="required">*</span>:</label>
                <input type="email" id="email" name="email" 
                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" 
                       placeholder="Enter your email address" required>
            </div>
            
            <div class="form-group">
                <label for="role">Role:</label>
                <select id="role" name="role">
                    <option value="USER" <%= "USER".equals(request.getAttribute("role")) ? "selected" : "" %>>User</option>
                    <option value="ADMIN" <%= "ADMIN".equals(request.getAttribute("role")) ? "selected" : "" %>>Admin</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="password">Password <span class="required">*</span>:</label>
                <input type="password" id="password" name="password" 
                       placeholder="Enter password (min 6 characters)" required>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password <span class="required">*</span>:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" 
                       placeholder="Confirm your password" required>
            </div>
            
            <button type="submit" class="create-btn">Create Account</button>
        </form>
        
        <div class="back-to-login">
            <a href="${pageContext.request.contextPath}/login">← Back to Login</a>
        </div>
    </div>
</body>
</html>
