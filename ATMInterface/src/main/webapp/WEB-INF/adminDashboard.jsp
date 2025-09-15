<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Admin Dashboard</title>
	<link rel="stylesheet" href="/adminDashboard.css" />
</head>
<body>
	<header class="topbar">
	<h1>Admin Dashboard</h1>
		<nav>
			<a href="/admin/adminDashboard">Home</a>
			<a href="/books">Books</a>
			<a href="/admin/issuedBooks">Issued Books</a>
			<a href="/admin/reports">Reports</a>
			<form method="get" action="/logout" class="inline"><button type="submit">Logout</button></form>
		</nav>
	</header>
	<main class="container grid">
		<section class="card stat">
			<h3>Total Books</h3>
			<p class="big">${totalBooks}</p>
		</section>
		<section class="card stat">
			<h3>Issued Today</h3>
			<p class="big">${issuedToday}</p>
		</section>
		<section class="card stat">
			<h3>Reservations</h3>
			<p class="big">${reservations}</p>
		</section>
		<section class="card stat">
			<h3>Overdues</h3>
			<p class="big">${overdues}</p>
		</section>
		<section class="card full">
			<h2>Quick Actions</h2>
			<div class="actions">
			<a class="btn" href="/books/add">Add Book</a>
			<a class="btn" href="/admin/issuedBooks">Issued Book</a>
			<a class="btn" href="/admin/history">History</a>
			
			</div>
		</section>
	</main>
</body>
</html>