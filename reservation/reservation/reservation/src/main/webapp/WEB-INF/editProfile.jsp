<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .edit-profile-container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"], input[type="email"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box;
        }
        .button-group {
            text-align: center;
            margin-top: 30px;
        }
        .btn {
            padding: 12px 24px;
            margin: 0 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn:hover {
            opacity: 0.8;
        }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .readonly-field {
            background-color: #f8f9fa;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <div class="edit-profile-container">
        <h2>Edit Profile</h2>

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

        <%
            Object userObj = request.getAttribute("user");
            if (userObj != null) {
                try {
                    java.lang.reflect.Method getUsername = userObj.getClass().getMethod("getUsername");
                    java.lang.reflect.Method getFullName = userObj.getClass().getMethod("getFullName");
                    java.lang.reflect.Method getEmail = userObj.getClass().getMethod("getEmail");
                    
                    String username = (String) getUsername.invoke(userObj);
                    String fullName = (String) getFullName.invoke(userObj);
                    String email = (String) getEmail.invoke(userObj);
        %>
            <form action="${pageContext.request.contextPath}/editProfile" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="<%= username != null ? username : "" %>" readonly class="readonly-field">
                </div>
                
                <div class="form-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" name="fullName" value="<%= fullName != null ? fullName : "" %>" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="<%= email != null ? email : "" %>" required>
                </div>
                
                <div class="button-group">
                    <button type="submit" class="btn btn-primary">Update Profile</button>
                    <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        <%
                } catch (Exception e) {
        %>
            <div class="message error">Error loading user data: <%= e.getMessage() %></div>
        <%
                }
            }
        %>
    </div>
</body>
</html>
