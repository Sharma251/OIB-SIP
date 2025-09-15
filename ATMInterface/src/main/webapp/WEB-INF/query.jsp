<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRole().equals(User.Role.USER)) {
        response.sendRedirect("/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Submit Query | LMS</title>
<link rel="stylesheet" href="/query.css" />
</head>
<body>
<header class="topbar">
    <h1>Submit Query</h1>
    <nav>
        <a href="/user/userDashboard">Dashboard</a>
        <a href="/books/browseBook">Books</a>
        <a href="/user/reservations">My Reservations</a>
        <a href="/user/myIssues">My Issues</a>
        <form method="get" action="/logout" class="inline">
            <button type="submit">Logout</button>
        </form>
    </nav>
</header>

<main class="container">
    <section class="card">
        <h2>Contact Admin</h2>

        <form method="post" action="/user/submitQuery" class="query-form">
            <div class="form-group">
                <label for="to">To:</label>
                <input type="email" id="to" name="to" value="admin@library.com" readonly />
            </div>

            <div class="form-group">
                <label for="from">From:</label>
                <input type="text" id="from" name="from" value="<%= user.getName() %> (<%= user.getEmail() %>)" readonly />
            </div>
		<div class="complaint-container">
            <div class="form-group">
                <label for="subject">Subject:</label>
                <input type="text" id="subject" name="subject" required placeholder="Enter query subject" />
            </div>

            <div class="form-group">
                <label for="message">Message:</label>
                <textarea id="message" name="message" rows="8" required placeholder="Enter your query message here..."></textarea>
            </div>

            <button type="submit" class="btn">Send Query</button>
         	</div>   
	</form>
			
			    <c:if test="${not empty success}">
			        <p class="success">${success}</p>
			    </c:if>
			    <c:if test="${not empty error}">
			        <p class="error">${error}</p>
			    </c:if>   
	
	</section>
</main>
</body>
</html>
