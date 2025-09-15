<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Register | LMS</title>
	<link rel="stylesheet" href="/register.css" />
</head>
	<body>
	<main class="auth-wrapper">
		<section class="card">
			<h1>Create Account</h1>
				<form method="post" action="register">
					<div class="grid">
						<label>Full Name
							<input type="text" name="name" required />
						</label>
						<label>Email
							<input type="email" name="email" required />
						</label>
						<label>Username
							<input type="text" name="username" required />
						</label>
						<label>Password
							<input type="password" name="password" required />
						</label>
						<label>Confirm Password
							<input type="password" name="confirmPassword" required />
						</label>
						<label>Role
							<select name="role">
								<option value="USER">User</option>
						<option value="ADMIN">Admin</option>
 							</select>
						</label>
					</div>
				<button type="submit" class="btn primary">Create Account</button>
				<p class="muted">Already have an account? <a class="link" href="login">Sign in</a></p>
				</form>
		</section>
	</main>
</body>
</html>