<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reset Password | LMS</title>
<link rel="stylesheet" href="/resetPassword.css" />
</head>
<body>
<main class="auth-wrapper">
<section class="card">
<h1>Reset Password</h1>
<form method="post" action="resetPassword">
<input type="hidden" name="token" value="${token}" />

<label>New Password
<input type="password" name="newPassword" required placeholder="Enter new password" />
</label>

<label>Confirm Password
<input type="password" name="confirmPassword" required placeholder="Confirm new password" />
</label>

<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>
<c:if test="${not empty message}">
    <div class="success-message">${message}</div>
</c:if>

<button type="submit" class="btn primary">Update Password</button>
</form>
<p class="muted"><a class="link" href="login">Back to Login</a></p>
</section>
</main>
</body>
</html>