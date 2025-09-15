<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transfer Money</title>
  	<link rel="stylesheet" href="/transferStyle.css">
</head>
<body>
    <div class="container">
        <h2>Transfer Money</h2>
        <div class="balance">
            Current Balance: $${user.balance}
        </div>
        
        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        
        <form action="/transfer" method="post">
            <div class="form-group">
                <label for="targetUserId">Recipient User ID:</label>
                <input type="text" id="targetUserId" name="targetUserId" required>
            </div>
            <div class="form-group">
                <label for="amount">Amount to Transfer:</label>
                <input type="number" id="amount" name="amount" step="0.01" min="0.01" required>
            </div>
            <button type="submit">Transfer</button>
        </form>
        
        <div class="back">
            <a href="/dashboard">← Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
