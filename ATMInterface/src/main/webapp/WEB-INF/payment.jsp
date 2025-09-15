<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Transaction" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.Book" %>
<%@ page import="com.libraryManagement.LibraryManagement.entity.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment - Library Management</title>
	<link rel="stylesheet" href="/editBook.css" />
</head>
<body>
<header class="topbar">
    <h1>Payment Required</h1>
    <nav>
        <a href="/user/userDashboard">Dashboard</a>
        <a href="/user/myIssues">My Issues</a>
    </nav>
</header>
<main class="container">
<section class="card">
    <h2>Outstanding Fine Payment</h2>
    <%
        Long fine = (Long) session.getAttribute("pendingReturnFine");
        Transaction transaction = null;
        if (session.getAttribute("pendingReturnTransactionId") != null) {
            Long transactionId = (Long) session.getAttribute("pendingReturnTransactionId");
            // Note: In a real application, you'd fetch the transaction from the database
            // For now, we'll just display the fine amount
        }
    %>
    <div class="payment-details">
        <p><strong>Fine Amount:</strong> ₹<%= fine != null ? fine : "0" %></p>
        <p><strong>Reason:</strong> Late return of book</p>
        <p><strong>Payment Method:</strong> Online Payment</p>
    </div>

    <form class="payment-form" method="post" action="/user/processPayment">
        <input type="hidden" name="transactionId" value="<%= session.getAttribute("pendingReturnTransactionId") %>" />
        <input type="hidden" name="fineAmount" value="<%= fine %>" />

        <label>Card Number<input type="text" name="cardNumber" placeholder="1234 5678 9012 3456" required /></label>
        <label>Expiry Date<input type="text" name="expiryDate" placeholder="MM/YY" required /></label>
        <label>CVV<input type="text" name="cvv" placeholder="123" required /></label>
        <label>Card Holder Name<input type="text" name="cardHolderName" placeholder="John Doe" required /></label>

        <button class="btn primary full" type="submit">Pay ₹<%= fine %> & Complete Return</button>
    </form>

    <div class="payment-note">
        <p><em>Note: Payment processing is simulated. In a real application, this would integrate with a payment gateway.</em></p>
    </div>
</section>
</main>

</body>
</html>
