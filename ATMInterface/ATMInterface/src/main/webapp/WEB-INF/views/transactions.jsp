<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transaction History</title>
  	<link rel="stylesheet" href="/transactionStyle.css">
</head>
<body>
    <div class="container">
        <h2>Transaction History</h2>
        <div class="balance">
            Current Balance: $${user.balance}
        </div>
        
        <c:choose>
            <c:when test="${not empty transactions}">
                <table>
                    <thead>
                        <tr>
                            <th>Date & Time</th>
                            <th>Type</th>
                            <th>Amount</th>
                            <th>Target User</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="transaction" items="${transactions}">
                            <tr>
                                <%@ page import="java.time.ZoneId, java.util.Date" %>
                                <td>
                                    <%
                                        java.time.LocalDateTime ldt = (java.time.LocalDateTime) pageContext.getAttribute("transaction.timestamp");
                                        if (ldt != null) {
                                            Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
                                    %>
                                            <fmt:formatDate value="<%= date %>" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    <%
                                        } else {
                                    %>
                                            N/A
                                    <%
                                        }
                                    %>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${transaction.type == 'WITHDRAW'}">
                                            <span class="withdraw">Withdraw</span>
                                        </c:when>
                                        <c:when test="${transaction.type == 'DEPOSIT'}">
                                            <span class="deposit">Deposit</span>
                                        </c:when>
                                        <c:when test="${transaction.type == 'TRANSFER'}">
                                            <span class="transfer">Transfer</span>
                                        </c:when>
                                        <c:otherwise>
                                            ${transaction.type}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${transaction.type == 'WITHDRAW'}">
                                            -$${transaction.amount}
                                        </c:when>
                                        <c:when test="${transaction.type == 'DEPOSIT'}">
                                            +$${transaction.amount}
                                        </c:when>
                                        <c:when test="${transaction.type == 'TRANSFER'}">
                                            -$${transaction.amount}
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${not empty transaction.targetUserId}">
                                        ${transaction.targetUserId}
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="no-transactions">No transactions found.</div>
            </c:otherwise>
        </c:choose>
        
        <div class="back">
            <a href="/dashboard">← Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
