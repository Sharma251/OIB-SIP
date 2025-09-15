<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>ATM Dashboard</title>
    <link rel="stylesheet" href="/dashboardStyle.css">   
</head>
<body>
    <div class="container">
        <div class="welcome">
            <h2>Welcome, ${user.name}!</h2>
        </div>
        <div class="balance">
            Current Balance: $${user.balance}
        </div>
        <div class="menu">
            <a href="/withdraw" class="menu-button">Withdraw</a>
            <a href="/deposit" class="menu-button">Deposit</a>
            <a href="/transfer" class="menu-button">Transfer</a>
            <a href="/transactions" class="menu-button">Transactions</a>
        </div>
        <div class="logout">
            <a href="/logout">Logout</a>
        </div>
    </div>
</body>
</html>
