<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Advanced Login Form</title>
    <link rel="stylesheet" href="loginStyle.css">
    <!-- Font Awesome CDN for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <div class="bg-overlay"></div>

    <div class="login-wrapper">
        <form class="login-form" action="/login" method="post">
            <h2><i class="fas fa-user-circle"></i> Member Login</h2>

            <div class="input-group">
                <label for="username"><i class="fas fa-user"></i></label>
                <input type="text" id="username" name="username" placeholder="Enter username" required>
            </div>

            <div class="input-group">
                <label for="password"><i class="fas fa-lock"></i></label>
                <input type="password" id="password" name="password" placeholder="Enter password" required>
            </div>

            <div class="error-message" id="errorMsg"> <!-- JS can toggle this -->
                <!-- Example: Invalid credentials -->
            </div>

            <button type="submit" class="login-btn">Login</button>

            <div class="extra-options">
                <a href="/forgotPassword">Forgot Password?</a>
                <a href="/newAccount">Create an Account</a>
            </div>
        </form>
    </div>
</body>
</html>
