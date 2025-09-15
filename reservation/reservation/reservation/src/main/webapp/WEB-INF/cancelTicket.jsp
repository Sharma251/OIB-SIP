<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.reservation.reservation.model.TrainDetails" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cancel Ticket</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/cancelTicketStyle.css">
</head>
<body>
<div class="container">
    <h2>Cancel Ticket</h2>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        String successMessage = (String) request.getAttribute("successMessage");
        String infoMessage = (String) request.getAttribute("infoMessage");
        TrainDetails booking = (TrainDetails) request.getAttribute("booking");
    %>

    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
    <% } %>

    <% if (infoMessage != null) { %>
        <div class="info"><%= infoMessage %></div>
    <% } %>

    <% if (successMessage != null) { %>
        <div class="success"><%= successMessage %></div>
    <% } %>

    <% if (booking != null) { %>
        <form action="${pageContext.request.contextPath}/cancelTicket" method="post">
            <input type="hidden" name="pnr" value="<%= booking.getPnrNumber() %>" />
            <table>
                <tr><th>PNR</th><td><%= booking.getPnrNumber() %></td></tr>
                <tr><th>Train Number</th><td><%= booking.gettrainNumber() %></td></tr>
                <tr><th>Train Name</th><td><%= booking.getTrainName() %></td></tr>
                <tr><th>Passenger Name</th><td><%= booking.getPassengerName() %></td></tr>
                <tr><th>Status</th><td><%= booking.getStatus() %></td></tr>
            </table>
            <button type="submit">Confirm Cancellation</button>
        </form>
    <% } else { %>
        <p>Please enter a PNR number to search for your booking.</p>
        <form action="${pageContext.request.contextPath}/cancelTicket" method="get">
            <input type="text" name="pnr" placeholder="Enter PNR" required />
            <button type="submit">Search Booking</button>
        </form>
    <% } %>
</div>
</body>
</html>
