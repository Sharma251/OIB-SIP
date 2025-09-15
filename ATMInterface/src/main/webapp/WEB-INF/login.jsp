<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Login | LMS</title>
<link rel="stylesheet" href="/login.css" />
</head>
<body>
<main class="auth-wrapper">
<section class="card">
<h1>Library Login</h1>
<form method="post" action="login">
<label>Email or Username
<input type="text" name="login" required placeholder="you@example.com" />
</label>
<label>Password<input type="password" name="password" required placeholder="••••••••" />
</label>
<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>
<div class="row">
<label class="checkbox"><input type="checkbox" name="remember" /> Remember me</label>
<a class="link" href="forgotPassword">Forgot password?</a>
</div>
<button type="submit" class="btn primary">Sign In</button>
</form>
<p class="muted">New here? <a class="link" href="register">Create an account</a></p>
</section>
</main>
</body>
</html>