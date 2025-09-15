<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Train Search</title>
    <link rel="stylesheet" href="trainSearchFormStyle.css" ></head>
<body>
<div class="container">
        <h2>Search for Trains</h2>
        
        <!-- If there is an error message, show it -->
        <%
        String errorMsg = (String) request.getAttribute("errorMessage");
        if (errorMsg != null) {
        %>
            <div class="error-msg"><%= errorMsg %></div>
        <%
        }
        %>
        
        <form action="trainSearchForm" method="get">
            <label for="trainNumber">Train Number or Name:</label><br/>
            <input type="text" id="trainNumber" name="trainNumber" placeholder="Enter train number or name" required />
            <br/><br/>
            <input type="submit" value="Search Train" />
        </form>
    </div>
</body>
</html>