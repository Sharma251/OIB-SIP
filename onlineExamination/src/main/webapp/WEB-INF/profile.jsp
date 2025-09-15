<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.User" %>

<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="profileStyle.css">
</head>
<body>
<div class="container">
    <h2>User Profile</h2>
    <a href="studentDashboard" class="back-btn">Back to Dashboard</a>

    <% 
        String error = (String) session.getAttribute("error");
        String success = (String) session.getAttribute("success");
        User user = (User) session.getAttribute("user");  // adjust model package
    %>

    <% if (error != null) { %>
        <p class="alert error"><%= error %></p>
        <% session.removeAttribute("error"); %>
    <% } %>

    <% if (success != null) { %>
        <p class="alert success"><%= success %></p>
        <% session.removeAttribute("success"); %>
    <% } %>

    <div class="profile-grid">
        <!-- Left: User Details -->
        <div class="card details">
            <h3>Account Information</h3>
            <p><strong>Username:</strong> <%= user != null ? user.getUsername() : "" %></p>
            <p><strong>Full Name:</strong> <%= user != null ? user.getFullName() : "" %></p>
            <p><strong>Email:</strong> <%= user != null ? user.getEmail() : "" %></p>
           <%--  <p><strong>Phone:</strong> <%= user != null ? user.getPhoneNumber() : "" %></p>
            <p><strong>Role:</strong> <%= user != null ? user.getRole() : "" %></p>
            <p><strong>Member Since:</strong> <%= user != null ? user.getCreatedAt() : "" %></p>
            <p><strong>Last Updated:</strong> <%= user != null ? user.getUpdatedAt() : "" %></p> --%>
        </div>

        <!-- Right: Update Forms -->
        <div class="card forms">
            <h3>Edit Profile</h3>
            <form action="updateProfile" method="post" class="form">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" 
                       value="<%= user != null ? user.getFullName() : "" %>" required>

                <label for="email">Email</label>
                <input type="email" id="email" name="email" 
                       value="<%= user != null ? user.getEmail() : "" %>" required>

                <%-- <label for="phoneNumber">Phone</label>
                <input type="tel" id="phoneNumber" name="phoneNumber" 
                       value="<%= user != null ? user.getPhoneNumber() : "" %>"> --%>

                <button type="submit" class="btn primary">Update Profile</button>
            </form>

            <hr/>

            <h3>Change Password</h3>
            <form action="changePassword" method="post" class="form">
                <label for="currentPassword">Current Password</label>
                <input type="password" id="currentPassword" name="currentPassword" required autocomplete="current-password">

                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" required autocomplete="new-password">

                <button type="submit" class="btn warning">Change Password</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
