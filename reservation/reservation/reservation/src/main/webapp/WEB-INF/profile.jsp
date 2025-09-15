<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="profileStyle.css">
</head>
<body>
    <div class="profile-container">
        <h2>User Profile</h2>

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
            <p style="color:red;"><%= error %></p>
        <%
            }
        %>

        <div class="profile-info">
            <%
                Object userObj = request.getAttribute("user");
                if (userObj != null) {
                    // Use reflection to access user properties safely
                    try {
                        java.lang.reflect.Method getUsername = userObj.getClass().getMethod("getUsername");
                        java.lang.reflect.Method getFullName = userObj.getClass().getMethod("getFullName");
                        java.lang.reflect.Method getEmail = userObj.getClass().getMethod("getEmail");
                        
                        String username = (String) getUsername.invoke(userObj);
                        String fullName = (String) getFullName.invoke(userObj);
                        String email = (String) getEmail.invoke(userObj);
            %>
                <p><strong>Username:</strong> <span><%= username != null ? username : "N/A" %></span></p>
                <p><strong>Full Name:</strong> <span><%= fullName != null ? fullName : "N/A" %></span></p>
                <p><strong>Email:</strong> <span><%= email != null ? email : "N/A" %></span></p>
            <%
                    } catch (Exception e) {
            %>
                <p>Error loading user data: <%= e.getMessage() %></p>
            <%
                    }
                }
            %>
        </div>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/editProfile">Edit Profile</a>
            <a href="${pageContext.request.contextPath}/changePassword">Change Password</a>
            <a href="${pageContext.request.contextPath}/bookingHistory">Booking History</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>
</body>
</html>