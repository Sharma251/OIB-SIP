<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Forgot Password | LMS</title>
<link rel="stylesheet" href="/forgotPassword.css" />
</head>
<body>
<main class="auth-wrapper">
<section class="card">
<h1>Forgot Password</h1>
<form method="post" action="forgotPassword">
<label>Username Or Email
<input type="text" name="login" required placeholder="Enter your username or email" />
</label>
<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>
<c:if test="${not empty message}">
    <div class="success-message">${message}</div>
</c:if>
<c:if test="${not empty resetLink}">
    <div class="success-message">
        <a href="${resetLink}" class="link">Click here to reset your password</a>
    </div>
</c:if>
<button type="submit" class="btn primary">Next</button>
</form>
<a class="link" href="login">Back to Login</a>
</section>
</main>
</body>
</html>
