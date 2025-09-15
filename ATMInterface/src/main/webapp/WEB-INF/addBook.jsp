<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Add Book</title>
	<link rel="stylesheet" href="/addBook.css" />
</head>
<body>
<header class="topbar"><h1>Add Book</h1><nav><a href="/books">Back</a></nav></header>
<main class="container">
<section class="card">
<form class="grid" method="post" action="/books/add">
<label>Title<input type="text" name="title" required /></label>
<label>Author<input type="text" name="author" required /></label>
<label>Category<input type="text" name="category" /></label>
<label>Quantity<input type="number" name="quantity" min="1" value="1" required /></label>
<label>Status
<select name="status" required>
    <option value="AVAILABLE">Available</option>
    <option value="ISSUED">Issued</option>
    <option value="RESERVED">Reserved</option>
</select>
</label>
<button class="btn primary full" type="submit">Save Book</button>
</form>
</section>
</main>

</body>
</html>